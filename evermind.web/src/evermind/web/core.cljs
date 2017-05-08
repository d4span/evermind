(ns evermind.web.core
 (:require-macros [cljs.core.async.macros :refer [go]])
 (:require [cljs.core.async :refer [put! chan <!]]
           [evermind.domain.core :as d]
           [om.core :as om :include-macros true]
           [om.dom :as dom :include-macros true]))

(enable-console-print!)

;; define your app data so that it doesn't get over-written on reload

(def init-mindmap
  (d/add-children
    (d/set-attributes (d/create-mindmap) {:text "Root node"})
    (d/add-children (d/set-attributes (d/create-node) {:text "Child 1"})
                    (d/set-attributes (d/create-node) {:text "Child 1-2"})
                    (d/set-attributes (d/create-node) {:text "Child 1-3"})
                    (d/set-attributes (d/create-node) {:text "Child 1-4"}))
    (d/add-children (d/set-attributes (d/create-node) {:text "Child 2"})
                    (d/set-attributes (d/create-node) {:text "Child 2-1"})
                    (d/set-attributes (d/create-node) {:text "Child 2-2"}))
    (d/add-children (d/set-attributes (d/create-node) {:text "Child 3"})
                    (d/set-attributes (d/create-node) {:text "Child 3-2"}))))

(defonce app-state (atom
                     {:text    "hello"
                      :mindmap init-mindmap}))

(defn node-to-string [node]
      (-> node :attributes :text))

(defn update-node-position
  [depth top bottom node]
  (update-in node [:visu]
    (fn [c] {:x (* depth 20)
             :y (+ top (/ (- bottom top) 2))})))

(defn update-node
  ([root]
   (update-node 0 0 0 100 root))
  ([depth index top bottom node]
   (let [newnode (update-node-position depth top bottom node)
         c (count (:children node))
         box-height (/ (- bottom top) c)
         d (inc depth)]
     (update-in newnode [:children]
       (fn [children]
           (map-indexed
             (fn [i c]
              (let [t (+ top (* box-height i))
                    b (+ t box-height)]
               (update-node d i t b c)))
             children))))))

(defn node-view [node owner]
  (reify
    om/IInitState
    (init-state [state]
      {:select (:select state)})
    om/IRenderState
    (render-state [this {:keys [select]}]
        (dom/g #js {:key (str "group-" (:key node))}
          (dom/text #js {:onClick (fn [e] (put! select node))
                         :fontSize "3pt"
                         :fill (if (-> node :attributes :selected) "red" "black")
                         :x (-> node :visu :x)
                         :y (-> node :visu :y)}
                    (-> node :attributes :text))
          (om/build-all node-view
                        (:children node)
                        {:init-state {:select select}
                         :key :id})))))

(defn update-node-selection [node selected]
  (if (== (-> node :id) (-> selected :id))
    (update-in node [:attributes]
               (fn [v]
                 (assoc v :selected true)))
    (update-in node [:attributes]
               (fn [v]
                 (assoc v :selected false)))))

(defn select-node [node selected]
  (let [updated-node (update-node-selection node selected)]
    (update-in updated-node [:children]
               (fn [children]
                 (mapv
                   (fn [c] (select-node c selected))
                   children)))))


(defn mindmap-view [data owner]
  (reify
    om/IInitState
    (init-state [_]
       {:select (chan)})
    om/IWillMount
    (will-mount [_]
       (let [select (om/get-state owner :select)]
         (go (loop []
               (let [selected (<! select)]
                  (om/transact! data [:mindmap]
                                (fn [m]
                                  (select-node m selected)))
                  (recur))))))
    om/IRenderState
    (render-state [this {:keys [select]}]
        (let [mindmap (update-node (:mindmap data))]
          (dom/div nil
            (dom/svg #js {:id "mindmap-svg" :viewBox "0 0 100 100"}
              (om/build node-view mindmap {:init-state {:select select}})))))))

(om/root mindmap-view app-state {:target (. js/document (getElementById "app"))})

(defn on-js-reload [])
;; optionally touch your app-state to force rerendering depending on
;; your application
;; (swap! app-state update-in [:__figwheel_counter] inc)


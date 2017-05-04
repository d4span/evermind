(ns evermind.web.core
 (:require [om.core :as om :include-macros true]
           [om.dom :as dom :include-macros true]
           [evermind.domain.core :as d]))

(enable-console-print!)

(println "This text is printed from src/evermind.web/core.cljs. Go ahead and edit it and see reloading in action.")

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

(defn position-children [depth top bottom children]
      (let [c (count children)
            box-height (/ (- bottom top) c)
            d (+ 1 depth)]
           (map-indexed (fn [i n]
                            (let [t (+ top (* box-height i))
                                  b (+ t box-height)]
                                 {:depth  d
                                  :node   n
                                  :x      (* d 10)
                                  :y      (+ t (/ (- b t) 2))
                                  :top    t
                                  :bottom b
                                  :text   (node-to-string n)}))
                        children)))

(defn node-view [node owner]
      (reify
        om/IRender
        (render [this]
                (dom/g #js {:key (str "group-" (:key node))}
                       (dom/text #js {:fill "black" :font-size "10px" :x (:x node) :y (:y node)}
                                 (:text node))
                       (om/build-all node-view
                                     (position-children (+ 1 (:depth node)) (:top node) (:bottom node) (:children (:node node))))))))

(defn mindmap-view [data owner]
      (reify
        om/IRender
        (render [this]
                (dom/div nil
                         (dom/svg #js {:id "mindmap-svg" :viewBox "0 0 100 100"}
                                  (om/build-all node-view (position-children 1 0 100 [(:mindmap data)])))))))

(om/root mindmap-view app-state {:target (. js/document (getElementById "app"))})

(defn on-js-reload [])
;; optionally touch your app-state to force rerendering depending on
;; your application
;; (swap! app-state update-in [:__figwheel_counter] inc)


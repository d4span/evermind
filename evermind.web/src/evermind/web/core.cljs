(ns evermind.web.core
  (:require-macros [cljs.core.async.macros :as asyncmacros])
  (:require [cljs.core.async :as async]
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
  [depth index top bottom node]
  (update-in node [:visu]
             (fn [c] {:x (* depth 20)
                      :y (+ top (/ (- bottom top) 2))
                      :index index})))

(defn update-node
  ([root]
   (update-node 0 0 0 100 root))
  ([depth index top bottom node]
   (let [newnode (update-node-position depth index top bottom node)
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
             (dom/text #js {:onClick (fn [e] (async/put! select node))
                            :fontSize "3pt"
                            :fill (if (-> node :attributes :selected) "red" "black")
                            :x (-> node :visu :x)
                            :y (-> node :visu :y)}
                       (-> node :attributes :text))
             (om/build-all node-view
                           (sort-by #(:index %) (:children node))
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

(defn is-selected
  [node] (-> node :attributes :selected))

(defn selected-node
  [tree]
  (d/reduce-tree
   tree
   #(is-selected %)))

(defn handle-left
  [data]
  (om/transact! data [:mindmap]
                (fn [m]
                  (let [selected (first (selected-node m))
                        parent (d/parent-node m selected)]
                    (if (nil? selected)
                      (select-node m m)
                      (if (not (nil? parent))
                        (select-node m parent)
                        m))))))

(defn handle-right
  [data]
  (om/transact! data [:mindmap]
                (fn [m]
                  (let [selected (first (selected-node m))
                        child (first (:children selected))]
                    (if (nil? selected)
                      (select-node m m)
                      (if (not (nil? child))
                        (select-node m child)
                        m))))))

(defn next-sibling
  [parent of]
  (let [n (reduce (fn [a c]
                    (if (= true a)
                      c
                      (if (of c)
                        true
                        a)))
                  nil
                  (:children parent))]
    (if (= true n)
      nil
      n)))

(defn handle-next-sibling
  [data]
  (om/transact! data [:mindmap]
                (fn [m]
                  (let [selected (first (selected-node m))
                        parent (d/parent-node m selected)
                        next-sibling (next-sibling parent is-selected)]
                    (if (nil? selected)
                      (select-node m m)
                      (if (not (nil? next-sibling))
                        (select-node m next-sibling)
                        m))))))

(defn previous-sibling
  [parent of]
  (second
   (reduce
    (fn [[a s] c]
      (if (is-selected c)
        [nil, a]
        (if (nil? s)
          [c, nil]
          [a, s])))
    [nil nil]
    (:children parent))))

(defn handle-previous-sibling
  [data]
  (om/transact! data [:mindmap]
                (fn [m]
                  (let [selected (first (selected-node m))
                        parent (d/parent-node m selected)
                        previous-sibling (previous-sibling parent is-selected)]
                    (if (nil? selected)
                      (select-node m m)
                      (if (not (nil? previous-sibling))
                        (select-node m previous-sibling)
                        m))))))

(defn handle-delete
  [data]
  (om/transact! data [:mindmap]
                (fn [m]
                  (let [selected (first (selected-node m))
                        parent (d/parent-node m selected)
                        next-sibling (next-sibling parent is-selected)
                        filtered (d/filter-tree
                                  m
                                  (fn [c] (not (is-selected c))))]
                    (if (nil? next-sibling)
                      (select-node filtered parent)
                      (select-node filtered next-sibling))))))

(defn handle-insert
  [data]
  (om/transact! data [:mindmap]
                (fn [m]
                  (d/map-tree
                   m
                   (fn [n]
                     (if (-> n :attributes :selected)
                       (let [node-text (.prompt js/window "Node content" "New node")]
                         (if node-text
                           (d/add-children n (d/set-attributes (d/create-node) {:text node-text}))
                           n))
                       n))))))

(defn handle-edit
  [data]
  (om/transact! data [:mindmap]
                (fn [m]
                  (d/map-tree
                   m
                   (fn [n]
                     (if (-> n :attributes :selected)
                       (let [node-text (.prompt js/window "Node content" (-> n :attributes :text))]
                         (if node-text
                           (d/set-attributes n (merge (-> n :attributes) {:text node-text}))
                           n))
                       n))))))

(defn handle-key-press [k data]
  (case k
    ; I
    73 data
    ; a
    97 (handle-insert data)
    ; d
    100 (handle-delete data)
    ; e
    101 (handle-edit data)
    ; h
    104 (handle-left data)
    ; i
    105 data
    ; j
    106 (handle-next-sibling data)
    ; k
    107 (handle-previous-sibling data)
    ; l
    108 (handle-right data)
    ; o
    111 data
    data))

(defn mindmap-view [data owner]
  (reify
    om/IInitState
    (init-state [_]
      {:select (async/chan)})
    om/IWillMount
    (will-mount [_]
      (let [select (om/get-state owner :select)]
        (asyncmacros/go (loop []
                          (let [selected (async/<! select)]
                            (om/transact! data [:mindmap]
                                          (fn [m]
                                            (select-node m selected)))
                            (recur))))))
    om/IRenderState
    (render-state [this {:keys [select]}]
      (let [mindmap (update-node (:mindmap data))]
        (dom/div #js {:tabIndex 0
                      :onKeyPress #(handle-key-press (.-charCode %) data)}
                 (dom/svg #js {:id "mindmap-svg"
                               :viewBox "0 0 100 100"}
                          (om/build node-view mindmap {:init-state {:select select}})))))))

(om/root mindmap-view app-state {:target (. js/document (getElementById "app"))})

(defn on-js-reload [])
;; optionally touch your app-state to force rerendering depending on
;; your application
;; (swap! app-state update-in [:__figwheel_counter] inc)


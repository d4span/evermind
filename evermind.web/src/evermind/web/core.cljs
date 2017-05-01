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
                 (d/set-attributes (d/create-node) {:text "Child 1-2"}))
    (d/set-attributes (d/create-node) {:text "Child 2"})
    (d/set-attributes (d/create-node) {:text "Child 3"})))

(defonce app-state (atom
                     {:text    "hello"
                      :mindmap init-mindmap}))

(defn node-to-string [node]
      (-> node :attributes :text))

(defn node-view [node owner]
      (reify
        om/IRender
        (render [this]
                (dom/li nil
                        (dom/h2 nil (node-to-string node))
                        (apply dom/ul nil
                               (om/build-all node-view (:children node)))))))

(defn mindmap-view [data owner]
      (reify
        om/IRender
        (render [this]
                (dom/div nil
                         (dom/h2 nil "Mind Map")
                         (dom/ul nil
                                 (om/build node-view (:mindmap data)))))))

(om/root mindmap-view app-state {:target (. js/document (getElementById "app"))})

(defn on-js-reload [])
;; optionally touch your app-state to force rerendering depending on
;; your application
;; (swap! app-state update-in [:__figwheel_counter] inc)


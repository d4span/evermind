(ns evermind.web.core
  (:require [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]
            [evermind.domain.core :as d]))

(enable-console-print!)

(println "This text is printed from src/evermind.web/core.cljs. Go ahead and edit it and see reloading in action.")

;; define your app data so that it doesn't get over-written on reload

(def init-mindmap
  (d/set-attributes (d/create-mindmap) {:text "Root node"}))

(defonce app-state (atom {:text "hello" :mindmap init-mindmap}))

(om/root
  (fn [data owner]
      (reify om/IRender
             (render [_]
                     (dom/div nil
                              (dom/h1 nil (-> data :mindmap :attributes :text))))))
  app-state
  {:target (. js/document (getElementById "app"))})

(defn on-js-reload [])
;; optionally touch your app-state to force rerendering depending on
;; your application
;; (swap! app-state update-in [:__figwheel_counter] inc)


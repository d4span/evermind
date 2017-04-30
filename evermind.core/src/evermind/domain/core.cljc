(ns evermind.domain.core
  (:require [clojure.set :as cljset]))



(defn create-node
  ([] {}))



(defn set-attributes
  ([node attributes]
   (assoc node :attributes attributes)))



(defn add-child
  ([node child]
   (assoc node :children (set (conj (:children node) child)))))



(defn remove-child
  ([node child]
   (assoc node :children (set (remove #(= % child) (:children node))))))



(defn filter-children
  ([node pred]
   (assoc node :children (set (filter pred (:children node))))))



(defn create-mindmap
  ([] (create-node)))


(ns evermind.domain.core
  (:require [clojure.set :as cljset]))



(defn create-node
  ([] {}))



(defn set-attributes
  ([node attributes]
   (assoc node :attributes attributes)))



(defn add-children
  ([node child]
   (assoc node :children (set (conj (:children node) child))))
  ([node child & more]
   (reduce add-children node (conj more child))))



(defn remove-child
  ([node child]
   (assoc node :children (set (remove #(= % child) (:children node))))))



(defn filter-children
  ([node pred]
   (assoc node :children (set (filter pred (:children node))))))



(defn create-mindmap
  ([] (create-node)))


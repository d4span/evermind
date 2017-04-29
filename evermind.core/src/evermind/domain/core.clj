(ns evermind.domain.core
  (:require [clojure.spec.alpha :as s]
            [clojure.spec.gen.alpha :as gen]))

(s/def ::node
  (s/keys :req-un []
          :opt-un [::parent ::children]))

(s/def ::parent ::node)

(s/def ::children (s/coll-of ::node :kind set?))


(defn create-node
  ([] {}))

(s/fdef create-node
        :args (s/cat)
        :ret ::node)



(defn create-mindmap
  ([] (create-node)))

(s/fdef create-mindmap
        :args (s/cat)
        :ret ::node)



(defn add-child
  ([parent child]
   (assoc parent :children (conj (:children parent) child))))
;;  ([node child pos]))

(s/fdef add-child
        :args (s/cat :parent ::node :child ::node)
        :ret ::node
        :fn #(contains? (-> % :ret :children) (-> % :args :child)))




(defn remove-child
  ([node child]))



(defn filter-children
  ([node pred]))
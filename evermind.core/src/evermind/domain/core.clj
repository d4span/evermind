(ns evermind.domain.core
    (:require [clojure.spec.alpha :as s]
      [clojure.set :as cljset]
      [clojure.spec.gen.alpha :as gen]))

(s/def ::node
  (s/keys :req-un []
          :opt-un [::parent ::children]))

(s/def ::parent ::node)

(s/def ::children (s/coll-of ::node :kind set? :into #{}))


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
      ([node child]
        (assoc node :children
          (cljset/union (:children node) #{child}))))

(s/fdef add-child
        :args (s/cat :node ::node :child ::node)
        :ret ::node
        :fn #(contains?
               (-> % :ret :children)
               (-> % :args :child)))




(defn remove-child
  ([node child]))



(defn filter-children
  ([node pred]))
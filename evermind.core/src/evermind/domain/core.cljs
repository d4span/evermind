(ns evermind.domain.core
  (:require [clojure.set :as cljset]
            [clojure.spec :as s]
            [cljs.spec.impl.gen :as gen]))



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

(s/def ::node
  (s/keys :req-un []
          :opt-un [::parent ::children ::attributes]))

(s/def ::parent ::node)

(s/def ::children (s/coll-of ::node :kind set? :into #{}))

(s/def ::text string?)

(s/def ::attributes
  (s/keys :req-un []
          :opt-un [::text]))

(s/def ::node-pred (s/with-gen (s/fspec :args (s/cat :node ::node) :ret boolean?)
                               #(gen/return
                                  (memoize
                                    (first
                                      (gen/sample
                                        (s/gen (s/fspec :args (s/cat :node ::node) :ret boolean?)) 1))))))



(s/fdef create-node
        :args (s/cat)
        :ret ::node)



(s/fdef set-attributes
        :args (s/cat :node ::node :attributes ::attributes)
        :ret ::node
        :fn #(= (-> % :ret :attributes)
                (-> % :args :attributes)))



(s/fdef add-children
        :args (s/cat :node ::node :child ::node)
        :ret ::node
        :fn #(contains?
               (-> % :ret :children)
               (-> % :args :child)))



(s/fdef remove-child
        :args (s/cat :node ::node :child ::node)
        :ret ::node
        :fn #(not
               (contains?
                 (-> % :ret :children)
                 (-> % :args :child))))



(s/fdef filter-children
        :args (s/cat :node ::node :pred ::node-pred)
        :ret ::node
        :fn #(and (every? false? (map (-> % :args :pred)
                                      (cljset/difference (-> % :args :node :children)
                                                         (-> % :ret :children))))
                  (every? true? (map (-> % :args :pred) (-> % :ret :children)))))



(s/fdef create-mindmap
        :args (s/cat)
        :ret ::node)

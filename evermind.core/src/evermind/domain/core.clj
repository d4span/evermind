(ns evermind.domain.core
    (:require [clojure.spec.alpha :as s]
      [clojure.set :as cljset]
      [clojure.spec.gen.alpha :as gen]))

(s/def ::node
  (s/keys :req-un []
          :opt-un [::parent ::children ::attributes]))

(s/def ::parent ::node)

(s/def ::children (s/coll-of ::node :kind set? :into #{}))

(s/def ::text string?)

(s/def ::attributes
  (s/keys :req-un []
          :opt-un [::text]))

(s/def ::pred-result boolean?)

(s/fdef ::pred
         :args (s/cat :node ::node)
         :ret ::pred-result)



(defn create-node
  ([] {}))

(s/fdef create-node
        :args (s/cat)
        :ret ::node)



(defn set-attributes
      ([node attributes]
        (assoc node :attributes attributes)))

(s/fdef set-attributes
        :args (s/cat :node ::node :attributes ::attributes)
        :ret ::node
        :fn #(= (-> % :ret :attributes)
                 (-> % :args :attributes)))



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
  ([node child]
    (assoc node :children
           (cljset/union
             #{}
             (cljset/difference (:children node) #{child})))))

(s/fdef remove-child
        :args (s/cat :node ::node :child ::node)
        :ret ::node
        :fn #(not
               (contains?
                 (-> % :ret :children)
                 (-> % :args :child))))



(defn filter-children
  ([node pred]
    (assoc node :children (cljset/union
                            #{}
                            (cljset/select pred (:children node))))))

(s/fdef filter-children
        :args (s/cat :node ::node :pred ::pred)
        :ret ::node
        :fn #(and
               (<=
                 (count (-> % :ret :children))
                 (count (-> % :args :node :children)))))



(defn create-mindmap
      ([] (create-node)))

(s/fdef create-mindmap
        :args (s/cat)
        :ret ::node)

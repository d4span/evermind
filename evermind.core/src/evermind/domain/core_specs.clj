(ns evermind.domain.core-specs
  (:require [evermind.domain.core :as d]
            [clojure.spec.alpha :as s]
            [clojure.spec.gen.alpha :as gen]
            [clojure.set :as cljset]
            ))

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
                                        (s/gen (s/fspec :args (s/cat :node ::node) :ret boolean?))
                                        1))))))



(s/fdef d/create-node
        :args (s/cat)
        :ret ::node)



(s/fdef d/set-attributes
        :args (s/cat :node ::node :attributes ::attributes)
        :ret ::node
        :fn #(= (-> % :ret :attributes)
                (-> % :args :attributes)))



(s/fdef d/add-children
        :args (s/cat :node ::node :child ::node)
        :ret ::node
        :fn #(contains?
               (-> % :ret :children)
               (-> % :args :child)))



(s/fdef d/remove-child
        :args (s/cat :node ::node :child ::node)
        :ret ::node
        :fn #(not
               (contains?
                 (-> % :ret :children)
                 (-> % :args :child))))



(s/fdef d/filter-children
        :args (s/cat :node ::node :pred ::node-pred)
        :ret ::node
        :fn #(and (every? false? (map (-> % :args :pred)
                                      (cljset/difference (-> % :args :node :children)
                                                         (-> % :ret :children))))
                  (every? true? (map (-> % :args :pred) (-> % :ret :children)))))



(s/fdef d/create-mindmap
        :args (s/cat)
        :ret ::node)

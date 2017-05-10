(ns evermind.domain.core-test
  (:require    [clojure.test :refer :all]
               [clojure.spec.test.alpha]
               [evermind.test-util]
               [evermind.domain.core]))

;(defn fixture [f]
;  (tu/instrument-namespaces)
;  (f))
;
;(use-fixtures :once fixture)

(deftest test-generate-id (evermind.test-util/check-fn-clj 'evermind.domain.core/generate-id 40))
(deftest test-create-node (evermind.test-util/check-fn-clj 'evermind.domain.core/create-node 40))
(deftest test-set-attributes (evermind.test-util/check-fn-clj 'evermind.domain.core/set-attributes 40))
(deftest test-add-children (evermind.test-util/check-fn-clj 'evermind.domain.core/add-children 40))
(deftest test-remove-child (evermind.test-util/check-fn-clj 'evermind.domain.core/remove-child 40))
(deftest test-filter-children (evermind.test-util/check-fn-clj 'evermind.domain.core/filter-children 20))
(deftest test-create-mindmap (evermind.test-util/check-fn-clj 'evermind.domain.core/create-mindmap 40))
(ns evermind.domain.core-test
  (:require [cljs.test :refer [deftest is use-fixtures]]
            [cljs.spec.test]
            [evermind.domain.core])
  (:require-macros [cljs.spec.test :refer [check]]
                   [evermind.test-util]))

(deftest test-create-node (evermind.test-util/check-fn-cljs 'evermind.domain.core/create-node 10))
(deftest test-set-attributes (evermind.test-util/check-fn-cljs 'evermind.domain.core/set-attributes 10))
(deftest test-add-children (evermind.test-util/check-fn-cljs 'evermind.domain.core/add-children 10))
(deftest test-remove-child (evermind.test-util/check-fn-cljs 'evermind.domain.core/remove-child 10))
;(deftest test-filter-children (evermind.test-util/check-fn-cljs 'evermind.domain.core/filter-children 5))
(deftest test-create-mindmap (evermind.test-util/check-fn-cljs 'evermind.domain.core/create-mindmap 10))

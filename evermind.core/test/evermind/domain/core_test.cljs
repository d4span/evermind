(ns evermind.domain.core-test
  (:require
            ;#?(:clj  [clojure.test :refer :all]
            ;   :cljs [cljs.test :refer [deftest is use-fixtures]])
            [cljs.spec.test]
            [cljs.test :refer [deftest is use-fixtures]]
            [evermind.domain.core-specs]
            [evermind.domain.core])
  (:require-macros [cljs.spec.test :refer [check]]
                   [evermind.test-util]))

;(defn fixture [f]
;  (tu/instrument-namespaces)
;  (f))
;
;(use-fixtures :once fixture)


(deftest test-create-node (evermind.test-util/check-fn-cljs 'evermind.domain.core/create-node 40))


(deftest test-set-attributes (evermind.test-util/check-fn-cljs 'evermind.domain.core/set-attributes 40))


(deftest test-add-children (evermind.test-util/check-fn-cljs 'evermind.domain.core/add-children 40))


(deftest test-remove-child (evermind.test-util/check-fn-cljs 'evermind.domain.core/remove-child 40))


(deftest test-filter-children (evermind.test-util/check-fn-cljs 'evermind.domain.core/filter-children 20))


(deftest test-create-mindmap (evermind.test-util/check-fn-cljs 'evermind.domain.core/create-mindmap 40))

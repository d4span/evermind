(ns evermind.domain.core-test
  (:require
            ;#?(:clj  [clojure.test :refer :all]
            ;   :cljs [cljs.test :refer [deftest is use-fixtures]])
            [cljs.test :refer [deftest is use-fixtures]]
            [evermind.domain.core-specs]
            [evermind.domain.core]
            [evermind.test-util :as tu]))

;(defn fixture [f]
;  (tu/instrument-namespaces)
;  (f))
;
;(use-fixtures :once fixture)


(deftest test-create-node
         (is (tu/check-sym 'evermind.domain.core/create-node)))


(deftest test-set-attributes
         (is (tu/check-sym 'evermind.domain.core/set-attributes)))


(deftest test-add-children
         (is (tu/check-sym 'evermind.domain.core/add-children)))


(deftest test-remove-child
         (is (tu/check-sym 'evermind.domain.core/remove-child)))


(deftest test-filter-children
         (is (tu/check-sym 'evermind.domain.core/filter-children {;#?(:clj  :clojure.spec.test.check/opts
                                                              ;   :cljs :clojure.test.check/opts
                                                                  :clojure.test.check/opts {:num-tests 20}})))


(deftest test-create-mindmap
         (is (tu/check-sym 'evermind.domain.core/create-mindmap)))

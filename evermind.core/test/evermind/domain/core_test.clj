(ns evermind.domain.core-test
  (:require [clojure.test :refer :all]
            [evermind.domain.core :refer :all]
            [clojure.spec.test.alpha :as stest]
            [evermind.test-util :as tu]))

(defn fixture [f]
  (tu/instrument-namespaces)
  (f))

(use-fixtures :once fixture)


(deftest test-create-node
         (is (tu/check 'evermind.domain.core/create-node)))


(deftest test-set-attributes
         (is (tu/check 'evermind.domain.core/set-attributes)))


(deftest test-add-child
         (is (tu/check 'evermind.domain.core/add-child)))


(deftest test-remove-child
  (is (tu/check 'evermind.domain.core/remove-child)))


(deftest test-create-mindmap
         (is (tu/check 'evermind.domain.core/create-mindmap)))

(ns evermind.domain.core-test
  (:require [clojure.test :refer :all]
            [evermind.domain.core :refer :all]
            [clojure.spec.test.alpha :as stest]
            [evermind.test-util :as tu]))

(defn fixture [f]
  (tu/instrument-namespaces)
  (f))

(use-fixtures :once fixture)


(deftest test-create-mindmap
  (is (tu/check 'evermind.domain.core/create-mindmap)))


(deftest test-create-node
  (is (tu/check 'evermind.domain.core/create-node)))


(deftest test-add-child
  (is (tu/check 'evermind.domain.core/add-child)))
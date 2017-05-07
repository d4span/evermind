(ns evermind.test-util)
  ;(:require [cljs.spec.test :as t :refer [check]]))
  ;(:require-macros [cljs.spec.test :as t]))
            ;[clojure.test.check]))
  ;#?@(:clj [
  ;          (:require [clojure.spec.test.alpha :as st])
  ;          (:import (clojure.lang Namespace))]))

(def test-iterations 40)

;#?(:clj (defn instrument-namespaces []
;          (let [syms (map (fn [^Namespace ns] (.getName ns)) (all-ns))]
;            (map #(-> (st/enumerate-namespace %) st/instrument) syms))))

(defn check-sym
  ([sym]
   (check-sym sym {:clojure.test.check/opts {:num-tests test-iterations}}))
               ;#?(:clj  :clojure.spec.test.check/opts
               ;   :cljs :clojure.test.check/opts
  ([sym params]
   (let [outcome (cljs.spec.test/check sym params)
                  ;#?(:clj st/check
                  ;   :cljs cljs.spec.test/check
         data    (-> outcome first)
         failure (:failure data)
         result  (nil? failure)]
     ;(if data
     ;  (println sym (:clojure.spec.test.check/ret data)))
     (if (empty? outcome)
       (println "No spec found for" sym))
     (and result (not (empty? outcome))))))

(defproject evermind.core "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}

  :plugins [[lein-modules "0.3.11"]
            [cljsbuild "1.1.6"]
            [lein-doo "0.1.7" :exclusions [[org.clojure/clojurescript]]]]

  :dependencies [[org.clojure/clojure "_"]
                 [org.clojure/clojurescript "_"]]

  :profiles {:dev {:dependencies [[evermind.test-util :version]]
                   :cljsbuild {:builds [{:id           "test"
                                         :source-paths ["src" "test"]
                                         :compiler     {:main          evermind.runner
                                                        :optimizations :none
                                                        :output-to     "target/tests.js"
                                                        :output-dir    "target"
                                                        :pretty-print  true
                                                        :target        :nodejs}}]}}})
(defproject evermind "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}

  :plugins [[lein-modules "0.3.11"]]

  :modules {:versions {org.clojure/clojure                          "1.9.0-alpha16"
                       org.clojure/clojurescript                    "1.9.521"
                       com.gfredericks.forks.org.clojure/test.check "0.10.0-PREVIEW-1"}}

  :profiles {:cljs-dev {:dependencies [[org.clojure/test.check "0.9.0"]
                                       [org.clojure/clojurescript "_"]
                                       [org.clojure/clojure "_"]]

                        :cljsbuild    {:builds [{:id           "doo"
                                                 :source-paths ["src" "test"]
                                                 :compiler     {:main          evermind.runner
                                                                :optimizations :none
                                                                :output-to     "target/tests.js"
                                                                :output-dir    "target"
                                                                :pretty-print  true
                                                                :target        :nodejs}}]}}

             :dev {:dependencies [[com.gfredericks.forks.org.clojure/test.check "_"]]}

             :clojurescript {:dependencies [[org.clojure/clojurescript "_"]]

                             :prep-tasks   ["compile" ["cljsbuild" "once"]]

                             :cljsbuild    {:builds [{:source-paths ["src"]
                                                      :jar          true
                                                      :compiler     {:output-to     "target/main.js"
                                                                     :optimizations :whitespace
                                                                     :pretty-print  true}}]}}})
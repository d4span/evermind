(defproject evermind.core "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :plugins [[lein-modules "0.3.11"]
            [lein-cljsbuild "1.1.6"]]

  :profiles {:dev {:dependencies [[evermind.test-util :version]]}}

  :cljsbuild {
              :builds [{
                        ; The path to the top-level ClojureScript source directory:
                        :source-paths ["src" "test"]
                        ; The standard ClojureScript compiler options:
                        ; (See the ClojureScript compiler documentation for details.)
                        :compiler {
                                   :optimizations :none
                                   :pretty-print true}}]}

  :hooks [leiningen.cljsbuild]

  :dependencies [[org.clojure/clojure "_"]
                 [org.clojure/clojurescript "_"]
                 [evermind.test-util :version]])
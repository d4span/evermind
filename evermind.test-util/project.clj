(defproject evermind.test-util "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :plugins [[lein-modules "0.3.11"]
            [lein-cljsbuild "1.1.6"]]

  :cljsbuild {
              :builds [{
                        ; The path to the top-level ClojureScript source directory:
                        :source-paths ["src"]
                        ; The standard ClojureScript compiler options:
                        ; (See the ClojureScript compiler documentation for details.)
                        :compiler {
                                   :optimizations :whitespace
                                   :pretty-print true}}]}

  :hooks [leiningen.cljsbuild]

  :profiles {:uberjar {:aot :all
                       :prep-tasks ["compile" ["cljsbuild" "once"]]}}

  :dependencies [[org.clojure/clojure "_"]
                 [org.clojure/clojurescript "_"]])

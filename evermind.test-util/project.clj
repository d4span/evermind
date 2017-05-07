(defproject evermind.test-util "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}

  :plugins [[lein-modules "0.3.11"]
            [lein-cljsbuild "1.1.6"]]

  :dependencies [[org.clojure/clojurescript "_"]
                 [org.clojure/clojure "_"]]

  :prep-tasks ["compile" ["cljsbuild" "once"]]

  :cljsbuild {:builds [{:source-paths ["src"]
                        :jar          true
                        :compiler     {:output-to     "target/main.js"
                                       :optimizations :whitespace
                                       :pretty-print  true}}]})
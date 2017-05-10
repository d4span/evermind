(defproject evermind.core "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}

  :plugins [[lein-modules "0.3.11"]
            [lein-cljsbuild "1.1.6"]
            [lein-doo "0.1.7" :exclusions [[org.clojure/clojurescript]]]]

  :dependencies [[org.clojure/clojure "_"]
                 [cljs-uuid "0.0.4"]]

  :profiles {:dev {:dependencies [[evermind.test-util :version]]}})
(defproject evermind "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}

  :plugins [[lein-modules "0.3.11"]]

  :modules {:versions {org.clojure/clojure       "1.9.0-alpha16"
                       org.clojure/clojurescript "1.9.521"
                       org.clojure/test.check    "0.9.0"}}

  :main ^:skip-aot evermind.core
  :target-path "target/%s"

  :profiles {:uberjar {:aot :all}
             :dev     {:dependencies [[org.clojure/test.check "_"]]}})


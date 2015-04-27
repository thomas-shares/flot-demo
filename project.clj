(defproject flot "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [jarohen/chord "0.4.2" :exclusions [org.clojure/clojure]]
                 [ring/ring-core "1.2.0"]
                 [compojure "1.1.5"]
                 [hiccup "1.0.5"]
                 [org.clojure/core.async "0.1.301.0-deb34a-alpha"]
                 [org.clojure/clojurescript "0.0-2411"]
                 [jayq "2.5.2"]]
  :main ^:skip-aot flot.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}}

  :plugins [[lein-cljsbuild "1.0.4-SNAPSHOT"]]

  :cljsbuild {
    :builds [{:source-paths ["src-cljs"]
              :compiler {
                :output-to "resources/public/js/flot.js"
                ;;:output-dir ""
               :optimizations :whitespace
                :pretty-print true}}]})

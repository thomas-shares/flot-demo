(defproject flot "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [jarohen/chord "0.7.0" :exclusions [org.clojure/clojure]]
                 [ring/ring-core "1.4.0"]
                 [compojure "1.5.0"]
                 [hiccup "1.0.5"]
                 [org.clojure/core.async "0.2.374"]
                 [org.clojure/clojurescript "1.7.228"]
                 [ring/ring-jetty-adapter "1.1.0"]
                 [jayq "2.5.4"]]
  :main ^:skip-aot flot.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}
             :dependencies [[javax.servlet/servlet-api "2.5"]]}

  :plugins [[lein-cljsbuild "1.1.3"]]

  :cljsbuild {
              :builds [{:source-paths ["src-cljs"]
                        :compiler {
                                   :output-to "resources/public/js/flot.js"
                                   :optimizations :advanced
                                   :pretty-print true}}]})

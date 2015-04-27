(ns flot.core
  (:require [org.httpkit.server :as server]
            [chord.http-kit :refer [with-channel wrap-websocket-handler]]
            [ring.util.response :refer [response]]
            [clojure.core.async :refer [<! >! put! close! go-loop timeout]]
            [compojure.route :only [not-found] :as route]
            [compojure.handler :only [site] :as  handler]
            [compojure.core :only [defroutes POST GET ] :as comp]
            [hiccup.page :refer [html5 include-js]]
            [hiccup.element :as element])
  (:gen-class))

(defn page-frame []
  (html5
    [:head
      [:title "Chord Example"]
      (include-js "/js/jquery-1.8.2.js")
      (include-js "/js/jquery.flot.js" )
      (include-js "/js/jquery.flot.time.js")]
      (include-js "/js/flot.js")
        [:body
          (element/javascript-tag "goog.require('flot.core');" )
          (element/javascript-tag "flot.core.init();")
          [:center
            [:div {:id "placeholder" :style "width:850px;height:425px"}]]]))

(defn ws-handler [{:keys [ws-channel] :as req}]
  (println "Opened connection from" (:remote-addr req))
  (go-loop [previous 50]
      (let [new1 (+ previous (- (* (rand) 10) 5) )
            new2 (if (neg? new1)
                   0
                   new1)
            new3 (if (> new2 100)
                   100
                   new2)]
        (>! ws-channel new3 )
        (<! (timeout 40))
        (recur new3))))

(comp/defroutes all-routes
    (comp/GET "/" [] (response (page-frame)))
    (comp/GET "/ws" [] (-> ws-handler
                           (wrap-websocket-handler {:format :json-kw})) )
    (route/resources "/")
    (route/not-found "<p>Page not found.</p>"))

(defn -main []
  (server/run-server all-routes {:port 8080}))

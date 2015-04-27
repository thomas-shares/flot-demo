(ns flot.core
  (:use [jayq.core :only [$]] )
  (:require-macros [cljs.core.async.macros :refer [go-loop go]])
  (:require [goog.dom :as dom]
            [goog.events :as events]
            [cljs.core.async :refer [put! chan <!]]
            [chord.client :refer [ws-ch]] ))

(enable-console-print!)

(defn options []
  (clj->js { :grid
               {:borderWidth 1
                :minBorderMargin 20
                :labelMargin 10
                :backgroundColor {:colors ["#fff", "#e4f4f4"]}
                :margin {:top 8
                         :bottom 20
                         :left 20}}
             :xaxis {:tickFormatter ""}
             :yaxis {:min 0
                     :max 110}
             :legend {:show false}}))


(defn add-msg [msgs new-msg]
  ;; we keep the most recent 425 messages
  (->> (conj msgs new-msg)
       (take 425)))

(defn receive-msgs! [!msgs server-ch]
  ;; every time we get a message from the server, add it to our list
  (go-loop []
    (when-let [msg (<! server-ch)]
      (swap! !msgs add-msg (:message  msg))
      (let [plot-data (vec (map vec (partition 2 (interleave (range) @!msgs ))))]
        (.plot js/jQuery ($ "#placeholder") (clj->js [ plot-data ]) (options)))
      (recur))))

(defn ^:export init []
  (println "this is the init function...." )
  (go
          (let [{:keys [ws-channel error]} (<! (ws-ch "ws://localhost:8080/ws" {:format :json-kw}))]
            (if error
              ;; connection failed, print error
              (println "error " error)
              (let [!msgs (doto (atom [])
                            (receive-msgs! ws-channel))
                    plot-data !msgs
                    ]
                (println plot-data)
                (.plot js/jQuery ($ "#placeholder") (clj->js [plot-data]) (options )))))))

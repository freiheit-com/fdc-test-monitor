(ns ftm.core
  (:use ftm.component.bar-chart ftm.component.text-header)
  (:require [lanterna.screen :as s]
            [lanterna.terminal :as t]
            [ftm.config :as config]
            [clj-http.client :as client]
            [cheshire.core :as cheshire]
            [clojure.core.async :as go])
  (:gen-class))

;TODO Add refresh date to bottom bar

;TODO output project-info -> :info <project-name> print info to info panel!
;TODO test-change-ticker (like a stock ticker with coverage data changes)

(def +refresh-interval+ (* 60 60 1000)) ;1 hour

(defn- load-projects []
  (let [projects (client/get (str (config/statistic-server-url) "/meta/projects")
                             {:insecure? true
                              :content-type :json
                              :headers {"auth-token" (config/meta-auth-token)}})]
    (-> projects :body (cheshire/parse-string true) :projects)))

(defn- load-coverage-for-project [project]
  (let [project-data (cheshire/parse-string (:body (client/get (str (config/statistic-server-url) "/statistics/coverage/latest/" (:project project))
                                                      {:insecure? true
                                                       :content-type :json
                                                       :headers {"auth-token" (config/statistics-auth-token)}})) true)]
    (assoc (:overall-coverage project-data) :project (:project project))))

(defn- load-coverage [projects]
   (doall (map load-coverage-for-project projects)))

(defn- load-data []
  (-> (load-projects) (load-coverage)))

(defn- refresh-screen [scr]
  (s/clear scr)

  (s/move-cursor scr 0 0)
  (text-header scr)

  (s/move-cursor scr 0 7)
  (bar-chart scr (load-data))

  (s/move-cursor scr 0 0)

  (s/redraw scr))

(defn- read-input [scr]
  ;a stub atm, read any input and throw it away
  (s/get-key-blocking scr)
  (recur scr))

(defn -main [& args]
  (def scr (s/get-screen))

  (s/start scr)

  (go/go-loop []
    (do (refresh-screen scr)
        (Thread/sleep +refresh-interval+)
        (recur)))

  (read-input scr)
  (s/stop scr))

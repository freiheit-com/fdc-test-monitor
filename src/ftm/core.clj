(ns ftm.core
  (:use ftm.component.bar-chart)
  (:require [lanterna.screen :as s]
            [lanterna.terminal :as t]
            [ftm.config :as config]
            [clj-http.client :as client]
            [cheshire.core :as cheshire])
  (:gen-class))


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

(defn -main [& args]
  (def scr (s/get-screen))

  (s/start scr)

  ;(println (s/get-size scr))

  (s/move-cursor scr 0 5)
  (bar-chart scr (load-data))
  ;(bar-chart scr [{:percentage 0.11 :project "zzz"}
  ;                {:percentage 0.501 :project "test1"} {:percentage 0.77 :project "test2"}
  ;                {:percentage 1 :project "test3"} {:percentage 0.23 :project "test4"}
  ;                {:percentage 0.37 :project "test5-with-very-long-name"})
  (s/move-cursor scr 0 0)

  (s/redraw scr)
  (s/get-key-blocking scr)

  (s/stop scr))

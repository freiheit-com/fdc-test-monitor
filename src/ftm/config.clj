(ns ftm.config
  (:require [clojure.edn :as edn]))

(def ^:private +config-file+ ".fdc-test-monitor-config")

(defn- read-config []
  (let [config-file (new java.io.File (str (System/getenv "HOME") "/" +config-file+))]
    (if (not (.exists config-file))
      {}
      (edn/read-string (slurp config-file)))))

(def ^:private ^:dynamic *fdc-test-monitor-config* (read-config))

(def statistic-server-url (partial *fdc-test-monitor-config* :statistic-server-url))

(defn meta-auth-token []
  (-> *fdc-test-monitor-config* :auth-tokens :meta))

(defn statistics-auth-token []
  (-> *fdc-test-monitor-config* :auth-tokens :statistics))

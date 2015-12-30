(ns ftm.component.bar-chart
  (:use ftm.common)
  (:require [lanterna.screen :as s] [clojure.pprint :as pprint] [cheshire.core :as cheshire]))

(def +chart-height+ 10)

;TODO encode status in chart
; stale -> gray (coverage data older than x days)

;TODO compare old to current (30 days ago default)
;TODO output project-info -> :info <project-name> print info to info panel!

;TODO Repsonsive View, use free space as good as possible

(defn- project-legend [scr col row-start text]
  (let [cut-text (take +chart-height+ text)]
    (doseq [i (range 0 (count cut-text))]
      (s/put-string scr col (+ 1 (- row-start (- (count cut-text) i))) (str (.charAt text i))))))

(defn- status-colour [percentage]
  (cond (<= percentage 0.3) :red
        (<= percentage 0.85) :yellow
        :else :green))

(defn- percentage-legend [scr col row-start percentage]
  (s/put-string scr col (+ 1 row-start) (pprint/cl-format nil "~,1f" (* 100 percentage)) {:fg (status-colour percentage)}))

(defn- fragment [percentage]
  (let [rst (mod percentage 0.1)]
    (cond (<= rst 0.0001) " "
          (<= rst 0.0125) "\u2581"
          (<= rst 0.025) "\u2582"
          (<= rst 0.0375) "\u2583"
          (<= rst 0.05) "\u2584"
          (<= rst 0.0625) "\u2585"
          (<= rst 0.075) "\u2586"
          (<= rst 0.0875) "\u2587"
          :else "\u2588")))

(defn- bar [scr col row-start percentage]
  (let [full (int (/ percentage  0.1))
        style {:fg (status-colour percentage)}]
    (doseq [r (range row-start (- (- row-start full) 1) -1)]
      (s/put-string scr col r "\u2588" style))
    (s/put-string scr col (- row-start full) (fragment percentage) style)))

(defn- bar-with-legend [scr [start-col start-row] col project-data]
  (let [bottom-row (+ start-row +chart-height+)]
    (project-legend scr col bottom-row (:project project-data))
    (bar scr (+ 1 col) bottom-row (:percentage project-data))
    (percentage-legend scr col bottom-row (:percentage project-data))
    (+ col 5)))

(defn bar-chart [scr data-raw]
  (let [data (sort-by :project data-raw)
        start-pos (get-cursor-position scr)
        [col row] start-pos
        bottom-row (+ row +chart-height+)]
    (reduce (partial bar-with-legend scr start-pos) col data)))

(ns ftm.component.bar-chart
  (:require [lanterna.screen :as s]))

(def +chart-height+ 10)

;TODO encode status in chart, < 0.3 -> red, 0.3 - 0.85 yellow, > 0.85 yellow
; stale -> other color (coverage data older than x days)

;TODO compare old to current (30 days ago default)

;TODO output project-info -> :info <project-name> print info to info panel!

(defn- legend [scr col row-start text]
  (doseq [i (range 0 (count text))]
    (s/put-string scr col (- row-start (- (count text) i)) (str (.charAt text i)))))

;TODO fragment bar elements (mod percentage 0.1)
(defn- bar [scr col row-start percentage]
  (let [full (int (/ percentage  0.1))]
    (doseq [r (range (- (+ row-start 1) full) row-start)]
      (s/put-string scr col r "\u2588"))))

(defn- bar-with-legend [scr [start-col start-row] col project-data]
  (let [bottom-row (+ start-row +chart-height+)]
    (legend scr col bottom-row (:project project-data))
    (bar scr (+ 1 col) bottom-row (:percentage project-data))
    (+ col 4)))

(defn bar-chart [scr data]
  (let [raw-size (.getCursorPosition scr); TODO rewrite with clojure-lanterna 0.9.5 release
        col (.getColumn raw-size)
        row (.getRow raw-size)
        start-pos [col row]
        bottom-row (+ row +chart-height+)]
    (reduce (partial bar-with-legend scr start-pos) col data)))

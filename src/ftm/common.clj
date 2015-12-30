(ns ftm.common)

; TODO rewrite with clojure-lanterna 0.9.5 release -> pull-request
(defn get-cursor-position [scr]
  (let [raw-size (.getCursorPosition scr); TODO rewrite with clojure-lanterna 0.9.5 release
        col (.getColumn raw-size)
        row (.getRow raw-size)]
    [col row]))

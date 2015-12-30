(ns ftm.component.text-header
  (:use ftm.common)
  (:require [lanterna.screen :as s]))
                                                                             
;TODO Centre
(defn text-header [scr]
  (let [[col row] (get-cursor-position scr)]
    (s/put-string scr col row       "  __    _             _            _                                _ _")
    (s/put-string scr col (+ 1 row) " / _|  | |           | |          | |                              (_) |)")
    (s/put-string scr col (+ 2 row) "| |_ __| | ___ ______| |_ ___  ___| |_ ______ _ __ ___   ___  _ __  _| |_ ___  _ __")
    (s/put-string scr col (+ 3 row) "|  _/ _` |/ __|______| __/ _ \\/ __| __|______| '_ ` _ \\ / _ \\| '_ \\| | __/ _ \\| '__|")
    (s/put-string scr col (+ 4 row) "| || (_| | (__       | ||  __/\\__ \\ |_       | | | | | | (_) | | | | | || (_) | |))")
    (s/put-string scr col (+ 5 row) "|_| \\__,_|\\___|       \\__\\___||___/\\__|      |_| |_| |_|\\___/|_| |_|_|\\__\\___/|_|")))

(ns ftm.core
  (:require [lanterna.screen :as s] [lanterna.terminal :as t])
  (:gen-class))


(defn -main [& args]
  (def scr (s/get-screen))
  (s/start scr)

  (s/put-string scr 10 10 "\u2581 \u2582 \u2583 \u2585 \u2586 \u2587 \u2589 \u258A \u258B  - \u2588 \u2584  - \u2518 \u256B \uD83D"
          {:fg :red :bg :green})
  (s/put-string scr 10 11 (str "\u2589 \u2588" (char 219)) {:fg :red :bg :green})
  (s/put-string scr 10 12 "Press any key to exit!" {:fg :red :bg :green})
  (s/redraw scr)
  (s/get-key-blocking scr)

  (s/stop scr))

(defproject fdc-test-monitor "0.1.0"
  :description "test monitoring ui"
  :url "https://github.com/freiheit-com/fdc-test-monitor"
  :license {:name "GPLv3"
            :url "https://www.gnu.org/licenses/agpl-3.0.html"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [clojure-lanterna "3.0.0-beta1"]
                 [clj-http "2.0.0"]
                 [cheshire "5.5.0"]
                 [org.clojure/math.numeric-tower "0.0.4"]
                 [org.clojure/core.async "0.2.374"]]
  :aot :all
  :main ftm.core)

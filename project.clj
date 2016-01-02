(defproject fdc-test-monitor "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [clojure-lanterna "3.0.0-beta1"]
                 [clj-http "2.0.0"]
                 [cheshire "5.5.0"]
                 [org.clojure/math.numeric-tower "0.0.4"]
                 [org.clojure/core.async "0.2.374"]]
  :aot :all
  :main ftm.core)

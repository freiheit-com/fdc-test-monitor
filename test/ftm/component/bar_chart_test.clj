(ns ftm.component.bar-chart-test
  (:require [clojure.test :refer :all] [ftm.component.bar-chart :refer :all]))

(deftest should-calc-bar-width-even-spaced-if-enough-bars
  (is (= (#'ftm.component.bar-chart/bar-width 120 6) 17)))

(deftest should-round-bar-width-to-nearest-int
  (is (= (#'ftm.component.bar-chart/bar-width 113 6) 15)))

(deftest should-cal-bar-width-as-max-one-quarter-of-screen-width
  (is (= (#'ftm.component.bar-chart/bar-width 100 1) 25)))

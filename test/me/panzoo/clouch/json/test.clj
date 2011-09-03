(ns me.panzoo.clouch.json.test
  (:use
    me.panzoo.clouch.json
    [clojure.test :only (deftest is)])
  (:require
    [clojure.string :only (replace)]))

(deftest json
  (defn jobj [^String s]
    (org.json.JSONObject. s))
  (let [x "{\"a\": 1, \"b\": true, \"c\": [1, true, \"d\"], \"e\": {\"f\": [{}]}}"
        a (jobj x)
        b (map<- a)
        c (jobj<- b)
        d (map<- c)]
    (is (= b d {"a" 1, "b" true, "c" [1 true "d"], "e" {"f" [{}]}}))))

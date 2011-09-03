(ns me.panzoo.clouch.EvalView.test
  (:use
    me.panzoo.clouch.EvalView
    me.panzoo.clouch.json
    [clojure.test :only (deftest is)]))

(deftest mapreduce
  (let [ml (me.panzoo.clouch.EvalView.)]
    (.Configure ml
      (str
        '{:map (fn [{length "length"} & _]
                 (when length [[nil length]]))
          :reduce (fn [seq & _]
                    (if (empty? seq)
                      [0 0]
                      (let [len (count seq)]
                        [(/ (apply + (for [[_ l] seq] l)) len) len])))
          :rereduce (fn [seq & _]
                      (let [len (apply + (for [[_ l] seq] l))
                            avg (apply + (for [[a l] seq] (* a (/ l len))))]
                        [avg len]))
          :log (fn [msg & [conf]]
                 (pr (str "[\"log\", \"" (conf :prefix) msg "\"]")))
          :conf {:prefix "MeanLength: "}}))
    (is (= [[]] (seq<- (.MapDoc ml (jobj<- {"_id" "1234" "not-length" 55})))))
    (is (= [[nil 33]] (seq<- (.MapDoc ml (jobj<- {"_id" "34" "length" 33})))))
    (is (= [[0 0]] (seq<- (.Reduce ml (jarray<- [])))))
    (is (= [[0 0]] (seq<- (.ReReduce ml (jarray<- [])))))
    (is (= [[3 2]] (seq<- (.Reduce ml (jarray<- [[["id" "key"] 2] [["id" "key"] 4]])))))
    (is (= [[51/7 14]] (seq<- (.ReReduce ml (jarray<- [[3 2] [8 12]])))))))

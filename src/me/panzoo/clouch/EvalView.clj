(ns me.panzoo.clouch.EvalView
  (:use
    me.panzoo.clouch.json)
  (:gen-class
    :implements [com.cloudant.couchdbjavaserver.JavaView]
    :state state
    :init init
    :prefix m-))

(defn m-init []
  [[] (atom nil)])

(defn m-MapDoc [this doc]
  (let [{:keys [map conf]} @(.state this)] 
    (when-not map (throw (Exception. "Map function not defined.")))
    (jarray<- (or (map (map<- doc) conf) [[]]))))

(defn m-Reduce [this results]
  (let [{:keys [reduce conf]} @(.state this)]
    (when-not reduce (throw (Exception. "Reduce function not defined.")))
    (jarray<- [(reduce (seq<- results) conf)])))

(defn m-ReReduce [this results]
  (let [{:keys [rereduce conf]} @(.state this)]
    (when-not rereduce (throw (Exception. "Rereduce function not defined.")))
    (jarray<- [(rereduce (seq<- results) conf)])))

(defn m-Configure [this string]
  (swap! (.state this) (constantly (load-string string))))

(defn m-Log [this msg]
  (let [{:keys [log conf]} @(.state this)]
    ((or log (constantly nil)) msg conf)))

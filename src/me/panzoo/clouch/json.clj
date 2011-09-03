(ns me.panzoo.clouch.json)

(declare jarray<- jobj<- seq<- map<-)

(defn json->clj [x]
  (condp #(= (type %2) %1) x
    org.json.JSONArray (seq<- x)
    org.json.JSONObject (map<- x)
    x))

(defn jarray<- [^java.util.Collection seq]
  (org.json.JSONArray. seq))

(defn jobj<- [^java.util.Map cmap]
  (org.json.JSONObject. cmap))

(defn seq<- [array]
  (for [i (range (.length array))]
    (json->clj (.opt array i))))

(defn map<- [obj]
  (reduce 
    #(assoc %1 %2 (json->clj (.opt obj %2)))
    {}
    (iterator-seq (.keys obj))))

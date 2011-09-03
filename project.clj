(defproject
  me.panzoo/clouch "0.1.0-SNAPSHOT"

  :description "Clojure views for CouchDb and BigCouch (Cloudant)."

  :dependencies
  [[org.clojure/clojure "1.2.1"]
   [org.json/json "20090211"]]

  :java-source-path "java-src"
  
  :aot [me.panzoo.clouch.EvalView])

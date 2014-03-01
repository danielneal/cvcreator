(defproject cvcreator "0.1.2"
  :description "A simple little tool to convert a CV (in YAML format) to a prettier HTML version"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [clj-yaml "0.3.0-SNAPSHOT"]
                 [hiccup "1.0.0"]
                 [com.evocomputing/colors "1.0.0-SNAPSHOT"]]
  :main cvcreator.core)

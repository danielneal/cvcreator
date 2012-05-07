(ns cvcreator.core
  (:use c2.core hiccup.core hiccup.page
        c2.svg)
  (:require [clj-yaml.core :as yaml]
            [c2.scale :as scale]
            [cvcreator.css :as css]))

;; Get the raw data for the cv
(defn cv-raw [] (slurp "input/cv.yml"))
(defn cv-data [] (yaml/parse-string (cv-raw)))

(def w3-xhtml "http://www.w3.org/1999/xhtml")
(def w3-svg "http://www.w3.org/2000/svg")

(defn timeline [data]
  [:div#timeline
   [:h2 "Timeline"
    (unify data
           (fn [{:keys [period location activity]}]
             [:div#item
              [:svg.stop {:viewBox "0 0 12 12"} 
               [:circle {:cx 6 :cy 6 :r 5 :style (style {:stroke-width 2 :stroke "black" :fill "pink"})}]]
              [:h3 period]
              [:p activity]]))]])

(let [unfilled-color "lightgray"
      horizontal-spacing 2]
  (defn make-circles [{:keys [size filled-color number number-filled]}]
    (let [r (/ size 2)
          horizontal-separation (+ size horizontal-spacing)]
      (for [x (range number)]
        [:circle {:cx (+  r (* horizontal-separation x)) :cy r :r r :style
                  (style 
                   (if (< x number-filled)
                     {:fill filled-color}
                     {:fill unfilled-color}))}]))))

(defn skills [data]
  [:div#skills
   [:h2 "Skills"
    (unify data
           (fn [{:keys [skill ability enjoyment]}]
             [:div.skillrow 
              [:p skill]
              [:svg 
               (make-circles {:size 8 :filled-color "pink" :number 5 :number-filled ability})]
              [:svg 
               (make-circles {:size 8 :filled-color "red" :number 5 :number-filled enjoyment})]]))]])
            

(defn make-cv [& {:keys [mode] :or {mode "print"}}]
  (do
    (css/make-css-file!)
    (spit "output/cv.html"
          (html
           [:head
            [:style {:type "text/css"}
             (slurp (str "resources/css/" mode ".css"))]]
           [:body
            [:div#page
             (timeline (:timeline (cv-data)))
             (skills (:skills (cv-data)))]]))))







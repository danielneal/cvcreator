(ns cvcreator.core
  (:use c2.core hiccup.core hiccup.page
        c2.svg)
  (:require [clj-yaml.core :as yaml]
            [c2.scale :as scale]
            [cvcreator.css :as css]
            [com.evocomputing.colors.palettes.core :as core-palettes]
            [com.evocomputing.colors.palettes.color-brewer :as colour-brewer]
            [com.evocomputing.colors :as colours]))

;; Get the raw data for the cv
(defn cv-raw [] (slurp "input/cv.yml"))
(defn cv-data [] (yaml/parse-string (cv-raw)))

(def w3-xhtml "http://www.w3.org/1999/xhtml")
(def w3-svg "http://www.w3.org/2000/svg")

(defn timeline [data]
  (let [nt  (count data)
        colours (into [] (core-palettes/heat-hsl nt))]
    [:div#timeline
     [:h2 "Timeline"
      (unify (map-indexed vector data)
             (fn [[idx {:keys [period location activity last?]}]]
               [(if (= idx (dec nt)) :div#lastitem :div.item)
                [:svg.stop {:viewBox "0 0 12 12"} 
                 [:circle {:cx 6 :cy 6 :r 5
                           :style (style {:stroke-width 2 :stroke "black" :fill (colours/rgb-hexstr (colours idx))})}]]
                [:h3 period]
                [:p activity]]))]]))

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

(defn skill-rows [colour data]
  (unify data
         (fn [{:keys [skill ability enjoyment]}]
           [:div.skillrow 
              [:p skill]
            [:svg 
             (make-circles {:size 8 :filled-color (colours/rgb-hexstr colour) :number 5 :number-filled ability})]
            [:svg 
             (make-circles {:size 8 :filled-color (colours/rgb-hexstr colour) :number 5 :number-filled enjoyment})]])))

(defn skills [data]
  (let [ng (count data)
        colours (into [] (core-palettes/rainbow-hsl ng))]
    [:div#skills
     [:h2 "Skills"]
     (unify (map-indexed vector data)
            (fn [[idx {:keys [group skills]}]]
              [:div.group
               [:h3 group]
               [:div.skillrows
                (skill-rows (colours idx) skills)]]))]))


(defn contact [data]
  [:div#contact
   (unify data
          (fn [{:keys [method details]}]
            [:div.contactrow
             [:h2 method]
             [:p details]]))])  


(defn qualification-rows [data]
  (unify data
         (fn [{:keys [qualification result]}]
           [:div.qualificationrow
            [:p.qualification qualification]
            [:p.result result]])))

(defn qualifications [data]
  [:div#qualifications
   [:h2 "Qualifications"]
   [:div#content
    (unify data
           (fn [{:keys [group qualifications]}]
             [:div.group
              [:h3 group]
              (qualification-rows qualifications)]))]])

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
             [:h1 (:name (cv-data))]
             [:div#about [:p (:about (cv-data))]]
             [:img {:src (:picture (cv-data))}]
             (contact (:contact (cv-data)))
             (timeline (:timeline (cv-data)))
             (skills (:skills (cv-data)))
             (qualifications (:qualifications (cv-data)))]]))))




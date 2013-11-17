(ns cvcreator.core
  (:use hiccup.core hiccup.page)
  (:require [clj-yaml.core :as yaml]
            [com.evocomputing.colors.palettes.core :as core-palettes]
            [com.evocomputing.colors.palettes.color-brewer :as colour-brewer]
            [com.evocomputing.colors :as colours]))

(defn cv-raw [] (slurp "input/cv.yml"))

(defn cv-data [] (yaml/parse-string (cv-raw)))

(defn timeline [data]
  (let [nt  (count data)
        colours (into [] (core-palettes/heat-hsl nt))]
    (map (fn [[idx {:keys [period location activity last?]}]]
             [(if (= idx (dec nt)) :div#lastitem :div.item)
              [:svg.stop {:viewBox "0 0 12 12"}
               [:circle {:cx 6 :cy 6 :r 5
                         :stroke-width 2 :stroke "black" :fill (colours/rgb-hexstr (colours idx))}]]
              [:h3 period]
              [:p activity]])
         (map-indexed vector data))))

(defn make-circles [{:keys [number filled-colour number-filled unfilled-colour]
                     :or {filled-colour "#000000" unfilled-colour "#e6e6e6"}}]
  (for [x (range number)]
    [:svg.skilldot {:viewBox "0 0 10 10"}
     [:circle {:cx 5 :cy 5 :r 5
               :fill (if (< x number-filled) filled-colour unfilled-colour)}]]))

(def highest-score 5)

(defn skill-rows [colour data]
  (map
   (fn [{:keys [skill experience enjoyment]}]
     [:div.skillrow
      [:p skill]
      [:div.experience
       (make-circles {:filled-colour (colours/rgb-hexstr colour) :number highest-score :number-filled experience})]
      [:div.enjoyment
       (make-circles {:filled-colour (colours/rgb-hexstr colour) :number highest-score :number-filled enjoyment})]])
   data))

(defn skills [data]
  (let [ng (count data)
        colours (into [] (core-palettes/rainbow-hsl ng))]
    (map (fn [[idx {:keys [group skills]}]]
           [:div.group
            [:h3 group]
            (if (= idx 0) (html [:h4.experience "experience"] [:h4.enjoyment "enjoyment"]))
            [:div.skillrows
             (skill-rows (colours idx) skills)]])
         (map-indexed vector data))))

(defn skills-key [data]
  (map (fn [{:keys [score experience enjoyment]}]
         [:div.keyrow
            [:div.score (make-circles {:filled-colour "black" :number highest-score :number-filled score})]
            [:p.experience experience]
          [:p.enjoyment enjoyment]])
       (map #(hash-map :score %1 :experience %2 :enjoyment %3)
            (iterate inc 1)
            (:experience data)
            (:enjoyment data))))

(defn contact [data]
  (map (fn [{:keys [method details]}]
           [:div.contactrow
            [:h2 method]
            [:p details]])
       data))

(defn qualification-rows [data]
  (map
   (fn [{:keys [qualification result]}]
     [:div.qualificationrow
      [:p.qualification qualification]
      [:p.result result]])
   data))

(defn qualifications [data]
  (map (fn [{:keys [group qualifications]}]
           [:div.group
            [:h3 group]
            (qualification-rows qualifications)])
       data))

(defn make-other-information [data]
  (map (fn [x] [:h3 x])
       data))

(defn make-cv [& {:keys [mode] :or {mode "print"}}]
  (spit "output/cv.html"
        (html
         [:head
          [:style {:type "text/css"}
           (slurp (str "resources/css/" mode ".css"))]]
         [:body
          [:div#page
           [:div#header
            [:div#title
             [:h2#name (:name (cv-data))]
             [:h2#jobtitle (:jobtitle (:about (cv-data)))]]
            [:div#aboutme [:p (:me (:about (cv-data)))]]
            [:div#contact (contact (:contact (cv-data)))]
            [:img {:src (:picture (cv-data))}]]
           [:div#skills
            [:h2 "Skills"]
            (skills (:skills (cv-data)))
            [:div#skillskey
             [:h3 "score"]
             [:h4.experience "experience"]
             [:h4.enjoyment "enjoyment"]
             [:div#keyrows
              (skills-key (:skills-key (cv-data)))]]]
           [:div#timeline
            [:h2 "Timeline"]
            (timeline (:timeline (cv-data)))]
           [:div#qualifications
            [:h2 "Qualifications"]
            [:div#content (qualifications (:qualifications (cv-data)))]]
           [:div#aboutcv
            [:p [:span#title "About this CV"]
             [:span#content (:this-cv (:about (cv-data)))]]]]])))
(make-cv)

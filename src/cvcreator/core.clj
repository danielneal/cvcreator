(ns cvcreator.core
  (:require [clj-yaml.core :as yaml]
            [hiccup.core :refer [html]]
            [com.evocomputing.colors.palettes.core :as core-palettes]
            [com.evocomputing.colors.palettes.color-brewer :as colour-brewer]
            [com.evocomputing.colors :as colours])
  (:gen-class))

;; ----------------------------
;;        Timeline
;; ----------------------------

(defn make-timeline-section
  "Construct a timeline of activities, with SVG circles marking each item"
  [data]
  (let [number-of-timeline-items (count data)
        colours (into [] (core-palettes/heat-hsl number-of-timeline-items))]
    (map (fn [[idx {:keys [period activity]}]]
             [(if (= idx (dec number-of-timeline-items)) :div#lastitem :div.item)
              [:svg.stop {:viewBox "0 0 12 12"}
               [:circle {:cx 6 :cy 6 :r 5 :stroke-width 2
                         :stroke "black" :fill (colours/rgb-hexstr (colours idx))}]]
              [:h3 period]
              [:p activity]])
         (map-indexed vector data))))

;; ----------------------------
;;          Skills
;; ----------------------------

(def highest-score 5)

(defn make-skill-circles
  "Make a set of filled/unfilled SVG circles to represent a skill rating"
  [{:keys [number filled-colour number-filled unfilled-colour]
    :or {filled-colour "#000000" unfilled-colour "#e6e6e6"}}]
  (for [x (range number)]
    [:svg.skilldot {:viewBox "0 0 10 10"}
     [:circle {:cx 5 :cy 5 :r 5
               :fill (if (< x number-filled) filled-colour unfilled-colour)}]]))

(defn make-skill-rows
  "Make rows with filled circles to represent skill experience and enjoyment"
  [colour data]
  (map
   (fn [{:keys [skill experience enjoyment]}]
     [:div.skillrow
      [:p skill]
      [:div.experience
       (make-skill-circles {:filled-colour (colours/rgb-hexstr colour) :number highest-score :number-filled experience})]
      [:div.enjoyment
       (make-skill-circles {:filled-colour (colours/rgb-hexstr colour) :number highest-score :number-filled enjoyment})]])
   data))

(defn make-skills-section
  "Make a skills section, containing skill ratings divided into groups"
  [data]
  (let [number-of-groups (count data)
        colours (into [] (core-palettes/rainbow-hsl number-of-groups))]
    (map (fn [[idx {:keys [group skills]}]]
           [:div.group
            [:h3 group]
            (if (= idx 0) (html [:h4.experience "experience"] [:h4.enjoyment "enjoyment"]))
            [:div.skillrows
             (make-skill-rows (colours idx) skills)]])
         (map-indexed vector data))))

(defn make-skills-key
  "Make a key/legend for the skills section"
  [data]
  (map (fn [{:keys [score experience enjoyment]}]
         [:div.keyrow
            [:div.score (make-skill-circles {:filled-colour "black" :number highest-score :number-filled score})]
            [:p.experience experience]
          [:p.enjoyment enjoyment]])
       (map #(hash-map :score %1 :experience %2 :enjoyment %3)
            (iterate inc 1)
            (:experience data)
            (:enjoyment data))))

;; ----------------------------
;;         Contact
;; ----------------------------

(defn make-contact-section
  "Make a section to show contact details"
  [data]
  (map (fn [{:keys [method details]}]
           [:div.contactrow
            [:h2 method]
            [:p details]])
       data))

;; ----------------------------
;;       Qualifications
;; ----------------------------

(defn make-qualification-rows
  "Make a group of qualification/result rows"
  [data]
  (map
   (fn [{:keys [qualification result]}]
     [:div.qualificationrow
      [:p.qualification qualification]
      [:p.result result]])
   data))

(defn make-qualifications-section
  "Make the section to hold all qualifications"
  [data]
  (map (fn [{:keys [group qualifications]}]
           [:div.group
            [:h3 group]
            (make-qualification-rows qualifications)])
       data))

;; ----------------------------
;;   Putting it all together
;; ----------------------------

(defn cv->html
  "Convert (parsed) CV data into html using hiccup"
  [cv-data]
  (html
   [:head
    [:style {:type "text/css"} (slurp (str "resources/css/style.css"))]]
   [:body
    [:div#page
     [:div#header
      [:div#title
       [:h2#name (:name cv-data)]
       [:h2#jobtitle (:jobtitle (:about cv-data))]]
      [:div#aboutme [:p (:me (:about cv-data))]]
      [:div#contact (make-contact-section (:contact cv-data))]
      [:img {:src (:picture cv-data)}]]
     [:div#skills
      [:h2 "Skills"]
      (make-skills-section (:skills cv-data))
      [:div#skillskey
       [:h3 "score"]
       [:h4.experience "experience"]
       [:h4.enjoyment "enjoyment"]
       [:div#keyrows
        (make-skills-key (:skills-key cv-data))]]]
     [:div#timeline
      [:h2 "Timeline"]
      (make-timeline-section (:timeline cv-data))]
     [:div#qualifications
      [:h2 "Qualifications"]
      [:div#content (make-qualifications-section (:qualifications cv-data))]]
     [:div#aboutcv
      [:p [:span#title "About this CV"]
       [:span#content (:this-cv (:about cv-data))]]]]]))

(defn write-cv
  "Read the input .yml file and create the html visualisation"
  [input-file output-file]
  (->> (slurp input)
       (yaml/parse-string)
       (cv->html)
       (spit output-file)))

(defn -main
  [output-file]
  (write-cv "input/cv.yaml" output-file))

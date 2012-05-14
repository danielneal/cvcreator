(use 'cssgen.use)
(css-ns cvcreator.css)


(def nomargin
  (mixin
   :margin 0
   :padding 0))

(defn horizontal-space [x]
  (mixin
   :margin-left x
   :margin-right x))

(defn vertical-space [x]
  (mixin
   :margin-top x
   :margin-bottom x))

(def big-font
  (pt 16))

(def medium-font
  (pt 10))

(def small-font
  (pt 8))

(def timeline-width (px 10))
(def stop-size (px 16))
(def timeline-item
  (mixin
   (rule ".stop"
         :width  stop-size
         :height  stop-size
         :left (- (+ (/ stop-size 2) (/ timeline-width 2)))
         :top (- (/ stop-size 2))
         :position "absolute")
   (rule "h3"
         :position "relative"
         :font-size medium-font
         :top (- (/ stop-size 2))
         :left timeline-width
         :margin 0)
   (rule "p"
         :font-size (pt 8)
         :font-weight "normal"
         :position "relative"
         :margin 0
         :top (- (/ stop-size 2))
         :left timeline-width)))


(defn make-css-file! []
  
  (css-file "resources/css/print.css"  ;this is the path to the target CSS file
            (rule "body"
                  :color :black
                  :font-family "Tahoma"
                  :font-size medium-font)

            ;; a4 aspect ratio
            (rule "#page"
                  :width 1200)
            
            (rule "#header"
                  :width (% 100)
                  :position "relative"
                  (rule "#title"
                        :float "left"
                        :width (% 40)
                        :margin-left (% 5)
                        :margin-right (% 5)
                        (rule "#name"
                              :margin-top 0
                              :margin-bottom 0
                              :margin-left 0
                              :margin-right (px 10)
                              :float "left"
                              :font-size big-font)
                        (rule "#jobtitle"
                              nomargin
                              :clear "both"
                              :font-size medium-font
                              :float "left"
                              :font-weight "normal"
                              :margin-right (px 10)))
                  
                  (rule "#aboutme"
                        :width (% 40)
                        :margin-left (% 5)
                        :margin-right (% 5)
                        :margin-top (px 10)
                        :float "left"
                        :clear "both"
                        (rule "p"
                              nomargin
                              :float "left"
                              :font-size (pt 8)))
                  
                  (rule "#contact"
                        :width (% 15)
                        :float "left"
                        :margin-left (% 5)
                        :margin-right (% 10)
                        (rule "h2"
                              nomargin
                              :float "left"
                              :font-size (pt 8))
                        (rule "p"
                              :clear "left"
                              :font-size (pt 8)
                              nomargin)
                        (rule ".contactrow"
                              :float "left"))
                  
                  (rule "img"
                        :float "left"
                        :margin-left (% 5)
                        :width (% 10)))
            
            (rule "h2"
                  :margin-top (px 20)
                  :margin-bottom (px 10))

            (rule "#column1"
                  :width  (% 40)
                  :margin-left (% 5)
                  :margin-right (% 5)
                  :float "left"
                  :clear "both"
                  (rule "#qualifications"
                        :width (% 100)
                        (rule "h3"
                              nomargin)
                        (rule "#content"
                              :float "left"
                              :width (% 100))
                        (rule ".group"
                              :float "left"
                              :margin-top (px 10)
                              :width (% 100))
                        (rule "p"
                              nomargin
                              :font-size small-font
                              (rule "&.result"
                                    :color "gray"
                                    :float "right")
                              (rule "&.qualification"
                                    :float "left"
                                    :font-weight "bold"
                                    :clear "both")))
                  
                  (rule "#skills"
                        :width (% 100)
                        (rule "h3"
                              nomargin
                              :width (% 50)
                              :float "left"
                              :font-size medium-font)
                        (rule "h4"
                              nomargin
                              :font-size small-font
                              :float "left"
                              (rule "&.experience"
                                    :width (% 30))
                              (rule "&.enjoyment"
                                    :width (% 20)))
                        (rule "svg.skilldot"
                              :float "left"
                              :height 9
                              :width 9
                              :margin 1)
                        (rule ".group"
                              :float "left"
                              :clear "both"
                              :margin-bottom (px 10)
                              :width (% 100)
                              (rule ".skillrows"
                                    :float "left"
                                    :width (% 100)
                                    :border-top-style "solid")
                              (rule ".skillrow"
                                    :height  10
                                    :width (% 100)
                                    :clear "both"
                                    (rule "p"
                                          :float "left"
                                          :font-size small-font
                                          :font-weight "normal"
                                          :width (% 50)
                                          :margin 0)
                                    (rule ".experience"
                                          :float "left"
                                          :width (% 30))
                                    (rule ".enjoyment"
                                          :float "left"
                                          :text-align "left"
                                          :width (% 20))))
                        (rule "#skillskey"
                              :float "left"
                              :width (% 100)
                              :margin-top (px 10)
                              (rule "#keyrows"
                                    :float "left"
                                    :width (% 100)
                                    :margin-top (px 10))
                              (rule ".keyrow"
                                    :width (% 100)
                                    :height 10)
                              (rule "p"
                                    nomargin
                                    :font-size small-font
                                    :float "left"
                                    (rule "&.experience"
                                          :width (% 30))
                                    (rule "&.enjoyment"
                                          :float "left"
                                          :text-align "left"
                                          :width (% 20)))
                              (rule ".score"
                                    :float "left"
                                    :width (% 50)))))
             
            (rule "#column2"
                  :width  (% 40)
                  :margin-left (% 5)
                  :margin-right (% 5)
                  :float "left"
                  (rule "#timeline"
                        :width (% 100)
                        (rule "#lastitem"
                              :border-left-style "none"
                              :position "relative"
                              :margin-left timeline-width
                              timeline-item)
                        (rule ".item"
                              :border-left timeline-width
                              :border-left-style "solid"
                              :position "relative"
                              :padding-bottom  (% 3)
                              timeline-item)))
            
            (rule "#aboutcv"
                  :width (% 90)
                  :float "left"
                  :clear "both"
                  :margin (px 10)
                  (horizontal-space (% 5))
                  
                  (rule "p"
                        nomargin
                        :width "auto"
                        :display "inline"
                        :float "left"
                        :font-size small-font)
                  
                  (rule "#title"
                        :font-weight "bold"
                        :margin-right (px 10)))))

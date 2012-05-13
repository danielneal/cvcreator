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
                  :width  1000
                  :height  1410)

            (rule "h2"
                  :margin-top (px 20)
                  :margin-bottom (px 10))

            (rule "#header"
                  :width (% 100)
                  :height (% 10)
                  (rule "h1"
                        :width (% 40)
                        (horizontal-space (% 5))
                        :float "left")
                  
                  (rule "#aboutme"
                        :width (% 40)
                        :height (% 100)
                        (horizontal-space (% 5))
                        :float "left"
                        :clear "both"
                        (rule "p"
                              nomargin
                              :float "left"
                              :font-size (pt 8)))
                  
                  (rule "#contact"
                        :float "left"
                        :width (% 15)
                        :height (% 100)
                        :margin-left (% 5)
                        (rule "h2"
                              nomargin
                              :float "left"
                              :font-size (pt 8))
                        (rule "p"
                              :clear "left"
                              :font-size (pt 8)
                              nomargin)
                        (rule ".contactrow"
                              :margin-top 5
                              :clear "both"))
                  
                  (rule "img"
                        :width (% 15)
                        :margin-left (% 10)
                        :float "left"))
            
            
            (rule "#timeline"
                  :width  (% 40)
                  :height (% 50)
                  (horizontal-space (% 5))
                  :float "left"
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
                        timeline-item))
            
            (rule "#skills"
                  :width  (% 40)
                  :height (% 50)
                  (horizontal-space (% 5))
                  :float "left"
                  :clear "right"
                  (rule "h3"
                        nomargin
                        :width (% 60)
                        :float "left"
                        :font-size medium-font)
                  
                  (rule "h4"
                        nomargin
                        :font-size small-font
                        :width (% 20)
                        :float "left")

                  (rule "svg.skilldot"
                        :float "left"
                        :height 9
                        :width 9
                        :margin 1)
                        
                  
                  (rule ".group"
                        :float "left"
                        :clear "both"
                        :margin-bottom "10"
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
                                    :width (% 60)
                                    :margin 0)
                              (rule ".score"
                                    :float "left"
                                    :width (% 20))))
                  
                    (rule "#skillskey"
                          :float "left"
                          :width (% 100)
                          :margin-top (% 5)
                          (rule "#keyrows"
                                :margin-top (% 5))
                          (rule ".keyrow"
                                :width (% 100)
                                :height 10)
                          (rule "p"
                                nomargin
                                :font-size small-font
                                :float "left"
                                :width (% 20))
                          (rule ".score"
                                :float "left"
                                :width (% 60))))
            
            (rule "#qualifications"
                  :width (% 90)
                  :height (% 25)
                  :margin-top (% 5)
                  (horizontal-space (% 5))
                  :float "left"
                  (rule "h3"
                        nomargin)

                  (rule "#content"
                        :float "left"
                        :width (% 100))
                  (rule ".group"
                        :float "left"
                        :width (% 27)
                        :margin-top (% 2)
                        (horizontal-space (% 3)))
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
            
            (rule "#aboutcv"
                  :width (% 90)
                  :height (% 5)
                  :margin-top (% 5)
                  :float "left"
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

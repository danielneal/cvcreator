(use 'cssgen.use)
(css-ns cvcreator.css)


(def nomargin
  (mixin
   :margin 0
   :padding 0))

(defn horizontal-space [width]
  (mixin
   :margin-left width
   :margin-right width))

(def timeline-item
  (mixin
   (rule ".stop"
         :width  14
         :height  14
         :left (- 12)
         :top (- 2)
         :position "absolute")
   (rule "h3"
         :position "relative"
         :font-size (pt 10)
         :left 5
         :margin 0)
   (rule "p"
         :font-size (pt 8)
         :font-weight "normal"
         :position "relative"
         :margin 0
         :left 5)))


(defn make-css-file! []
  
  (css-file "resources/css/print.css"  ;this is the path to the target CSS file
            (rule "body"
                  :color :black
                  :font-family "Tahoma"
                  :font-size (pt 10))
            ;; a4 aspect ratio
            
            (rule "#page"
                  :width  1050
                  :height  1485)
            (rule "h1"
                  :width (% 40)
                  (horizontal-space (% 5))
                  :float "left")
            (rule "#about"
                  :width (% 40)
                  :float "left"
                  :clear "left"
                  (horizontal-space (% 5))
                  (rule "p"
                        nomargin
                        :float "left"
                        :font-size (pt 8)))
            (rule "img"
                  :width (% 15)
                  (horizontal-space (% 5))
                  :float "right")
            
            (rule "#contact"
                  :float "left"
                  :width (% 15)
                  :align "bottom"
                  (horizontal-space (% 5))
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
            
            (rule "#timeline"
                  :display "table"
                  :width  (% 40)
                  (horizontal-space (% 5))
                  :float "left"
                  :clear "both"
                  (rule "#lastitem"
                        :border-left-style "none"
                        :position "relative"
                        :margin-left (px 10)
                        timeline-item)
                  (rule ".item"
                        :border-left (px 10)
                        :border-left-style "solid"
                        :position "relative"
                        :padding-bottom  (% 3)
                        timeline-item))

            
            (rule "#skills"
                  :width  (% 40)
                  (horizontal-space (% 5))
                  :float "left"

                  (rule ".group"
                        :float "left"
                        :clear "both"
                        :margin-bottom "10"
                        :width (% 100)
                        (rule "h3"
                              nomargin
                              :font-size (pt 8))
                        (rule ".skillrows"
                              :border-top-style "solid")
                        (rule ".skillrow"
                              :height  10
                              :width (% 100)
                              :clear "both"
                              (rule "p"
                                    :float "left"
                                    :font-size (pt 8)
                                    :font-weight "normal"
                                    :width (% 50)
                                    :margin 0)
                              (rule "svg"
                                    :float "left"
                                    :height (% 100)
                                    :width (% 25)))))
            (rule "#qualifications"
                  :width (% 90)
                  (horizontal-space (% 5))
                  :float "left"
                  (rule "h2"
                        :float "left"
                        :clear "both")
                  (rule "#content"
                        :float "left"
                        :width (% 100))
                  (rule ".group"
                        :float "left"
                        :width (% 27)
                        (horizontal-space (% 3)))
                  (rule "p"
                        nomargin
                        :font-size (pt 8)
                        (rule "&.result"
                              :color "gray"
                              :float "right")
                        (rule "&.qualification"
                              :float "left"
                              :margin "0 10 0 0"
                              :font-weight "bold"
                              :clear "both")))))

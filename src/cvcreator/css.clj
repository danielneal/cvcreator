(use 'cssgen.use)
(css-ns cvcreator.css)


(def nomargin
  (mixin
   :margin 0
   :padding 0))

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
            ;;a4 aspect ratio
            
            (rule "#page"
                  :width  1050
                  :height  1485
                  :border-style "solid"
                  (rule "h1"
                        nomargin
                        :width (% 20)
                        :float "left"))

            (rule "#contact"
                  :float "right"
                  (rule "h2"
                        nomargin
                        :float "left"
                        :font-size (pt 8))
                  (rule "p"
                        nomargin
                        :float "left"
                        :font-size (pt 8))
                  (rule ".contactrow"
                        :clear "both"
                        :margin "0 0 0 5"))
            
            (rule "#timeline"
                  :display "table"
                  :width  (% 40)
                  :margin (% 5)
                  :float "left"
                  :clear "both"
                  (rule "#lastitem"
                        :border-left-style "none"
                        :position "relative"
                        :margin-left 10
                        timeline-item)
                  (rule ".item"
                        :border-left  10
                        :border-left-style "solid"
                        :position "relative"
                        :padding-bottom  5
                        timeline-item))

                        
            (rule "#skills"
                  :width  (% 40)
                  :margin (% 5)
                  :float "left"
                  (rule ".group"
                        :float "left"
                        :clear "both"
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
                                    :width (% 25)))))))

(use 'cssgen.use)
(css-ns cvcreator.css)

(defn make-css-file! []
  (css-file "resources/css/print.css"  ;this is the path to the target CSS file
            (rule "body"
                  :color :black
                  :font-family "Tahoma"
                  :font-size (pt 10))
            ;;a4 aspect ratio
            (rule "#page"
                  :width  1050
                  :height  1485) 
            (rule "#timeline"
                  :display "table"
                  :width  600
                  :float "left"
                  (rule "#item"
                        :border-left  10
                        :border-left-style "solid"
                        :position "relative"
                        :padding-bottom  5
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
            (rule "#skills"
                  :width  450
                  :float "left"
                  (rule ".skillrow"
                        :width  450
                        :height  10
                        :clear "both"
                        (rule "p"
                              :float "left"
                              :font-size (pt 8)
                              :font-weight "normal"
                              :width 150
                              :margin 0)
                        (rule "svg"
                              :float "left"
                              :margin-right 10
                              :width 50)))))

(ns samples.macro)

;;
;; マクロを使用すると独自のルールを作ることが出来る
;;
;; マクロでJavaのメソッド呼び出しを綺麗に呼べるようにする



;; Clojure
;; (.replace (.toUpperCase (.concat "Java" "World")) "V" "W")





;; Koheiさん: これがワイのルールや！
;; samples.macro> 
;; (kohei-rules "Java"
;;              :concat ["World"]
;;              :toUpperCase []
;;              :replace ["V" "W"])
;; "JAWAWORLD"

(defmacro kohei-rules [x & methods]
  (if (seq methods)
    (let [method      (take 2 methods)
          method_name (first  method)
          method_args (second method)]
      `(kohei-rules (~(symbol (str "." (name method_name))) ~x ~@method_args)
                    ~@(drop 2 methods)))
    x))





;; ArrowMacro
;; (-> "Java"
;;     (.concat "World")
;;     .toUpperCase
;;     (.replace "V" "W"))


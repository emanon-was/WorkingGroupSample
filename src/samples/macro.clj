(ns samples.macro)

;;
;; Lisp系言語の利点はコードもListとして扱える点
;;
;; マクロにそのコード(List)を渡してやることで,
;; 別のコード(List)に変換し実行することが出来る
;;
;; Lisp系言語の実行順
;; read -> compile -> eval
;;
;; このcompile時にマクロが展開される
;; 



;; ========================================
;;
;; 雑にForth的な逆ポーランド記法を書いてみる
;; samples.macro> (forth (1 2 3 +))
;; 6
;;
;; ========================================

;; => (forth (1 2 3 +))
;; => (+ 3 2 1)
;; => 6

(defmacro forth [form]
  (if (list? form)
    (reverse form)
    form))



;; ========================================
;;
;; 定義するときdefnって書くけどfunctionってJavascriptみたいに書きたい
;;
;; samples.macro> 
;; (function test-fn [x]
;;    (* x x))
;;
;; samples.macro> (test-fn 10)
;; 100
;;
;; ========================================

(defmacro function [& defn-args]
  `(defn ~@defn-args))



;; Javascriptっぽくするなら無名関数もfunctionで書けるべき

(defmacro function [& defn-args]
  (if (symbol? (first defn-args))
    `(defn ~@defn-args)
    `(fn ~@defn-args)))



;; ========================================
;;
;; Clojureにはletというレキシカルスコープの実装がある
;;
;; (let [x 10 y 21]
;;   (* x y))
;;
;;
;; これを my-let という名前で実装してみる
;;
;; (my-let [x 10 y 21]
;;   (* x y))
;;
;;
;; 上記は
;;
;; 関数 (fn [x y] (* x y))
;; 引数 10 21
;;
;; ((fn [x y] (* x y)) 10 21)
;;
;; と表現することが出来る
;;
;; ========================================

(defmacro my-let [bindings & body]
  `((fn ~(vec (map first (partition 2 bindings))) ~@body)
    ~@(vec (map second (partition 2 bindings)))))



;; ========================================
;;
;; マクロでJavaのメソッド呼び出しを綺麗に呼べるようにする
;;
;; Clojure
;; (.replace (.toUpperCase (.concat "Java" "World")) "V" "W")
;;
;;
;; Koheiさん: これがワイのルールや！
;;
;; samples.macro> 
;; (kohei-rules "Java"
;;              :concat ["World"]
;;              :toUpperCase []
;;              :replace ["V" "W"])
;; "JAWAWORLD"
;;
;; ========================================

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


(ns samples.java-method)

;; ========================================
;;
;; Javaのstaticメソッド呼び出し
;;
;; samples.core> (to-i "567")
;; 567
;;
;; ========================================

(defn to-i [s]
  (Integer/parseInt s))



;; ========================================
;;
;; Javaのインスタンスメソッド呼び出し
;;
;; samples.core> (substr "abcdefg" 0 5)
;; "abcde"
;;
;; ========================================

(defn substr [s i j]
  (.substring s i j))



;; Javaのメソッドチェーン呼び出し
;; (.. "Java"
;;     (concat "World")
;;     toUpperCase
;;     (replace "V" "W"))


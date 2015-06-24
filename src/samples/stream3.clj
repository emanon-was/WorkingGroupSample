(ns samples.stream3
  (:require [clojure.string  :as str])
  (:use  [samples.stream1 :only [file-reader]]
         [samples.stream2 :only [reader-seq printlst]]))


;;
;; Stream2では扱いやすさがわかりにくかったので
;; 各行をパースしてフィルターして表示させてみる
;;

;; ========================================
;;
;; パースしてhashmapにする
;;
;; samples.stream3> (row-parse "2.9|Clojure|28.42|27.15|82052|2162")
;; {:code "2162", :memory "82052", :time "27.15", :cpu "28.42", :source "Clojure", :bench "2.9"}
;;
;; ========================================

(defn row-parse [l]
  (zipmap [:bench :source :cpu :time :memory :code]
          (str/split l #"\|")))



;; ========================================
;;
;; sourceが"Clojure"の行を取り出して表示する
;;
;; samples.stream3> (printfile "test.txt")
;; {:code 2162, :memory 82052, :time 27.15, :cpu 28.42, :source Clojure, :bench 2.9}
;; nil
;;
;; ========================================

(defn printfile [fname]
  (with-open [r (file-reader fname)]
    (->> (reader-seq r)
         (map row-parse)
         (filter #(= "Clojure" (% :source)))
         printlst)))




;;
;; ループに相当する処理がちゃんとシーケンスとして扱るので汎用的
;;
;; Clojureはmapもfilterも遅延シーケンスを返すようになっているので
;; 最後にOutOfMemoryになるリストを返すような処理にしなければ
;; 通常のループ処理と同じように扱える
;; (無限ループでもOK)
;;


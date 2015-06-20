(ns samples.stream3
  (:require [clojure.java.io :as io]
            [clojure.string  :as str]))

;;
;; - with-openが邪魔
;; - 遅延シーケンスのためwith-openの外側にreader-seqを返せない

;;
;; BufferedReaderを閉じる役割もreader-seqに内包する
;;


(defn file-reader [s]
  (io/reader (io/file (io/resource s))))

(defn reader-seq [r]
  (let [line (.readLine r)]
    (if line
      (cons line (lazy-seq (reader-seq r)))
      (.close r))))

(defn row-parse [l]
  (zipmap [:bench :source :cpu :time :memory :code]
          (str/split l #"\|")))

(defn printlst [lst]
  (doseq [x lst]
    (println x)))

(defn printfile [fname]
  (->> (reader-seq (file-reader fname))
       (map row-parse)
       (filter #(= "Clojure" (% :source)))
       printlst))

;;
;; ループに相当する処理がちゃんとシーケンスとして扱えているはず！
;;
;; Clojureはmapもfilterも遅延シーケンスを返すようになっているので
;; 最後にOutOfMemoryになるリストを返すような処理にしなければ
;; 通常のループ処理と同じように扱える
;; (無限ループでもOK)
;;


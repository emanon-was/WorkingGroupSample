(ns samples.stream2
  (:require [clojure.java.io :as io]
            [clojure.string  :as str]))


;;
;; BufferedReaderをシーケンスとして扱えたら綺麗になるはず！
;;


(defn file-reader [s]
  (io/reader (io/file (io/resource s))))

(defn reader-seq [r]
  (let [line (.readLine r)]
    (if line
      (cons line (lazy-seq (reader-seq r))))))

(defn printlst [lst]
  (doseq [x lst]
    (println x)))

(defn printfile [fname]
  (with-open [r (file-reader fname)]
    (printlst (reader-seq r))))


;;
;; parseしてfilterをかけてみる
;;

(defn row-parse [l]
  (zipmap [:bench :source :cpu :time :memory :code]
          (str/split l #"\|")))
    

(defn printfile [fname]
  (with-open [r (file-reader fname)]
    (->> (reader-seq r)
         (map row-parse)
         (filter #(= "Clojure" (% :source)))
         printlst)))

;;
;; ループ処理の中にそれぞれの処理を書くよりも簡単になった
;;


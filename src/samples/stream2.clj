(ns samples.stream2
  (:use  [samples.stream1 :only [file-reader]]))



;; ========================================
;;
;; BufferedReaderを遅延シーケンスに変換する
;;
;; samples.stream2> (reader-seq (file-reader "test.txt"))
;; ("1.0|C++ g++ #8|9.40|9.40|944|1544" "1.0|C++ g++ #7|9.67|9.67|1052|1545"....)
;;
;; ========================================

(defn reader-seq [r]
  (let [line (.readLine r)]
    (if line
      (cons line (lazy-seq (reader-seq r)))
      (.close r))))



;; ========================================
;;
;; リストをprintlnする関数を書く
;;
;; samples.stream2> (printlst [1 2 3])
;; 1
;; 2
;; 3
;; nil
;;
;; ========================================

(defn printlst [lst]
  (doseq [x lst]
    (println x)))



;; ========================================
;;
;; samples.stream2> (printfile "test.txt")
;; 1.0|C++ g++ #8|9.40|9.40|944|1544
;; 1.0|C++ g++ #7|9.67|9.67|1052|1545
;; ...
;; nil
;;
;; ========================================

(defn printfile [fname]
  (with-open [r (file-reader fname)]
    (printlst (reader-seq r))))



;;
;; ループに相当することが簡潔に書けるようになった
;;

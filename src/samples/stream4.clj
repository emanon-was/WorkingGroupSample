(ns samples.stream4
  (:use  [samples.stream1 :only [file-reader]]
         [samples.stream2 :only [printlst]]
         [samples.stream3 :only [row-parse]]))


;;
;; 各処理の合間にprintlnを行い
;; 遅延シーケンスの処理順がどうなっているのか確認する
;;

(defn debug [subject result]
  (println (str "[" subject "] " result)))


(defn reader-seq [r]
  (let [line (.readLine r)]
    (if line
      (do
        (debug "seq" line)
        (cons line (lazy-seq (reader-seq r))))
      (.close r))))


(defn printfile [fname]
  (with-open [r (file-reader fname)]
    (->> (reader-seq r)
         (map #(do (debug "map" %)
                   (row-parse %)))
         (filter #(do (debug "fil" %)
                      (= "Clojure" (% :source))))
         printlst)))

;; samples.stream4> (printfile "test.txt")
;; [seq] 1.0|C++ g++ #8|9.40|9.40|944|1544
;; [map] 1.0|C++ g++ #8|9.40|9.40|944|1544
;; [fil] {:code "1544", :memory "944", :time "9.40", :cpu "9.40", :source "C++ g++ #8", :bench "1.0"}
;; [seq] 1.0|C++ g++ #7|9.67|9.67|1052|1545
;; [map] 1.0|C++ g++ #7|9.67|9.67|1052|1545
;; [fil] {:code "1545", :memory "1052", :time "9.67", :cpu "9.67", :source "C++ g++ #7", :bench "1.0"}
;; [seq] 1.0|Fortran Intel #5|9.78|9.79|516|1659
;; ....


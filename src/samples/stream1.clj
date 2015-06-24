(ns samples.stream1
  (:require [clojure.java.io :as io]))


;;
;; Javaのメソッドを使ってClojureで手続き的に書く
;;



;; ========================================
;;
;; ファイル読み込み用の関数をとりあえず書く
;;
;; samples.stream1> (file-reader "test.txt")
;; #<BufferedReader java.io.BufferedReader@188a25e2>
;;
;; ========================================

(defn file-reader [s]
  (io/reader (io/file (io/resource s))))



;; ========================================
;;
;; samples.stream1> (printfile "test.txt")
;; 1.0|C++ g++ #8|9.40|9.40|944|1544
;; 1.0|C++ g++ #7|9.67|9.67|1052|1545
;; ...
;; nil
;;
;; ========================================

(defn printfile [fname]
  (with-open [r (file-reader fname)]
    (loop [line (.readLine r)]
      (if line
        (do (println line)
            (recur (.readLine r)))))))



;;
;; - 汚い
;; - 修正しにくい(手を加えにくい)
;;

(ns samples.stream1
  (:require [clojure.java.io :as io]
            [clojure.string  :as str]))


;;
;; Javaのメソッドを使ってClojureで手続き的に書く
;;

;; ファイル読み込み用の関数をとりあえず書く
;; samples.stream> (file-reader "test.txt")
;; #<BufferedReader java.io.BufferedReader@188a25e2>

(defn file-reader [s]
  (io/reader (io/file (io/resource s))))

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

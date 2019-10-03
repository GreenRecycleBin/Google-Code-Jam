(ns io.github.greenrecyclebin.foregone-solution.fast
  (:require
   [io.github.greenrecyclebin.foregone-solution.protocol
    :refer [Solver solve]])
  (:import (java.math BigInteger)))

;; (set! *warn-on-reflection* true)
;; (set! *unchecked-math* :warn-on-boxed)

(defn solver []
  (reify Solver
    (solve [_ n]
      (loop [n (str n)
             a (new StringBuilder)
             b (new StringBuilder)]
        (if (seq n)
          (let [digit (first n)]
            (if (= \4 digit)
              (recur (rest n) (.append a "2") (.append b "2"))
              (recur (rest n) (.append a digit) (.append b "0"))))
          [(new BigInteger (str a)) (new BigInteger (str b))])))))

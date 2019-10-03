(ns io.github.greenrecyclebin.foregone-solution.brute-force
  (:require
   [io.github.greenrecyclebin.foregone-solution.protocol
    :refer [Solver solve]]))

;; (set! *warn-on-reflection* true)
;; (set! *unchecked-math* :warn-on-boxed)

(declare has-a-digit-4?)

(defn solver []
  (reify Solver
    (solve [_ n]
      (loop [a 1
             ^long n n]
        (if-not (has-a-digit-4? a)
          (let [b (- n a)]
            (if-not (has-a-digit-4? b)
              [a b]
              (recur (inc a) n)))

          (recur (inc a) n))))))

(defn- has-a-digit-4? [^long n]
  (loop [n (Math/abs n)]
    (if (zero? n)
      false

      (let [i (rem n 10)]
        (if (= i 4)
          true
          (recur (quot n 10)))))))

(ns io.github.greenrecyclebin.foregone-solution.core
  (:gen-class)
  (:require
   [io.github.greenrecyclebin.foregone-solution.protocol :refer [solve]]
   [io.github.greenrecyclebin.foregone-solution.brute-force :as brute-force]
   [io.github.greenrecyclebin.foregone-solution.random :as random]
   [io.github.greenrecyclebin.foregone-solution.fast :as fast]))

(declare solver)
(declare parse-scientific-notation-as-biginteger)
(declare random-biginteger)

(defn -main [& args]
  {:pre [(<= 2 (count args) 3)]}
  (let [solver-name (first args)]
    (if-let [solver (solver solver-name)]
      (case (count args)
        2
        (let [limit (parse-scientific-notation-as-biginteger (second args))
              ns (take 100 (repeatedly #(random-biginteger limit)))]
          (loop [ns ns]
            (when (seq ns)
              (let [n (first ns)]
                (println (format "solve(%d) = %s" n (solve solver n)))
                (recur (rest ns))))))

        3
        (let [limit (parse-scientific-notation-as-biginteger (second args))
              period (parse-scientific-notation-as-biginteger (nth args 2))]
          (time
           (dotimes [i (dec (int limit))]
             (let [n (inc i)
                   result (solve solver n)]
               (when (zero? (rem n period))
                 (println (format "solve(%d) = %s" n result))))))))
      (println "Unknown solver. Possible solvers are: brute-force."))))

(defn- solver [name]
  (case name
    "brute-force" (brute-force/solver)
    "random" (random/solver)
    "fast" (fast/solver)
    nil))

(defn- parse-scientific-notation-as-biginteger [s] (.toBigIntegerExact (new BigDecimal s)))

(defn- random-biginteger [limit]
  (loop [random (new java.math.BigInteger (.bitLength limit) (java.util.concurrent.ThreadLocalRandom/current))]
    (if (< random limit)
      random
      (recur (new java.math.BigInteger (.bitLength limit) (java.util.concurrent.ThreadLocalRandom/current))))))

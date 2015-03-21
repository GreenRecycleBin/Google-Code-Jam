(ns round-1a.a
  (:require [clojure.string :as string]))

(declare read-input-and-solve)
(declare minimum-scalar-product)

(defn -main [&]
  (let [t (Integer/parseInt (read-line))]
    (dotimes [n t] (read-input-and-solve n))))

(defn read-input-and-solve [n]
  (let [size (Integer/parseInt (read-line))
        coll1 (map #(Integer/parseInt %) (string/split (read-line) #"\s+"))
        coll2 (map #(Integer/parseInt %) (string/split (read-line) #"\s+"))]
    (println (str "Case #" (inc n) ": " (minimum-scalar-product coll1 coll2)))))

(defn minimum-scalar-product [coll1 coll2]
  (let [sorted-coll1 (sort coll1)
        ascendingly-sorted-coll2 (sort > coll2)]
    (reduce + (map * sorted-coll1 ascendingly-sorted-coll2))))

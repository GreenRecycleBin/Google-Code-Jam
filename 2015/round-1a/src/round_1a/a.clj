(ns round-1a.a
  (:require [clojure.edn :as edn]
            [clojure.string :as string]))

(declare first-method)
(declare second-method)

(defn -main [& _]
  (let [t (edn/read)]
    (dotimes [i t]
      (let [n (edn/read)
            _ (read-line)
            coll (map #(Integer/parseInt %) (string/split (read-line) #"\s+"))]
        (printf "Case #%d: %d %d\n" (inc i) (first-method coll) (second-method coll))))))

(declare difference-between-successive-elements)

(defn- first-method [coll]
  (reduce +
       (filter #(not (neg? %))
               (difference-between-successive-elements coll))))

(defn- second-method [coll]
  (let [max-rate (apply max
                        (difference-between-successive-elements coll))
        min-eat-coll (map #(min max-rate %) (drop-last coll))]
    (reduce + min-eat-coll)))

(defn- difference-between-successive-elements [coll]
  (map (fn [[a b]] (- a b))
       (partition 2 1 coll)))

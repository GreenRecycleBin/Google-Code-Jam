(ns round-1a.b
  (:require [clojure.string :as string]))

(declare read-input-and-solve)

(defn -main [&]
  (let [c (Integer/parseInt (read-line))]
    (dotimes [n c] (read-input-and-solve n))))

(declare read-all-choices)
(declare convert-option-from-boolean-to-number)
(declare convert-all-choices-to-hash)
(declare solve)

(defn read-input-and-solve [n]
  (let [num-flavors (Integer/parseInt (read-line))
        num-people (Integer/parseInt (read-line))
        all-choices (read-all-choices num-people)]
    (let [option (solve
                  (vec (take num-flavors (repeat false)))
                  (convert-all-choices-to-hash all-choices))]
      (println (str "Case #" (inc n) ": "
                    (if option
                      (string/join
                       " "
                       (convert-option-from-boolean-to-number option))
                      "IMPOSSIBLE"))))))

(defn read-all-choices
  ([n] (read-all-choices n []))

  ([n all-choices]
   (if (zero? n)
     all-choices
     (let [coll1 (rest (map #(Integer/parseInt %) (string/split (read-line) #"\s+")))]
       (read-all-choices (dec n) (conj all-choices (partition-all 2 coll1)))))))

(defn convert-option-from-boolean-to-number [option]
  (map #(if % 1 0) option))

(declare has-malted-choice?)
(declare convert-all-choices-from-number-to-boolean)

(defn convert-all-choices-to-hash [all-choices]
  (group-by #(if (has-malted-choice? %) :malted :unmalted)
            (convert-all-choices-from-number-to-boolean all-choices)))

(defn has-malted-choice? [choices]
  (some (fn [[_ is-malted]] is-malted) choices))

(defn convert-all-choices-from-number-to-boolean [all-choices]
  (map #(map (fn [[index value]] (if (zero? value) [index false] [index true]))
             %)
       all-choices))

(declare get-index-of-malted-choice)
(declare satisfy?)

(defn solve
  [option choices-hash]
  (if (and (every? #(satisfy? option %) (choices-hash :malted))
           (every? #(satisfy? option %) (choices-hash :unmalted)))
    option
    (if-let [choices (first (choices-hash :malted))]
      (let [new-option (assoc option (get-index-of-malted-choice choices) true)]
        (solve new-option (update-in choices-hash [:malted] #(rest %))))
      (when (every? #(satisfy? option %) (choices-hash :unmalted))
        option))))

(defn get-index-of-malted-choice [choices]
  (dec (some (fn [[index is-malted]] (when is-malted index)) choices)))

(defn satisfy? [option choices]
  (some (fn [[index is_malted]] (= is_malted (option (dec index))))
        choices))

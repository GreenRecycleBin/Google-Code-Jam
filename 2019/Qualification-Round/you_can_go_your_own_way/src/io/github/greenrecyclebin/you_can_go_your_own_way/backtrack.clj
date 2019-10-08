(ns io.github.greenrecyclebin.you-can-go-your-own-way.backtrack
  (:require
   [clojure.pprint]))

(set! *warn-on-reflection* true)
(set! *unchecked-math* :warn-on-boxed)

(def ^:private starting-cell [0 0])
(def ^:private directions #{\E \S})

(defn solve [^long size exclusion-directions]
  (letfn [(solve [^long n paths exclusion-path]
            (println "Solving for n =" n)
            (if (zero? n)

              (vec
               (for [direction directions]
                 [{:cell starting-cell :direction direction}]))

              (let [paths (solve (dec n) paths exclusion-path)
                    new-paths (mapcat #(generate-paths % exclusion-path) paths)]
                (filter (fn [path] (valid? n path exclusion-path))
                        new-paths))))

          (valid?
            ([n path exclusion-path]
             (let [edge (nth path n)
                   edge' (nth exclusion-path n)]
               (and (valid? edge)
                    (not (same-edge? edge edge')))))

            ([edge]
             (let [{:keys [cell direction]} edge
                   [x y] (next-cell cell direction)]
               (and (< -1 x size)
                    (< -1 y size)))))

          (same-edge? [edge edge']
            (let [{:keys [cell direction]} edge
                  {cell' :cell direction' :direction} edge'
                  next-cell' (next-cell cell' direction')
                  next-cell (next-cell cell direction)]
              (and (= direction direction') (= next-cell next-cell'))))


          (generate-paths [path exclusion-path]
            (let [cell (next-cell path)]
              (map #(conj path {:cell cell :direction %}) directions)))

          (next-cell
            ([cell direction]
             (let [[^long x ^long y] cell]
               (case direction
                 \E [x (inc y)]
                 \S [(inc x) y])))

            ([path]
             (if-let [edge (peek path)]
               (let [{:keys [cell direction]} edge]
                 (next-cell cell direction))

               starting-cell)))

          (generate-path-from-cell [cell directions]
            (loop [path []
                   cell cell
                   directions directions]
              (if-let [direction (first directions)]
                (recur (conj path {:cell cell :direction direction})
                       (next-cell cell direction)
                       (rest directions))

                path)))

          (format-path-as-directions [path]
            (map :direction path))]
    (->> (generate-path-from-cell starting-cell exclusion-directions)
         (solve (dec (* 2 (dec size))) nil)
         (map
          (fn [path]
            (apply str (format-path-as-directions path)))))))

(defn- generate-random-moves [^long size]
  (let [directions (vec directions)
        move-count (* 2 (dec size))
        direction-limit (dec size)]
    (loop [n move-count
           moves (new StringBuilder n)
           E-count 0
           S-count 0]
      (cond
        (zero? n) (str moves)

        (= E-count direction-limit)
        (do
          (.insert moves (- move-count n) (apply str (repeat (- direction-limit S-count) \S)))
          (str moves))

        (= S-count direction-limit)
        (do
          (.insert moves (- move-count n) (apply str (repeat (- direction-limit E-count) \E)))
          (str moves))

        :default
        (let [direction (rand-nth directions)]
          (recur (dec n)
                 (.append moves direction)
                 (if (= direction \E) (inc E-count) E-count)
                 (if (= direction \S) (inc S-count) S-count)))))))

;; (some #{"SE"} (solve 2 "ES"))
;; (some #{"SEEESSES"} (solve 5 "EESSSESE"))
;; (time (first (solve 100 "SESSSEEEEEEESESEEESESESSESESSSEEEESESEESEESESEESSESSESEEEESSSSESEESSSSESESEESEEESSESEESSSSEESSSESESESSSSESSSEESESSSESEEESSEEESESEESEEEESEESSSESEESSSESSEEESEEEESSESSSSESESESEESEEEESEESEESESSSESSSSSSS")))
;; (time (first (solve 1000 "ESESESSEESEEESEESESEEEEESSSSESEESEEEEEEEESSSSEESEESEEEESEEESESSEEEESSSSESEEESESSSSSEESSSEEESESEEESSEESEESSESSSESESSSSSSESESSSESEESESESSSSESSESEEESSESEESESSEESESESEEESEEESESSSEEEESSSEEESSESSSSSESESEEESESSESESSSSSEEESSESSESSESEESSEEEEEESSSSEESEESESSSESESESESESSEESSESSSSEESESEESEESEESSESESEEEESSSEEESSESEESSESESSSESEEESSESSSEEEESSESSSESSESSSSESEESSEEESEESESSSSEESESSEEESSEEEEESSESEESESSSESSSESESESEESESSSSSESESEESEEESSSSSESSEEEESEESESEEEEESESESSESSESSSEEESSEESSESEEEESSESSSEEESESSSSSSEESSESSEEESSEEESSSESSESESSSSESESSSESESSSSESESSEEEESSESSSSESSSESEESSESEESSSSESSSESESESESESSESEEEEESESEESSSSSESSEESSSEESEEEESSESESSESSESSSSEESSESEEEEEESESSSSSEEESESEEEESEESESSSSSSEESEEEESSSESESSESESESSESEESESSEESSSESESESSESSESSSESESSSSSSEESSESEEEEEEESSSESSEEESESSSESEEESESEEEESSSSEESEESEESEESESSEESEESEESSEESESSESSSSSSESSESSSEEEESEEEEESSEEESSSESSSEESSEESEESEESEESEESSSEEESEESEEEESESSSSSEEEESSSESESESESEEEESSEESSSSSEESEESEEESSSSSESESESESSESEESSESSSESEESESESESSEESEEESSESEESESSEEEESSEEEEEESESSSEESESEEESEEEESSSEESESSESESEEESSSSESSSSSSEEESEEEEESEEESEESSSEEESSESEESSSSESSSESESESSEESEESSSSSEESEEESSSSESEEESEEEEESEEESSSEESEESSSEEESSEEESESSSSSSSEEEEESSEESESSSSSESESSSESESEEESEEEEESEEEESSSEESSSESSEESESSSEESESEESEEESESSSESSEESSEESSEEEEESSESSSEEEESSSSESEESESESSSESESSESSEESSSSESSSSESSEEESEESSSEEEEEEEESEEESEEEEESSEESSESSEESESEESSEEESESSESSEEESSSSSSSEEESSESSEEESSEEEESSESSEESEESEEESESSEEEEESEESEEESEEEESEEEESEEEESESSESEEEESSSSEESESSESEEESESEESSESEESEEEEESESEEESSEEESEESSSESEESSSEEESSSSSSSESESESEESEESESESEEESSSESSSSESSSSSESEEEEESEESEEESEEESSSSEESSEEESESEEEESSSSESEEESSSESESSEEEESSESESEESSESEEEEESSEEESESSESSEEEESSSEEEESEESSSSSEEEESEEESSEESSEESEEEESEESESEEESEESEEEEESEESESEESSSEESEEEEEESSEEEEEEEESEEESSEEEEESEEEESEESSESSSSSESSEEEEESESESSESEESSESEEEEEESESESSEESSSEESESEESSEESSESESESESSSESESSSESSSEEESSEEEESESESESEESSSSSEEEEEESSEESEEEESEEEESEEEESEESSESSSSESSSEEEEESESSSSESEEEESSEESSSSEEEEEESSSSESESESSSSEESSSESSESSSEEESEESEEESSESSEEESSESSSESEESEESESESESSESESSESEESEESSESSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS")))
;; "Elapsed time: 1468944.770808 msecs"

(let [size 1500
      exclusion-moves (generate-random-moves size)]
  (prn exclusion-moves)
  (time (first (solve size exclusion-moves))))

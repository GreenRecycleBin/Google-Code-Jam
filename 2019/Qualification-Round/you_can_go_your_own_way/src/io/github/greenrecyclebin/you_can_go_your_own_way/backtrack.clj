(ns io.github.greenrecyclebin.you-can-go-your-own-way.backtrack
  (:require
   [clojure.pprint :refer [cl-format pprint print-table]]))

(set! *warn-on-reflection* true)
(set! *unchecked-math* :warn-on-boxed)

(def ^:private starting-cell [0 0])
(def ^:private directions #{\E \S})

(defn solve [^long size exclusion-directions]
  (letfn [(solve [paths ^long i n exclusion-path]
            (println "Solving for i =" i)
            #_(printf "paths = %s\n" (cl-format nil "~s" paths))
            (if (= i n)
              paths

              (let [new-paths (mapcat #(generate-paths i % exclusion-path) paths)]
                (recur new-paths (inc i) n exclusion-path))))

          (valid?
            ([n path exclusion-path]
             (let [edge (nth path n)
                   edge' (nth exclusion-path n)]
               (and (valid? edge)
                    (not (same-edge? edge edge')))))

            ([edge]
             (let [{:keys [cell direction]} edge
                   [x y] (next-cell cell direction)]
               (and (< -1 x size) (< -1 y size)))))

          (same-edge? [edge edge']
            (let [{:keys [cell direction]} edge
                  {cell' :cell direction' :direction} edge'
                  next-cell' (next-cell cell' direction')
                  next-cell (next-cell cell direction)]
              (and (= direction direction') (= next-cell next-cell'))))


          (generate-paths [^long n path exclusion-path]
            (let [cell (next-cell path)
                  new-paths (map #(conj path {:cell cell :direction %}) directions)
                  filtered-new-paths (filter (fn [path] (valid? (inc n) path exclusion-path))
                                             new-paths)]
              filtered-new-paths))

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
         (solve (vec
                 (for [direction directions]
                   [{:cell starting-cell :direction direction}]))

                0
                (dec (* 2 (dec size))))
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

(defn- generate-worst-case-moves [^long size]
  (let [move-count (* 2 (dec size))]
    (->> (take move-count (cycle directions))
         (apply str))))

#_(some #{"SE"} (solve 2 "ES"))
#_(some #{"SEEESSES"} (solve 5 "EESSSESE"))
#_(time (first (solve 100 "SESSSEEEEEEESESEEESESESSESESSSEEEESESEESEESESEESSESSESEEEESSSSESEESSSSESESEESEEESSESEESSSSEESSSESESESSSSESSSEESESSSESEEESSEEESESEESEEEESEESSSESEESSSESSEEESEEEESSESSSSESESESEESEEEESEESEESESSSESSSSSSS")))
#_(first (solve 1000 "ESESESSEESEEESEESESEEEEESSSSESEESEEEEEEEESSSSEESEESEEEESEEESESSEEEESSSSESEEESESSSSSEESSSEEESESEEESSEESEESSESSSESESSSSSSESESSSESEESESESSSSESSESEEESSESEESESSEESESESEEESEEESESSSEEEESSSEEESSESSSSSESESEEESESSESESSSSSEEESSESSESSESEESSEEEEEESSSSEESEESESSSESESESESESSEESSESSSSEESESEESEESEESSESESEEEESSSEEESSESEESSESESSSESEEESSESSSEEEESSESSSESSESSSSESEESSEEESEESESSSSEESESSEEESSEEEEESSESEESESSSESSSESESESEESESSSSSESESEESEEESSSSSESSEEEESEESESEEEEESESESSESSESSSEEESSEESSESEEEESSESSSEEESESSSSSSEESSESSEEESSEEESSSESSESESSSSESESSSESESSSSESESSEEEESSESSSSESSSESEESSESEESSSSESSSESESESESESSESEEEEESESEESSSSSESSEESSSEESEEEESSESESSESSESSSSEESSESEEEEEESESSSSSEEESESEEEESEESESSSSSSEESEEEESSSESESSESESESSESEESESSEESSSESESESSESSESSSESESSSSSSEESSESEEEEEEESSSESSEEESESSSESEEESESEEEESSSSEESEESEESEESESSEESEESEESSEESESSESSSSSSESSESSSEEEESEEEEESSEEESSSESSSEESSEESEESEESEESEESSSEEESEESEEEESESSSSSEEEESSSESESESESEEEESSEESSSSSEESEESEEESSSSSESESESESSESEESSESSSESEESESESESSEESEEESSESEESESSEEEESSEEEEEESESSSEESESEEESEEEESSSEESESSESESEEESSSSESSSSSSEEESEEEEESEEESEESSSEEESSESEESSSSESSSESESESSEESEESSSSSEESEEESSSSESEEESEEEEESEEESSSEESEESSSEEESSEEESESSSSSSSEEEEESSEESESSSSSESESSSESESEEESEEEEESEEEESSSEESSSESSEESESSSEESESEESEEESESSSESSEESSEESSEEEEESSESSSEEEESSSSESEESESESSSESESSESSEESSSSESSSSESSEEESEESSSEEEEEEEESEEESEEEEESSEESSESSEESESEESSEEESESSESSEEESSSSSSSEEESSESSEEESSEEEESSESSEESEESEEESESSEEEEESEESEEESEEEESEEEESEEEESESSESEEEESSSSEESESSESEEESESEESSESEESEEEEESESEEESSEEESEESSSESEESSSEEESSSSSSSESESESEESEESESESEEESSSESSSSESSSSSESEEEEESEESEEESEEESSSSEESSEEESESEEEESSSSESEEESSSESESSEEEESSESESEESSESEEEEESSEEESESSESSEEEESSSEEEESEESSSSSEEEESEEESSEESSEESEEEESEESESEEESEESEEEEESEESESEESSSEESEEEEEESSEEEEEEEESEEESSEEEEESEEEESEESSESSSSSESSEEEEESESESSESEESSESEEEEEESESESSEESSSEESESEESSEESSESESESESSSESESSSESSSEEESSEEEESESESESEESSSSSEEEEEESSEESEEEESEEEESEEEESEESSESSSSESSSEEEEESESSSSESEEEESSEESSSSEEEEEESSSSESESESSSSEESSSESSESSSEEESEESEEESSESSEEESSESSSESEESEESESESESSESESSESEESEESSESSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS"))
;; "Elapsed time: 1468944.770808 msecs"

#_(solve 3 "SSEE")
#_("EESS" "ESES" "ESSE" "SEES" "SESE" "SSEE")

(defn solve-for-n-with-random-exclusion-paths [n]
  (let [size n
        exclusion-moves (generate-random-moves size)]
    (prn exclusion-moves)
    (time (first (solve size exclusion-moves)))))

(defn solve-for-n-with-worst-case-exclusion-paths [n]
  (let [size n
        exclusion-moves (generate-worst-case-moves size)]
    (prn exclusion-moves)
    (time (first (solve size exclusion-moves)))))

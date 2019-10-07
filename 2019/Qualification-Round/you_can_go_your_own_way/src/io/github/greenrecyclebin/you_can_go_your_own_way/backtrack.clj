(ns io.github.greenrecyclebin.you-can-go-your-own-way.backtrack)

(defn solve [n exclusion-path]
  (letfn [(solve [size n paths exclusion-path]
            (println (format "(solve [%d %s %s])" n paths exclusion-path))
            (if (zero? n)
              [[[[0 0] \E]] [[[0 0] \S]]]
              (let [paths (solve size (dec n) paths exclusion-path)
                    new-paths (mapcat #(generate-paths % exclusion-path) paths)]
                (filter
                 (fn [path] (valid? size n path exclusion-path))
                 new-paths))))

          (valid? [size n paths exclusion-path]
            (println (format "(valid? [size %d %s %s])" size n paths exclusion-path))

            (let [[[x y] move] (nth paths n)
                  [[x' y'] move'] (nth exclusion-path n)
                  result (not (or (and (= move move')
                                       (= x x')
                                       (= y y'))
                                  (and (= move \E)
                                       (>= (inc y) size))
                                  (and (= move \S)
                                       (>= (inc x) size))))]
              (println "[[x y] move] =" [[x y] move])
              (println "[[x' y'] move'] =" [[x' y'] move'])
              (println "result =" result)
              result))

          (generate-paths [path exclusion-path]
            (let [[x y] (current-cell path)]
              [(conj path [[x y] \E])
               (conj path [[x y] \S])]))

          (current-cell [path]
            (println (format "(current-cell [%s])" path))
            (if-let [last-path-component (peek path)]
              (let [[[x y] move] last-path-component]
                (case move
                  \E [x (inc y)]
                  \S [(inc x) y]))
              [0 0]))]
    (solve n (dec (- (* 2 n) 2)) nil exclusion-path)))

(solve 2 [[[0 0] \S] [[1 0] \E]])
(solve 5 [[[0 0] \E] [[0 1] \E] [[0 2] \S] [[1 2] \S] [[2 2] \S] [[2 3] \E] [[3 3] \S] [[4 3] \E]])

;; paths = [[[0 0] "E"] [[0 1] "S"]]

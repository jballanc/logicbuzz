(ns logicbuzz.core
  (:require [clojure.core.logic :as cl]
            [clojure.core.logic.fd :as fd])
  (:gen-class))

(cl/defne fizzo
  "Succeeds if x is evenly divisible by 3"
  [x l]
  ([3 "fizz"])
  ([x "fizz"]
   (cl/fresh [a]
             (fd/in a (fd/interval 3 100))
             (fd/+ a 3 x)
             (fizzo a l))))

(cl/defne buzzo
  "Succeeds if x is evenly divisible by 5"
  [x l]
  ([5 "buzz"])
  ([_ "buzz"]
   (cl/fresh [a]
             (fd/in a (fd/interval 5 100))
             (fd/+ a 5 x)
             (buzzo a l))))

(cl/defnu fizzbuzzo
  "- If x is divisible by 3, unifies with 'fizz'
   - If x is divisible by 5, unifies with 'buzz'
   - If x is divisible by 3 and 5, unifies with 'fizzbuzz'
   - Otherwise unifies with x"
  [x l]
  ([_ "fizzbuzz"]
   (cl/fresh [a b]
             (fizzo x a)
             (buzzo x b)))
  ([_ "fizz"]
   (cl/fresh [a]
             (fizzo x a)))
  ([_ "buzz"]
   (cl/fresh [a]
             (buzzo x a)))
  ([_ x]))

(defn fizzbuzz
  "FizzBuzz!"
  []
  (let [fb (map #(cl/run* [q] (fizzbuzzo % q)) (range 1 101))]
    (doseq [[x] fb]
      (println x))))

(defn -main
  "LogicBuzz up to 100"
  [& args]
  (fizzbuzz))

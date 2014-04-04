(ns logicbuzz.core
  (:require [clojure.core.logic :as cl]
            [clojure.core.logic.fd :as fd])
  (:gen-class))

(cl/defne modo
  "Unifies r with the remainder after dividing p by q"
  [p q r]
  ([p _ p]
   (fd/< p q))
  ([_ _ r]
   (cl/fresh [a]
             (fd/in a (fd/interval 0 Integer/MAX_VALUE))
             (fd/+ a q p)
             (modo a q r))))

(cl/defnu fizzbuzzo
  "- If x is divisible by 3, unifies with 'fizz'
   - If x is divisible by 5, unifies with 'buzz'
   - If x is divisible by 3 and 5, unifies with 'fizzbuzz'
   - Otherwise x unifies with itself"
  [x l]
  ([_ "fizzbuzz"]
   (modo x 3 0)
   (modo x 5 0))
  ([_ "fizz"]
   (modo x 3 0))
  ([_ "buzz"]
   (modo x 5 0))
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

(ns better-ber.core
    (:require [better-ber.seai-client :as seai]))

(defn from-html 
  "Run the html extraction"
  [number]
  (def result (seai/get-ber number))
  (println result))

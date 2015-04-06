(ns better-ber.seai-result-parser
    (:require [clojure.string :as str]))


(def form-keys {
   :address "ctl00_DefaultContent_BERSearch_dfBER_div_PublishingAddress"
   :energy-rating "ctl00_DefaultContent_BERSearch_dfBER_div_EnergyRating"
   :emissions-indicator "ctl00_DefaultContent_BERSearch_dfBER_div_CDERValue"
   :date-of-issue "ctl00_DefaultContent_BERSearch_dfBER_container_DateOfIssue"
   :date-valid-until "ctl00_DefaultContent_BERSearch_dfBER_container_DateValidUntil"
   :mprn "ctl00_DefaultContent_BERSearch_dfBER_container_MPRN"
   :dwelling-type "ctl00_DefaultContent_BERSearch_dfNasStructuralDetails_container_DwellingType"
  })

(defn- el
  "Get the element by id"
  [page id]
   (.getHtmlElementById page id))

(defn- txt 
  "Get the text from the element"
  [page id]
  (str/trim (.asText (el page id))))

(defn- xpath 
  "Get the xpath result"
  [page id]
  (let 
    [els (.getByXPath page (str "//*[@id='" id "']/div/text()"))
     text (str/trim (.asText (first els)))]
    text ))

(defn- get-value 
  "get the value"
  [page]
  (fn [[k v]] 
    (def result 
      (if (.contains v "_div_")
        (txt page v)
        (xpath page v)))
    [k result])
  )

(defn parse
  "Parse the information from the seai html page"
  [page]
  (def res (map (get-value page) form-keys))
  (into {} res)
 )


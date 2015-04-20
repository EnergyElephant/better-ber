(ns better-ber.seai-result-parser
    (:require [clojure.string :as str]))


(def form-keys {
  :address "ctl00_DefaultContent_BERSearch_dfBER_div_PublishingAddress"
  :energy-rating "ctl00_DefaultContent_BERSearch_dfBER_div_EnergyRating"
  :co2-emissions-indicator "ctl00_DefaultContent_BERSearch_dfBER_div_CDERValue"
  :date-of-issue "ctl00_DefaultContent_BERSearch_dfBER_container_DateOfIssue"
  :date-valid-until "ctl00_DefaultContent_BERSearch_dfBER_container_DateValidUntil"
 	:ber-number "ctl00_DefaultContent_BERSearch_dfBER_container_BERNumber" 
  :mprn "ctl00_DefaultContent_BERSearch_dfBER_container_MPRN"
  :deap-version "ctl00_DefaultContent_BERSearch_dfBER_container_BERTool"
  :type-of-rating "ctl00_DefaultContent_BERSearch_dfBER_container_TypeOfRating"
  :dwelling-type "ctl00_DefaultContent_BERSearch_dfNasStructuralDetails_container_DwellingType"
  :no-of-storeys "ctl00_DefaultContent_BERSearch_dfNasStructuralDetails_container_NoStoresy"
  :year-of-construction "ctl00_DefaultContent_BERSearch_dfNasStructuralDetails_container_DateOfConstruction"
  :floor-area "ctl00_DefaultContent_BERSearch_dfNasStructuralDetails_div_FloorArea"
  :wall-type "ctl00_DefaultContent_BERSearch_dfNasStructuralDetails_container_WallType"
  :glazing-type "ctl00_DefaultContent_BERSearch_dfNasStructuralDetails_container_GlazingType"
  :percentage-low-energy-lighting "ctl00_DefaultContent_BERSearch_dfNasStructuralDetails_container_PercentLowEnergyLight"
  :main-space-heating-fuel "ctl00_DefaultContent_BERSearch_dfNasStructuralDetails_container_MainSpaceHeatingFuel"
  :main-space-heating-efficiency "ctl00_DefaultContent_BERSearch_dfNasStructuralDetails_container_MainSpaceHeatingEfficiency"
  :main-water-heating-fuel "ctl00_DefaultContent_BERSearch_dfNasStructuralDetails_container_MainWaterHeatingFuel"
 	:main-water-heating-efficiency "ctl00_DefaultContent_BERSearch_dfNasStructuralDetails_container_MainWaterHeatingEfficiency"
})

(defn- el
  "Get the element by id"
  [page id]
   (.getHtmlElementById page id))

(defn- txt 
  "Get the text from the element"
  [page id]
  (str/trim (.asText (el page id))))

(defn- div-text 
  "Get the div/text() of the given id result"
  [page id]
  (let 
    [ els (.getByXPath page (str "//*[@id='" id "']/div/text()"))
      el (first els)
      raw-text (.asText el)
      text (str/trim raw-text)]
    text ))

(defn- get-value 
  "get the value"
  [page]
  (fn [[k v]] 
    (def result 
      (if (.contains v "_div_")
        (txt page v)
        (div-text page v)))
    [k result])
  )

(defn parse
  "Parse the information from the seai html page"
  [page]
  (def res (map (get-value page) form-keys))
  (into {} res)
 )


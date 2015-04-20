(ns better-ber.seai-result-parser-test

  (:import 
    (com.gargoylesoftware.htmlunit WebClient StringWebResponse)
    (com.gargoylesoftware.htmlunit.html HTMLParser)
    (java.net URL))

  (:require [clojure.test :refer :all]
            [better-ber.seai-result-parser :refer :all]))

(def html-file-path "better_ber/seai_result.html")

(defn load-page
  "Load html at path to HtmlPage"
  [path]
  (let 
    [ url (clojure.java.io/resource path)
      unit-one (prn url)
      client (new WebClient)
      page (.getPage client url)]
     page))

(deftest parse-html 
  (testing "The parser converts the html into a map"
    (def page (load-page html-file-path))
    (def out (better-ber.seai-result-parser/parse page))
    (is (= (:address out) "46 CARRICKBRACK HILL\nSUTTON\nDUBLIN 13"))
    (is (= (:energy-rating out) "E1   321.78 (kWh/m2/yr)"))
    (is (= (:co2-emissions-indicator out) "68.22 (kgCO2/m2/yr)"))
    (is (= (:date-of-issue out) "10-03-2015"))
    (is (= (:date-valid-until out) "10-03-2025"))
    (is (= (:ber-number out) "107385114"))
    (is (= (:mprn out) "10003671500"))
    (is (= (:type-of-rating out) "Existing Dwelling"))
    (is (= (:deap-version out) "3.2.1"))
    (is (= (:dwelling-type out) "Detached house"))
    (is (= (:no-of-storeys out) "2"))
    (is (= (:year-of-construction out) "1975"))
    (is (= (:floor-area out) "194.94 (m2)"))
    (is (= (:wall-type out) "Masonry"))
    (is (= (:glazing-type out) "Single / Double Glazing"))
    (is (= (:percentage-low-energy-lighting out) "3%"))
    (is (= (:main-space-heating-fuel out) "Gas"))
    (is (= (:main-space-heating-efficiency out) "73%"))
    (is (= (:main-water-heating-fuel out) "Gas"))
    (is (= (:main-water-heating-efficiency out) "73%"))
     ))

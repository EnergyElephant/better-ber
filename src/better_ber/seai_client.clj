(ns better-ber.seai-client
	(:require [better-ber.seai-result-parser :as srp])
  (:import 
  	(com.gargoylesoftware.htmlunit WebClient BrowserVersion)))

(def ^:private form-keys {
  :ber-input "ctl00$DefaultContent$BERSearch$dfSearch$txtBERNumber"
	:search-button	"ctl00$DefaultContent$BERSearch$dfSearch$Bottomsearch"
  :view-details "ctl00_DefaultContent_BERSearch_gridRatings_gridview_ctl02_ViewDetails"
	})

(def seai-search "https://ndber.seai.ie/pass/ber/search.aspx")

(defn get-ber
  "Load the ber information from seai.ie, using an html client"
  [number]
  (let [client (new WebClient)
		    start (.getPage client seai-search)
		    form (.getFormByName start "aspnetForm")
		    number-input (.getInputByName form (:ber-input form-keys))
		    submit (.getInputByName form (:search-button form-keys))]
    (.setValueAttribute number-input number)
    (def result (.click submit))
    (let [form-two (.getFormByName result "aspnetForm")
          link (.getElementById result (:view-details form-keys))
          details-page (.click link)]
      (srp/parse details-page))))

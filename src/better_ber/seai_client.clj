(ns better-ber.seai-client
  (:import (com.gargoylesoftware.htmlunit WebClient BrowserVersion)))

(def seai-search "https://ndber.seai.ie/pass/ber/search.aspx")

(defn get-ber
  "Load the ber information from seai.ie, using an html client"
  [number]
  (let
    [client (new WebClient)
     start (.getPage client seai-search)
     form (.getFormByName start "aspnetForm")
     number-input (.getInputByName form "ctl00$DefaultContent$BERSearch$dfSearch$txtBERNumber")
     submit (.getInputByName form "ctl00$DefaultContent$BERSearch$dfSearch$Bottomsearch")]
     (.setValueAttribute number-input number)
     (def result (.click submit))
     (prn (.asXml result))
     (let [
        form-two (.getFormByName result "aspnetForm")
        link (.getElementById result "ctl00_DefaultContent_BERSearch_gridRatings_gridview_ctl02_ViewDetails")
        details-page (.click link)]
       (prn (.asXml details-page))
      )
    )
  number)

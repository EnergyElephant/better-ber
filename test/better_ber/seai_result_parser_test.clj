(ns better-ber.seai-result-parser-test

  (:import 
    (com.gargoylesoftware.htmlunit WebClient StringWebResponse)
    (com.gargoylesoftware.htmlunit.html HTMLParser)
    (java.net URL)
    )

  (:require [clojure.test :refer :all]
            [better-ber.seai-result-parser :refer :all]))

(def html-file-path "better_ber/seai_result.html")

(comment
  import com.gargoylesoftware.htmlunit.*
  import com.gargoylesoftware.htmlunit.html.*
  URL url = new URL("http://edeustace.com");
  StringWebResponse response = new StringWebResponse("<html><head><title>Test</title></head><body></body></html>", url);
  WebClient client = new WebClient()
  HtmlPage page = HTMLParser.parseHtml(response, client.getCurrentWindow());
  System.out.println(page.getTitleText());

  webClient.getPage(java_io_file.toURL());
)


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
     ))

(ns better-ber.service
  (:require [better-ber.dao :as dao]
            [taoensso.timbre :as timbre]
            [better-ber.seai-client :as seai]))

(timbre/refer-timbre)

(defn- scrape-and-save [db input]
  "Scrape the info and save it to the db"
  (debug "load ber from seai" input)
  (def scraped (seai/get-ber input))
  (if scraped
    (dao/update db input scraped)
    nil)
  scraped)

(defn get-ber [db input]
  "Get the ber info, try the db and if not present scrape it and save to the db"
  (debug "get-ber input: " input)
  (def db-result (dao/find db input))
  (if (not db-result)
    (scrape-and-save db input)
    db-result))

(ns better-ber.dao
  ;(:refer-clojure :exclude [find])
  (:require
    [taoensso.timbre :as timbre]
    [monger.json]
    [monger.core :as mg]
    [monger.collection :as mc]
    [clojure.java.io :as io]
    [clojure.string :as str]))

(timbre/refer-timbre)

(defn init-index [db]
  "init 'ber-number' unique indexes"
  (debug "initing db index for 'ber-number'")
  (mc/ensure-index db "bers" (array-map :ber-number 1) { :unique true }))

(defn find [db number]
  "find by ber number"
  (debug "find by :ber-number" number)
  (mc/find-one db "bers" {:ber-number number}))

(defn rm [db number]
  "Rm db entry"
  (mc/remove db "bers" {:ber-number number}))

(defn update [db number data] "update the ber details"
  (def merged (merge {:ber-number number} data))
  (debug "add to bers collection:" merged)
  (mc/update db "bers" {:ber-number number} merged {:upsert true}))

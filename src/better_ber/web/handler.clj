(ns better-ber.web.handler
  (:require [taoensso.timbre :as timbre]
            [compojure.core :refer :all]
            [ring.middleware.json :as middleware]
            [compojure.handler :as handler]
            [clojure.string :as str]
            [ring.util.response :as r]
            [compojure.route :as route]
            [monger.core :as mg]
            [monger.json]
            [monger.conversion]
            [monger.cursor :as mc]
            [better-ber.service :as bbs]
            [better-ber.dao :as dao])

  (:import
    [com.mongodb MongoOptions ServerAddress]
    ))

(timbre/refer-timbre)

(def app
  (let [ uri (get (System/getenv) "MONGOLAB_URI" "mongodb://localhost/better-ber")
         {:keys [conn db]} (mg/connect-via-uri uri)]

    (info "Bootstrapping routes!!")
    (dao/init-index db)
    (defroutes app-routes
      (GET "/" [] {:body {:message "Better ber"}})

      (GET "/:ber-number.json" [ber-number]
        (if (re-matches #"[0-9]*" ber-number)
          (let
            [ber-info (bbs/get-ber db ber-number)]
            (if ber-info
              (debug "found info for" ber-number)
              (warn "can't find info for" ber-number))
            (if (not ber-info)
              {:status 404}
              {:body (monger.conversion/from-db-object ber-info false)}))
          {:status 400 :body {:error "bad format"}}))

      (DELETE "/:ber-number.json" [ber-number]
        (if (re-matches #"[0-9]*" ber-number)
          (let
           [success (dao/rm db ber-number)]
              (if success
                { :body { :message (str "Successfully removed " ber-number)}}
                {:status 404})
           )
          {:status 400 :body {:error "bad format"}})))

    ( ->
         (handler/api app-routes)
         (middleware/wrap-json-body)
         (middleware/wrap-json-params)
         (middleware/wrap-json-response)
         )))

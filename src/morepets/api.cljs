(ns morepets.api
     (:require [ajax.core :refer [GET PUT POST]]
               [morepets.config :as config]
               [clojure.walk :as cljwalk]))

(defn unwrap-response [response]
 (cljwalk/keywordize-keys
  (js->clj response)))

(defn debug-handler [response]
 (.log js/console (unwrap-response response)))

(defn error-handler [{:keys [status status-text]}]
  (.log js/console (str "something bad happened: " status " " status-text)))

(defn get-pets
 [options]
 (GET (str config/server-url "/pets")
      options))

(defn put-pet
 [id options]
 (PUT (str config/server-url "/pets/" id)
      options))

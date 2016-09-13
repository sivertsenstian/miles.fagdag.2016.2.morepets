(ns morepets.handlers
    (:require [re-frame.core :as re-frame]
              [morepets.db :as db]
              [morepets.api :as api]))

(re-frame/reg-event-db
 :initialize-db
 (fn  [_ _]
   db/default-db))

(re-frame/reg-event-db
 :set-active-panel
 (fn [db [_ active-panel]]
   (assoc db :active-panel active-panel)))

(re-frame/reg-event-db
 :select
 (fn [db [_ item]]
  (js/console.log "Selecting!")
  (js/console.log item)
  (assoc db :selected item)))

(re-frame/reg-event-db
 :request-pets
 (fn [db _]
  (api/get-pets {:handler #(re-frame/dispatch [:receive-pets (api/unwrap-response %)])
                 :error-handler #(api/error-handler %)})
  db))

(re-frame/reg-event-db
 :receive-pets
 (fn [db [_ pets]]
  (assoc db :pets pets)))

(re-frame/reg-event-db
 :request-save-pet
 (fn [db [_ pet]]
  (api/put-pet (:id pet)
               {:params pet
                :format :json
                :handler #(do
                           (js/console.log "SAVE OK!")
                           (re-frame/dispatch [:select nil]))
                :error-handler #(api/error-handler %)})
  db))


(re-frame/reg-event-db
 :update-item
 (fn [db [_ collection id key value]]
  (assoc
   db
   collection
   (map
    #(if (= (:id %) id)
      (assoc % key value)
      %)
    (get db collection)))))

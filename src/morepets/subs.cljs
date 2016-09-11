(ns morepets.subs
    (:require-macros [reagent.ratom :refer [reaction]])
    (:require [re-frame.core :as re-frame]))

;;general
(re-frame/reg-sub
 :name
 (fn [db]
   (:name db)))

(re-frame/reg-sub
 :active-panel
 (fn [db _]
   (:active-panel db)))

(re-frame/reg-sub
 :selected
 (fn [db]
  (:selected db)))

;;pets
(re-frame/reg-sub
 :pets
 (fn [db]
  (:pets db)))

;;robots
(re-frame/reg-sub
 :robots
 (fn [db]
  (:robots db)))

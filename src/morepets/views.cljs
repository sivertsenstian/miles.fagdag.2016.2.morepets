(ns morepets.views
    (:require [re-frame.core :as re-frame]))

;; error/404
(defn error-panel []
 (fn []
  [:div.col-xs-12.text-xs-center
   [:h1 "404"]
   [:img.bordered {:src "/img/404.jpg"}]]))

;; home
(defn home-panel []
  (let [name (re-frame/subscribe [:name])]
    (fn []
      [:div.col-xs-12.text-xs-center
       [:h1 "Hello Miles!"]
       [:hr]
       [:img.bordered {:src "img/miles.png"}]])))

;; pets
(declare pet-row)
(declare selected-pet)
(declare form-row)

(defn pets-panel []
  (re-frame/dispatch [:request-pets])
  (fn []
    (let [pets (re-frame/subscribe [:pets])
          selected (re-frame/subscribe [:selected])]
     [:div.col-xs-12
      [:h1.text-xs-center "Pets!"]
      [:hr]
      (when @selected
       (selected-pet (first
                      (filter #(= (:id %) @selected) @pets))))
      [:div.col-xs-12]
      (if (> (count @pets) 0)
       [:table.table.table-bordered.table-hover
        [:thead
         [:tr
          [:th "Id"]
          [:th "Name"]
          [:th "Gender"]
          [:th ""]]]
        [:tbody
         (map pet-row @pets)]]

       [:div.col-xs-12.text-xs-center
        [:h3 "No pets :("]])])))

(defn pet-row [{:keys [id name gender url] :as pet}]
 ^{:key id}
 [:tr {:on-click #(re-frame/dispatch [:select id])}
  [:td id]
  [:td name]
  [:td [:img {:src (case gender
                    "Female" "img/female.jpg"
                    "Male" "img/male.jpg"
                    "img/transgender.png")
              :height 25}]]
  [:td [:img {:src url :height 50}]]])

(defn selected-pet [{:keys [id name gender url] :as pet}]
 ^{:key id}
 [:div.row.jumbotron
  [:div.pull-xs-right.btn.btn-secondary
   {:on-click #(re-frame/dispatch [:select nil])
    :style {:position "relative"
            :top -55
            :left 15}}
   "X"]

  [:div.col-xs-12
   [:div.form-group.row
    [:label.col-xs-1.col-form-label "Id: "]
    [:div.col-xs-2
     [:div.form-control.tag.tag-info {:style {:padding 15}}
      (str "# " id " ")]]]

   [form-row "Name" name {:collection :pets
                          :id id
                          :field :name}]
   [form-row "Gender" gender {:collection :pets
                              :id id
                              :field :gender}]
   [form-row "Url" url {:collection :pets
                        :id id
                        :field :url}]
   [:div.form-group.row
    [:div.col-xs-2.offset-xs-10
     [:div.btn.btn-success.btn-block
      {:on-click #(re-frame/dispatch [:request-save-pet pet])}
      "Save changes"]]]]])

;; robots
(declare robot-row)
(declare selected-robot)

(defn robots-panel []
  (re-frame/dispatch [:request-robots])
  (fn []
    (let [robots (re-frame/subscribe [:robots])
          selected (re-frame/subscribe [:selected])]
     [:div.col-xs-12
      [:h1.text-xs-center "Robots!"]
      [:hr]
      (when @selected
       (selected-robot (first
                        (filter #(= (:id %) @selected) @robots))))
      [:div.col-xs-12]
      (if (> (count @robots) 0)
       [:table.table.table-bordered.table-hover
        [:thead
         [:tr
          [:th "Id"]
          [:th "Name"]
          [:th "Color"]
          [:th "IP"]
          [:th ""]]]
        [:tbody
         (map robot-row @robots)]]

       [:div.col-xs-12.text-xs-center
        [:h3 "No robots :("]])])))

(defn robot-row [{:keys [id name color ipaddress url] :as robot}]
 ^{:key id}
 [:tr {:on-click #(re-frame/dispatch [:select id])}
  [:td id]
  [:td name]
  [:td {:style {:background-color color}}]
  [:td ipaddress]
  [:td [:img {:src url :height 50}]]])

(defn selected-robot [{:keys [id name color ipaddress url] :as robot}]
 ^{:key id}
 [:div.row.jumbotron
  [:div.pull-xs-right.btn.btn-secondary
   {:on-click #(re-frame/dispatch [:select nil])
    :style {:position "relative"
            :top -55
            :left 15}}
   "X"]

  [:div.col-xs-12
   [:div.form-group.row
    [:label.col-xs-1.col-form-label "Id: "]
    [:div.col-xs-2
     [:div.form-control.tag.tag-info {:style {:padding 15}}
      (str "# " id " ")]]]
   [form-row "Name" name {:collection :robots
                          :id id
                          :field :name}]
   [form-row "Color" color {:collection :robots
                            :id id
                            :field :color}]
   [form-row "Ip" ipaddress {:collection :robots
                             :id id
                             :field :ipaddress}]
   [form-row "Url" url {:collection :robots
                        :id id
                        :field :url}]

   [:div.form-group.row
    [:div.col-xs-2.offset-xs-10
     [:div.btn.btn-success.btn-block
      {:on-click #(re-frame/dispatch [:request-save-robot robot])}
      "Save changes"]]]]])

;; components
(defn form-row [label value {:keys [collection id field] :as update}]
  [:div.form-group.row
   [:label.col-xs-1.col-form-label (str label": ")]
   [:div.col-xs-11
    [:input.form-control {:default-value value
                          :on-input #(re-frame/dispatch
                                      [:update-item
                                       collection
                                       id
                                       field
                                       (-> % .-target .-value)])}]]])

(defn header []
 (fn [active-panel]
  [:nav.navbar.navbar-light.bg-faded
   [:a.navbar-brand {:href "#"} "MorePets!"]
   [:ul.nav.navbar-nav
    [:li.nav-item {:class (when (= @active-panel :home-panel) "active")}
     [:a.nav-link
      {:href "#/"
       :on-click #(re-frame/dispatch [:set-active-panel :home-panel])}
      "Home"]]
    [:li.nav-item {:class (when (= @active-panel :pets-panel) "active")}
     [:a.nav-link
      {:href "#/pets"
       :on-click #(re-frame/dispatch [:set-active-panel :pets-panel])}
      "Pets"]]
    [:li.nav-item {:class (when (= @active-panel :robots-panel) "active")}
     [:a.nav-link
      {:href "#/robots"
       :on-click #(re-frame/dispatch [:set-active-panel :robots-panel])}
      "Robots"]]]]))

;; main
(defmulti panels identity)
(defmethod panels :home-panel [] [home-panel])
(defmethod panels :pets-panel [] [pets-panel])
(defmethod panels :robots-panel [] [robots-panel])
(defmethod panels :default [] [error-panel])

(defn show-panel
  [panel-name]
  [panels panel-name])

(defn main-panel []
  (let [active-panel (re-frame/subscribe [:active-panel])]
    (fn []
      [:div.container
       [:div.row
        [header active-panel]]
       [:div.row
        [show-panel @active-panel]]])))

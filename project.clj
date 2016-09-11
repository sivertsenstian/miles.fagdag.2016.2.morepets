(defproject morepets "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure        "1.8.0"]
                 [org.clojure/clojurescript  "1.9.89"]
                 [reagent "0.6.0-rc"]
                 [binaryage/devtools "0.6.1"]
                 [re-frame "0.8.0"]
                 [secretary "1.2.3"]
                 [cljs-ajax "0.5.8"]]

  :plugins [[lein-cljsbuild "1.1.3"]
            [lein-less "1.7.5"]]

  :min-lein-version "2.5.3"

  :source-paths ["src/clj"]

  :clean-targets ^{:protect false} ["resources/public/js/compiled" "target"]

  :figwheel {:css-dirs ["resources/public/css"]}

  :less {:source-paths ["less"]
         :target-path  "resources/public/css"}

  :profiles
  {:dev
   {:dependencies []

    :plugins      [[lein-figwheel "0.5.4-3"]]}}


  :cljsbuild
  {:builds
   [{:id           "dev"
     :source-paths ["src"]
     :figwheel     {:on-jsload "morepets.core/mount-root"}
     :compiler     {:main                 morepets.core
                    :output-to            "resources/public/js/compiled/app.js"
                    :output-dir           "resources/public/js/compiled/out"
                    :asset-path           "js/compiled/out"
                    :source-map-timestamp true}}

    {:id           "min"
     :source-paths ["src"]
     :compiler     {:main            morepets.core
                    :output-to       "resources/public/js/compiled/app.js"
                    :optimizations   :advanced
                    :closure-defines {goog.DEBUG false}
                    :pretty-print    false}}]})

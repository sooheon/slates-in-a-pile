(ns everyday-adventures.slates.slate-4
  (:require [om.core :as om :include-macros true]
            [om-tools.core :refer-macros [defcomponent]]
            [om-tools.dom :refer [div h1 p]]
            [everyday-adventures.slates.slate-utils :refer [create-element-join
                                                            create-element-grouping
                                                            create-text-join]]))
(defn select-canvas []
  (.select js/d3 "#slate-4 .canvas"))

(defn initialize-canvas []
  (-> (.select js/d3 "#slate-4 .slate-content")
      (.append "svg")
      (.attr "class" "canvas")
      (.attr "width" 1088)
      (.attr "height" 300)))

(defn vectorize-constructs [constructs-data]
  (let [select-by-id (fn [id] (.select js/d3 (str "#slate-4 #" id)))]
    (mapv (comp select-by-id :id) constructs-data)))



;; statics ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; statics get created with constant data, and are always static ;;

(def services-properties
  [{:id "service-a" :text "service a" :y 200}])


(def environments-properties
  [{:id "dev" :text "dev" :x 300}
   {:id "qa" :text "qa" :x 600}
   {:id "prod" :text "prod" :x 900}])

(defn create-service-construct
  [{:keys [id text x y opacity] :or {x 0 opacity 1}}]
  (let [group-data {:class "service" :id id :x x :y y :opacity opacity}
        group (create-element-grouping (select-canvas) group-data)]
    (create-element-join group "rect" [{:width 100 :height 28 :x 5 :rx 10 :ry 10}])
    (create-text-join group [{:text text :dx 24 :dy 19}])
    (create-element-join group "line" [{:x1 105 :x2 320 :y1 14 :y2 14 :class "dashed"}
                                       {:x1 340 :x2 620 :y1 14 :y2 14}
                                       {:x1 640 :x2 920 :y1 14 :y2 14}
                                       {:x1 940 :x2 970 :y1 14 :y2 14}])))

(defn create-environment-construct
  [{:keys [id text x y opacity] :or {y 100 opacity 1}}]
  (let [group-data {:class "environment" :id id :x x :y y :opacity opacity}
        group (create-element-grouping (select-canvas) group-data)]
    (create-element-join group "circle" [{:cx 30 :cy 10 :r 24}
                                         {:cx 30 :cy 114 :r 10}])
    (create-text-join group [{:text text :dx 30 :dy 15}])
    (create-element-join group "line" [{:x1 30 :x2 30 :y1 34 :y2 103}
                                       {:x1 30 :x2 30 :y1 125 :y2 153}])))

(defn initialize-statics []
  (doseq [[create-construct construct-properties] [[create-service-construct services-properties]
                                                   [create-environment-construct environments-properties]]]
    (mapv (partial create-construct) construct-properties)))



;; animatics ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; animatics get created with constant data, but are subject to animizing as a function of step ;;

(def triggers-properties
  [{:id "trigger-a"
    :x 121
    :y 207
    :opacity 0
    :animize (fn [self step]
               (when (contains? #{1 2 3} step)
                 (-> self
                     .transition
                     (.duration 700)
                     (.attr "transform" "translate(231,207)")
                     (.attr "opacity" 1)
                     .transition
                     (.duration 600)
                     (.attr "transform" "translate(121,207)")
                     (.attr "opacity" 0))))}])

(defn create-trigger-construct
  [{:keys [id x y opacity]}]
  (let [group-data {:class "trigger" :id id :x x :y y :opacity opacity}
        group (create-element-grouping (select-canvas) group-data)]
    (create-element-join group "line" [{:x1 0 :x2 8 :y1 0 :y2 8}
                                       {:x1 8 :x2 0 :y1 6 :y2 14}
                                       {:x1 10 :x2 18 :y1 0 :y2 8}
                                       {:x1 18 :x2 10 :y1 6 :y2 14}
                                       {:x1 20 :x2 28 :y1 0 :y2 8}
                                       {:x1 28 :x2 20 :y1 6 :y2 14}])))

(defn initialize-animatics []
  (doseq [[create-construct constructs-properties] [[create-trigger-construct triggers-properties]]]
    (mapv (partial create-construct) constructs-properties)))

(defn animize-animatics [step]
  (let [animizers (mapv :animize triggers-properties)
        triggers (vectorize-constructs triggers-properties)]
    (mapv (fn [animizer trigger] (animizer trigger step)) animizers triggers)))



;; dynamics ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; dynamics get created with data as a function of step, and are subject to dynamizing as a function of step ;;

(def builds-properties
  [{:id "b1-a"
    :class "service-a"
    :text "b1"
    :opacity (fn [step] (if (> step 0) 1 0))
    :x (fn [step] (condp >= step 1 330 2 630 930))
    :y 214}
   {:id "b2-a"
    :class "service-a"
    :text "b2"
    :opacity (fn [step] (if (> step 1) 1 0))
    :x (fn [step] (if (> step 2) 630 330))
    :y 214}
   {:id "b3-a"
    :class "service-a"
    :text "b3"
    :opacity (fn [step] (if (> step 2) 1 0))
    :x 330
    :y 214}])

(defn create-build-construct [{:keys [id class text x y opacity]}]
  (let [group-data {:class (str "build " class) :id id :x x :y y :opacity opacity}
        group (create-element-grouping (select-canvas) group-data)]
    (create-text-join group [{:text text :dx 10 :dy 16}])
    (create-element-join group "circle" [{:cx 0 :cy 0 :r 7}])))

(defn evaluate [constructs-properties step]
  (let [apply-step (fn [v step] (if (fn? v) (v step) v))]
    (mapv
      #(reduce (fn [m [k v]] (assoc m k (apply-step v step))) {} %)
      constructs-properties)))

(defn initialize-dynamics [step]
  (doseq [[create-construct constructs-properties] [[create-build-construct builds-properties]]]
    (mapv (partial create-construct) (evaluate constructs-properties step))))

(defn dynamize-build [build {:keys [opacity x y]}]
  (let [transform (str "translate(" x "," y ")")]
    (-> build .transition (.duration 1000) (.attr "transform" transform) (.attr "opacity" opacity))))

(defn dynamize-dynamics [step]
  (let [builds (vectorize-constructs builds-properties)]
    (mapv dynamize-build builds (evaluate builds-properties step))))



;; om component ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defcomponent slate-4 [{:keys [step] :as cursor} owner]
  (did-mount [_]
    (.log js/console "Slate 4 mounted with step: " step)
    (initialize-canvas)
    (initialize-statics)
    (initialize-animatics)
    (initialize-dynamics step))

  (did-update [_ _ _]
    (.log js/console "Slate 4 updated with step: " step)
    (animize-animatics step)
    (dynamize-dynamics step))

  (render-state [_ _]
    (div
      {:class "slate-container"}
      (div
        {:id "slate-4" :class "slate"}
        (div
          {:class "slate-content"})))))

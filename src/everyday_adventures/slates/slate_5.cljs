(ns everyday-adventures.slates.slate-5
  (:require [om.core :as om :include-macros true]
            [om-tools.core :refer-macros [defcomponent]]
            [om-tools.dom :refer [div h1 p]]
            [everyday-adventures.slates.slate-utils :refer [create-element-join
                                                            create-element-grouping
                                                            create-text-join]]))
(defn select-canvas []
  (.select js/d3 "#slate-5 .canvas"))

(defn initialize-canvas []
  (-> (.select js/d3 "#slate-5 .slate-content")
      (.append "svg")
      (.attr "class" "canvas")
      (.attr "width" 1088)
      (.attr "height" 450)))

(defn vectorize-constructs [constructs-data]
  (let [select-by-id (fn [id] (.select js/d3 (str "#slate-5 #" id)))]
    (mapv (comp select-by-id :id) constructs-data)))



;; statics ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; statics get created with constant data, and are always static ;;

(def hosts-properties
  [{:id "host-1"
    :text "host 1"
    :x 140
    :y 150 }])

(def services-properties
  [{:id "service-a"
    :text "service a"
    :x 155
    :y 185}])

(defn create-host-construct
  [{:keys [id text x y opacity] :or {x 0 opacity 1}}]
  (let [group-data {:class "host" :id id :x x :y y :opacity opacity}
        group (create-element-grouping (select-canvas) group-data)]
    (create-element-join group "rect" [{:width 160 :height 140 :rx 3 :ry 3 :opacity 0.6}])
    (create-text-join group [{:text text :dx 38 :dy 24}])))

(defn create-service-construct
  [{:keys [id text x y opacity] :or {y 100 opacity 1}}]
  (let [group-data {:class "service" :id id :x x :y y :opacity opacity}
        group (create-element-grouping (select-canvas) group-data)]
    (create-element-join group "rect" [{:width 130 :height 90 :rx 3 :ry 3}])
    (create-text-join group [{:text text :dx 45 :dy 48}])))

(defn initialize-statics []
  (doseq [[create-construct construct-properties] [[create-host-construct hosts-properties]
                                                   [create-service-construct services-properties]]]
    (mapv (partial create-construct) construct-properties)))

;; dynamics ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; dynamics get created with data as a function of step, and are subject to dynamizing as a function of step ;;

(def system-metrics-properties
  [{:id "system-metric"
    :text "system metrics"
    :opacity (fn [step] (condp >= step 0 0 1))
    :x 270
    :y 170}])

(def service-metrics-properties
  [{:id "service-metric"
    :text "service metrics"
    :opacity (fn [step] (condp >= step 0 0 1))
    :x 270
    :y 200}])

(def custom-events-properties
  [{:id "custom-event"
    :text "custom events"
    :opacity (fn [step] (condp >= step 2 0 1))
    :x 270
    :y 230}])

(def service-logs-properties
  [{:id "service-log"
    :text "service logs"
    :opacity (fn [step] (condp >= step 4 0 1))
    :x 270
    :y 260}])

(def hosted-graphites-properties
  [{:id "hosted-graphite"
    :text "hosted graphite"
    :opacity (fn [step] (condp >= step 0 0 1))
    :x 570
    :y (fn [step] (condp >= step 8 75 55))}])

(def queues-properties
  [{:id "queue"
    :text "queue"
    :opacity (fn [step] (condp >= step 2 0 8 1 0))
    :x 528
    :y 216}])

(def workers-properties
  [{:id "worker"
    :text "worker"
    :opacity (fn [step] (condp >= step 2 0 8 1 0))
    :x 640
    :y 175}])

(def pager-duties-properties
  [{:id "pager-duty"
    :text "pager-duty"
    :opacity (fn [step] (condp >= step 2 0 1))
    :x 600
    :y 285}])

(def elasticsearches-properties
  [{:id "elasticsearch"
    :text "elasticsearch"
    :opacity (fn [step] (condp >= step 4 0 1))
    :x 435
    :y 318}])

(def graphs-properties
  [{:id "graph"
    :opacity (fn [step] (condp >= step 1 0 1))
    :x 800
    :y 30}])

(def alerts-properties
  [{:id "alert"
    :opacity (fn [step] (condp >= step 3 0 1))
    :x 800
    :y 170}])

(def logs-properties
  [{:id "logs"
    :opacity (fn [step] (condp >= step 5 0 1))
    :x 800
    :y 310}])

(def black-boxes-properties
  [{:id "black-box"
    :opacity (fn [step] (condp >= step 6 0 7 1 0))
    :x 300
    :y 210}])

(defn create-system-metric-construct [{:keys [id text x y opacity]}]
  (let [group-data {:class "system-metric" :id id :x x :y y :opacity opacity}
        group (create-element-grouping (select-canvas) group-data)]
    (create-text-join group [{:text text :dx 120 :dy 5}])
    (create-element-join group "circle" [{:cx -5 :cy 0 :r 5 :opacity 0.6}])
    (create-element-join group "line" [{:x1 0 :x2 70 :y1 0 :y2 0 :opacity 0.6}
                                       {:x1 170 :x2 220 :y1 0 :y2 0 :opacity 0.6}
                                       {:x1 219 :x2 219 :y1 -1 :y2 -61 :opacity 0.6}
                                       {:x1 220 :x2 285 :y1 -60 :y2 -60 :opacity 0.6}])
    (create-element-join group "polygon" [{:points "285,-66 295,-60 285,-54" :opacity 0.6}])))


(defn create-service-metric-construct [{:keys [id text x y opacity]}]
  (let [group-data {:class "service-metric" :id id :x x :y y :opacity opacity}
        group (create-element-grouping (select-canvas) group-data)]
    (create-text-join group [{:text text :dx 120 :dy 5}])
    (create-element-join group "circle" [{:cx -5 :cy 0 :r 5 :opacity 0.6}])
    (create-element-join group "line" [{:x1 0 :x2 70 :y1 0 :y2 0 :opacity 0.6}
                                       {:x1 170 :x2 240 :y1 0 :y2 0 :opacity 0.6}
                                       {:x1 239 :x2 239 :y1 -1 :y2 -71 :opacity 0.6}
                                       {:x1 240 :x2 285 :y1 -70 :y2 -70 :opacity 0.6}])
    (create-element-join group "polygon" [{:points "285,-76 295,-70 285,-64" :opacity 0.6}])))

(defn create-custom-event-construct [{:keys [id text x y opacity]}]
  (let [group-data {:class "custom-event" :id id :x x :y y :opacity opacity}
        group (create-element-grouping (select-canvas) group-data)]
    (create-text-join group [{:text text :dx 119 :dy 5}])
    (create-element-join group "circle" [{:cx -5 :cy 0 :r 5 :opacity 0.6}])
    (create-element-join group "line" [{:x1 0 :x2 70 :y1 0 :y2 0 :opacity 0.6}
                                       {:x1 168 :x2 242 :y1 0 :y2 0 :opacity 0.6}])
    (create-element-join group "polygon" [{:points "242,-6 252,0 242,6" :opacity 0.6}])))

(defn create-service-log-construct [{:keys [id text x y opacity]}]
  (let [group-data {:class "custom-event" :id id :x x :y y :opacity opacity}
        group (create-element-grouping (select-canvas) group-data)]
    (create-text-join group [{:text text :dx 109 :dy 5}])
    (create-element-join group "circle" [{:cx -5 :cy 0 :r 5 :opacity 0.6}])
    (create-element-join group "line" [{:x1 0 :x2 70 :y1 0 :y2 0 :opacity 0.6}
                                       {:x1 150 :x2 236 :y1 0 :y2 0 :opacity 0.6}
                                       {:x1 235 :x2 235 :y1 1 :y2 40 :opacity 0.6}])
    (create-element-join group "polygon" [{:points "241,40 235,50 229,40" :opacity 0.6}])))

(defn create-hosted-graphite-construct [{:keys [id text x y opacity]}]
  (let [group-data {:class "hosted-graphite" :id id :x x :y y :opacity opacity}
        group (create-element-grouping (select-canvas) group-data)]
    (create-element-join group "rect" [{:width 140 :height 80 :rx 3 :ry 3}])
    (create-text-join group [{:text text :dx 12 :dy 45}])))

(defn create-queue-construct [{:keys [id text x y opacity]}]
  (let [group-data {:class "queue" :id id :x x :y y :opacity opacity}
        group (create-element-grouping (select-canvas) group-data)]
    (create-element-join group "rect" [{:width 100 :height 30 :rx 3 :ry 3}])
    (create-element-join group "circle" [{:cx 70 :cy 14 :r 5 :opacity 0.6}
                                         {:cx 87 :cy 14 :r 5 :opacity 0.6}
                                         {:cx 192 :cy -26 :r 5 :opacity 0.6}])
    (create-element-join group "line" [{:x1 70 :x2 70 :y1 9 :y2 -25 :opacity 0.6}
                                       {:x1 69 :x2 97 :y1 -26 :y2 -26 :opacity 0.6}
                                       {:x1 87 :x2 87 :y1 19 :y2 52 :opacity 0.6}
                                       {:x1 192 :x2 192 :y1 -21 :y2 13 :opacity 0.6}
                                       {:x1 117 :x2 193 :y1 14 :y2 14 :opacity 0.6}])
    (create-text-join group [{:text text :dx 12 :dy 19}])
    (create-element-join group "polygon" [{:points "97,-32 107,-26 97,-20" :opacity 0.6}
                                          {:points "117,20 107,14 117,8" :opacity 0.6}
                                          {:points "81,52 87,62 93,52" :opacity 0.6}])))

(defn create-worker-construct [{:keys [id text x y opacity]}]
  (let [group-data {:class "worker" :id id :x x :y y :opacity opacity}
        group (create-element-grouping (select-canvas) group-data)]
    (create-element-join group "rect" [{:width 100 :height 30 :rx 3 :ry 3}])
    (create-text-join group [{:text text :dx 12 :dy 19}])))

(defn create-pager-duty-construct [{:keys [id text x y opacity]}]
  (let [group-data {:class "pager-duty" :id id :x x :y y :opacity opacity}
        group (create-element-grouping (select-canvas) group-data)]
    (create-element-join group "rect" [{:width 140 :height 80 :rx 3 :ry 3}])
    (create-text-join group [{:text text :dx 28 :dy 45}])))

(defn create-elasticsearch-construct [{:keys [id text x y opacity]}]
  (let [group-data {:class "elasticsearch" :id id :x x :y y :opacity opacity}
        group (create-element-grouping (select-canvas) group-data)]
    (create-element-join group "rect" [{:width 140 :height 80 :rx 3 :ry 3}])
    (create-text-join group [{:text text :dx 22 :dy 45}])))

(defn create-graph-construct [{:keys [id text x y opacity]}]
  (let [group-data {:class "graph window" :id id :x x :y y :opacity opacity}
        group (create-element-grouping (select-canvas) group-data)]
    (create-element-join group "rect" [{:width 160 :height 110 :x 0 :y 0 :rx 4 :ry 4}
                                       {:width 154 :height 104 :x 3 :y 3}
                                       {:width 154 :height 11 :x 3 :y 2}
                                       {:width 154 :height 11 :x 3 :y 13}])
    (create-text-join group [{:text text :dx 12 :dy 45}])
    (create-element-join group "circle" [{:cx 8 :cy 7 :r 3}
                                         {:cx 17 :cy 7 :r 3}
                                         {:cx 26 :cy 7 :r 3}])
    (create-element-join group "line" [{:x1 0 :x2 -89 :y1 85 :y2 85 :class "link"}
                                       {:x1 40 :x2 40 :y1 66 :y2 90}
                                       {:x1 47 :x2 47 :y1 62 :y2 90}
                                       {:x1 54 :x2 54 :y1 58 :y2 90}
                                       {:x1 61 :x2 61 :y1 54 :y2 90}
                                       {:x1 68 :x2 68 :y1 63 :y2 90}
                                       {:x1 75 :x2 75 :y1 58 :y2 90}
                                       {:x1 82 :x2 82 :y1 62 :y2 90}
                                       {:x1 89 :x2 89 :y1 68 :y2 90}
                                       {:x1 96 :x2 96 :y1 71 :y2 90}
                                       {:x1 103 :x2 103 :y1 68 :y2 90}
                                       {:x1 110 :x2 110 :y1 59 :y2 90}
                                       {:x1 117 :x2 117 :y1 72 :y2 90}
                                       {:x1 124 :x2 124 :y1 60 :y2 90}])))

(defn create-alert-construct [{:keys [id text x y opacity]}]
  (let [group-data {:class "alert window" :id id :x x :y y :opacity opacity}
        group (create-element-grouping (select-canvas) group-data)]
    (create-element-join group "rect" [{:width 160 :height 110 :x 0 :y 0 :rx 4 :ry 4}
                                       {:width 154 :height 104 :x 3 :y 3}
                                       {:width 154 :height 11 :x 3 :y 2}
                                       {:width 154 :height 11 :x 3 :y 13}])
    (create-text-join group [{:text "!" :dx 75 :dy 75}])
    (create-element-join group "circle" [{:cx 8 :cy 7 :r 3 :class "a"}
                                         {:cx 17 :cy 7 :r 3 :class "b"}
                                         {:cx 26 :cy 7 :r 3 :class "c"}
                                         {:cx 79 :cy 63 :r 20 :class "ring"}])
    (create-element-join group "line" [{:x1 0 :x2 -30 :y1 85 :y2 85 :class "link"}
                                       {:x1 -31 :x2 -31 :y1 84 :y2 159 :class "link"}
                                       {:x1 -30 :x2 -59 :y1 160 :y2 160 :class "link"}])))

(defn create-logs-construct [{:keys [id text x y opacity]}]
  (let [group-data {:class "logs window" :id id :x x :y y :opacity opacity}
        group (create-element-grouping (select-canvas) group-data)]
    (create-element-join group "rect" [{:width 160 :height 110 :x 0 :y 0 :rx 4 :ry 4}
                                       {:width 154 :height 104 :x 3 :y 3}
                                       {:width 154 :height 11 :x 3 :y 2}
                                       {:width 154 :height 11 :x 3 :y 13}])
    (create-text-join group [{:text text :dx 12 :dy 45}])
    (create-element-join group "circle" [{:cx 8 :cy 7 :r 3 :class "a"}
                                         {:cx 17 :cy 7 :r 3 :class "b"}
                                         {:cx 26 :cy 7 :r 3 :class "c"}])
    (create-element-join group "line" [{:x1 0 :x2 -224 :y1 70 :y2 70 :class "link"}
                                       {:x1 40 :x2 125 :y1 45 :y2 45}
                                       {:x1 30 :x2 35 :y1 45 :y2 45}
                                       {:x1 40 :x2 115 :y1 51 :y2 51}
                                       {:x1 30 :x2 35 :y1 51 :y2 51}
                                       {:x1 40 :x2 103 :y1 57 :y2 57}
                                       {:x1 30 :x2 35 :y1 57 :y2 57}
                                       {:x1 40 :x2 120 :y1 63 :y2 63}
                                       {:x1 30 :x2 35 :y1 63 :y2 63}
                                       {:x1 40 :x2 118 :y1 69 :y2 69}
                                       {:x1 30 :x2 35 :y1 69 :y2 69}
                                       {:x1 40 :x2 116 :y1 75 :y2 75}
                                       {:x1 30 :x2 35 :y1 75 :y2 75}
                                       {:x1 40 :x2 104 :y1 81 :y2 81}
                                       {:x1 30 :x2 35 :y1 81 :y2 81}
                                       {:x1 40 :x2 114 :y1 87 :y2 87}
                                       {:x1 30 :x2 35 :y1 87 :y2 87}])))

(defn create-black-box-construct [{:keys [id text x y opacity]}]
  (let [group-data {:class "black-box" :id id :x x :y y :opacity opacity}
        group (create-element-grouping (select-canvas) group-data)]
    (create-element-join group "rect" [{:width 460 :height 370 :x 20 :y -170 :rx 3 :ry 3 :opacity 0.8 :class "place-holder"}
                                       {:width 120 :height 40 :x 190 :y -6 :rx 2 :ry 2 :class "text-box"}])
    (create-text-join group [{:text "black box" :dx 250 :dy 20}])))

(defn evaluate [constructs-properties step]
  (let [apply-step (fn [v step] (if (fn? v) (v step) v))]
    (mapv
      #(reduce (fn [m [k v]] (assoc m k (apply-step v step))) {} %)
      constructs-properties)))

(defn initialize-dynamics [step]
  (doseq [[create-construct constructs-properties] [[create-system-metric-construct system-metrics-properties]
                                                    [create-service-metric-construct service-metrics-properties]
                                                    [create-service-log-construct service-logs-properties]
                                                    [create-hosted-graphite-construct hosted-graphites-properties]
                                                    [create-worker-construct workers-properties]
                                                    [create-queue-construct queues-properties]
                                                    [create-pager-duty-construct pager-duties-properties]
                                                    [create-custom-event-construct custom-events-properties]
                                                    [create-elasticsearch-construct elasticsearches-properties]
                                                    [create-graph-construct graphs-properties]
                                                    [create-alert-construct alerts-properties]
                                                    [create-logs-construct logs-properties]
                                                    [create-black-box-construct black-boxes-properties]]]
    (mapv (partial create-construct) (evaluate constructs-properties step))))

(defn dynamize-construct [construct {:keys [opacity x y]}]
  (let [transform (str "translate(" x "," y ")")]
    (-> construct .transition (.duration 1000) (.attr "transform" transform) (.attr "opacity" opacity))))

(defn dynamize-dynamics [step]
  (doseq [constructs-properties [system-metrics-properties
                                 service-metrics-properties
                                 service-logs-properties
                                 hosted-graphites-properties
                                 workers-properties
                                 queues-properties
                                 pager-duties-properties
                                 custom-events-properties
                                 elasticsearches-properties
                                 graphs-properties
                                 alerts-properties
                                 logs-properties
                                 black-boxes-properties]]
    (mapv dynamize-construct
          (vectorize-constructs constructs-properties)
          (evaluate constructs-properties step))))



;; om component ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defcomponent slate-5 [{:keys [step] :as cursor} owner]
  (did-mount [_]
    (.log js/console "Slate 5 mounted with step: " step)
    (initialize-canvas)
    (initialize-statics)
    (initialize-dynamics step))

  (did-update [_ _ _]
    (.log js/console "Slate 5 updated with step: " step)
    (dynamize-dynamics step))

  (render-state [_ _]
    (div
      {:class "slate-container"}
      (div
        {:id "slate-5" :class "slate"}
        (div
          {:class "slate-content"})))))

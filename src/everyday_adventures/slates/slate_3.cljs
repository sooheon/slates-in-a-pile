(ns everyday-adventures.slates.slate-3
  (:require [om.core :as om :include-macros true]
            [om-tools.core :refer-macros [defcomponent]]
            [om-tools.dom :refer [div h1 p]]))


;;;;;;;;;;;;;;;;;;;;; helpers ;;;;;;;;;;;;;;;;;;;;;;;;

(defn initialize-canvas [owner]
  (let [canvas (-> (.select js/d3 "#slate-3 .slate-content")
                   (.append "svg")
                   (.attr "width" 1088)
                   (.attr "height" 400))]
    (om/set-state! owner :canvas canvas)
    canvas))

(defn use-attribute [element attribute]
  (.attr element attribute (fn [data _] (aget data attribute))))

(defn create-element-join [parent-element element-name data]
    (let [join (-> parent-element (.selectAll element-name) (.data (clj->js data)))
          attributes (->> data first keys (mapv name))
          use-attributes (fn [element attributes] (reduce use-attribute element attributes))]
      (-> join .enter (.append element-name) (use-attributes attributes))))

(defn create-text-join [parent-element data]
    (let [join (-> parent-element (.selectAll "text") (.data (clj->js data)))
          use-text (fn [element] (.text element (fn [data _] (aget data "text"))))]
      (-> join
          .enter
          (.append "text")
          (use-attribute "dx")
          (use-attribute "dy")
          (use-text))))

(defn create-element-grouping [parent-element {:keys [class id x y opacity]}]
  (let [transform (str "translate(" x "," y ")")]
    (-> parent-element
        (.append "g")
        (.attr "class" class)
        (.attr "id" id)
        (.attr "transform" transform)
        (.attr "opacity" opacity))))



;;;;;;;;;;;;;;;;;;;;; statics ;;;;;;;;;;;;;;;;;;;;;;;;
; statics get created with constant data, and are always static

(defn generate-services-data []
  [{:id "service-a" :text "service a" :y 200}])

(defn generate-environments-data []
  [{:id "dev" :text "dev" :x 300}
   {:id "qa" :text "qa" :x 600}
   {:id "prod" :text "prod" :x 900}])

(defn create-service [parent-element {:keys [id text x y opacity] :or {x 0 opacity 1}}]
  (let [group-data {:class "service" :id id :x x :y y :opacity opacity}
        group (create-element-grouping parent-element group-data)]
    (create-element-join group "rect" [{:width 100 :height 28 :x 5 :rx 10 :ry 10}])
    (create-text-join group [{:text text :dx 24 :dy 19}])
    (create-element-join group "line" [{:x1 105 :x2 320 :y1 14 :y2 14 :class "dashed"}
                                       {:x1 340 :x2 620 :y1 14 :y2 14}
                                       {:x1 640 :x2 920 :y1 14 :y2 14}
                                       {:x1 940 :x2 970 :y1 14 :y2 14}])))

(defn create-environment [parent-element {:keys [id text x y opacity] :or {y 100 opacity 1}}]
  (let [group-data {:class "environment" :id id :x x :y y :opacity opacity}
        group (create-element-grouping parent-element group-data)]
    (create-element-join group "circle" [{:cx 30 :cy 10 :r 24}
                                         {:cx 30 :cy 114 :r 10}])
    (create-text-join group [{:text text :dx 30 :dy 15}])
    (create-element-join group "line" [{:x1 30 :x2 30 :y1 34 :y2 103}
                                       {:x1 30 :x2 30 :y1 125 :y2 153}])))

(defn initialize-statics [parent-element]
  (doseq [[create-construct generate-constructs-data] [[create-service generate-services-data]
                                                        [create-environment generate-environments-data]]]
    (mapv (partial create-construct parent-element) (generate-constructs-data))))



;;;;;;;;;;;;;;;;;;;;; animatics ;;;;;;;;;;;;;;;;;;;;;;;;
; animatics get created with constant data, but are subject to animizing as a function of step

(defn generate-triggers-data []
  [{:id "trigger-a" :x 121 :y 207 :opacity 0}])

(defn create-trigger [parent-element {:keys [id x y opacity]}]
  (let [group-data {:class "trigger" :id id :x x :y y :opacity opacity}
        group (create-element-grouping parent-element group-data)]
    (create-element-join group "line" [{:x1 0 :x2 8 :y1 0 :y2 8}
                                       {:x1 8 :x2 0 :y1 6 :y2 14}
                                       {:x1 10 :x2 18 :y1 0 :y2 8}
                                       {:x1 18 :x2 10 :y1 6 :y2 14}
                                       {:x1 20 :x2 28 :y1 0 :y2 8}
                                       {:x1 28 :x2 20 :y1 6 :y2 14}])
    group))

(defn initialize-animatics [parent-element owner]
  (doseq [[create-construct generate-constructs-data key] [[create-trigger generate-triggers-data :triggers]]]
    (->> (generate-constructs-data)
         (mapv (partial create-construct parent-element))
         (om/set-state! owner key))))

(defn generate-triggers-animatics-data [step]
  [{:id "trigger-a" :animize? (contains? #{1 2 3} step)}])

(defn animize-trigger [trigger]
  (-> trigger
      .transition (.duration 700) (.attr "transform" "translate(231,207)") (.attr "opacity" 1)
      .transition (.duration 600) (.attr "transform" "translate(151,207)") (.attr "opacity" 0)))

(defn animize-animatics [step owner]
  (let [triggers (om/get-state owner :triggers)
        trigger-animatics (generate-triggers-animatics-data step)]
    (mapv #(when %1 (animize-trigger %2)) trigger-animatics triggers)))



;;;;;;;;;;;;;;;;;;;;; dynamics ;;;;;;;;;;;;;;;;;;;;;;;;
; dynamics get created with data as a function of step, and are subject to dynamizing as a function of step

(defn generate-builds-data [step]
  [{:id "b1"
    :text "b1"
    :opacity (if (contains? #{1 2 3} step) 1 0)
    :x (+ 330 (if (contains? #{1 2 3} step) (* (dec step) 300) (if (> step 3) 600 0)))
    :y 214}
   {:id "b2"
    :text "b2"
    :opacity (if (contains? #{2 3 4} step) 1 0)
    :x (+ 330 (if (contains? #{2 3 4} step) (* (- step 2) 300) (if (> step 4) 600 0)))
    :y 214}
   {:id "b3"
    :text "b3"
    :opacity (if (contains? #{3 4 5} step) 1 0)
    :x (+ 330 (if (contains? #{3 4 5} step) (* (- step 3) 300) (if (> step 5) 600 0)))
    :y 214}])

(defn create-build [parent-element {:keys [id text x y opacity]}]
  (let [group-data {:class "build" :id id :x x :y y :opacity opacity}
        group (create-element-grouping parent-element group-data)]
    (create-text-join group [{:text text :dx 10 :dy 16}])
    (create-element-join group "circle" [{:cx 0 :cy 0 :r 7}])
    group))

(defn initialize-builds [parent-element owner step]
  (->> (generate-builds-data step)
       (mapv #(create-build parent-element %))
       (om/set-state! owner :builds)))

(defn dynamize-build [build {:keys [opacity x y]}]
  (let [transform (str "translate(" x "," y ")")]
    (-> build .transition (.duration 1000) (.attr "transform" transform) (.attr "opacity" opacity))))

(defn dynamize-builds [builds step]
  (mapv dynamize-build builds (generate-builds-data step)))

;;;;;;;;;;;;;;;;;;;;; om component ;;;;;;;;;;;;;;;;;;;;;;;;

(defcomponent slate-3 [{:keys [step] :as cursor} owner]
  (did-mount [_]
    (.log js/console "Slate 3 mounted with step: " step)
    (let [canvas (initialize-canvas owner)]
      (initialize-statics canvas)
      (initialize-animatics canvas owner)
      (initialize-builds canvas owner step)))

  (did-update [_ _ _]
    (.log js/console "Slate 3 updated with step: " step)
    (let [builds (om/get-state owner :builds)]
      (animize-animatics step owner)
      (dynamize-builds builds step)))

  (render-state [_ _]
    (div
      {:class "slate-container"}
      (div
        {:id "slate-3" :class "slate"}
        (div
          {:class "slate-content"})))))

(ns everyday-adventures.slates.slate-6
  (:require [om.core :as om :include-macros true]
            [om-tools.core :refer-macros [defcomponent]]
            [om-tools.dom :refer [div h3 p]]))

(defcomponent slate-6 [cursor owner]
  (render-state [_ _]
    (div
      {:class "slate-container"}
      (div
        {:id "slate-6" :class "slate"}
        (div
          {:class "slate-content"}
          (h3 "MONITORING"))))))

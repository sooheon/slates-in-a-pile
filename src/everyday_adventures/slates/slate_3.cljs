(ns everyday-adventures.slates.slate-3
  (:require [om.core :as om :include-macros true]
            [om-tools.core :refer-macros [defcomponent]]
            [om-tools.dom :refer [div h3 p]]))

(defcomponent slate-3 [cursor owner]
  (render-state [_ _]
    (div
      {:class "slate-container"}
      (div
        {:id "slate-3" :class "slate"}
        (div
          {:class "slate-content"}
          (h3 "DEPLOYMENTS"))))))

(ns slates-in-a-pile.styles.slates.slate-3
  (:require [garden.def :refer [defstyles]]
            [slates-in-a-pile.styles.utils.variables :refer :all]))

(defstyles slate-3
  [:#slate-3 {:color (:hard-green colours)}
   [:.backdrop {:background-color (:soft-green colours)}]])

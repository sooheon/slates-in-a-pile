(ns slates-in-a-pile.styles.slates.slate-1
  (:require [garden.def :refer [defstyles]]
            [slates-in-a-pile.styles.utils.variables :refer :all]))

(defstyles slate-1
  [:#slate-1 {:color (:hard-green colours)}
   [:.backdrop {:background-color (:soft-green colours)}]])
(ns app.hello
  (:require-macros  [cljs.core.async.macros :refer  [go go-loop alt!]])
  (:require
    [solsort.util :refer  [route <ajax log]]
    [reagent.core :as reagent :refer  []]
    [solsort.misc :refer  [js-seq]]
    [clojure.string :refer [replace split blank?]]
    [cljs.core.async :refer  [>! <! chan put! take! timeout close! pipe]]))

(defn name->kw [o] (keyword (str (.-nodeName o))))
(defn dom->clj [dom]
  (case (.-nodeType dom)
    ((.-DOCUMENT_NODE dom) (.-ELEMENT_NODE dom))
    (let [tag (name->kw dom)
          children (map dom->clj (js-seq (.-children dom)))
          children (if (empty? children)
                     (if (blank? (.-textContent dom))
                       []
                       [(str (.-textContent dom))])
                     children)
          attrs (into {} (map (fn [o] [(name->kw o) (.-textContent o)]))
                      (js-seq (or (.-attributes dom) [])))]
      {:tag tag
       :attrs attrs
       :children children})
    (.-TEXT_NODE dom) (str (.-textContent dom))))


(go
  (let 
    [club-site (<! (<ajax "//solsort.com/cors/http/tmclub.eu/portal.php?page=842" :result "text"))  
     dom (.parseFromString  (js/DOMParser.) club-site "text/html")

     dom (dom->clj dom)
     ]
    (aset js/document.body "innerHTML" (replace (str dom)
                                                "<"
                                                "&lt;"))
  (js/console.log (clj->js dom)))
  )

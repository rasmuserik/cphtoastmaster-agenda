(ns app.hello
  (:require-macros  [cljs.core.async.macros :refer  [go go-loop alt!]])
  (:require
    [solsort.util :refer  [route log]]
    [reagent.core :as reagent :refer  []]
    [solsort.util :refer  [<ajax]]
    [cljs.core.async :refer  [>! <! chan put! take! timeout close! pipe]]))

(go
  (let 
    [club-site (<! (<ajax "//solsort.com/cors/http/tmclub.eu/portal.php?page=842" :result "text"))  
     dom (.parseFromString  (js/DOMParser.) club-site "text/html")
     ]
  (js/console.log dom))
  )

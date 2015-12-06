(ns app.hello
  (:require-macros  [cljs.core.async.macros :refer  [go go-loop alt!]])
  (:require
    [solsort.util :refer  [route log]]
    [reagent.core :as reagent :refer  []]
    [solsort.util :refer  [<ajax]]
    [cljs.core.async :refer  [>! <! chan put! take! timeout close! pipe]])

  )

(go
  (js/console.log (<! (<ajax "//solsort.com/cors/http/tmclub.eu/portal.php?page=1&c=381" :result "text")))
  )

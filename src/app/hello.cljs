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


(defn xml-find [p xml]
  (if (p xml) xml (some #(xml-find p %) (:children xml))))
(defn xml-find-child [p xml] (some #(xml-find p %) (:children xml)))
(defn xml->sxml [xml] 
  (if (:tag xml)
    [(:tag xml) (:attrs xml) (map xml->sxml (:children xml))]
    xml))
(go
  (let 
    [club-site (<! (<ajax "http://tmclub.eu/portal.php?page=1&c=381" :result "text"))
     next-meeting-url (re-find #"view_meeting.php.t=[0-9]*" club-site)
     next-meeting-url "view_agenda.php?t=59916"
     next-meeting (<! (<ajax (str "http://tmclub.eu/" next-meeting-url) :result "text"))
     dom (.parseFromString  (js/DOMParser.) next-meeting "text/html") ]
    (js/console.log "\n\n\n\n\n\n\n\n\n\n")
    (js/console.log next-meeting-url)
    (aset js/document.body "innerHTML" (replace next-meeting "<" "&lt;"))
    ;(log (xml-find #(= "forumline" (:class (:attrs %))) (dom->clj dom)))
    (doall
      (map 
      log
         (->> (dom->clj dom)
      (xml-find #(= "postbody;gen" (:class (:attrs %))))
      (xml-find-child #(= :TBODY (:tag %)))
      (xml-find-child #(= :TBODY (:tag %)))
      (xml-find-child #(= :TBODY (:tag %)))
      (xml->sxml)
      )))
    )
  )

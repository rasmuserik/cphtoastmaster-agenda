(defproject app/hello "0.0.1"
  :description ".."
  :url "https://github.com/rasmuserik/.."
  :dependencies
  [[org.clojure/clojure "1.8.0-RC2"]
   [org.clojure/clojurescript "1.7.170"]
   [org.clojure/core.async "0.2.374"]
   [cljsjs/pouchdb "3.5.0-1"]
   [solsort/util "0.0.3"]
   [reagent "0.5.1"]
   [re-frame "0.5.0"]]

  :plugins
  [[lein-cljsbuild "1.1.1"]
   [lein-ancient "0.6.8"]
   [lein-figwheel "0.5.0-2"]
   [lein-kibit "0.1.2"]]

  :source-paths ["src/"]

  :clean-targets ^{:protect false} 
  [
   "resources/public/out"
   "resources/public/index.js"
   "target/"
   "figwheel_server.log"
   ]

  :cljsbuild 
  {:builds 
   [{:id "dev"
     :source-paths ["src/"]
     :figwheel {:websocket-host ~(.getHostAddress (java.net.InetAddress/getLocalHost))
               ; :on-jsload "" 
                }
     :compiler {:main app.hello
                :asset-path "out"
                :output-to "resources/public/index.js"
                :output-dir "resources/public/out"
                :source-map-timestamp true }}
    {:id "dist"
     :source-paths ["src"]
     :compiler {:output-to "index.js"
                :main app.hello
                :externs ["externs.js"]
                :optimizations :advanced
                :pretty-print false}}]}
  :figwheel {:nrepl-port 7888})

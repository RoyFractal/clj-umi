(defproject net.clojars.kesha1225/clj-umi "0.0.4"
  :description "Umi wrapper for clojure"
  :repositories [["github" {:url           "https://maven.pkg.github.com/RoyFractal/clj-umi"
                            :username      "private-token"
                            :password      :env/GITHUB_TOKEN
                            :sign-releases false}]]
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url  "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [clj-http "3.12.3"]
                 [org.clojure/data.json "2.4.0"]]
  :main ^:skip-aot clj-umi.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot      :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})

(ns build
  (:require [ch.codesmith.anvil.libs :as libs]
            [ch.codesmith.anvil.release :as rel]
            [ch.codesmith.anvil.shell :as sh]
            [clojure.tools.build.api :as b]))

(def lib '{{group/id}}/{{artifact/id}})
(def version (str "0.1." (b/git-count-revs {})))
(def release-branch-name "main")
#_(def docker-registry)

(def description-data
  {:license        :epl
   :inception-year 2022
   :description    "<description>"
   :organization   {:name "Codesmith GmbH"
                    :url  "https://codesmith.ch"}
   :authors        [{:name  "Stanislas Nanchen"
                     :email "stan@codesmith.ch"}]
   :scm            {:type         :github
                    :organization "<organisation>"
                    :project      "{{artifact/id}}"}})

;; Choose the appropriate release function
(comment

  (defn release "library with git releases"
    [_]
    (rel/git-release! {:deps-coords         lib
                       :version             version
                       :release-branch-name "master"
                       :artifact-type       :deps})
    (verify nil))

  (defn release "library with clojars releases"
    [_]
    (verify nil)
    (let [jar-file (libs/jar {:lib              lib
                              :version          version
                              :target-dir       "target"
                              :with-pom?        true
                              :description-data description-data
                              :clean?           true})]
      (libs/deploy {:jar-file jar-file
                    :lib      lib
                    :pom-file "target/classes/META-INF/maven/{{group/id}}/{{artifact/id}}/pom.xml"})
      (rel/git-release! {:deps-coord          lib
                         :version             version
                         :release-branch-name release-branch-name
                         :artifact-type       :mvn})))

  (defn release "app with docker image"
    [_]
    (verify nil)
    (let [{:keys [app-docker-tag]} (apps/docker-generator {:lib              lib
                                                           :version          version
                                                           :target-dir       "target"
                                                           :description-data description-data
                                                           :java-runtime     {:version         :java17
                                                                              :type            :jre
                                                                              :modules-profile :java.base}
                                                           :main-namespace   "hello"
                                                           :aot              aot
                                                           :docker-registry  docker-registry})]
      (sh/sh! "./target/docker-app/docker-build.sh")
      (sh/sh! "./target/docker-app/docker-push.sh")
      (rel/git-release! {:deps-coord          lib
                         :version             version
                         :release-branch-name release-branch-name
                         :artifact-type       :docker-image})))

  )


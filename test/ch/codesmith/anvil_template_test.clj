(ns ch.codesmith.anvil-template-test
  (:require [clojure.edn :as edn]
            [clojure.java.io :as io]
            [clojure.spec.alpha :as s]
            [clojure.test :refer :all]
            [org.corfield.new] ; for the Specs
            [ch.codesmith.anvil-template :refer :all]))

(deftest valid-template-test
  (testing "template.edn is valid."
    (let [template (edn/read-string (slurp (io/resource "ch/codesmith/anvil_template/template.edn")))]
      (is (s/valid? :org.corfield.new/template template)
          (s/explain-str :org.corfield.new/template template)))))

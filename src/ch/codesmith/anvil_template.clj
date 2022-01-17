(ns ch.codesmith.anvil-template)

(defn data-fn
  "Example data-fn handler.

  Result is merged onto existing options data."
  [_]
  ;; returning nil means no changes to options data
  (println "data-fn returning nil")
  nil)

(defn template-fn
  "Example template-fn handler.

  Result is used as the EDN for the template."
  [edn _]
  ;; must return the whole EDN hash map
  (println "template-fn returning edn")
  edn)

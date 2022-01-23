(ns {{top/ns}}.{{main/ns}}
  (require [ch.codesmith.logger :as log]))

(log/deflogger)

(defn foo
  "I don't do a whole lot."
  [x]
  (log/info-c "Hello, World! {}" :x x))

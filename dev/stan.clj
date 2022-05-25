(ns stan
  (:import (org.beryx.textio TextIoFactory)))


(comment

  (def text-io (TextIoFactory/getTextIO))



  (def user (.. text-io
                (newStringInputReader)
                (withDefaultValue "admin")
                (read ["username" "give a user admin"])))

  )
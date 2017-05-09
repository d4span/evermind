(ns evermind.runner
  (:require [doo.runner :refer-macros [doo-tests]]
            [evermind.domain.core-test]))

(doo-tests 'evermind.domain.core-test)
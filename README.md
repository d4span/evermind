# Evermind

[![Build Status](https://travis-ci.org/d4span/evermind.svg?branch=master)](https://travis-ci.org/d4span/evermind)

Build and install using Clojure

    lein modules do test, install

Build and install using ClojureScript

    lein modules with-profiles clojurescript do clean, install

To run the frontend

    lein modules :dirs evermind.web figwheel


## Notes

- Leiningen 2.7.0 has to be installed since lein-modules plugin is currently not working with Leiningen >= 2.7.1



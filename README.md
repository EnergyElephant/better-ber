# better-ber

An api for Ber ratings

## Usage

GET /ber/XXXXXX.json => { "address" : "arsta", ... }


## Running

To start a web server for the application, run:

    lein ring server

## Running tests


### Running a single test

    (require '[clojure.test :refer [run-tests]])
    (require 'better-ber.seai-result-parser-test :reload-all)
    (run-tests 'better-ber.seai-result-parser-test)

## License

Copyright © 2015 FIXME

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.

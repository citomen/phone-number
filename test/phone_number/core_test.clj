(ns

    ^{:doc    "phone-number library, core tests."
      :author "Paweł Wilk"
      :added  "8.12.4-0"
      :no-doc true}

    phone-number.core-test

  (:refer-clojure :exclude [format type])

  (:require [clojure.spec.alpha      :as             s]
            [midje.sweet             :refer       :all]
            [midje.experimental      :refer  [for-all]]
            [clojure.spec.gen.alpha  :as           gen]
            [orchestra.spec.test     :as            st]
            [phone-number.core       :refer       :all]
            [phone-number.spec       :as          spec]
            [expound.alpha           :as       expound])

  (:import  [com.google.i18n.phonenumbers
             Phonenumber$PhoneNumber
             NumberParseException]))

(s/check-asserts true)

(alter-var-root #'*default-dialing-region* (constantly :us))

(facts "about `number`"
       (fact "when it returns nil for nil or empty"
             (number nil) => nil
             (number {})  => nil)
       (fact "when it returns PhoneNumber object for a string"
             (class (number "+48 998"))                            => Phonenumber$PhoneNumber
             (class (number "+448081570001"))                      => Phonenumber$PhoneNumber
             (class (number "8081570001" :gb))                     => Phonenumber$PhoneNumber
             (class (number "8081570001" :phone-number.region/gb)) => Phonenumber$PhoneNumber
             (number "+448081570001")                              => native?)
       (fact "when it fails on bad input"
             (number "abc1")                                       => (throws AssertionError)
             (number "")                                           => (throws AssertionError)
             (number 1)                                            => (throws AssertionError)
             (number 12)                                           => (throws AssertionError)
             (number 998)                                          => (throws AssertionError)
             (number "998")                                        => (throws NumberParseException)
             (number {:a 1})                                       =future=> (throws AssertionError)))

(facts "about `info`"
       (fact "when it returns nil for nil or empty"
             (info nil) => nil
             (info {})  => nil)
       (fact "when it retains a dialing region when source is a map"
             (:phone-number/dialing-region (info "112" :pl :pl :pl))        => :phone-number.region/pl
             (:phone-number/dialing-region (info (info "112" :pl :pl :pl))) => :phone-number.region/pl))

(ns

    ^{:doc    "Global network calling codes handling for phone-number."
      :author "Paweł Wilk"
      :added  "8.12.4-0"}

    phone-number.net-code

  (:require [phone-number.util :as util])
  (:import  [com.google.i18n.phonenumbers
             PhoneNumberUtil
             NumberParseException]))

;;
;; Supported Global Network Calling Codes
;;

(def ^{:added "8.12.4-0"
       :const true
       :tag Integer}
  unknown 0)

(def ^{:added "8.12.4-0"
       :const true
       :tag Integer}
  unknown-val unknown)

(def ^{:added "8.12.16-1"
       :tag clojure.lang.PersistentHashSet}
  all-arg
  "Set of supported global network calling codes (to be used as arguments)."
  (set (.getSupportedGlobalNetworkCallingCodes (util/instance))))

(def ^{:added "8.12.16-1"
       :tag clojure.lang.PersistentHashSet}
  by-val-arg all-arg)

(def ^{:added "8.12.4-0"
       :const true
       :tag Integer}
  default 0)

(def ^{:added "8.12.4-0"
       :const true
       :tag Integer}
  default-val 0)

(def ^{:added "8.12.4-0"
       :tag clojure.lang.PersistentHashSet}
  all
  "Set of supported global network calling codes (including unknown)."
  (conj
   (set (.getSupportedGlobalNetworkCallingCodes (util/instance)))
   default))

(def ^{:added "8.12.16-1"
       :tag clojure.lang.PersistentHashSet}
  by-val
  (conj by-val-arg default-val))

(def ^{:added "8.12.4-0"
       :tag clojure.lang.PersistentVector}
  all-vec
  "Vector of all supported global network calling codes."
  (vec all))

(def ^{:added "8.12.16-1"
       :tag clojure.lang.PersistentVector}
  by-val-vec
  "Vector of all supported global network calling codes."
  all-vec)

(def ^{:added "8.12.16-1"
       :tag clojure.lang.PersistentVector}
  all-arg-vec
  "Vector of all supported global network calling codes (valid as args)."
  (vec all-arg))

(def ^{:added "8.12.16-1"
       :tag clojure.lang.PersistentVector}
  by-val-arg-vec
  "Vector of all supported global network calling codes (valid as args)."
  all-arg-vec)

(defn valid?
  "Returns true if the given region-specification is a valid region code, false
  otherwise."
  {:added "8.12.4-0" :tag Boolean}
  [^Integer calling-code]
  (contains? all calling-code))

(defn valid-arg?
  "Returns true if the given region-specification is a valid region code (to be used as
  an argument), false otherwise."
  {:added "8.12.16-1" :tag Boolean}
  [^Integer calling-code]
  (contains? all-arg calling-code))

(defn parse
  "Parses a network calling code and returns a value that can be supplied to
  Libphonenumber methods."
  {:added "8.12.4-0" :tag Integer}
  ([^Integer calling-code]
   (assert (valid-arg? calling-code)
           (str "Global network calling code " calling-code " is not valid"))
   calling-code))

(defn generate-sample
  "Generates random number type."
  {:added "8.12.4-0" :tag Integer}
  ([] (rand-nth all-vec))
  ([^java.util.Random rng] (util/get-rand-nth all-vec rng)))

(defn generate-sample-val
  "Generates random number type."
  {:added "8.12.16-1" :tag Integer}
  ([] (rand-nth by-val-vec))
  ([^java.util.Random rng] (util/get-rand-nth by-val-vec rng)))

(defn generate-arg-sample
  "Generates random number type."
  {:added "8.12.16-1" :tag Integer}
  ([] (rand-nth all-arg-vec))
  ([^java.util.Random rng] (util/get-rand-nth all-arg-vec rng)))

(defn generate-arg-sample-val
  "Generates random number type."
  {:added "8.12.16-1" :tag Integer}
  ([] (rand-nth by-val-arg-vec))
  ([^java.util.Random rng] (util/get-rand-nth by-val-arg-vec rng)))

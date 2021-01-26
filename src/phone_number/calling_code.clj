(ns

    ^{:doc    "Calling codes handling for phone-number."
      :author "Paweł Wilk"
      :added  "8.12.4-0"}

    phone-number.calling-code

  (:require [phone-number.util         :as         util]
            [phone-number.country-code :as country-code]
            [phone-number.net-code     :as     net-code])
  (:import  [com.google.i18n.phonenumbers
             PhoneNumberUtil
             NumberParseException]))

;;
;; Supported Calling Codes
;;

(def ^{:added "8.12.4-0"
       :const true
       :tag Integer}
  unknown 0)

(def ^{:added "8.12.4-0"
       :const true
       :tag Integer}
  unknown-val unknown)

(def ^{:added "8.12.4-0"
       :tag clojure.lang.PersistentHashSet}
  all
  "Set of supported calling codes."
  (clojure.set/union country-code/all net-code/all))

(def ^{:added "8.12.4-0"
       :tag clojure.lang.PersistentHashSet}
  by-val all)

(def ^{:added "8.12.4-0"
       :const true
       :tag Integer}
  default 0)

(def ^{:added "8.12.4-0"
       :const true
       :tag Integer}
  default-val 0)

(def ^{:added "8.12.16-1"
       :tag clojure.lang.PersistentHashSet}
  all-arg
  "Set of supported calling codes to be passed as arguments."
  (disj all unknown))

(def ^{:added "8.12.4-0"
       :tag clojure.lang.PersistentVector}
  all-vec
  "Vector of all supported calling codes."
  (vec all))

(def ^{:added "8.12.16-1"
       :tag clojure.lang.PersistentVector}
  all-arg-vec
  "Vector of all supported calling codes to be used as arguments."
  (vec all-arg))

(def ^{:added "8.12.4-0"
       :tag clojure.lang.PersistentVector}
  by-val-vec
  "Vector of all supported calling codes."
  all-vec)

(def ^{:added "8.12.16-1"
       :tag clojure.lang.PersistentVector}
  by-val-arg-vec
  "Vector of all supported calling codes."
  all-arg-vec)

(defn valid?
  "Returns true if the given region-specification is a valid region code, false
  otherwise."
  {:added "8.12.4-0" :tag Boolean}
  [^Integer calling-code]
  (contains? all calling-code))

(defn valid-arg?
  "Returns true if the given region-specification is a valid region code to be used as
  an argument, false otherwise."
  {:added "8.12.16-1" :tag Boolean}
  [^Integer calling-code]
  (contains? all-arg calling-code))

(defn parse
  "Parses a calling code and returns a value that can be supplied to
  Libphonenumber methods."
  {:added "8.12.4-0" :tag Integer}
  ([^Integer calling-code]
   (assert (valid-arg? calling-code)
           (str "Calling code " calling-code " is not valid"))
   calling-code))

(defn generate-sample
  "Generates a random country calling code."
  {:added "8.12.4-0" :tag Integer}
  ([] (rand-nth all-vec))
  ([^java.util.Random rng] (util/get-rand-nth all-vec rng)))

(defn generate-sample-arg
  "Generates a random country calling code to be used as an argument."
  {:added "8.12.16-1" :tag Integer}
  ([] (rand-nth all-vec))
  ([^java.util.Random rng] (util/get-rand-nth all-arg-vec rng)))

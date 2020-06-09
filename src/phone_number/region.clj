(ns

    ^{:doc    "Region handling for phone-number."
      :author "Paweł Wilk"
      :added  "8.12.4-0"}

    phone-number.region

  (:require [phone-number.util :as util])
  (:import  [com.google.i18n.phonenumbers
             PhoneNumberUtil
             NumberParseException]))

;;
;; Supported Regions
;;

(def ^{:added "8.12.4-0"
       :const true
       :tag clojure.lang.Keyword}
  unknown :unknown)

(def ^{:added "8.12.4-0"
       :const true
       :tag String}
  unknown-val "ZZ")

(def ^{:added "8.12.4-0"
       :tag clojure.lang.PersistentHashMap}
  all
  "Mapping of supported regions (keywords) to region values (strings)."
  (let [tns (str (ns-name *ns*))]
    (into #::{}
          (map
           (juxt #(keyword tns (clojure.string/lower-case %)) identity)
           (.getSupportedRegions (util/instance))))))

(def ^{:added "8.12.4-0"
       :tag clojure.lang.PersistentHashMap}
  by-val
  "Mapping of supported region values (strings) to regions (keywords)."
  (clojure.set/map-invert all))

(def ^{:added "8.12.4-0"
       :const true
       :tag clojure.lang.Keyword}
  default nil)

(def ^{:added "8.12.4-0"
       :const true
       :tag String}
  default-val (all default))

(def ^{:added "8.12.4-0"
       :tag clojure.lang.PersistentVector}
  all-vec
  "Vector of regions (keywords)."
  (vec (keys all)))

(def ^{:added "8.12.4-0"
       :tag clojure.lang.PersistentVector}
  by-val-vec
  "Vector of regions (string values)."
  (vec (keys by-val)))

(defn valid?
  "Returns true if the given region-specification is a valid region code, false
  otherwise. In its binary form it uses namespace inference."
  {:added "8.12.4-0" :tag Boolean}
  ([^clojure.lang.Keyword region-specification]
   (contains? all region-specification))
  ([^clojure.lang.Keyword region-specification
    ^Boolean use-infer]
   (contains? all (util/ns-infer "phone-number.region" region-specification use-infer))))

(defn parse
  "Parses a region code and returns a value that can be supplied to Libphonenumber
  methods."
  {:added "8.12.4-0" :tag String}
  ([^clojure.lang.Keyword k]
   (parse k true))
  ([^clojure.lang.Keyword k
    ^Boolean use-infer]
   (when (some? k)
     (if (keyword? k)
       (let [k (util/ns-infer "phone-number.region" k use-infer)]
         (assert (valid? k) (str "Region code " k " is not valid"))
         (all k))
       (do (assert (contains? by-val k) (str "Region " k " is not valid")) k)))))

(defn generate-sample
  "Generates random region code."
  {:added "8.12.4-0" :tag clojure.lang.Keyword}
  ([] (rand-nth all-vec))
  ([^java.util.Random rng] (util/get-rand-nth all-vec rng)))

(defn generate-sample-val
  "Generates random region code (string value)."
  {:added "8.12.4-0" :tag String}
  ([] (rand-nth by-val-vec))
  ([^java.util.Random rng] (util/get-rand-nth by-val-vec rng)))

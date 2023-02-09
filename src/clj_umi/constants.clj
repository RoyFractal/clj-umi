(ns clj-umi.constants
  (:require [clojure.spec.alpha :as spec]))


(def base-url "https://api.umipay.me")
(def prefixes {:umi "umi" :glize "glz"})


(spec/def :request/int-or-nil (spec/or :int   int?
                                       :nil   nil?))

(spec/def :request/address string?)
(spec/def :request/prefix string?)
(spec/def :request/mnemonic string?)
(spec/def :request/message string?)
(spec/def :request/limit :request/int-or-nil)
(spec/def :request/offset :request/int-or-nil)

(spec/def :request/public-key (spec/* int?))
(spec/def :request/private-key (spec/* int?))
(spec/def :request/amount (spec/or :int int?
                                   :float float?))




(spec/def :request/get-transaction (spec/keys :opt [:request/offset :request/limit]))
(spec/def :request/send (spec/keys :req [:request/public-key :request/private-key :request/address :request/amount]
                                   :opt [:request/prefix]))
(spec/def :request/restore-wallet (spec/keys :req [:request/mnemonic]
                                             :opt [:request/prefix]))
(spec/def :request/sign-message (spec/keys :req [:request/private-key :request/message]))

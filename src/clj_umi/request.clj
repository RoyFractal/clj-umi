(ns clj-umi.request
  (:require [clj-http.client :as client]
            [clj-umi.constants :as constants]
            [clojure.data.json :as json]
            [clojure.spec.alpha :as spec]))


(defn base-request [^String method ^String path & {:keys [params data]
                                                   :or   {params {}
                                                          data   nil}}]
  (let [url      (str constants/base-url "/" path)
        response (cond
                   (= method "GET")
                   (client/get url {:accept       :json
                                    :query-params params})
                   (= method "POST")
                   (client/post url {:content-type :json
                                     :body         (json/write-str data)}))]
    (-> response
        :body
        (json/read-str :key-fn keyword))))


(defn get-balance [^String address]
  {:pre [(spec/valid? string? address)]}
  (base-request "GET" (str "get_balance/" address)))


(defn get-transactions [^String address & {:keys [^Integer limit ^Integer offset]
                                           :or   {limit  nil
                                                  offset nil}}]
  {:pre [(spec/valid? string? address)
         (spec/valid? :request/get-transaction {:request/limit limit :request/offset offset})]}
  (base-request "GET" (str "get_transactions/" address) :params {:limit limit :offset offset}))



(defn generate-wallet [& {:keys [^String prefix]
                          :or   {prefix (:umi constants/prefixes)}}]
  (base-request "POST" "generate_wallet" :data {:prefix prefix}))


(defn send [private-key public-key ^String target-address amount & {:keys [^String prefix]
                                                                    :or   {prefix (:umi constants/prefixes)}}]
  {:pre [(spec/valid? :request/send {:request/private-key private-key
                                     :request/public-key  public-key
                                     :request/address     target-address
                                     :request/amount      amount
                                     :request/prefix      prefix})]}
  (base-request "POST" "send"
                :data {:privateKey    private-key
                       :publicKey     public-key
                       :targetAddress target-address
                       :amount        amount
                       :prefix        prefix}))

(defn restore-wallet [^String mnemonic & {:keys [^String prefix]
                                          :or   {prefix (:umi constants/prefixes)}}]
  {:pre [(spec/valid? :request/restore-wallet {:request/mnemonic mnemonic
                                               :request/prefix   prefix})]}
  (base-request "POST" "restore_wallet"
                :data {:mnemonic mnemonic
                       :prefix   prefix}))


(defn sign-message [private-key ^String message]
  {:pre [(spec/valid? :request/sign-message {:request/private-key private-key
                                             :request/message     message})]}
  (base-request "POST" "sign_message"
                :data {:privateKey private-key
                       :message    message}))

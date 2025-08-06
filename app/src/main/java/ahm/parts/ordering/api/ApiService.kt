package ahm.parts.ordering.api

import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.MultipartBody
import retrofit2.http.*

interface ApiService {

    @Multipart
    @POST("auth/login")
    fun login(
        @Part("email") username: String?,
        @Part("password") password: String?,
        @Part("regid") regid: String?
    ): Observable<String>

    @Multipart
    @POST("auth/forgot-password")
    fun forgotPassword(
        @Part("email") username: String?
    ): Single<String>

    @POST("oauth/token")
    fun oauthToken(): Single<String>

    @POST("auth/logout")
    fun logout(
    ): Single<String>

    @Multipart
    @POST("oauth/renew")
    fun renewToken(
        @Part("password") password: String?
    ): Single<String>

    //Wallboard
    @GET("home")
    fun getHome(
    ): Single<String>

    @GET("master/bulan")
    fun getMasterMonth(
    ): Single<String>


    @Multipart
    @POST("dashboard/index")
    fun dashboard(
        @Part("category") category: String?,
        @Part("month") month: String?,
        @Part("year") year: String?,
        @Part("ms_dealer_id") msDealerId: String
    ): Single<String>


    @POST("profile/profile")
    fun profile(
    ): Single<String>

    @GET("promo/brosure-promo")
    fun getPromoBrosure(
        @Query("page") page: Int
    ): Single<String>


    @GET("catalog/catalog")
    fun getCatalogue(
    ): Single<String>


    @GET("promo/part-promo")
    fun getPartPromo(
        @Query("page") page: Int
    ): Single<String>

    @GET("master/calendar")
    fun getMasterCalendar(
        @Query("year") year: Int?,
        @Query("month") month: Int?
    ): Single<String>

    @GET("home/activity")
    fun getActivity(
        @Query("date_activity") date: String?
    ): Single<String>


    @Multipart
    @POST("part/list-motor-type")
    fun getPartMotor(
        @Part("search") search: String
    ): Single<String>

    @Multipart
    @POST("part/list-item-group")
    fun getPartKelompokBarang(
        @Part("search") search: String
    ): Single<String>

    @Multipart
    @POST("part/part-favorit")
    fun getPartFavorit(
        @Part("ms_dealer_id") msDealerId: String
    ): Single<String>

    @Multipart
    @POST("part/list-cart")
    fun getCart(
        @Part("ms_dealer_id") msDealerId: String
    ): Single<String>

    @Multipart
    @POST("part/order-suggest")
    fun getOrderSuggestion(
        @Part("ms_dealer_id") msDealerId: String
    ): Single<String>

    @Multipart
    @POST("part/skema-pembelian")
    fun getSkemaPembelian(
        @Part("part_id") partId: String,
        @Part("part_number") partNumber: Int,
        @Part("part_description") partDescription: Int,
        @Part("het") het: Int,
        @Part("qty_w1") qtyW1: Int,
        @Part("amount_w1") amountW1: Int,
        @Part("qty_w2") qtyW2: Int,
        @Part("amount_w2") amountW2: Int,
        @Part("qty_w3") qtyW3: Int,
        @Part("amount_w3") amountW3: Int,
        @Part("qty_w4") qtyW4: Int,
        @Part("amount_w4") amountW4: Int,
        @Part("qty_avg") qtyAvg: Int,
        @Part("amount_avg") amountAvg: Int,
        @Part("qty_back") qtyBack: Int,
        @Part("amount_back") amount_back: Int,
        @Part("qty_suggest") qtySuggest: Int,
        @Part("amount_suggest") amountSuggest: Int,
        @Part("flag_campaign") flagCampaign: Int,
        @Part("multiple_dus") multipleDus: Int,
        @Part("ms_dealer_id") msDealerId: String
    ): Single<String>


    @GET("collection/list-collection")
    fun getCollection(
        @Query("kddealer") kddealer: String,
        @Query("begineffdate") begineffdate: String,
        @Query("endeffdate") endeffdate: String
    ): Single<String>

    @Multipart
    @POST("collection/payment-collection")
    fun PaymentCollection(
        @Part("idtransaksi") idtransaksi: String,
        @Part("datepayment") datepayment: String,
        @Part("totalpayment") totalpayment: String,
        @Part("photo") photo: String
    ): Single<String>


    @GET("dealer/list-competitor")
    fun getDealerCompetitor(
    ): Single<String>

    @Multipart
    @POST("sales/date-visit")
    fun getRencanaVisit(
        @Part("ms_dealer_id") msDealerId: String
    ): Single<String>

    @Multipart
    @POST("dealer/add-new-dealer")
    fun addNewDealer(
        @Part("name") name: String,
        @Part("latlong") latlong: String,
        @Part("address") address: String,
        @Part photo: MultipartBody.Part,
        @Part("phone") phone: String,
        @Part("description") description: String
    ): Single<String>

    @Multipart
    @POST("kredit/check-kredit-limit")
    fun kreditCheckLimit(
        @Part("ms_dealer_id") msDealerId: String
    ): Single<String>


    @Multipart
    @POST("dashboard/notice")
    fun notification(
        @Part("page") page: Int
    ): Single<String>

    @Multipart
    @POST("profile/change-password")
    fun changePassword(
        @Part("password") password: String
    ): Single<String>

    @Multipart
    @POST("kredit/check-jatuh-tempo")
    fun kreditJatuhTempo(
        @Part("ms_dealer_id") msDealerId: String
    ): Single<String>

    @Multipart
    @POST("kredit/list-kredit-limit")
    fun kreditLimit(
        @Part("ms_dealer_id") msDealerId: String
    ): Single<String>

    @Multipart
    @POST("kredit/list-jatuh-tempo")
    fun kreditJatuhTempoList(
        @Part("ms_dealer_id") msDealerId: String
    ): Single<String>


    @Multipart
    @POST("tracking/tracking-order")
    fun getTrackingOrder(
        @Part("ms_dealer_id") msDealerId: String,
        @Part("search") search: String,
        @Part("sorting") sorting: String,
        @Part("part_number") partNumber: String,
        @Part("part_description") partDescription: String,
        @Part("month") month: String,
        @Part("no_order") noOrder: String,
        @Part("status_bo") statusBo: String,
        @Part("status_invoice") statusInvoice: String,
        @Part("status_shipping") statusShipping: String
    ): Single<String>

    @Multipart
    @POST("tracking/detail-tracking")
    fun getTrackingOrderDetail(
        @Part("tracking_item_id") trackingItemId: String
    ): Single<String>


    @Multipart
    @POST("sales/add-visit")
    fun addSalesVisit(
        @Part("latitude") latitude: String,
        @Part("longitude") longitude: String,
        @Part("ms_dealer_id") msDealerId: String,
        @Part("date") date: String
    ): Single<String>


    @Multipart
    @POST("sales/checkin")
    fun salesVisitCheckin(
        @Part("code_visit") codeVisit: String
    ): Single<String>


    @Multipart
    @POST("sales/checkin-checkout")
    fun salesVisitCheckout(
        @Part("code_visit") codeVisit: String
    ): Single<String>

    @POST("sales/check-checkin")
    fun checkinViewDashboard(
    ): Single<String>


    @Multipart
    @POST("dealer/add-competitor")
    fun addDealerCompetitor(
        @Part("code_dealer") codeDealer: String,
        @Part("id_role") id_role: String,
        @Part("name_competitor") name_competitor: String,
        @Part("product") product: String,
        @Part("title_activity_competitor") title_activity_competitor: String,
        @Part("begin_effdate") begin_effdate: String,
        @Part("end_effdate") end_effdate: String,
        @Part photo: MultipartBody.Part,
        @Part("description") description: String
    ): Single<String>

    @Multipart
    @POST("dealer/add-competitor")
    fun addDealerCompetitorMultiple(
        @Part("code_dealer") codeDealer: String,
        @Part("id_role") id_role: String,
        @Part("name_competitor") name_competitor: String,
        @Part("product") product: String,
        @Part("title_activity_competitor") title_activity_competitor: String,
        @Part("begin_effdate") begin_effdate: String,
        @Part("end_effdate") end_effdate: String,
        @Part("photo") photo: String,
        @Part("description") description: String
    ): Single<String>


    @Multipart
    @POST("part/add-favorit")
    fun addFavorite(
        @Part("id_part") idPart: String,
        @Part("is_love") isLove: Int,
        @Part("ms_dealer_id") msDealerId: String
    ): Single<String>


    @GET("sales/list-dealer")
    fun getKodeDealer(
        @Query("search") search: String
    ): Single<String>

    @GET("sales/list-dealer")
    fun getKodeDealerRealisasiVisit(
        @Query("search") search: String
    /*    @Query("salesman_id") salesmanId: String*/
    ): Single<String>

    @GET("sales/koordinator")
    fun getSalesKoordinator(
        @Query("search") search: String
    ): Single<String>

    @GET("sales/salesman")
    fun getSalesman(
        @Query("search") search: String
    ): Single<String>


    @Multipart
    @POST("sales/efectivitas-salesman")
    fun getEfektivitasSalesman(
        @Part("dealer") dealer: String,
        @Part("start_date") start_date: String,
        @Part("end_date") end_date: String,
        @Part("page") page: Int
    ): Single<String>

    @Multipart
    @POST("sales/efectivitas-coordinator")
    fun getEfektivitasCoordinator(
        @Part("salesman") salesman: String,
        @Part("dealer") dealer: String,
        @Part("start_date") start_date: String,
        @Part("end_date") end_date: String,
        @Part("page") page: Int
    ): Single<String>

    @Multipart
    @POST("sales/next-prev-efectivitas-salesman")
    fun getEfektivitasNextSalesman(
        @Part("effectivitas_id") effectivitasId: String,
        @Part("dealer") dealer: String,
        @Part("start_date") startDate: String,
        @Part("end_date") endDate: String
    ): Single<String>

    @Multipart
    @POST("sales/next-prev-efectivitas-coordinator")
    fun getEfektivitasNextCoordinator(
        @Part("salesman") salesman: String,
        @Part("dealer") dealer: String,
        @Part("start_date") startDate: String,
        @Part("end_date") endDate: String,
        @Part("effectivitas_id") effectivitas_id: String
    ): Single<String>


    @Multipart
    @POST("sales/next-prev-realisasi-visit-salesman")
    fun getRealisasiVisitNextSalesman(
        @Part("realisasi_id") realisasiId: String,
        @Part("dealer") dealer: String,
        @Part("start_date") startDate: String,
        @Part("end_date") endDate: String
    ): Single<String>

    @Multipart
    @POST("sales/next-prev-realisasi-visit-coordinator")
    fun getRealisasiVisitNextCoordinator(
        @Part("realisasi_id") realisasiId: String,
        @Part("salesman") salesman: String,
        @Part("dealer") dealer: String,
        @Part("start_date") startDate: String,
        @Part("end_date") endDate: String
    ): Single<String>


    @Multipart
    @POST("sales/realisasi-visit-salesman")
    fun getRealisasiVisitSalesman(
        @Part("dealer") dealer: String,
        @Part("start_date") start_date: String,
        @Part("end_date") end_date: String,
        @Part("page") page: Int
    ): Single<String>


    @Multipart
    @POST("sales/realisasi-visit-coordinator")
    fun getRealisasiVisitCoordinator(
        @Part("salesman") salesman: String,
        @Part("dealer") dealer: String,
        @Part("start_date") start_date: String,
        @Part("end_date") end_date: String,
        @Part("page") page: Int
    ): Single<String>

    @Multipart
    @POST("sales/realisasi-visit-manager")
    fun getRealisasiVisitCoordinatorManager(
        @Part("coordinator") coordinator: String,
        @Part("salesman") salesman: String,
        @Part("dealer") dealer: String,
        @Part("start_date") start_date: String,
        @Part("end_date") end_date: String,
        @Part("page") page: Int
    ): Single<String>


    @Multipart
    @POST("sales/efectivitas-manager")
    fun getEfektvitasCoordinatorManager(
        @Part("coordinator") coordinator: String,
        @Part("salesman") salesman: String,
        @Part("dealer") dealer: String,
        @Part("start_date") start_date: String,
        @Part("end_date") end_date: String,
        @Part("page") page: Int
    ): Single<String>


    @Multipart
    @POST("sales/efectivitas")
    fun getEfektivitasVisit(
        @Part("koordinator") koordinator: String,
        @Part("salesman") salesman: String,
        @Part("dealer") dealer: String,
        @Part("start_date") start_date: String,
        @Part("end_date") end_date: String
    ): Single<String>

    @Multipart
    @POST("sales/realisasi-visit")
    fun getRealisasiVisit(
        @Part("koordinator") koordinator: String,
        @Part("salesman") salesman: String,
        @Part("dealer") dealer: String,
        @Part("start_date") start_date: String,
        @Part("end_date") end_date: String
    ): Single<String>


    @Multipart
    @POST("part/part-search")
    fun getPartSearch(
        @Part("similarity") similarity: String,
        @Part("part_number") part_number: String,
        @Part("part_description") part_description: String,
        @Part("item_group") item_group: String,
        @Part("motor_type") motor_type: String,
        @Part("shorting") shorting: String,
        @Part("ms_dealer_id") msDealerId: String
    ): Single<String>


    @Multipart
    @POST("part/add-to-cart")
    fun partNumberAddToCart(
        @Part("id_part") idPart: String,
        @Part("ms_dealer_id") msDealerId: String
    ): Single<String>

    @Multipart
    @POST("part/add-cart")
    fun partNumberAddToCartJson(
        @Part("item_part") itemPart: String,
        @Part("ms_dealer_id") msDealerId: String
    ): Single<String>


    @Multipart
    @POST("part/use-suggestion")
    fun partUseSugestion(
        @Part("list_item") listItem: String,
        @Part("ms_dealer_id") msDealerId: String
    ): Single<String>


    @Multipart
    @POST("part/submit-order")
    fun partSummary(
        @Part("month_delivery") monthDelivery: String,
        @Part("ms_dealer_id") msDealerId: String
    ): Single<String>


    @Multipart
    @POST("part/update-qty")
    fun partNumberUpdateQty(
        @Part("id_part_cart") idPart: String,
        @Part("qty") qty: String,
        @Part("ms_dealer_id") msDealerId: String
    ): Single<String>


    @Multipart
    @POST("part/remove-cart")
    fun partNumberRemoveCart(
        @Part("id_part_cart") idPart: String,
        @Part("ms_dealer_id") msDealerId: String
    ): Single<String>

    @Multipart
    @POST("stock/check-stock")
    fun getStockSearch(
        @Part("similarity") similarity: String,
        @Part("part_number") part_number: String,
        @Part("part_description") part_description: String,
        @Part("item_group") item_group: String,
        @Part("motor_type") motor_type: String,
        @Part("shorting") shorting: String
    ): Single<String>


    //Stock
    @GET("stock/filter")
    fun getStockFilter(): Single<String>

    @GET("stock/unit")
    fun getStockUnit(
        @Query("search") search: String?,
        @Query("year") year: String?,
        @Query("type_id") type_id: Int?,
        @Query("page") page: Int?
    ): Single<String>

    @GET("stock/accessories")
    fun getStockAccessories(
        @Query("search") search: String?,
        @Query("year") year: String?,
        @Query("type_id") type_id: Int?,
        @Query("page") page: Int
    ): Single<String>

    @GET("stock/accessories-detail")
    fun getStockAccessoriesDetail(
        @Query("search") search: String?,
        @Query("id_unit") idUnit: Int
    ): Single<String>

    @GET("stock/apparel")
    fun getStockApparel(
        @Query("search") search: String?,
        @Query("page") page: Int
    ): Single<String>

    //Activity
    @GET("master/customer")
    fun getCustomer(
        @Query("search") search: String?,
        @Query("page") page: Int?
    ): Single<String>

    @GET("master/type-unit")
    fun getUnit(
        @Query("search") search: String?
    ): Single<String>

    @GET("master/pekerjaan")
    fun getJob(
    ): Single<String>

    @GET("master/metode-followup")
    fun getFollowUp(
    ): Single<String>

    //Prospect
    @GET("prospek/info")
    fun getInfoProspect(
    ): Single<String>


    //HOME
    @GET("leaderboard")
    fun getLeaderboard(
    ): Single<String>


}
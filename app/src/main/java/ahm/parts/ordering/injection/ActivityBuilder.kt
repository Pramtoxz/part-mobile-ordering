package ahm.parts.ordering.injection

import ahm.parts.ordering.helper.*
import ahm.parts.ordering.ui.auth.forgotpassword.ForgotPasswordActivity
import ahm.parts.ordering.ui.auth.forgotpassword.ForgotPasswordModule
import ahm.parts.ordering.ui.home.HomeActivity
import ahm.parts.ordering.ui.home.HomeModule
import ahm.parts.ordering.ui.widget.activity.SalesActivityFragment
import ahm.parts.ordering.ui.widget.activity.prospect.ProspectActivity
import ahm.parts.ordering.ui.widget.activity.prospect.ProspectModule
import ahm.parts.ordering.ui.widget.activity.prospect.customer.ProspectCustomerActivity
import ahm.parts.ordering.ui.widget.activity.prospect.customer.ProspectCustomerModule
import ahm.parts.ordering.ui.widget.activity.prospect.detail.DetailProspectActivity
import ahm.parts.ordering.ui.widget.activity.prospect.detail.DetailProspectModule
import ahm.parts.ordering.ui.widget.activity.sales.ActivitySalesActivity
import ahm.parts.ordering.ui.widget.activity.sales.ActivitySalesModule
import ahm.parts.ordering.ui.widget.activity.spk.SPKActivity
import ahm.parts.ordering.ui.widget.activity.spk.SPKModule
import ahm.parts.ordering.ui.home.catalogue.CatalogueFragment
import ahm.parts.ordering.ui.home.catalogue.detail_file.file.CatalogueFileActivity
import ahm.parts.ordering.ui.home.catalogue.detail_file.file_list.CatalogueFileListActivity
import ahm.parts.ordering.ui.home.catalogue.detail_file.file_parent.CatalogueFileParentActivity
import ahm.parts.ordering.ui.home.catalogue.part_catalogue.PartCatalogueActivity
import ahm.parts.ordering.ui.home.dealer.competitor.CompetitorModule
import ahm.parts.ordering.ui.home.dealer.competitor.add.single.CompetitorAddActivity
import ahm.parts.ordering.ui.home.dealer.competitor.list.CompetitorActivity
import ahm.parts.ordering.ui.home.dealer.competitor.detail.single.CompetitorDetailActivity
import ahm.parts.ordering.ui.home.dealer.kreditlimit.KreditLimitActivity
import ahm.parts.ordering.ui.home.dealer.kreditlimit.KreditLimitModule
import ahm.parts.ordering.ui.home.dealer.kreditlimit.detail.jatuhtempo.KreditJatuhTempoDetailActivity
import ahm.parts.ordering.ui.home.dealer.kreditlimit.detail.kreditlimit.KreditLimitDetailActivity
import ahm.parts.ordering.ui.home.dealer.kreditlimit.fragment.CheckKreditJatuhTempoFragment
import ahm.parts.ordering.ui.home.dealer.kreditlimit.fragment.CheckKreditLimitFragment
import ahm.parts.ordering.ui.home.dealer.salesmanvisit.SalesmanVisitActivity
import ahm.parts.ordering.ui.home.dealer.salesmanvisit.efektivitas.EfektivitasModule
import ahm.parts.ordering.ui.home.dealer.salesmanvisit.realisasivisit.RealisasiVisitModule
import ahm.parts.ordering.ui.home.dealer.salesmanvisit.visit.AddNewVisitActivity
import ahm.parts.ordering.ui.home.dealer.salesmanvisit.visit.VisitModule
import ahm.parts.ordering.ui.home.dealer.salesmanvisit.visit.rencanavisit.RencanaVisitActivity
import ahm.parts.ordering.ui.home.dealer.salesmanvisit.visit.salesvisit.SalesVisitActivity
import ahm.parts.ordering.ui.home.profile.ProfileFragment
import ahm.parts.ordering.ui.home.home.HomeFragment
import ahm.parts.ordering.ui.home.home.campaignpromo.CampaignPromoActivity
import ahm.parts.ordering.ui.home.home.campaignpromo.CampaignPromoModule
import ahm.parts.ordering.ui.home.home.campaignpromo.fragment.brosur.BrosurPromoFragment
import ahm.parts.ordering.ui.home.home.campaignpromo.fragment.part.PartPromoFragment
import ahm.parts.ordering.ui.home.home.campaignpromo.fragment.brosur.BrosureDetailActivity
import ahm.parts.ordering.ui.home.home.campaignpromo.fragment.part.PartPromoDetailActivity
import ahm.parts.ordering.ui.home.home.ordersugestion.CheckStockSearchActivity
import ahm.parts.ordering.ui.home.home.ordersugestion.cekstockpartfilter.CheckStockFilterActivity
import ahm.parts.ordering.ui.home.home.ordersugestion.cekstockpartfilter.CheckStockFilterModule
import ahm.parts.ordering.ui.home.home.ordersugestion.cekstockpartfilter.CheckStockPartActivity
import ahm.parts.ordering.ui.home.home.dashboard.dealer.DealerFragment
import ahm.parts.ordering.ui.home.home.dashboard.salesman.SalesmanFragment
import ahm.parts.ordering.ui.home.home.ordersugestion.SearchPartActivity
import ahm.parts.ordering.ui.home.home.partnumber.favorite.FavoriteActivity
import ahm.parts.ordering.ui.home.home.ordersugestion.kelompok_barang.KelompokBarangSearchActivity
import ahm.parts.ordering.ui.home.home.ordersugestion.tipemotor.TipeMotorSearchActivity
import ahm.parts.ordering.ui.home.home.partnumber.cart.CartActivity
import ahm.parts.ordering.ui.home.home.partnumber.cart.CartModule
import ahm.parts.ordering.ui.home.home.partnumber.cart.skemapembelian.filter.FilterListActivity
import ahm.parts.ordering.ui.home.home.partnumber.detail.PartNumberDetailActivity
import ahm.parts.ordering.ui.home.home.partnumber.favorite.FavoriteModule
import ahm.parts.ordering.ui.home.home.partnumber.historyorder.list.HistoryOrderActivity
import ahm.parts.ordering.ui.home.home.partnumber.kelompokbarang.KelompokBarangSearchModule
import ahm.parts.ordering.ui.home.home.partnumber.kodedealer.KodeDealerActivity
import ahm.parts.ordering.ui.home.home.partnumber.kodedealer.KodeDealerModule
import ahm.parts.ordering.ui.home.home.partnumber.partnumbersearch.PartNumberFilterModule
import ahm.parts.ordering.ui.home.home.partnumber.cart.skemapembelian.SkemaPembelianActivity
import ahm.parts.ordering.ui.home.home.partnumber.cart.skemapembelian.SkemaPembelianModule
import ahm.parts.ordering.ui.home.home.partnumber.cart.summary.SummaryActivity
import ahm.parts.ordering.ui.home.home.partnumber.cart.suggestorder.SuggestOrderActivity
import ahm.parts.ordering.ui.home.home.partnumber.cart.summary.result.SummaryResultActivity
import ahm.parts.ordering.ui.home.home.partnumber.searchpart.SearchPartModule
import ahm.parts.ordering.ui.home.home.partnumber.tipemotor.TipeMotorSearchModule
import ahm.parts.ordering.ui.home.notification.NotificationActivity
import ahm.parts.ordering.ui.home.notification.NotificationModule
import ahm.parts.ordering.ui.home.notification.detail.NotificationInformationActivity
import ahm.parts.ordering.ui.home.notification.detail.NotificationKreditLimitActivity
import ahm.parts.ordering.ui.home.order.tracking.TrackingOrderActivity
import ahm.parts.ordering.ui.home.order.tracking.TrackingOrderModule
import ahm.parts.ordering.ui.home.order.tracking.detailitem.TrackingOrderDetailActivity
import ahm.parts.ordering.ui.home.order.tracking.detailpengiriman.TrackingOrderPengirimanDetailActivity
import ahm.parts.ordering.ui.auth.login.LoginActivity
import ahm.parts.ordering.ui.auth.login.LoginModule
import ahm.parts.ordering.ui.home.dealer.collection.CollectionActivity
import ahm.parts.ordering.ui.home.dealer.collection.CollectionModule
import ahm.parts.ordering.ui.home.dealer.collection.CollectionNotification
import ahm.parts.ordering.ui.home.dealer.collection.add.CollectionAddMultipleActivity
import ahm.parts.ordering.ui.home.dealer.collection.list.CollectionActivityList
import ahm.parts.ordering.ui.home.dealer.salesmanvisit.addnewdealer.AddNewDealerActivity
import ahm.parts.ordering.ui.home.dealer.salesmanvisit.addnewdealer.AddNewDealerModule
import ahm.parts.ordering.ui.home.dealer.competitor.add.multiple.CompetitorAddMultipleActivity
import ahm.parts.ordering.ui.home.dealer.kreditlimit.detail.jatuhtempo.KreditJatuhTempoListDetailActivity
import ahm.parts.ordering.ui.home.dealer.kreditlimit.detail.kreditlimit.KreditLimitListDetailActivity
import ahm.parts.ordering.ui.home.dealer.kreditlimit.fragment.single.CheckKreditJatuhTempoSingleChoiceFragment
import ahm.parts.ordering.ui.home.dealer.kreditlimit.fragment.single.CheckKreditLimitSingleChoiceFragment
import ahm.parts.ordering.ui.home.dealer.salesmanvisit.efektivitas.EfektivitasActivity
import ahm.parts.ordering.ui.home.dealer.salesmanvisit.efektivitas.planactual.detail.PlanActualEfektivitasDetailFragment
import ahm.parts.ordering.ui.home.dealer.salesmanvisit.efektivitas.planactual.detail.PlanActualEfektivitasDetailTabActivity
import ahm.parts.ordering.ui.home.dealer.salesmanvisit.efektivitas.planactual.koordinator.PlanActualEfektivitasCoordinatorActivity
import ahm.parts.ordering.ui.home.dealer.salesmanvisit.efektivitas.planactual.manager.PlanActualEfektivitasCoordinatorManagerActivity
import ahm.parts.ordering.ui.home.dealer.salesmanvisit.efektivitas.planactual.manager.PlanActualEfektivitasSalesmanManagerActivity
import ahm.parts.ordering.ui.home.dealer.salesmanvisit.efektivitas.planactual.manager.detail.PlanActualManagerDetailEfektivitasFragment
import ahm.parts.ordering.ui.home.dealer.salesmanvisit.efektivitas.planactual.salesman.PlanActualEfektivitasSalesmanActivity
import ahm.parts.ordering.ui.home.dealer.salesmanvisit.realisasivisit.RealisasiVisitActivity
import ahm.parts.ordering.ui.home.dealer.salesmanvisit.realisasivisit.planactual.PlanActualRealiasiModule
import ahm.parts.ordering.ui.home.dealer.salesmanvisit.realisasivisit.planactual.koordinator.PlanActualRealisasiKoordinatorActivity
import ahm.parts.ordering.ui.home.dealer.salesmanvisit.realisasivisit.planactual.detail.PlanActualDetailRealisasiFragment
import ahm.parts.ordering.ui.home.dealer.salesmanvisit.realisasivisit.planactual.detail.PlanActualDetailRealisasiVisitTabActivity
import ahm.parts.ordering.ui.home.dealer.salesmanvisit.realisasivisit.planactual.manager.PlanActualRealisasiCoordinatorManagerActivity
import ahm.parts.ordering.ui.home.dealer.salesmanvisit.realisasivisit.planactual.manager.PlanActualRealisasiSalesmanManagerActivity
import ahm.parts.ordering.ui.home.dealer.salesmanvisit.realisasivisit.planactual.manager.detail.PlanActualManagerDetailActivity
import ahm.parts.ordering.ui.home.dealer.salesmanvisit.realisasivisit.planactual.manager.detail.PlanActualManagerDetailFragment
import ahm.parts.ordering.ui.home.dealer.salesmanvisit.realisasivisit.planactual.salesman.PlanActualRealisasiSalesmanActivity
import ahm.parts.ordering.ui.home.home.ordersugestion.ordersugestion.SuggestOrderModule
import ahm.parts.ordering.ui.home.order.order.OrderModule
import ahm.parts.ordering.ui.home.profile.ProfileModule
import ahm.parts.ordering.ui.home.profile.changepassword.ChangePasswordActivity
import ahm.parts.ordering.ui.previewimage.PreviewImageActivity
import ahm.parts.ordering.ui.splash.SplashActivity
import ahm.parts.ordering.ui.splash.SplashModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    /*ACTIVITY*/
    @ContributesAndroidInjector(modules = [SplashModule::class])
    abstract fun bindSplashActivity(): SplashActivity

    @ContributesAndroidInjector(modules = [LoginModule::class])
    abstract fun bindLoginActivity(): LoginActivity

    @ContributesAndroidInjector(modules = [HomeModule::class])
    abstract fun bindHomeActivity(): HomeActivity

    @ContributesAndroidInjector(modules = [SearchPartModule::class])
    abstract fun bindPartNumberSearchActivity(): ahm.parts.ordering.ui.home.home.partnumber.searchpart.SearchPartActivity

    @ContributesAndroidInjector(modules = [ProspectModule::class])
    abstract fun bindProspectActivity(): ProspectActivity

    @ContributesAndroidInjector(modules = [SPKModule::class])
    abstract fun bindSPKActivity(): SPKActivity

    @ContributesAndroidInjector(modules = [ActivitySalesModule::class])
    abstract fun bindActivitySalesActivity(): ActivitySalesActivity


    @ContributesAndroidInjector(modules = [DetailProspectModule::class])
    abstract fun bindDetailProspectActivity(): DetailProspectActivity

    @ContributesAndroidInjector(modules = [ProspectCustomerModule::class])
    abstract fun bindProspectCustomerActivity(): ProspectCustomerActivity


    /*FRAGMENT*/
    @ContributesAndroidInjector(modules = [HomeModule::class])
    abstract fun bindWallboardFragment(): HomeFragment

    @ContributesAndroidInjector(modules = [HomeModule::class])
    abstract fun bindMoreFragment(): ProfileFragment

    @ContributesAndroidInjector(modules = [HomeModule::class])
    abstract fun bindSalesFragment(): SalesActivityFragment

    @ContributesAndroidInjector(modules = [HomeModule::class])
    abstract fun bindKelompokBarangSearchActivity(): KelompokBarangSearchActivity

    @ContributesAndroidInjector(modules = [HomeModule::class])
    abstract fun bindTipeMotorSearchActivity(): TipeMotorSearchActivity

    @ContributesAndroidInjector(modules = [FavoriteModule::class])
    abstract fun bindFavoriteActivity(): FavoriteActivity

    @ContributesAndroidInjector(modules = [HomeModule::class])
    abstract fun bindCampaignPromoActivity(): CampaignPromoActivity

    @ContributesAndroidInjector(modules = [CampaignPromoModule::class])
    abstract fun bindBrosurPromoFragment(): BrosurPromoFragment

    @ContributesAndroidInjector(modules = [CampaignPromoModule::class])
    abstract fun bindPartPromoFragment(): PartPromoFragment

    @ContributesAndroidInjector(modules = [HomeModule::class])
    abstract fun bindSalesmanFragment(): SalesmanFragment

    @ContributesAndroidInjector(modules = [HomeModule::class])
    abstract fun bindDealerDashboardFragment(): DealerFragment

    @ContributesAndroidInjector(modules = [HomeModule::class])
    abstract fun bindBukuPedomanPemilikActivity(): CatalogueFileActivity

    @ContributesAndroidInjector(modules = [HomeModule::class])
    abstract fun bindCatalogueFileListActivity(): CatalogueFileListActivity

    @ContributesAndroidInjector(modules = [HomeModule::class])
    abstract fun bindPartCatalogueActivity(): PartCatalogueActivity

    @ContributesAndroidInjector(modules = [KelompokBarangSearchModule::class])
    abstract fun bindKelompokBarangSearchActivityPart(): ahm.parts.ordering.ui.home.home.partnumber.kelompokbarang.KelompokBarangSearchActivity

    @ContributesAndroidInjector(modules = [TipeMotorSearchModule::class])
    abstract fun bindTipeMotorSearchActivityPart(): ahm.parts.ordering.ui.home.home.partnumber.tipemotor.TipeMotorSearchActivity

    @ContributesAndroidInjector(modules = [HomeModule::class])
    abstract fun bindSearchPartActivity(): SearchPartActivity

    @ContributesAndroidInjector(modules = [HomeModule::class])
    abstract fun bindCheckStockPartActivity(): CheckStockPartActivity

    @ContributesAndroidInjector(modules = [HomeModule::class])
    abstract fun bindCatalogueFragment(): CatalogueFragment

    @ContributesAndroidInjector(modules = [PartNumberFilterModule::class])
    abstract fun bindPartNumberFilterActivity(): ahm.parts.ordering.ui.home.home.partnumber.partnumbersearch.PartNumberFilterActivity

    @ContributesAndroidInjector(modules = [HomeModule::class])
    abstract fun bindPartNumberDetailActivity(): PartNumberDetailActivity

    @ContributesAndroidInjector(modules = [HomeModule::class])
    abstract fun bindCatalogueFileParentActivity(): CatalogueFileParentActivity

    @ContributesAndroidInjector(modules = [HomeModule::class])
    abstract fun bindBrosureDetailActivity(): BrosureDetailActivity

    @ContributesAndroidInjector(modules = [HomeModule::class])
    abstract fun bindPartPromoDetailActivity(): PartPromoDetailActivity

    @ContributesAndroidInjector(modules = [CheckStockFilterModule::class])
    abstract fun bindCheckStockFilterActivity(): CheckStockFilterActivity

    @ContributesAndroidInjector(modules = [HomeModule::class])
    abstract fun bindCheckStockSearchActivity(): CheckStockSearchActivity

    @ContributesAndroidInjector(modules = [TrackingOrderModule::class])
    abstract fun bindOnGoingOrderActivity(): TrackingOrderActivity

    @ContributesAndroidInjector(modules = [TrackingOrderModule::class])
    abstract fun bindOnGoingOrderDetailsActivity(): TrackingOrderDetailActivity

    @ContributesAndroidInjector(modules = [KodeDealerModule::class])
    abstract fun bindKodeDealerActivity(): KodeDealerActivity

    @ContributesAndroidInjector(modules = [KodeDealerModule::class])
    abstract fun bindKodeDealerSalesmanVisitActivity(): ahm.parts.ordering.ui.home.dealer.salesmanvisit.visit.kodedealer.KodeDealerActivity

    @ContributesAndroidInjector(modules = [CompetitorModule::class])
    abstract fun bindCompetitorActivity(): CompetitorActivity

    @ContributesAndroidInjector(modules = [CompetitorModule::class])
    abstract fun bindCompetitorAddMultipleActivity(): CompetitorAddMultipleActivity

    @ContributesAndroidInjector(modules = [HomeModule::class])
    abstract fun bindDealerFragment(): ahm.parts.ordering.ui.home.dealer.DealerFragment

    @ContributesAndroidInjector(modules = [VisitModule::class])
    abstract fun bindAddNewVisitActivity(): AddNewVisitActivity

    @ContributesAndroidInjector(modules = [HomeModule::class])
    abstract fun bindSalesmanVisitActivity(): SalesmanVisitActivity

//    @ContributesAndroidInjector(modules = [HomeModule::class])
//    abstract fun bindOrderFragment(): OrderFragment

    @ContributesAndroidInjector(modules = [OrderModule::class])
    abstract fun bindOrderMenu(): OrderMenu

//    @ContributesAndroidInjector(modules = [HomeModule::class])
//    abstract fun bindKodeDealerEfektivitasActivity(): ahm.parts.ordering.ui.home.dealer.salesmanvisit.efektivitas.dealer.KodeDealerActivity

    @ContributesAndroidInjector(modules = [RealisasiVisitModule::class])
    abstract fun bindRealisasiVisitMultipleActivity(): RealisasiVisitActivity

    @ContributesAndroidInjector(modules = [CartModule::class])
    abstract fun bindCartActivity(): CartActivity

    @ContributesAndroidInjector(modules = [HomeModule::class])
    abstract fun bindFilterListActivity(): FilterListActivity

    @ContributesAndroidInjector(modules = [HomeModule::class])
    abstract fun bindHistoryOrderActivity(): HistoryOrderActivity

    @ContributesAndroidInjector(modules = [SkemaPembelianModule::class])
    abstract fun bindSkemaPembelianActivity(): SkemaPembelianActivity

    @ContributesAndroidInjector(modules = [SkemaPembelianModule::class])
    abstract fun bindSkemaPembelianOrderSuggestionActivity(): SkemaPembelianOrderSuggestionActivity

    @ContributesAndroidInjector(modules = [CartModule::class])
    abstract fun bindSuggestOrderPartActivity(): SuggestOrderActivity

    @ContributesAndroidInjector(modules = [HomeModule::class])
    abstract fun bindCompetitorDetailActivity(): CompetitorDetailActivity

    @ContributesAndroidInjector(modules = [HomeModule::class])
    abstract fun bindCompetitorDetailSliderActivity(): CompetitorDetailSliderActivity

    @ContributesAndroidInjector(modules = [CompetitorModule::class])
    abstract fun bindCompetitorAddActivity(): CompetitorAddActivity

    @ContributesAndroidInjector(modules = [CartModule::class])
    abstract fun bindSummaryActivity(): SummaryActivity

    @ContributesAndroidInjector(modules = [CartModule::class])
    abstract fun bindSummaryResultActivity(): SummaryResultActivity

    @ContributesAndroidInjector(modules = [SuggestOrderModule::class])
    abstract fun bindSuggestOrderActivity(): ahm.parts.ordering.ui.home.home.ordersugestion.ordersugestion.SuggestOrderActivity

    @ContributesAndroidInjector(modules = [ahm.parts.ordering.ui.home.dealer.competitor.kodedealer.KodeDealerModule::class])
    abstract fun bindKodeDealerCompetitorActivity(): ahm.parts.ordering.ui.home.dealer.competitor.kodedealer.KodeDealerActivity

    @ContributesAndroidInjector(modules = [ahm.parts.ordering.ui.home.home.ordersugestion.kodedealer.KodeDealerModule::class])
    abstract fun bindKodeDealerOrderSuggestionActivity(): ahm.parts.ordering.ui.home.home.ordersugestion.kodedealer.KodeDealerActivity

    @ContributesAndroidInjector(modules = [CartModule::class])
    abstract fun bindCartOrderSugestionActivity(): ahm.parts.ordering.ui.home.home.ordersugestion.cart.CartActivity

    @ContributesAndroidInjector(modules = [SearchPartModule::class])
    abstract fun bindPartNumberSearchOrderSugestionActivity(): ahm.parts.ordering.ui.home.home.ordersugestion.partsearch.PartNumberSearchActivity

    @ContributesAndroidInjector(modules = [VisitModule::class])
    abstract fun bindRencanaVisitActivity(): RencanaVisitActivity

    @ContributesAndroidInjector(modules = [VisitModule::class])
    abstract fun bindSalesVisitActivity(): SalesVisitActivity

    @ContributesAndroidInjector(modules = [KodeDealerModule::class])
    abstract fun bindKodeDealerTrackingOrderActivity(): KodeDealerTrackingOrderActivity

    @ContributesAndroidInjector(modules = [TrackingOrderModule::class])
    abstract fun bindTrackingOrderPengirimanDetailActivity(): TrackingOrderPengirimanDetailActivity

    @ContributesAndroidInjector(modules = [NotificationModule::class])
    abstract fun bindNotificationActivity(): NotificationActivity

    @ContributesAndroidInjector(modules = [NotificationModule::class])
    abstract fun bindNotificationInformationActivity(): NotificationInformationActivity

    @ContributesAndroidInjector(modules = [HomeModule::class])
    abstract fun bindNotificationKreditLimitActivity(): NotificationKreditLimitActivity

    @ContributesAndroidInjector(modules = [CollectionModule::class])
    abstract fun bindcollectionActivity(): CollectionActivity

    @ContributesAndroidInjector(modules = [CollectionModule::class])
    abstract fun bindcollectionActivityList(): CollectionActivityList

    @ContributesAndroidInjector(modules = [CollectionModule::class])
    abstract fun bindCollectionAddPaymentActivity(): CollectionAddPaymentActivity

    @ContributesAndroidInjector(modules = [CollectionModule::class])
    abstract fun bindCollectionNotificationActivity(): CollectionNotification

    @ContributesAndroidInjector(modules = [ahm.parts.ordering.ui.home.dealer.collection.kodedealer.KodeDealerModule::class])
    abstract fun bindkodedealercollectionActivity(): ahm.parts.ordering.ui.home.dealer.collection.kodedealer.KodeDealerActivity

    @ContributesAndroidInjector(modules = [KreditLimitModule::class])
    abstract fun bindKreditLimitActivity(): KreditLimitActivity

    @ContributesAndroidInjector(modules = [KreditLimitModule::class])
    abstract fun bindCheckKreditLimitFragment(): CheckKreditLimitFragment

    @ContributesAndroidInjector(modules = [KreditLimitModule::class])
    abstract fun bindCheckKreditLimitSingleChoiceFragment(): CheckKreditLimitSingleChoiceFragment

    @ContributesAndroidInjector(modules = [KreditLimitModule::class])
    abstract fun bindCheckKreditJatuhTempoFragment(): CheckKreditJatuhTempoFragment

    @ContributesAndroidInjector(modules = [KreditLimitModule::class])
    abstract fun bindCheckKreditJatuhTempoSingleChoiceFragment(): CheckKreditJatuhTempoSingleChoiceFragment

    @ContributesAndroidInjector(modules = [KodeDealerModule::class])
    abstract fun bindKodeDealerKreditLimit(): KodeDealerKreditLimit

    @ContributesAndroidInjector(modules = [KodeDealerModule::class])
    abstract fun bindKodeDealerKreditLimitSingleActivity(): KodeDealerKreditLimitSingleActivity

    @ContributesAndroidInjector(modules = [KreditLimitModule::class])
    abstract fun bindKreditLimitDetailActivity(): KreditLimitDetailActivity

    @ContributesAndroidInjector(modules = [KreditLimitModule::class])
    abstract fun bindKreditJatuhTempoDetailActivity(): KreditJatuhTempoDetailActivity

    @ContributesAndroidInjector(modules = [KreditLimitModule::class])
    abstract fun bindKreditLimitListDetailActivity(): KreditLimitListDetailActivity

    @ContributesAndroidInjector(modules = [KreditLimitModule::class])
    abstract fun bindKreditJatuhTempoListDetailActivity(): KreditJatuhTempoListDetailActivity

    @ContributesAndroidInjector(modules = [RealisasiVisitModule::class])
    abstract fun bindKodeDealerRealisasiVisit(): KodeDealerRealisasiVisit

    @ContributesAndroidInjector(modules = [RealisasiVisitModule::class])
    abstract fun bindKoordinatorSalesmanRealisasiActivity(): KoordinatorSalesmanRealisasiActivity

//    @ContributesAndroidInjector(modules = [RealisasiVisitModule::class])
//    abstract fun bindPlanActualActivity(): PlanActualActivity

    @ContributesAndroidInjector(modules = [PlanActualRealiasiModule::class])
    abstract fun bindPlanActualRealisasiSalesmanActivity(): PlanActualRealisasiSalesmanActivity

    @ContributesAndroidInjector(modules = [PlanActualRealiasiModule::class])
    abstract fun bindPlanActualRealisasiKoordinatorActivity(): PlanActualRealisasiKoordinatorActivity

    @ContributesAndroidInjector(modules = [PlanActualRealiasiModule::class])
    abstract fun bindPlanActualRealisasiManagerActivity(): PlanActualRealisasiCoordinatorManagerActivity

    @ContributesAndroidInjector(modules = [PlanActualRealiasiModule::class])
    abstract fun bindPlanActualManagerDetailActivity(): PlanActualManagerDetailActivity

    @ContributesAndroidInjector(modules = [PlanActualRealiasiModule::class])
    abstract fun bindPlanActualManagerDetailFragment(): PlanActualManagerDetailFragment

    @ContributesAndroidInjector(modules = [PlanActualRealiasiModule::class])
    abstract fun bindPlanActualRealisasiSalesmanManagerActivity(): PlanActualRealisasiSalesmanManagerActivity

    @ContributesAndroidInjector(modules = [PlanActualRealiasiModule::class])
    abstract fun bindPlanActualDetailRealisasiVisitTabActivity(): PlanActualDetailRealisasiVisitTabActivity

    @ContributesAndroidInjector(modules = [PlanActualRealiasiModule::class])
    abstract fun bindPlanActualCoordinatorDetailFragment(): PlanActualDetailRealisasiFragment

    @ContributesAndroidInjector(modules = [RealisasiVisitModule::class])
    abstract fun bindSalesmanRealisasiVisitActivity(): SalesmanRealisasiVisitActivity

    @ContributesAndroidInjector(modules = [EfektivitasModule::class])
    abstract fun bindKodeDealerEfektivitas(): KodeDealerEfektivitas

    @ContributesAndroidInjector(modules = [EfektivitasModule::class])
    abstract fun bindSalesmanEfektivitasActivity(): SalesmanEfektivitasActivity

    @ContributesAndroidInjector(modules = [EfektivitasModule::class])
    abstract fun bindKoordinatorSalesmanEfektivitasActivity(): KoordinatorSalesmanEfektivitasActivity

    @ContributesAndroidInjector(modules = [EfektivitasModule::class])
    abstract fun bindEfektivitasMultipleActivity(): EfektivitasActivity

//    @ContributesAndroidInjector(modules = [EfektivitasModule::class])
//    abstract fun bindPlanActualEfektivitasActivity(): PlanActualEfektivitasActivity

    @ContributesAndroidInjector(modules = [EfektivitasModule::class])
    abstract fun bindPlanActualEfektivitasSalesmanActivity(): PlanActualEfektivitasSalesmanActivity

    @ContributesAndroidInjector(modules = [EfektivitasModule::class])
    abstract fun bindPlanActualEfektivitasCoordinatorActivity(): PlanActualEfektivitasCoordinatorActivity


    @ContributesAndroidInjector(modules = [EfektivitasModule::class])
    abstract fun bindPlanActualEfektivitasDetailTabActivity(): PlanActualEfektivitasDetailTabActivity

    @ContributesAndroidInjector(modules = [EfektivitasModule::class])
    abstract fun bindPlanActualEfektivitasDetailFragment(): PlanActualEfektivitasDetailFragment

    @ContributesAndroidInjector(modules = [EfektivitasModule::class])
    abstract fun bindPlanActualEfektivitasCoordinatorManagerActivity(): PlanActualEfektivitasCoordinatorManagerActivity

    @ContributesAndroidInjector(modules = [EfektivitasModule::class])
    abstract fun bindPlanActualEfektivitasSalesmanManagerActivity(): PlanActualEfektivitasSalesmanManagerActivity

    @ContributesAndroidInjector(modules = [EfektivitasModule::class])
    abstract fun bindPlanActualManagerDetailActivityEfektivitas(): ahm.parts.ordering.ui.home.dealer.salesmanvisit.efektivitas.planactual.manager.detail.PlanActualManagerDetailActivity

     @ContributesAndroidInjector(modules = [EfektivitasModule::class])
    abstract fun bindPlanActualManagerDetailEfektivitasFragment(): PlanActualManagerDetailEfektivitasFragment

    @ContributesAndroidInjector(modules = [HomeModule::class])
    abstract fun bindPreviewImageActivity(): PreviewImageActivity

    @ContributesAndroidInjector(modules = [ForgotPasswordModule::class])
    abstract fun bindForgotPasswordActivity(): ForgotPasswordActivity

    @ContributesAndroidInjector(modules = [ProfileModule::class])
    abstract fun bindChangePasswordActivity(): ChangePasswordActivity


    @ContributesAndroidInjector(modules = [KodeDealerDashboardDealerModule::class])
    abstract fun bindKodeDealerDashboardDealer(): KodeDealerDashboardDealer

    @ContributesAndroidInjector(modules = [AddNewDealerModule::class])
    abstract fun bindAddNewDealerActivity(): AddNewDealerActivity



}

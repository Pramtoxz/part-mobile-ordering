package ahm.parts.ordering.helper

import ahm.parts.ordering.R
import ahm.parts.ordering.ui.home.dealer.collection.add.CollectionAddMultipleActivity
import ahm.parts.ordering.ui.home.dealer.competitor.detail.slider.CompetitorDetailActivity
import ahm.parts.ordering.ui.home.dealer.salesmanvisit.efektivitas.koordinatorsalesman.KoordinatorSalesmanActivity
import ahm.parts.ordering.ui.home.dealer.salesmanvisit.realisasivisit.salesman.SalesmanActivity
import ahm.parts.ordering.ui.home.home.ordersugestion.ordersugestion.skemapembelian.SkemaPembelianActivity
import ahm.parts.ordering.ui.home.home.partnumber.kodedealer.KodeDealerModule
import ahm.parts.ordering.ui.home.home.partnumber.kodedealer.KodeDealerViewModel
import ahm.parts.ordering.ui.home.order.kodedealer.KodeDealerActivity
import ahm.parts.ordering.ui.home.order.order.OrderFragment

typealias S = R.string
typealias C = R.color
typealias D = R.drawable
typealias L = R.layout

typealias KodeDealerTrackingOrderActivity = KodeDealerActivity
typealias KodeDealerOrderSuggestionActivity = ahm.parts.ordering.ui.home.home.ordersugestion.kodedealer.KodeDealerActivity
typealias KodeDealerKreditLimit = ahm.parts.ordering.ui.home.dealer.kreditlimit.kodedealer.KodeDealerActivity
typealias KodeDealerKreditLimitSingleActivity = ahm.parts.ordering.ui.home.dealer.kreditlimit.kodedealer.single.KodeDealerActivity

typealias KodeDealerRealisasiVisit = ahm.parts.ordering.ui.home.dealer.salesmanvisit.realisasivisit.dealer.KodeDealerActivity
typealias KodeDealerEfektivitas = ahm.parts.ordering.ui.home.dealer.salesmanvisit.efektivitas.dealer.KodeDealerActivity

typealias KoordinatorSalesmanEfektivitasActivity = KoordinatorSalesmanActivity
typealias KoordinatorSalesmanRealisasiActivity = ahm.parts.ordering.ui.home.dealer.salesmanvisit.realisasivisit.koordinatorsalesman.KoordinatorSalesmanActivity

typealias SalesmanRealisasiVisitActivity = SalesmanActivity
typealias SalesmanEfektivitasActivity = ahm.parts.ordering.ui.home.dealer.salesmanvisit.efektivitas.salesman.SalesmanActivity

//typealias PlanActualRealisasiActivity = PlanActualActivity
/*typealias PlanActualRealisasiDetailActivity = PlanActualDetailRealisasiVisitActivity*/

//typealias PlanActualEfektivitasActivity = ahm.parts.ordering.ui.home.dealer.salesmanvisit.efektivitas.planactual.PlanActualActivity

typealias KodeDealerDashboardDealer = ahm.parts.ordering.ui.home.home.dashboard.dealer.kodedealer.KodeDealerActivity
typealias KodeDealerDashboardDealerViewModel = KodeDealerViewModel
typealias KodeDealerDashboardDealerModule = KodeDealerModule

typealias CompetitorDetailSliderActivity = CompetitorDetailActivity

//Collection
typealias CollectionAddPaymentActivity = CollectionAddMultipleActivity

/**
 * order suggestion
 *
 */
typealias SkemaPembelianOrderSuggestionActivity = SkemaPembelianActivity

typealias OrderMenu = OrderFragment
package ahm.parts.ordering.data.model.param

import ahm.parts.ordering.data.model.activity.Customer
import ahm.parts.ordering.data.model.activity.FollowUp
import ahm.parts.ordering.data.model.activity.KodeUnitTipe

open class ParamProspek {

    var customer = Customer()
    var selectedFollowUp = FollowUp()
    var selectedKodeUnit = KodeUnitTipe()
    var preferensiUji = String()

}
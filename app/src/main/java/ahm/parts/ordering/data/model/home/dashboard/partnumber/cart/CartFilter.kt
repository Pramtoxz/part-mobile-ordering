package ahm.parts.ordering.data.model.home.dashboard.partnumber.cart

class CartFilter {

    var name: String = ""
    var isCheck: Boolean = true
    var isCheckInt: Int = 1

    constructor() {}

    constructor(name: String, isCheck: Boolean) {
        this.name = name
        this.isCheck = isCheck
    }

    constructor(name: String, isCheck: Boolean,isCheckInt : Int) {
        this.name = name
        this.isCheck = isCheck
        this.isCheckInt = isCheckInt
    }

    constructor(name: String) {
        this.name = name
    }
}

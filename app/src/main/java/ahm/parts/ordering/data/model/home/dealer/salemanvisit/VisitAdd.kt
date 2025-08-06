package ahm.parts.ordering.data.model.home.dealer.salemanvisit


import com.google.gson.annotations.SerializedName

data class VisitAdd(
    @SerializedName("code_visit")
    var codeVisit: String = "",

    @SerializedName("distance")
    var distance: String = "",

    var status : Int = 0

)
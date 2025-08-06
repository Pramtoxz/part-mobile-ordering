package ahm.parts.ordering.data.model.stock

import com.google.gson.annotations.SerializedName

data class YearFilter(
    @field:SerializedName("id")
    val id : Int = 0,
    @field:SerializedName("year")
    val year : String = "0"

) {

    override fun toString(): String {
        return "$year"
    }
}
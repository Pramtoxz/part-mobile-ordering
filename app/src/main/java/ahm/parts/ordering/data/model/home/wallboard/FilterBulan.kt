package ahm.parts.ordering.data.model.home.wallboard


import com.google.gson.annotations.SerializedName

data class FilterBulan(
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("name")
    var name: String = ""
){
    /**
     * Returns a string representation of the object.
     */
    override fun toString(): String {
        return name
    }
}
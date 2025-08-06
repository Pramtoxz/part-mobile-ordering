package ahm.parts.ordering.data.model.activity


import com.google.gson.annotations.SerializedName

data class KodeUnitTipe(
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("name")
    var name: String = "",
    @SerializedName("code")
    var code: String = ""
){
        override fun toString(): String {
        return "$name,$code"
    }
}
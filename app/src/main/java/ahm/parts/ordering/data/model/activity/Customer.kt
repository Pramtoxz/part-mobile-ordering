package ahm.parts.ordering.data.model.activity


import com.google.gson.annotations.SerializedName

data class Customer(
    @SerializedName("address")
    var address: String = "",
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("image")
    var image: String = "",
    @SerializedName("ktp")
    var ktp: String = "",
    @SerializedName("lat")
    var lat: Double = 0.0,
    @SerializedName("lng")
    var lng: Double = 0.0,
    @SerializedName("name")
    var name: String = "",
    @SerializedName("office_address")
    var officeAddress: String = "",
    @SerializedName("office_phone")
    var officePhone: String = "",
    @SerializedName("pekerjaan_id")
    var pekerjaanId: Int = 0,
    @SerializedName("pekerjaan_name")
    var pekerjaanName: String = "",
    @SerializedName("phone")
    var phone: String = ""
){
    /**
     * Returns a string representation of the object.
     */
    override fun toString(): String {
        return "$name,$ktp"
    }
}
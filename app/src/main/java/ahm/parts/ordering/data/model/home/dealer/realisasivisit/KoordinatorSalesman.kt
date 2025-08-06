package ahm.parts.ordering.data.model.home.dealer.realisasivisit


import com.google.gson.annotations.SerializedName

data class KoordinatorSalesman(
    @SerializedName("created_at")
    var createdAt: String = "",
    @SerializedName("id")
    var id: String = "",
    @SerializedName("koordinator_code")
    var koordinatorCode: String = "",
    @SerializedName("name")
    var name: String = "",
    @SerializedName("updated_at")
    var updatedAt: String = "",
    var selectedKoordinator : Boolean = false
)
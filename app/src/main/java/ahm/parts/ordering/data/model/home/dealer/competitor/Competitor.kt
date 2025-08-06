package ahm.parts.ordering.data.model.home.dealer.competitor


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Competitor(
    @SerializedName("begin_effdate")
    var beginEffdate: String = "",
    @SerializedName("code_dealer")
    var codeDealer: String = "",
    @SerializedName("created_at")
    var createdAt: String = "",
    @SerializedName("description")
    var description: String = "",
    @SerializedName("end_effdate")
    var endEffdate: String = "",
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("id_role")
    var idRole: String = "",
    @SerializedName("id_user")
    var idUser: Int = 0,
    @SerializedName("name_competitor")
    var nameCompetitor: String = "",
    @SerializedName("photo")
    var photo: List<Photo> = listOf(),
    @SerializedName("product")
    var product: String = "",
    @SerializedName("title_activity_competitor")
    var titleActivityCompetitor: String = ""
)

data class Photo(
    @SerializedName("photo")
    var photo: String = ""
)
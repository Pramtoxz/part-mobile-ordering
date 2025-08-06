package ahm.parts.ordering.data.room.user

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = false)
    @Expose
    @SerializedName("id_user")
    val idUser: Int = 1,

    @SerializedName("code_user")
    @Expose
    var codeUser: String = "",

    @SerializedName("company")
    @Expose
    var company: String = "",

    @SerializedName("email")
    @Expose
    var email: String = "",

    @SerializedName("job")
    @Expose
    var role: String = "",

    @SerializedName("id_role")
    @Expose
    var id_role: String = "",

    @SerializedName("name")
    @Expose
    var name: String = "",

    @SerializedName("phone_number")
    @Expose
    var phoneNumber: String = "",

    @SerializedName("photo")
    @Expose
    var photo: String = "",

    @SerializedName("session_id")
    @Expose
    var sessionId: String = "",

    @SerializedName("dealer_id")
    @Expose
    var dealerId: String = "",

    @SerializedName("dealer_name")
    @Expose
    var dealerName: String = "",

    @SerializedName("dealer_code")
    @Expose
    var dealerCode: String = ""

)
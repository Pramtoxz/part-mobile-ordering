package ahm.parts.ordering.data.room.step.infopelanggan

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "info_pelanggan")
data class InfoPelanggan(
    @PrimaryKey(autoGenerate = false)
    @Expose
    @SerializedName("id_user")
    val idUser: Int = 1,
    @Expose
    @SerializedName("nik")
    val nik: String? = null,
    @Expose
    @SerializedName("nama_prospek")
    val namaProspek: String? = null,
    @Expose
    @SerializedName("no_telp")
    val noTelp: String? = null,
    @Expose
    @SerializedName("tgl_prospek")
    val tglProspek: String? = null,
    @Expose
    @SerializedName("no_ktp")
    val noKTP: String? = null,
    @Expose
    @SerializedName("alamat")
    val alamat: String? = null,
    @Expose
    @SerializedName("pekerjaan")
    val pekerjaan: String? = null,
    @Expose
    @SerializedName("alamat_kantor")
    val alamatKantor: String? = null,
    @Expose
    @SerializedName("no_telp_kantor")
    val noTelpKantor: String? = null,
    @Expose
    @SerializedName("kode_tipe_unit")
    val kodeTipeUnit: String? = null,
    @Expose
    @SerializedName("metode_followup")
    val metodeFollowup: String? = null,
    @Expose
    @SerializedName("pref_uji_perjalanan")
    val prefUjiPerjalanan: String? = null
)
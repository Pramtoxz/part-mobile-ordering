package ahm.parts.ordering.data.model.activity


import com.google.gson.annotations.SerializedName

data class Job(
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("name")
    var name: String = ""
){
    override fun toString(): String {
        return name
    }
}
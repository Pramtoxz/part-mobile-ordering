package ahm.parts.ordering.data.model.stock


import com.google.gson.annotations.SerializedName

data class TypeFilter(
    @field:SerializedName("id")
    var id: Int = 0,
    @field:SerializedName("name")
    var name: String = ""
)
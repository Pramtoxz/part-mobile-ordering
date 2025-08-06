package ahm.parts.ordering.data.model.catalogue


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Catalogue(
    
    @SerializedName("data")
    @Expose
    var data: List<CatalogueList> = listOf(),
    
    @SerializedName("part_catalogue")
    @Expose
    var partCatalogue: PartCatalogue
)


data class CatalogueList(

    @SerializedName("detail")
    @Expose
    var detail: List<CatalogueDetailFile> = listOf(),

    @SerializedName("icon")
    @Expose
    var icon: String = "",

    @SerializedName("id")
    @Expose
    var id: Int = 0,

    @SerializedName("name")
    @Expose
    var name: String = "",

    @SerializedName("list")
    @Expose
    var list: Boolean = false

)


data class CatalogueDetailFile(

    @SerializedName("detail")
    @Expose
    var detail: List<CatalogueDetailFileList> = listOf(),

    @SerializedName("id")
    @Expose
    var id: Int = 0,

    @SerializedName("name")
    @Expose
    var name: String = "",

    @SerializedName("size")
    @Expose
    var size: String = "",

    @SerializedName("url")
    @Expose
    var url: String = ""


)


data class CatalogueDetailFileList(

    @SerializedName("id")
    @Expose
    var id: Int = 0,

    @SerializedName("name")
    @Expose
    var name: String = "",

    @SerializedName("size")
    @Expose
    var size: String = "",

    @SerializedName("url")
    @Expose
    var url: String = ""
)


data class PartCatalogue(

    @SerializedName("detail")
    @Expose
    var detail: String = "",

    @SerializedName("icon")
    @Expose
    var icon: String = "",

    @SerializedName("id")
    @Expose
    var id: Int = 0,

    @SerializedName("name")
    @Expose
    var name: String = ""
)
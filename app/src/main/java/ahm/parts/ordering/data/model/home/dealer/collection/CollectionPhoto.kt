package ahm.parts.ordering.data.model.home.dealer.collection

class CollectionPhoto {
    var id = 0
    var photoPath: String = ""
    var base64Encode : String =""

    constructor(id: Int, photoPath: String,base64Encode : String) {
        this.id = id
        this.photoPath = photoPath
        this.base64Encode = base64Encode
    }
}
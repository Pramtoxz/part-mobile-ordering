package ahm.parts.ordering.data.model.home.dealer.competitor

class CompetitorPhoto {
    var id = 0
    var photoPath: String = ""
    var base64Encode : String =""

    constructor() {}

    constructor(id: Int, photoPath: String,base64Encode : String) {
        this.id = id
        this.photoPath = photoPath
        this.base64Encode = base64Encode
    }
}
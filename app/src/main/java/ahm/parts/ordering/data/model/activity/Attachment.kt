package ahm.parts.ordering.data.model.activity

open class Attachment {

    constructor(fileName: String) {
        this.fileName = fileName
    }

    var id = 0
    var fileName = ""
    var path = ""
}
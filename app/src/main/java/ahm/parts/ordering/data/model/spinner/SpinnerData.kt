package ahm.parts.ordering.data.model.spinner

class SpinnerData{

    var id : Int = 0
    var name : String = ""
    var nameParam : String? = null

    constructor(id: Int, name: String,nameParam : String?= null) {
        this.id = id
        this.name = name
        this.nameParam = nameParam
    }

    override fun toString(): String {
        return name
    }
}



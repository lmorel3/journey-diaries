package g3.cpe.fr.journeydiaries.models

import java.util.*

data class Journey(val id: Int?, var name: String, var from: Calendar, var to: Calendar) {

    companion object {
        fun make(): Journey {
            return Journey(null, "", Calendar.getInstance(), Calendar.getInstance())
        }
    }

}
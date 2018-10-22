package g3.cpe.fr.journeydiaries.models

import android.databinding.BaseObservable
import java.text.SimpleDateFormat
import java.util.*

class JourneyViewModel internal constructor(private var journey: Journey) : BaseObservable() {

    fun getId() = journey.id
    fun getName() = journey.name
    fun getFrom() = formatDate(journey.from)
    fun getTo() = formatDate(journey.to)

    fun getJourney() = journey
    fun getEmptyNote() = Note.mkNote(journey)

    companion object {
        fun formatDate(date: Calendar): String {
            return SimpleDateFormat.getDateInstance(SimpleDateFormat.MEDIUM, Locale.getDefault()).format(date.time)
        }

        fun parseDate(str: String): Calendar {
            val date = SimpleDateFormat.getDateInstance(SimpleDateFormat.MEDIUM, Locale.getDefault()).parse(str)
            val cal = Calendar.getInstance()
            cal.time = date

            return cal
        }
    }

}
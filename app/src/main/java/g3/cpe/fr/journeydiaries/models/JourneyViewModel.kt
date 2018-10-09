package g3.cpe.fr.journeydiaries.models

import android.databinding.BaseObservable
import java.text.SimpleDateFormat
import java.util.*

class JourneyViewModel internal constructor(private val journey: Journey) : BaseObservable() {

    fun getName() = journey.name
    fun getFrom() = formatDate(journey.from)
    fun getTo() = formatDate(journey.to)

    fun onJourneyClick() {
        println("OnClick ${getName()}")
    }

    private fun formatDate(date: Calendar): String {
        return SimpleDateFormat.getDateInstance(SimpleDateFormat.MEDIUM, Locale.getDefault()).format(date.time)
    }

}
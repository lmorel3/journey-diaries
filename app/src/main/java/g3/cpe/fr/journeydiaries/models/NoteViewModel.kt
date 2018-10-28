package g3.cpe.fr.journeydiaries.models

import android.databinding.BaseObservable

class NoteViewModel internal constructor(private var journey: Journey, private var note: Note) : BaseObservable() {

    fun getId() = note.id
    fun getDescription() = note.description
    fun getLat() = note.lat
    fun getLong() = note.long

    fun getJourney() = journey

    fun setLocation(lat: Double, long: Double) {
        note.lat = lat
        note.long = long
    }

}
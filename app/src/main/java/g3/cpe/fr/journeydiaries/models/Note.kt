package g3.cpe.fr.journeydiaries.models

data class Note(
        val id: Int?,
        val journeyId: Int?,
        var description: String,
        var lat: Double,
        var long: Double
) {
    companion object {
        fun mkNote(journey: Journey): Note {
            return Note(null, journey.id, "", 0.0, 0.0)
        }
    }
}
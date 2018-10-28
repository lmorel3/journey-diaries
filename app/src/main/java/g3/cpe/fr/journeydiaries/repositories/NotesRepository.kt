package g3.cpe.fr.journeydiaries.repositories

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import g3.cpe.fr.journeydiaries.models.Note

const val NOTE_TABLE_NAME: String = "note"
const val KEY_NOTE_ID: String = "rowid"
const val KEY_NOTE_JOURNEY_ID: String = "journey_id"
const val KEY_NOTE_DESCRIPTION: String = "description"
const val KEY_NOTE_LAT: String = "latitude"
const val KEY_NOTE_LONG: String = "longitude"

class NotesRepository(context: Context) {

    val db: DbHelper = DbHelper.getInstance(context)

    fun save(note: Note) {

        try {

            db.writableDatabase.beginTransaction()

            val values = ContentValues()
            values.put("`$KEY_NOTE_JOURNEY_ID`", note.journeyId)
            values.put("`$KEY_NOTE_DESCRIPTION`", note.description)
            values.put("`$KEY_NOTE_LAT`", note.lat)
            values.put("`$KEY_NOTE_LONG`", note.long)

            if(note.id == null) {
                db.writableDatabase.insert(NOTE_TABLE_NAME, null, values)
            } else {
                values.put(KEY_NOTE_ID, note.id)
                db.writableDatabase.update(NOTE_TABLE_NAME, values, "$KEY_NOTE_ID=?", arrayOf(note.id.toString()))
            }

            db.writableDatabase.setTransactionSuccessful()

        } finally {
            db.writableDatabase.endTransaction()
        }

    }

    fun getByJourneyId(journeyId: Int): List<Note> {
        val curs = db.readableDatabase.query(NOTE_TABLE_NAME, arrayOf(KEY_NOTE_ID, KEY_NOTE_JOURNEY_ID, KEY_NOTE_DESCRIPTION, KEY_NOTE_LAT, KEY_NOTE_LONG), "$KEY_NOTE_JOURNEY_ID=?", arrayOf(journeyId.toString()), null, null, null)
        return extractNotes(curs)
    }

    fun get(id: Int): Note {
        val curs = db.readableDatabase.query(NOTE_TABLE_NAME, arrayOf(KEY_NOTE_ID, KEY_NOTE_JOURNEY_ID, KEY_NOTE_DESCRIPTION, KEY_NOTE_LAT, KEY_NOTE_LONG), "$KEY_NOTE_ID=?", arrayOf(id.toString()), null, null, null)
        return extractNotes(curs)[0]
    }

    fun delete(journey: Note) {
        try {

            db.writableDatabase.beginTransaction()
            db.writableDatabase.delete(NOTE_TABLE_NAME, "$KEY_NOTE_ID=?", arrayOf(journey.id.toString()))
            db.writableDatabase.setTransactionSuccessful()

        } finally {
            db.writableDatabase.endTransaction()
        }
    }

    private fun extractNotes(curs: Cursor): List<Note> {
        val results: MutableList<Note> = mutableListOf()

        (1 .. curs.count).map {
            curs.moveToNext()

            val id: Int = curs.getInt(curs.getColumnIndex(KEY_NOTE_ID))
            val journeyId: Int = curs.getInt(curs.getColumnIndex(KEY_NOTE_JOURNEY_ID))
            val desription: String = curs.getString(curs.getColumnIndex(KEY_NOTE_DESCRIPTION))
            val latitude = curs.getDouble(curs.getColumnIndex(KEY_NOTE_LAT))
            val longitude = curs.getDouble(curs.getColumnIndex(KEY_NOTE_LONG))

            results.add(Note(id, journeyId, desription, latitude, longitude))
        }

        curs.close()
        return results
    }

}
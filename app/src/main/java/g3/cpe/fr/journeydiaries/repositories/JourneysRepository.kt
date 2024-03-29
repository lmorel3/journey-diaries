package g3.cpe.fr.journeydiaries.repositories

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import g3.cpe.fr.journeydiaries.models.Journey
import java.util.*

const val JOURNEY_TABLE_NAME: String = "journey"
const val KEY_JOURNEY_ID: String = "rowid"
const val KEY_JOURNEY_NAME: String = "name"
const val KEY_JOURNEY_FROM: String = "from"
const val KEY_JOURNEY_TO: String = "to"

class JourneysRepository(context: Context) {

    val db: DbHelper = DbHelper.getInstance(context)

    fun save(journey: Journey) {

        try {

            db.writableDatabase.beginTransaction()

            val values = ContentValues()
            values.put("`$KEY_JOURNEY_NAME`", journey.name)
            values.put("`$KEY_JOURNEY_FROM`", journey.from.timeInMillis)
            values.put("`$KEY_JOURNEY_TO`", journey.to.timeInMillis)

            if(journey.id == null) {
                db.writableDatabase.insert(JOURNEY_TABLE_NAME, null, values)
            } else {
                values.put(KEY_JOURNEY_ID, journey.id)
                db.writableDatabase.update(JOURNEY_TABLE_NAME, values, "$KEY_JOURNEY_ID=?", arrayOf(journey.id.toString()))
            }

            db.writableDatabase.setTransactionSuccessful()

        } finally {
            db.writableDatabase.endTransaction()
        }

    }

    fun getAll(): List<Journey> {
        val curs = db.readableDatabase.query(JOURNEY_TABLE_NAME, arrayOf(KEY_JOURNEY_ID, KEY_JOURNEY_NAME, "`$KEY_JOURNEY_FROM`", "`$KEY_JOURNEY_TO`"), null, null, null, null, null)
        return extractJourneys(curs)
    }

    fun get(id: Int): Journey {
        val curs = db.readableDatabase.query(JOURNEY_TABLE_NAME, arrayOf(KEY_JOURNEY_ID, KEY_JOURNEY_NAME, "`$KEY_JOURNEY_FROM`", "`$KEY_JOURNEY_TO`"), "$KEY_JOURNEY_ID=?", arrayOf(id.toString()), null, null, null)
        return extractJourneys(curs)[0]
    }

    fun delete(journey: Journey) {
        try {

            db.writableDatabase.beginTransaction()
            db.writableDatabase.delete(JOURNEY_TABLE_NAME, "$KEY_JOURNEY_ID=?", arrayOf(journey.id.toString()))
            db.writableDatabase.setTransactionSuccessful()

        } finally {
            db.writableDatabase.endTransaction()
        }
    }

    private fun extractJourneys(curs: Cursor): List<Journey> {
        val results: MutableList<Journey> = mutableListOf()

        (1 .. curs.count).map {
            curs.moveToNext()

            val id: Int = curs.getInt(curs.getColumnIndex(KEY_JOURNEY_ID))
            val name: String = curs.getString(curs.getColumnIndex(KEY_JOURNEY_NAME))
            val calFrom = Calendar.getInstance()
            calFrom.timeInMillis = curs.getLong(curs.getColumnIndex(KEY_JOURNEY_FROM))
            val calTo = Calendar.getInstance()
            calTo.timeInMillis = curs.getLong(curs.getColumnIndex(KEY_JOURNEY_TO))

            results.add(Journey(id, name, calFrom, calTo))
        }

        curs.close()
        return results
    }

}
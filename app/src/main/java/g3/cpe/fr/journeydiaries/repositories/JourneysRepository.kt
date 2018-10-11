package g3.cpe.fr.journeydiaries.repositories

import android.content.ContentValues
import android.content.Context
import g3.cpe.fr.journeydiaries.models.Journey
import java.util.*

const val TABLE_NAME: String = "journey"
const val KEY_JOURNEY_ID: String = "id"
const val KEY_JOURNEY_NAME: String = "name"
const val KEY_JOURNEY_FROM: String = "from"
const val KEY_JOURNEY_TO: String = "to"

class JourneysRepository(context: Context) {

    val db: DbHelper = DbHelper.getInstance(context)

    fun save(journey: Journey) {

        try {

            db.writableDatabase.beginTransaction()

            val values = ContentValues()
            values.put(KEY_JOURNEY_NAME, journey.name)
            values.put(KEY_JOURNEY_FROM, journey.from.timeInMillis)
            values.put(KEY_JOURNEY_TO, journey.to.timeInMillis)

            if(journey.id == null) {
                db.writableDatabase.insert(TABLE_NAME, null, values)
            } else {
                values.put(KEY_JOURNEY_ID, journey.id)
                db.writableDatabase.update(TABLE_NAME, values, "$KEY_JOURNEY_ID=?", arrayOf(journey.id.toString()))
            }

            db.writableDatabase.setTransactionSuccessful()

        } finally {
            db.writableDatabase.endTransaction()
        }

    }

    fun getAll(): List<Journey> {
        val curs = db.readableDatabase.query(TABLE_NAME, null, null, null, null, null, null)
        val results: MutableList<Journey> = mutableListOf()

        (1 .. curs.count).map {
            curs.moveToNext()

            val id: Int = curs.getInt(curs.getColumnIndex(KEY_JOURNEY_ID))
            val name: String = curs.getString(curs.getColumnIndex(KEY_JOURNEY_NAME))
            val calFrom = Calendar.getInstance()
            calFrom.timeInMillis = curs.getLong(curs.getColumnIndex(KEY_JOURNEY_FROM))
            val calTo = Calendar.getInstance()
            calTo.timeInMillis = curs.getLong(curs.getColumnIndex(KEY_JOURNEY_FROM))

            results.add(Journey(id, name, calFrom, calTo))
        }

        curs.close()
        return results
    }

}
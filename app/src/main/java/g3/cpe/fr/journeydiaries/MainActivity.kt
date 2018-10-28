package g3.cpe.fr.journeydiaries

import android.app.FragmentTransaction
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import g3.cpe.fr.journeydiaries.fragments.*
import g3.cpe.fr.journeydiaries.listeners.ClickListener
import g3.cpe.fr.journeydiaries.models.Journey
import g3.cpe.fr.journeydiaries.models.Note


class MainActivity : AppCompatActivity(), MainActivityContract.View {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        showList()
    }

    override fun showList() {
        val transaction: FragmentTransaction = fragmentManager.beginTransaction()

        val clickListener = object: ClickListener<Journey> {
            override fun onClick(data: Journey) {
                showAddEdit(data)
            }
        }

        val fragment = JourneysFragment()
        fragment.btnClickListener = clickListener

        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }

    override fun showAddEdit(journey: Journey) {
        val transaction: FragmentTransaction = fragmentManager.beginTransaction()
        val fragment = AddEditJourneyFragment()

        val btnListener = object: ClickListener<Void?> {
            override fun onClick(data: Void?) {
                showList()
            }
        }

        fragment.journey = journey
        fragment.addEditPresenter = AddEditJourneyFragment.AddEditPresenter(this)
        fragment.btnListener = btnListener

        transaction.addToBackStack(null)
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }

    override fun showAddEditNote(journey: Journey, note: Note) {
        checkPermissions()

        val transaction: FragmentTransaction = fragmentManager.beginTransaction()
        val fragment = AddEditNoteFragment()

        fragment.journey = journey
        fragment.note = note
        fragment.addEditNotePresenter = AddEditNoteFragment.AddEditNotePresenter(this)

        transaction.addToBackStack(null)
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }

    override fun showMap(journeyId: Int) {
        checkPermissions()

        val transaction: FragmentTransaction = fragmentManager.beginTransaction()
        val fragment = MapFragment()

        fragment.presenter = MapFragment.MapPresenter(this)
        fragment.journeyId = journeyId

        transaction.addToBackStack(null)
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }

    private fun checkPermissions() {
        if (ActivityCompat.checkSelfPermission(this,
                        android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
            return
        }
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

}

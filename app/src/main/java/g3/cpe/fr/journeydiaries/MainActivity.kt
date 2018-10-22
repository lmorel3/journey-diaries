package g3.cpe.fr.journeydiaries

import android.app.FragmentManager
import android.app.FragmentTransaction
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import g3.cpe.fr.journeydiaries.fragments.AddEditJourneyFragment
import g3.cpe.fr.journeydiaries.fragments.JourneysFragment
import g3.cpe.fr.journeydiaries.fragments.MainActivityContract
import g3.cpe.fr.journeydiaries.fragments.MapFragment
import g3.cpe.fr.journeydiaries.listeners.ClickListener
import g3.cpe.fr.journeydiaries.models.Journey


class MainActivity : AppCompatActivity(), MainActivityContract.View, OnMapReadyCallback {

    private var map: GoogleMap? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        showList()
    }

    override fun showList() {
        val manager: FragmentManager = getFragmentManager()
        val transaction: FragmentTransaction = manager.beginTransaction()

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
        val manager: FragmentManager = getFragmentManager()
        val transaction: FragmentTransaction = manager.beginTransaction()
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

    override fun showMap(journeyId: Int) {
        val manager: FragmentManager = getFragmentManager()
        val transaction: FragmentTransaction = manager.beginTransaction()
        val fragment = MapFragment()

        fragment.journeyId = journeyId

        transaction.addToBackStack(null)
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        map!!.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        map!!.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }


}

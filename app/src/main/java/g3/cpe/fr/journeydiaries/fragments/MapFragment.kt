package g3.cpe.fr.journeydiaries.fragments

import android.app.Fragment
import android.databinding.DataBindingUtil
import android.os.Build
import android.os.Bundle
import android.support.annotation.Nullable
import android.support.annotation.RequiresApi
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import g3.cpe.fr.journeydiaries.R
import g3.cpe.fr.journeydiaries.databinding.FragmentMapBinding
import g3.cpe.fr.journeydiaries.models.Journey
import g3.cpe.fr.journeydiaries.models.JourneyViewModel
import g3.cpe.fr.journeydiaries.repositories.JourneysRepository


class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    lateinit var journeysRepository: JourneysRepository
    var journeyId: Int = 0

    @RequiresApi(Build.VERSION_CODES.N)
    @Nullable
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding: FragmentMapBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_map, container, false)

        journeysRepository = JourneysRepository(context)
        val journey = loadJourney()
        binding.jvm = JourneyViewModel(journey)

        //binding.root.mapView.getMapAsync(this)

        return binding.root
    }

    fun loadJourney(): Journey {
        return journeysRepository.get(journeyId)
    }

}
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

    private lateinit var journeysRepository: JourneysRepository
    var journeyId: Int = 0
    private lateinit var binding: FragmentMapBinding

    @RequiresApi(Build.VERSION_CODES.N)
    @Nullable
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding: FragmentMapBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_map, container, false)

        journeysRepository = JourneysRepository(context)
        val journey = loadJourney()
        binding.jvm = JourneyViewModel(journey)

        binding.map.getMapAsync(this)

        return binding.root
    }

    private fun loadJourney(): Journey {
        return journeysRepository.get(journeyId)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        // Add a marker in Sydney, Australia, and move the camera.
        val sydney = LatLng(-34.0, 151.0)
        with(googleMap) {
            moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13f))
            addMarker(MarkerOptions().position(sydney))
        }
    }

}
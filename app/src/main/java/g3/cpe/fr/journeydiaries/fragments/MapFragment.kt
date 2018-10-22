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
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import g3.cpe.fr.journeydiaries.R
import g3.cpe.fr.journeydiaries.databinding.FragmentMapBinding
import g3.cpe.fr.journeydiaries.models.Journey
import g3.cpe.fr.journeydiaries.models.JourneyViewModel
import g3.cpe.fr.journeydiaries.repositories.JourneysRepository
import g3.cpe.fr.journeydiaries.repositories.NotesRepository


class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var journeysRepository: JourneysRepository
    private lateinit var noteRepository: NotesRepository

    var journeyId: Int = 0
    private lateinit var binding: FragmentMapBinding

    @RequiresApi(Build.VERSION_CODES.N)
    @Nullable
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_map, container, false)

        journeysRepository = JourneysRepository(context)
        noteRepository = NotesRepository(context)

        val journey = loadJourney()
        binding.jvm = JourneyViewModel(journey)

        binding.map.getMapAsync(this)
        binding.map.onCreate(savedInstanceState)

        return binding.root
    }

    private fun loadJourney(): Journey {
        return journeysRepository.get(journeyId)
    }

    override fun onMapReady(map: GoogleMap) {

        map.getUiSettings().isMyLocationButtonEnabled = false
        //map.isMyLocationEnabled = true

        // Needs to call MapsInitializer before doing any CameraUpdateFactory calls
        try {
            MapsInitializer.initialize(this.activity)
        } catch (e: GooglePlayServicesNotAvailableException) {
            e.printStackTrace()
        }


        // Updates the location and zoom of the MapView
        val cameraUpdate = CameraUpdateFactory.newLatLngZoom(LatLng(43.1, -87.9), 10f)
        map.animateCamera(cameraUpdate)

        val notes = noteRepository.getByJourneyId(journeyId)

        // TODO: Ask for removal on long click + edit
        // https://stackoverflow.com/questions/15391665/setting-a-longclicklistener-on-a-map-marker

        if(notes.isEmpty()) {
            val sydney = LatLng(-34.0, 151.0)
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13f))
            map.addMarker(MarkerOptions().position(sydney))
        } else {
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(notes[0].lat, notes[0].long), 13f))
            for(note in notes) {
                map.addMarker(MarkerOptions().position(LatLng(note.lat, note.long)).title(note.description))
            }
        }

    }

    override fun onResume() {
        binding.map.onResume()
        super.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.map.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.map.onLowMemory()
    }

}
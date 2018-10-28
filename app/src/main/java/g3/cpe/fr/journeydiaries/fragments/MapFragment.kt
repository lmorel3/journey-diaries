package g3.cpe.fr.journeydiaries.fragments

import android.app.Fragment
import android.content.pm.PackageManager
import android.databinding.DataBindingUtil
import android.os.Build
import android.os.Bundle
import android.support.annotation.Nullable
import android.support.annotation.RequiresApi
import android.support.v4.app.ActivityCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import g3.cpe.fr.journeydiaries.R
import g3.cpe.fr.journeydiaries.databinding.FragmentMapBinding
import g3.cpe.fr.journeydiaries.models.Journey
import g3.cpe.fr.journeydiaries.models.JourneyViewModel
import g3.cpe.fr.journeydiaries.models.Note
import g3.cpe.fr.journeydiaries.repositories.JourneysRepository
import g3.cpe.fr.journeydiaries.repositories.NotesRepository




class MapFragment : Fragment(), OnMapReadyCallback {

    class MapPresenter(view: MainActivityContract.View) : MainActivityContract.Presenter(view)

    private lateinit var journeysRepository: JourneysRepository
    private lateinit var noteRepository: NotesRepository

    lateinit var presenter: MainActivityContract.Presenter

    private val markers: MutableMap<Marker, Note> = mutableMapOf()

    var journeyId: Int = 0
    private lateinit var journey: Journey
    private lateinit var binding: FragmentMapBinding

    @RequiresApi(Build.VERSION_CODES.N)
    @Nullable
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_map, container, false)

        journeysRepository = JourneysRepository(context)
        noteRepository = NotesRepository(context)

        journey = loadJourney()
        binding.jvm = JourneyViewModel(journey)

        binding.map.getMapAsync(this)
        binding.map.onCreate(savedInstanceState)

        return binding.root
    }

    private fun loadJourney(): Journey {
        return journeysRepository.get(journeyId)
    }

    override fun onMapReady(map: GoogleMap) {

        map.uiSettings.isMyLocationButtonEnabled = false

        if (ActivityCompat.checkSelfPermission(activity,
                        android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            map.isMyLocationEnabled = true
        }

        // Needs to call MapsInitializer before doing any CameraUpdateFactory calls
        try {
            MapsInitializer.initialize(this.activity)
        } catch (e: GooglePlayServicesNotAvailableException) {
            e.printStackTrace()
        }

        map.setOnMapLongClickListener { latLng ->
            var found: Boolean = false
            for (marker in markers.keys) {
                if (!found && Math.abs(marker.position.latitude - latLng.latitude) < 0.0009 && Math.abs(marker.position.longitude - latLng.longitude) < 0.0009) {
                    markers[marker]?.let { presenter.onAddEditNote(journey, it) }
                    found = true
                }
            }

            // If no marker found : adds a new note
            if(!found){
                val note: Note = Note.mkNote(journey)
                note.lat = latLng.latitude
                note.long = latLng.longitude

                presenter.onAddEditNote(journey, note)
            }
        }

        val notes = noteRepository.getByJourneyId(journeyId)

        if(!notes.isEmpty()) {
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(notes[0].lat, notes[0].long), 13f))
            for(note in notes) {
                val marker: Marker = map.addMarker(MarkerOptions().position(LatLng(note.lat, note.long)).title(note.description))
                markers[marker] = note
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
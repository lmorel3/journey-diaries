package g3.cpe.fr.journeydiaries.fragments

import android.app.AlertDialog
import android.app.Fragment
import android.content.Context
import android.content.pm.PackageManager
import android.databinding.DataBindingUtil
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.support.annotation.Nullable
import android.support.annotation.RequiresApi
import android.support.v4.app.ActivityCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import g3.cpe.fr.journeydiaries.R
import g3.cpe.fr.journeydiaries.databinding.FragmentAddNoteBinding
import g3.cpe.fr.journeydiaries.models.Journey
import g3.cpe.fr.journeydiaries.models.Note
import g3.cpe.fr.journeydiaries.models.NoteViewModel
import g3.cpe.fr.journeydiaries.repositories.NotesRepository


class AddEditNoteFragment : Fragment() {

    class AddEditNotePresenter(view: MainActivityContract.View) : MainActivityContract.Presenter(view)

    lateinit var note: Note
    lateinit var journey: Journey

    var locationManager : LocationManager? = null

    private lateinit var notesRepository: NotesRepository

    lateinit var addEditNotePresenter: AddEditNotePresenter
    lateinit var binding: FragmentAddNoteBinding

    @RequiresApi(Build.VERSION_CODES.N)
    @Nullable
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        notesRepository = NotesRepository(context)

        val binding: FragmentAddNoteBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_note, container, false)
        this.binding = binding

        if(note.lat == 0.0) getLocation() // If its a new note

        binding.nvm = NoteViewModel(journey, note)

        binding.btnSave.setOnClickListener { save() }
        binding.btnDelete.setOnClickListener { openConfirmDelete() }

        return binding.root
    }

    private fun openConfirmDelete() {
        val builder = AlertDialog.Builder(context)
        builder.setMessage("Really ?")
                .setPositiveButton("Sure!") { _, _ -> delete() }
                .setNegativeButton("Nope") { dialog, _ -> dialog.cancel() }

        builder.create().show()
    }

    private fun save() {

        if(binding.description.text.isEmpty()) {
            return Toast.makeText(context, "Description shouln't be blank", Toast.LENGTH_SHORT).show()
        }

        updateJourney()
        notesRepository.save(note)
        Toast.makeText(context, "Saved !", Toast.LENGTH_SHORT).show()

        addEditNotePresenter.onAddEdit(journey)
    }

    private fun delete() {
        notesRepository.delete(note)
        Toast.makeText(context, "Removed !", Toast.LENGTH_SHORT).show()

        addEditNotePresenter.onAddEdit(journey)
    }

    private fun updateJourney() {
        note.description = binding.description.text.toString()
    }

    private val locationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            binding.nvm!!.setLocation(location.latitude, location.longitude)
            locationManager!!.removeUpdates(this)
        }
        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }

    private fun getLocation() {
        locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager?

        if (ActivityCompat.checkSelfPermission(activity,
                        android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager?.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0L, 0f, locationListener);
        }
        /*
        LocationManager manager = ( LocationManager )
        getSystemService ( Context . LOCATION SERVICE ) ;
        manager . requestLocationUpdates (
                LocationManager . GPS PROVIDER, 120 , 100 ,
                myLocationListener ) ;
        manager . requestLocationUpdates (
                LocationManager .NETWORK PROVIDER, 120 , 100 ,
                myLocationListener ) ;*/
    }

}
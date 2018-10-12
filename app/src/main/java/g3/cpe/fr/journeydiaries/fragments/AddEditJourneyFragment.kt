package g3.cpe.fr.journeydiaries.fragments

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Fragment
import android.databinding.DataBindingUtil
import android.os.Build
import android.os.Bundle
import android.support.annotation.Nullable
import android.support.annotation.RequiresApi
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import g3.cpe.fr.journeydiaries.R
import g3.cpe.fr.journeydiaries.databinding.FragmentAddJourneyBinding
import g3.cpe.fr.journeydiaries.listeners.ClickListener
import g3.cpe.fr.journeydiaries.models.Journey
import g3.cpe.fr.journeydiaries.models.JourneyViewModel
import g3.cpe.fr.journeydiaries.repositories.JourneysRepository
import java.util.*


class AddEditJourneyFragment : Fragment() {

    lateinit var journey: Journey
    lateinit var btnListener: ClickListener<Void?>
    lateinit var journeysRepository: JourneysRepository

    lateinit var binding: FragmentAddJourneyBinding

    @RequiresApi(Build.VERSION_CODES.N)
    @Nullable
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        journeysRepository = JourneysRepository(context)

        val binding: FragmentAddJourneyBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_journey, container, false)
        this.binding = binding

        binding.jvm = JourneyViewModel(journey)

        binding.inputFrom.setOnClickListener { openDatePicker(binding.inputFrom) }
        binding.inputTo.setOnClickListener { openDatePicker(binding.inputTo) }

        binding.btnCancel.setOnClickListener { cancel() }
        binding.btnSave.setOnClickListener { save() }
        binding.btnDelete.setOnClickListener { openConfirmDelete() }

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun openDatePicker(input: EditText) {
        val datePicker = DatePickerDialog(context)

        datePicker.setOnDateSetListener { view, year, month, dayOfMonth ->
            val cal = Calendar.getInstance()
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            cal.set(Calendar.MONTH, month)
            cal.set(Calendar.YEAR, year)
            input.setText(JourneyViewModel.formatDate(cal))
        }

        datePicker.show()
    }

    private fun openConfirmDelete() {
        val builder = AlertDialog.Builder(context)
        builder.setMessage("Really ?")
                .setPositiveButton("Sure!") { _, _ -> delete() }
                .setNegativeButton("Nope") { dialog, _ -> dialog.cancel() }

        builder.create().show()
    }

    private fun save() {

        if(binding.inputName.text.isEmpty()) {
            return Toast.makeText(context, "Name shouln't be blank", Toast.LENGTH_SHORT).show()
        }

        updateJourney()
        journeysRepository.save(journey)

        Toast.makeText(context, "Saved !", Toast.LENGTH_SHORT).show()
        cancel()
    }

    private fun delete() {
        journeysRepository.delete(journey)

        Toast.makeText(context, "Removed !", Toast.LENGTH_SHORT).show()
        cancel()
    }

    private fun updateJourney() {
        journey.name = binding.inputName.text.toString()
        journey.from = JourneyViewModel.parseDate(binding.inputFrom.text.toString())
        journey.to = JourneyViewModel.parseDate(binding.inputTo.text.toString())
    }

    private fun cancel() {
        btnListener.onClick(null)
    }

}
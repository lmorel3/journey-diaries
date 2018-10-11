package g3.cpe.fr.journeydiaries.fragments

import android.app.Fragment
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.annotation.Nullable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import g3.cpe.fr.journeydiaries.R
import g3.cpe.fr.journeydiaries.databinding.FragmentAddJourneyBinding
import g3.cpe.fr.journeydiaries.listeners.ClickListener
import g3.cpe.fr.journeydiaries.models.Journey
import g3.cpe.fr.journeydiaries.models.JourneyViewModel


class AddEditJourneyFragment : Fragment() {

    lateinit var journey: Journey
    lateinit var btnListener: ClickListener<Void?>

    @Nullable
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding: FragmentAddJourneyBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_journey, container, false)

        binding.jvm = JourneyViewModel(journey)

        binding.btnCancel.setOnClickListener { cancel() }
        binding.btnSave.setOnClickListener { save() }

        return binding.root
    }

    private fun save() {
        // TODO: Persist data

        Toast.makeText(context, "Saved !", Toast.LENGTH_SHORT).show()

        cancel()
    }

    private fun cancel() {
        btnListener.onClick(null)
    }

}
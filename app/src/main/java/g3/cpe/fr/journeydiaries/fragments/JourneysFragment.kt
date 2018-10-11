package g3.cpe.fr.journeydiaries.fragments

import android.app.Fragment
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.annotation.Nullable
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import g3.cpe.fr.journeydiaries.R
import g3.cpe.fr.journeydiaries.adapters.JourneyListAdapter
import g3.cpe.fr.journeydiaries.databinding.FragmentJourneysBinding
import g3.cpe.fr.journeydiaries.listeners.ClickListener
import g3.cpe.fr.journeydiaries.models.Journey
import java.text.SimpleDateFormat
import java.util.*


class JourneysFragment : Fragment() {

    lateinit var btnClickListener: ClickListener<Journey>

    @Nullable
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding = populateList(inflater, container)

        binding.addJourneyButton.setOnClickListener { btnClickListener.onClick(Journey(null, "", Calendar.getInstance(), Calendar.getInstance())) }

        return binding.root
    }

    private fun populateList(inflater: LayoutInflater, container: ViewGroup?): FragmentJourneysBinding {
        val binding: FragmentJourneysBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_journeys, container, false)
        binding.journeysList.layoutManager = LinearLayoutManager(binding.root.context)

        val journeys: MutableList<Journey> = ArrayList()

        journeys.add(Journey(1, "Torontoto", mkDate("2010/10/10"), mkDate("2010/11/11")))
        journeys.add(Journey(2, "Zob", mkDate("2013/12/12"), mkDate("2014/01/01")))

        binding.journeysList.adapter = JourneyListAdapter(journeys, btnClickListener)

        return binding
    }

    private fun mkDate(str: String): Calendar {
        val cal = Calendar.getInstance()
        val sdf = SimpleDateFormat("yyyy/mm/dd", Locale.FRENCH)
        cal.time = sdf.parse(str)

        return cal
    }

}
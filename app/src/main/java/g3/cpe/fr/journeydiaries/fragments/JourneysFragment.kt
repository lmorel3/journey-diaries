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
import g3.cpe.fr.journeydiaries.repositories.JourneysRepository


class JourneysFragment : Fragment() {

    lateinit var btnClickListener: ClickListener<Journey>
    lateinit var journeysRepository: JourneysRepository

    @Nullable
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        journeysRepository = JourneysRepository(context)

        val binding = populateList(inflater, container)
        binding.addJourneyButton.setOnClickListener { btnClickListener.onClick(Journey.make()) }

        return binding.root
    }

    private fun populateList(inflater: LayoutInflater, container: ViewGroup?): FragmentJourneysBinding {
        val binding: FragmentJourneysBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_journeys, container, false)
        binding.journeysList.layoutManager = LinearLayoutManager(binding.root.context)

        val journeys = journeysRepository.getAll()
        binding.journeysList.adapter = JourneyListAdapter(journeys, btnClickListener)

        return binding
    }

}
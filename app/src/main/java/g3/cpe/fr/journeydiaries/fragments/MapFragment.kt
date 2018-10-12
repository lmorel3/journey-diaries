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
import g3.cpe.fr.journeydiaries.R
import g3.cpe.fr.journeydiaries.databinding.FragmentMapBinding
import g3.cpe.fr.journeydiaries.models.Journey
import g3.cpe.fr.journeydiaries.models.JourneyViewModel
import g3.cpe.fr.journeydiaries.repositories.JourneysRepository


class MapFragment : Fragment() {

    lateinit var journeysRepository: JourneysRepository
    var journeyId: Int = 0

    @RequiresApi(Build.VERSION_CODES.N)
    @Nullable
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding: FragmentMapBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_map, container, false)

        journeysRepository = JourneysRepository(context)
        val journey = loadJourney()
        binding.jvm = JourneyViewModel(journey)

        return binding.root
    }

    fun loadJourney(): Journey {
        return journeysRepository.get(journeyId)
    }

}
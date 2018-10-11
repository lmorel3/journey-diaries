package g3.cpe.fr.journeydiaries.adapters

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import g3.cpe.fr.journeydiaries.R
import g3.cpe.fr.journeydiaries.databinding.ItemJourneyBinding
import g3.cpe.fr.journeydiaries.listeners.ClickListener
import g3.cpe.fr.journeydiaries.models.Journey
import g3.cpe.fr.journeydiaries.models.JourneyViewModel


class JourneyListAdapter(private val journeys : List<Journey>, private val itemClickListener: ClickListener<Journey>) : RecyclerView.Adapter<JourneyListAdapter.BindingHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingHolder {
        val binding: ItemJourneyBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_journey, parent,false)

        return BindingHolder(binding)
    }

    override fun onBindViewHolder(holder: BindingHolder, position: Int) {
        val binding: ItemJourneyBinding = holder.binding
        val journey: Journey = journeys[position]

        binding.jvm = JourneyViewModel(journey)
        binding.journeyItem.setOnClickListener { itemClickListener.onClick(journey) }
    }

    override fun getItemCount(): Int {
        return journeys.size
    }

    class BindingHolder(val binding: ItemJourneyBinding)  : RecyclerView.ViewHolder(binding.journeyItem)

}
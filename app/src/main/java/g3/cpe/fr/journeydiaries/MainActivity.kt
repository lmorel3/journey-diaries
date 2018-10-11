package g3.cpe.fr.journeydiaries

import android.app.FragmentManager
import android.app.FragmentTransaction
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import g3.cpe.fr.journeydiaries.fragments.AddEditJourneyFragment
import g3.cpe.fr.journeydiaries.fragments.JourneysFragment
import g3.cpe.fr.journeydiaries.listeners.ClickListener
import g3.cpe.fr.journeydiaries.models.Journey


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        showStartup()
    }

    private fun showStartup()
    {
        val manager: FragmentManager = getFragmentManager()
        val transaction: FragmentTransaction = manager.beginTransaction()

        val clickListener = object: ClickListener<Journey> {
            override fun onClick(data: Journey) {
                showAddEdit(data)
            }
        }

        val fragment = JourneysFragment()
        fragment.btnClickListener = clickListener

        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }

    private fun showAddEdit(journey: Journey) {
        val manager: FragmentManager = getFragmentManager()
        val transaction: FragmentTransaction = manager.beginTransaction()
        val fragment = AddEditJourneyFragment()

        val btnListener = object: ClickListener<Void?> {
            override fun onClick(data: Void?) {
                showStartup()
            }
        }

        fragment.journey = journey
        fragment.btnListener = btnListener

        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }

}

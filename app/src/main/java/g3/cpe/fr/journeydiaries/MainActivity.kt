package g3.cpe.fr.journeydiaries

import android.app.FragmentManager
import android.app.FragmentTransaction
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import g3.cpe.fr.journeydiaries.fragments.JourneysFragment

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
        val fragment = JourneysFragment()

        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }
}

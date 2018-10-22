package g3.cpe.fr.journeydiaries.fragments

import g3.cpe.fr.journeydiaries.models.Journey

class MainActivityContract {

    abstract class Presenter(private val view: MainActivityContract.View) {
        fun onShowList() {
            view.showList()
        }
        fun onShowMap(journeyId: Int) {
            view.showMap(journeyId)
        }
        fun onAddEdit(journey: Journey) {
            view.showAddEdit(journey)
        }
    }

    interface View {
        fun showList()
        fun showMap(journeyId: Int)
        fun showAddEdit(journey: Journey)
    }

}
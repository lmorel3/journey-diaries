package g3.cpe.fr.journeydiaries.fragments

import g3.cpe.fr.journeydiaries.models.Journey
import g3.cpe.fr.journeydiaries.models.Note

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
        fun onAddEditNote(journey: Journey, note: Note) {
            view.showAddEditNote(journey, note)
        }
    }

    interface View {
        fun showList()
        fun showMap(journeyId: Int)
        fun showAddEdit(journey: Journey)
        fun showAddEditNote(journey: Journey, note: Note)
    }

}
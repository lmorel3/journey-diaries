package g3.cpe.fr.journeydiaries.fragments

class MainActivityContract {

    interface Presenter {
        fun onShowList()
        fun onShowMap(journeyId: Int)
    }

    interface View {
        fun showList()
        fun showMap(journeyId: Int)
        // TODO : showAddEdit()
    }

}
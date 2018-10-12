package g3.cpe.fr.journeydiaries.fragments

class MainActivityContract {

    interface Presenter {
        fun onClick()
    }

    interface View {
        fun showList()
    }

}
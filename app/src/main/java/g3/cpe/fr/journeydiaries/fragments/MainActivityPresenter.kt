package g3.cpe.fr.journeydiaries.fragments

class MainActivityPresenter(private val view: MainActivityContract.View) : MainActivityContract.Presenter {
    override fun onClick() {
        view.showList()
    }
}
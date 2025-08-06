package ahm.parts.ordering.ui.home.dealer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import ahm.parts.ordering.R
import ahm.parts.ordering.data.constant.Constants
import ahm.parts.ordering.data.room.user.User
import ahm.parts.ordering.helper.goTo
import ahm.parts.ordering.helper.hide
import ahm.parts.ordering.helper.show
import ahm.parts.ordering.ui.base.BaseFragment
import ahm.parts.ordering.ui.home.HomeActivity
import ahm.parts.ordering.ui.home.HomeViewModel
import ahm.parts.ordering.ui.home.dealer.collection.CollectionActivity
import ahm.parts.ordering.ui.home.dealer.competitor.list.CompetitorActivity
import ahm.parts.ordering.ui.home.dealer.kreditlimit.KreditLimitActivity
import ahm.parts.ordering.ui.home.dealer.salesmanvisit.SalesmanVisitActivity
import kotlinx.android.synthetic.main.fragment_dealer.*
import kotlinx.android.synthetic.main.fragment_dealer_category.*

class DealerFragment : BaseFragment<HomeViewModel>(), View.OnClickListener {

    lateinit var mainActivity : HomeActivity
    var user : User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dealer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
        initListener()

    }

    private fun initUI(){
        try {
            mainActivity.roleFiturMenu()
        } catch (e: Exception) {
        }
    }

    private fun initListener(){
        btnCollection.setOnClickListener(this)
        btnCompetitor.setOnClickListener(this)
        btnSalesmanVisit.setOnClickListener(this)
        btnKreditLimit.setOnClickListener(this)
   /*     btnAddDealer.setOnClickListene r(this)*/

        /*
        * Check User Login
        * If Dealer
        * Hide Menu Collection
        */
        viewModel.getUser().observe(viewLifecycleOwner, Observer {
            try {
                user = it
                if(user!!.id_role == Constants.ROLE.DEALER){
                    btnCollection.hide()


                }else{
                    btnCollection.show()
                }
            } catch (e: Exception) {
            }
        })

        /*
        * End Check
        */
    }


    override fun onClick(v: View?) {
        when(v){
            btnCollection -> {
                goTo<CollectionActivity>(requestCode = Constants.REQUEST.KODE_DEALER) {  }
            }
            btnCompetitor -> {
                goTo<CompetitorActivity>(requestCode = Constants.REQUEST.KODE_DEALER) {  }
            }
            btnSalesmanVisit -> {
                goTo<SalesmanVisitActivity> {  }
            }
            btnKreditLimit -> {
                goTo<KreditLimitActivity> {  }
            }
       /*     btnAddDealer -> {

            }*/
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(activity : HomeActivity) = DealerFragment().apply {
            this.mainActivity = activity
        }
    }
}

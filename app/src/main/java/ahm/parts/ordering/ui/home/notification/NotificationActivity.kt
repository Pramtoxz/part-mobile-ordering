package ahm.parts.ordering.ui.home.notification

import ahm.parts.ordering.R
import ahm.parts.ordering.data.model.notification.Notification
import ahm.parts.ordering.helper.initItem
import ahm.parts.ordering.helper.scrollBottom
import ahm.parts.ordering.ui.base.BaseActivity
import ahm.parts.ordering.ui.home.HomeViewModel
import ahm.parts.ordering.ui.home.notification.adapter.NotificationAdapter
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_notification.*

/**
 * class ini mengandung interaksi UI dengan layout activity_notification.xml
 *
 */
class NotificationActivity : BaseActivity<NotificationViewModel>(), View.OnClickListener {

    var paging = 1

    var notifications = ArrayList<Notification>()
    lateinit var notificationAdapter : NotificationAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)

        initUI()

        observerData()
        viewModel.hitNotification(paging,false)
    }

    private fun initUI(){
        setToolbar(getString(R.string.lbl_title_notifikasi),true)

        swipeRefresh.setOnRefreshListener {
            paging = 1
            viewModel.hitNotification(paging,false)
        }

        initAdapter()
    }

    /**
     * Semua observer di inisialisasikan pada method observeData()
     */
    private fun observerData(){
        viewModel.apiResponse.observe(this, Observer {
            showStateApiView(it){
                paging = 1
                viewModel.hitNotification(paging,false)
            }
        })

        /**
         * Observer data notifikasi jika api status 1
         *
         */
        viewModel.notifications.observe(this, Observer {
            notifications.clear()
            notifications.addAll(it)

            notificationAdapter.notifyDataSetChanged()
        })

        /**
         * Observer data notifikasi load more
         *
         */
        viewModel.notificationsLoadMore.observe(this, Observer {
            notifications.addAll(it)

            notificationAdapter.notifyDataSetChanged()
        })

    }

    /**
     * Fungsi untuk mengeset adapter list
     *
     */
    private fun initAdapter(){
        notificationAdapter = NotificationAdapter(notifications,this){}

        rvNotification.initItem(this,notificationAdapter)

        scrollView.scrollBottom{
            paging += 1
            viewModel.hitNotification(paging,true)
        }
    }


    override fun onClick(v: View?) {
        when(v!!){

        }
    }

}

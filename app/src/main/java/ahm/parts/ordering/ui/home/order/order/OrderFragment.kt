package ahm.parts.ordering.ui.home.order.order

import ahm.parts.ordering.R
import ahm.parts.ordering.api.ApiStatus
import ahm.parts.ordering.data.constant.Constants
import ahm.parts.ordering.data.constant.Constants.DEFAULT.TAG
import ahm.parts.ordering.data.model.home.dashboard.partnumber.kodedealer.AllDealer
import ahm.parts.ordering.data.model.home.order.TrackingOrderParam
import ahm.parts.ordering.data.model.spinner.SpinnerData
import ahm.parts.ordering.data.room.user.User
import ahm.parts.ordering.helper.*
import ahm.parts.ordering.ui.base.BaseFragment
import ahm.parts.ordering.ui.dialog.CalendarDialog
import ahm.parts.ordering.ui.home.order.tracking.TrackingOrderActivity
import android.app.DatePickerDialog
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.DatePicker
import android.widget.Toast
import androidx.lifecycle.Observer
import com.whiteelephant.monthpicker.MonthPickerDialog
import kotlinx.android.synthetic.main.fragment_order.*
import java.lang.reflect.Field
import java.util.*
import kotlin.collections.ArrayList


class OrderFragment : BaseFragment<OrderViewModel>(), View.OnClickListener {

    var dateOrder = ""
    var statusBo = ""
    var statusInvoice = ""
    var statusShipping = ""

    lateinit var user : User

    var dealer : AllDealer? = null
    var trackingParam = TrackingOrderParam()

    lateinit var dialogCalendar : CalendarDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_order, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
        initListener()

        applyLocale()

        observeData()
    }

    private fun initUI(){

        activity!!.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        dialogCalendar = CalendarDialog(activity!!)

        /**
         * set spinner status BO
         */
        val statusBoList = ArrayList<SpinnerData>()

        statusBoList.add(SpinnerData(1,getString(R.string.lbl_spinner_pilih_status_bo)))
        statusBoList.add(SpinnerData(2,getString(R.string.lbl_spinner_open)))
        statusBoList.add(SpinnerData(3,getString(R.string.lbl_spinner_close)))

        spStatusBo.initSp(activity!!,statusBoList){
            statusBo = if(it.name == getString(R.string.lbl_spinner_pilih_status_bo)){
                ""
            }else{
                it.name
            }
        }

        /**
         * set spinner status Invoice
         */
        val statusInvoiceList = ArrayList<SpinnerData>()

        statusInvoiceList.add(SpinnerData(1,getString(R.string.lbl_spinner_pilih_status_invoice),""))
        statusInvoiceList.add(SpinnerData(2,getString(R.string.lbl_spinner_sudah_bayar),"paid"))
        statusInvoiceList.add(SpinnerData(3,getString(R.string.lbl_spinner_belum_bayar),"waiting"))

        spStatusInvoice.initSp(activity!!,statusInvoiceList){
            statusInvoice = it.nameParam!!
        }

        /**
         * set spinner status Shipping
         */
        val statusShippingList = ArrayList<SpinnerData>()

        statusShippingList.add(SpinnerData(1,getString(R.string.lbl_spinner_pilih_shipping)))
        statusShippingList.add(SpinnerData(2,getString(R.string.lbl_spinner_open)))
        statusShippingList.add(SpinnerData(3,getString(R.string.lbl_spinner_close)))

        spStatusShipping.initSp(activity!!,statusShippingList){
            statusShipping = if(it.name == getString(R.string.lbl_spinner_pilih_shipping)){
                ""
            }else{
                it.name
            }
        }
    }

    private fun observeData(){
        viewModel.apiResponse.observe(viewLifecycleOwner, Observer {
            if (it.status == ApiStatus.LOADING) {
                loadingDialog.show(R.string.lbl_loading_harap_tunggu)
            } else {
                loadingDialog.dismiss()
                if (it.status == ApiStatus.SUCCESS) {
                    goTo<TrackingOrderActivity> {
                        putExtra(Constants.BUNDLE.KODEDEALER,dealer.getString())
                        putExtra(Constants.BUNDLE.PARAM,trackingParam.getString())
                    }
                } else {
                    snacked(rootView, it)
                }
            }
        })

        viewModel.getUser().observe(viewLifecycleOwner, Observer {
            try {
                user = it!!
                if(user.id_role == Constants.ROLE.DEALER){
                    viewCariDealer.hide()
                }else{
                    viewCariDealer.show()
                }
            } catch (e: Exception) {
            }
        })

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == Constants.REQUEST.KODE_DEALER && resultCode == Constants.RESULT.KODE_DEALER){

            dealer = data!!.extra(Constants.BUNDLE.JSON).getObject()

            etCariDealer text dealer?.code + " ~ " + dealer?.name


        }
    }

    private fun initListener(){
        etPartNumber.div(vDivPartNumber, R.color.colorPrimary)
        etPartDeskripsi.div(vDivPartDeskripsi, R.color.colorPrimary)
        etNomorOrder.div(vDivNomorOrder, R.color.colorPrimary)

        etMonth.setOnClickListener(this)
        etCariDealer.setOnClickListener(this)
        btnTrackingOrder.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v){
            etMonth -> {
                dialogCalendar()
            }
            etCariDealer -> {
                goTo<KodeDealerTrackingOrderActivity>(requestCode = Constants.REQUEST.KODE_DEALER) {  }
            }
            btnTrackingOrder -> {
                if(user.id_role == Constants.ROLE.DEALER){
                    when{
                        textOf(etMonth) == "" -> {
                            snacked(rootView,getString(R.string.lbl_required_month))
                        }
                        else -> {
                            dealer = AllDealer(id = user.dealerId,name = user.dealerName,code = user.dealerCode)
                            trackingParam = TrackingOrderParam(textOf(etPartNumber),textOf(etPartDeskripsi),dateOrder,textOf(etNomorOrder),statusBo,statusInvoice,statusShipping)
                            viewModel.hitTrackingOrder(dealer!!.id,trackingParam)
                        }
                    }
                }else{
                    when{
                        textOf(etMonth) == "" -> {
                            snacked(rootView,getString(R.string.lbl_required_month))
                        }
                        textOf(etCariDealer) == "" -> {
                            snacked(rootView,getString(R.string.lbl_required_dealer))
                        }
                        else -> {
                            trackingParam = TrackingOrderParam(textOf(etPartNumber),textOf(etPartDeskripsi),dateOrder,textOf(etNomorOrder),statusBo,statusInvoice,statusShipping)
                            viewModel.hitTrackingOrder(dealer!!.id,trackingParam)
                        }
                    }
                }

            }
        }
    }
    private fun dialogCalendar(){

        val today = Calendar.getInstance()

        val builder = MonthPickerDialog.Builder(
            activity!!,
            MonthPickerDialog.OnDateSetListener { selectedMonth, selectedYear ->

                val dateSelect = (selectedMonth + 1).toString() + " " +"$selectedYear"

                dateOrder = CalendarUtils.setFormatDate(
                    dateSelect,
                    "M yyyy",
                    "yyyy-MM"
                )
                etMonth text CalendarUtils.setFormatDate(
                    dateSelect,
                    "M yyyy",
                    "MMMM yyyy"
                )

            },
            today.get(Calendar.YEAR),
            today.get(Calendar.MONTH)
        )

        builder
            .setActivatedYear(CalendarUtils.getCurrentDate("yyyy").toInt())
            .setTitle("Pilih bulan dan tahun")
            .setMonthRange(
                Calendar.JANUARY,
                Calendar.DECEMBER
            )
            .setOnMonthChangedListener { selectedMonth -> }
            .setOnYearChangedListener { selectedYear -> }
            .build()
            .show()
    }

    private fun applyLocale() {
        val locale = Locale("id", "ID", "ID")
        Locale.setDefault(locale)
        val res: Resources = resources
        val config = Configuration(res.getConfiguration())
        config.locale = locale
        activity!!.baseContext.resources.updateConfiguration(
            config,
            activity!!.baseContext.resources.displayMetrics
        )
    }

    private fun createDialogWithoutDateField(): DatePickerDialog? {
        val dpd = DatePickerDialog(activity!!, null, CalendarUtils.getCurrentDate("yyyy").toInt(), 1, 24)
        try {
            val datePickerDialogFields: Array<Field> =
                dpd.javaClass.declaredFields
            for (datePickerDialogField in datePickerDialogFields) {
                if (datePickerDialogField.name == "mDatePicker") {
                    datePickerDialogField.isAccessible = true
                    val datePicker: DatePicker = datePickerDialogField[dpd] as DatePicker
                    val datePickerFields =
                        datePickerDialogField.type.declaredFields
                    for (datePickerField in datePickerFields) {
                        Log.i("test", datePickerField.name)
                        if ("mDaySpinner" == datePickerField.name) {
                            datePickerField.isAccessible = true
                            val dayPicker = datePickerField[datePicker]
                            (dayPicker as View).visibility = View.GONE
                        }
                    }
                }
            }
        } catch (ex: Exception) {
        }
        return dpd
    }

    companion object {
        @JvmStatic
        fun newInstance() = OrderFragment()
    }



//                dialogCalendar.positiveListener = object : CalendarDialog.ClickListenerPos {
//                    override fun clickPos(dialog: CalendarDialog, monthSelect: String) {
//
//                        dateOrder = CalendarUtils.setFormatDate(
//                            monthSelect,
//                            "MMMM yyyy",
//                            "yyyy-MM"
//                        )
//                        etMonth text monthSelect
//                    }
//                }
}

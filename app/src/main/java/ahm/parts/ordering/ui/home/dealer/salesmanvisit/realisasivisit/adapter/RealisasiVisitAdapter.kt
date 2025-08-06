package ahm.parts.ordering.ui.home.dealer.salesmanvisit.realisasivisit.adapter

import ahm.parts.ordering.R
import ahm.parts.ordering.data.constant.Constants
import ahm.parts.ordering.data.model.home.dashboard.partnumber.kodedealer.AllDealer
import ahm.parts.ordering.data.model.home.dealer.realisasivisit.*
import ahm.parts.ordering.helper.*
import ahm.parts.ordering.ui.home.notification.detail.NotificationKreditLimitActivity
import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Space
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class KodeDealerAdapter constructor(
    var items: ArrayList<AllDealer>,
    val activity: Activity,
    private val listener: (Int) -> Unit
) :
    RecyclerView.Adapter<KodeDealerAdapter.MyViewHolder>() {

    var dealerSelected : AllDealer? = null
    var selectedDealer = -1
    var isAllDealer = false

    inner class MyViewHolder(vi: View) : RecyclerView.ViewHolder(vi) {

        val tvKodeDealer = vi.findViewById(R.id.tvKodeDealer) as TextView
        val ivCheck = vi.findViewById(R.id.ivCheck) as ImageView

        fun bindItem(position: Int) {
            itemView.setOnClickListener {
                listener(position)
                if(position == 0){
                    if(items[position].selectedDealer){
                        items.forEach {
                            it.selectedDealer = false
                        }
                        isAllDealer = false
                    }else{
                        items.forEach {
                            it.selectedDealer = true
                        }
                        isAllDealer = true
                    }
                    notifyDataSetChanged()
                }else{
                    if(items[position].selectedDealer){
                        selectedDealer = -1
                        dealerSelected = null
                        items[position].selectedDealer = false
                    }else{
                        selectedDealer = position
                        dealerSelected = items[position]
                        items[position].selectedDealer = true
                    }
                    notifyDataSetChanged()
                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView: View? = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_kode_dealer_efektifitas, parent, false)

        var viewHolder: MyViewHolder? = null

        if (viewHolder == null)
            viewHolder = MyViewHolder(itemView!!)
        else
            viewHolder = parent.tag as MyViewHolder


        return viewHolder
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindItem(position)

        val item = items[position]

        if(position == 0){
            holder.tvKodeDealer text item.name
        }else{
            holder.tvKodeDealer text item.code + " ~ " + item.name
        }


        if (item.selectedDealer) {
            holder.ivCheck.setImageResource(R.drawable.bg_fill_sorting_red)
        } else {
            holder.ivCheck.setImageResource(R.drawable.bg_fill_sorting_grey)
        }

    }

    fun filterList(filteredList: ArrayList<AllDealer>) {
        items = filteredList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.size
    }
}

class KoordinatorSalesmanAdapter constructor(
    var items: ArrayList<KoordinatorSalesman>,
    val activity: Activity,
    private val listener: (Int) -> Unit
) :
    RecyclerView.Adapter<KoordinatorSalesmanAdapter.MyViewHolder>() {

    var isAllKoordinator = false
    var koordinatorSelected : KoordinatorSalesman? = null

    inner class MyViewHolder(vi: View) : RecyclerView.ViewHolder(vi) {

        val tvKodeDealer = vi.findViewById(R.id.tvKodeDealer) as TextView
        val ivCheck = vi.findViewById(R.id.ivCheck) as ImageView

        fun bindItem(position: Int) {
            itemView.setOnClickListener {
                listener(position)
                if(position == 0){
                    if(items[position].selectedKoordinator){
                        items.forEach {
                            it.selectedKoordinator = false
                        }
                        isAllKoordinator = false
                    }else{
                        items.forEach {
                            it.selectedKoordinator = true
                        }
                        isAllKoordinator = true
                    }
                    notifyDataSetChanged()
                }else{
                    if(items[position].selectedKoordinator){
                        koordinatorSelected = null
                        items[position].selectedKoordinator = false
                    }else{
                        koordinatorSelected = items[position]
                        items[position].selectedKoordinator = true
                    }
                    notifyDataSetChanged()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView: View? = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_kode_dealer_efektifitas, parent, false)

        var viewHolder: MyViewHolder? = null

        if (viewHolder == null)
            viewHolder = MyViewHolder(itemView!!)
        else
            viewHolder = parent.tag as MyViewHolder


        return viewHolder
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindItem(position)

        val item = items[position]

        if(position == 0){
            holder.tvKodeDealer text item.name
        }else{
            holder.tvKodeDealer text item.koordinatorCode + " ~ " + item.name
        }

        if (item.selectedKoordinator) {
            holder.ivCheck.setImageResource(R.drawable.bg_fill_sorting_red)
        } else {
            holder.ivCheck.setImageResource(R.drawable.bg_fill_sorting_grey)
        }

    }

    fun filterList(filteredList: ArrayList<KoordinatorSalesman>) {
        items = filteredList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.size
    }
}


class SalesmanAdapter constructor(
    var items: ArrayList<Salesman>,
    val activity: Activity,
    private val listener: (Int) -> Unit
) :
    RecyclerView.Adapter<SalesmanAdapter.MyViewHolder>() {


    var salesmanSelected : Salesman? = null
    var selectedSalesman = -1
    var isAllSalesman = false

    inner class MyViewHolder(vi: View) : RecyclerView.ViewHolder(vi) {

        val tvKodeDealer = vi.findViewById(R.id.tvKodeDealer) as TextView
        val ivCheck = vi.findViewById(R.id.ivCheck) as ImageView

        fun bindItem(position: Int) {
            itemView.setOnClickListener {
                listener(position)
                if(position == 0){
                    if(items[position].selectedSales){
                        items.forEach {
                            it.selectedSales = false
                        }
                        isAllSalesman = false
                    }else{
                        items.forEach {
                            it.selectedSales = true
                        }
                        isAllSalesman = true
                    }
                    notifyDataSetChanged()
                }else{
                    if(items[position].selectedSales){
                        selectedSalesman = -1
                        salesmanSelected = null
                        items[position].selectedSales = false
                    }else{
                        selectedSalesman = position
                        salesmanSelected = items[position]
                        items[position].selectedSales = true
                    }
                    notifyDataSetChanged()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView: View? = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_kode_dealer_efektifitas, parent, false)

        var viewHolder: MyViewHolder? = null

        if (viewHolder == null)
            viewHolder = MyViewHolder(itemView!!)
        else
            viewHolder = parent.tag as MyViewHolder


        return viewHolder
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindItem(position)

        val item = items[position]

        if(position == 0){
            holder.tvKodeDealer text item.name
        }else{
            holder.tvKodeDealer text item.salesCode + " ~ " + item.name
        }

        if (item.selectedSales) {
            holder.ivCheck.setImageResource(R.drawable.bg_fill_sorting_red)
        } else {
            holder.ivCheck.setImageResource(R.drawable.bg_fill_sorting_grey)
        }

    }

    fun filterList(filteredList: ArrayList<Salesman>) {
        items = filteredList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.size
    }
}


class RealisasiPlanActualAdapter constructor(
    var items: ArrayList<Data>,
    val activity: Activity,
    val role : String,
    private val listener: (Int) -> Unit
) :
    RecyclerView.Adapter<RealisasiPlanActualAdapter.MyViewHolder>() {


    inner class MyViewHolder(vi: View) : RecyclerView.ViewHolder(vi) {

        val tvSalesName = vi.findViewById<TextView>(R.id.tvSalesName)
        val tvSalesKode = vi.findViewById<TextView>(R.id.tvSalesKode)
        val tvAddress = vi.findViewById<TextView>(R.id.tvAddress)
        val tvReport = vi.findViewById<TextView>(R.id.tvReport)

        fun bindItem(position: Int) {
            itemView.setOnClickListener {
                listener(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView: View? = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_realisasi_visit_plan_actual, parent, false)

        var viewHolder: MyViewHolder? = null

        if (viewHolder == null)
            viewHolder = MyViewHolder(itemView!!)
        else
            viewHolder = parent.tag as MyViewHolder


        return viewHolder
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindItem(position)

        val item = items[position]

        holder.tvSalesName text item.name

        holder.tvSalesKode text item.code + " • " + item.name
        holder.tvAddress text item.address

        holder.tvReport text item.plan + " Plan Visit • "+ item.actual + " Actual Visit " + "\nRealisasi " + item.realisasi + "%"

        if(role == Constants.ROLE.KOORDINATOR){
            holder.tvSalesKode.hide()
            holder.tvAddress.hide()
        }else if(role == Constants.ROLE.SALESMAN){
            holder.tvSalesName.hide()
        }

    }

    fun filterList(filteredList: ArrayList<Data>) {
        items = filteredList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.size
    }
}

class RealisasiPlanActualCoordinatorManagerAdapter constructor(
    var items: ArrayList<Coordinator>,
    val activity: Activity,
    val role : String,
    private val listener: (Int) -> Unit
) :
    RecyclerView.Adapter<RealisasiPlanActualCoordinatorManagerAdapter.MyViewHolder>() {


    inner class MyViewHolder(vi: View) : RecyclerView.ViewHolder(vi) {

        val tvSalesName = vi.findViewById<TextView>(R.id.tvSalesName)
        val tvSalesKode = vi.findViewById<TextView>(R.id.tvSalesKode)
        val tvAddress = vi.findViewById<TextView>(R.id.tvAddress)
        val tvReport = vi.findViewById<TextView>(R.id.tvReport)

        fun bindItem(position: Int) {
            itemView.setOnClickListener {
                listener(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView: View? = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_realisasi_visit_plan_actual_coordinator_manager, parent, false)

        var viewHolder: MyViewHolder? = null

        if (viewHolder == null)
            viewHolder = MyViewHolder(itemView!!)
        else
            viewHolder = parent.tag as MyViewHolder


        return viewHolder
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindItem(position)

        val item = items[position]

        holder.tvSalesName text item.name

        holder.tvSalesKode.hide()
        holder.tvAddress.hide()

        holder.tvReport text item.plan + " Plan Visit • " + item.actual + " Actual Visit " + "\nRealisasi " + item.realisasi + "%"

    }

    fun filterList(filteredList: ArrayList<Coordinator>) {
        items = filteredList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.size
    }
}


class RealisasiPlanActualSalesmanManagerAdapter constructor(
    var items: ArrayList<Sales>,
    val activity: Activity,
    val role : String,
    private val listener: (Int) -> Unit
) :
    RecyclerView.Adapter<RealisasiPlanActualSalesmanManagerAdapter.MyViewHolder>() {


    inner class MyViewHolder(vi: View) : RecyclerView.ViewHolder(vi) {

        val tvSalesName = vi.findViewById<TextView>(R.id.tvSalesName)
        val tvSalesKode = vi.findViewById<TextView>(R.id.tvSalesKode)
        val tvAddress = vi.findViewById<TextView>(R.id.tvAddress)
        val tvReport = vi.findViewById<TextView>(R.id.tvReport)

        fun bindItem(position: Int) {
            itemView.setOnClickListener {
                listener(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView: View? = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_realisasi_visit_plan_actual_coordinator_manager, parent, false)

        var viewHolder: MyViewHolder? = null

        if (viewHolder == null)
            viewHolder = MyViewHolder(itemView!!)
        else
            viewHolder = parent.tag as MyViewHolder


        return viewHolder
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindItem(position)

        val item = items[position]

        holder.tvSalesName text item.name

       /* holder.tvSalesKode text item.dealerCode + " • " + item.dealerName
        holder.tvAddress text item.dealerAddress
*/
        holder.tvSalesKode.hide()
        holder.tvAddress.hide()

        holder.tvReport text item.plan + " Plan Visit • " + item.actual +" Actual Visit "+ "\nRealisasi " + item.realisasi + "%"
    }

    fun filterList(filteredList: ArrayList<Sales>) {
        items = filteredList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.size
    }
}

class VisitDetailsAdapter constructor(
    var items: ArrayList<Visit>,
    val activity: Activity,
    private val listener: (Int) -> Unit
) :
    RecyclerView.Adapter<VisitDetailsAdapter.MyViewHolder>() {


    inner class MyViewHolder(vi: View) : RecyclerView.ViewHolder(vi) {

        val tvReport = vi.findViewById<TextView>(R.id.tvReport)
        val tvRencanaVisit = vi.findViewById<TextView>(R.id.tvRencanaVisit)
        val tvActualVisit = vi.findViewById<TextView>(R.id.tvActualVisit)
        val tvRealisasi = vi.findViewById<TextView>(R.id.tvRealisasi)
        //val tvEfektivitas = vi.findViewById<TextView>(R.id.tvEfektivitas)

        fun bindItem(position: Int) {
            itemView.setOnClickListener {
                listener(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView: View? = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_visit_details, parent, false)

        var viewHolder: MyViewHolder? = null

        if (viewHolder == null)
            viewHolder = MyViewHolder(itemView!!)
        else
            viewHolder = parent.tag as MyViewHolder


        return viewHolder
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindItem(position)

        val item = items[position]

        holder.tvReport text "Report Visit " + "${(position + 1)}"
        holder.tvRencanaVisit text CalendarUtils.setFormatDate(item.planVisit,SERVER_DATE_FORMAT,VIEW_DATE_SHORT_FORMAT)
        holder.tvActualVisit text CalendarUtils.setFormatDate(item.actualVisit,SERVER_DATE_FORMAT,VIEW_DATE_SHORT_FORMAT)
        holder.tvRealisasi text item.realisasiPersentase + "%"

    }

    fun filterList(filteredList: ArrayList<Visit>) {
        items = filteredList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.size
    }
}


class VisitDetailsManagerAdapter constructor(
    var items: ArrayList<VisitSalesman>,
    val activity: Activity,
    private val listener: (Int) -> Unit
) :
    RecyclerView.Adapter<VisitDetailsManagerAdapter.MyViewHolder>() {


    inner class MyViewHolder(vi: View) : RecyclerView.ViewHolder(vi) {

        val tvReport = vi.findViewById<TextView>(R.id.tvReport)
        val tvRencanaVisit = vi.findViewById<TextView>(R.id.tvRencanaVisit)
        val tvActualVisit = vi.findViewById<TextView>(R.id.tvActualVisit)
        val tvRealisasi = vi.findViewById<TextView>(R.id.tvRealisasi)

        fun bindItem(position: Int) {
            itemView.setOnClickListener {
                listener(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView: View? = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_visit_details, parent, false)

        var viewHolder: MyViewHolder? = null

        if (viewHolder == null)
            viewHolder = MyViewHolder(itemView!!)
        else
            viewHolder = parent.tag as MyViewHolder


        return viewHolder
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindItem(position)

        val item = items[position]

        holder.tvReport text "Report Visit " + "${(position + 1)}"
        holder.tvRencanaVisit text CalendarUtils.setFormatDate(item.planVisit,SERVER_DATE_FORMAT,VIEW_DATE_SHORT_FORMAT)
        holder.tvActualVisit text CalendarUtils.setFormatDate(item.actualVisit,SERVER_DATE_FORMAT,VIEW_DATE_SHORT_FORMAT)
        holder.tvRealisasi text item.realisasiPersentase + "%"

    }

    fun filterList(filteredList: ArrayList<VisitSalesman>) {
        items = filteredList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.size
    }
}


class KodeDealerSelectedAdapter constructor(
    var items: ArrayList<AllDealer>,
    val activity: Context,
    private val listener: (Int) -> Unit
) :
    RecyclerView.Adapter<KodeDealerSelectedAdapter.MyViewHolder>() {

    inner class MyViewHolder(vi: View) : RecyclerView.ViewHolder(vi) {

        //TODO init View
        val tvNameDealer = vi.findViewById<TextView>(R.id.tvNameDealer)
        val ivClose = vi.findViewById<ImageView>(R.id.ivClose)
        val spView = vi.findViewById<Space>(R.id.spView)

        fun bindItem(position: Int) {
            itemView.setOnClickListener {
                listener(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView: View? = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_kreditlimit_dealer_selected, parent, false)

        var viewHolder: MyViewHolder? = null

        viewHolder = if (viewHolder == null)
            MyViewHolder(itemView!!)
        else
            parent.tag as MyViewHolder


        return viewHolder
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindItem(position)

        val item = items[position]

        if(position == 0){
            holder.spView.hide()
        }

        holder.tvNameDealer text item.name

        holder.ivClose.onClick {
            items.remove(item)
            notifyDataSetChanged()
        }

    }

    fun filterList(filteredList: ArrayList<AllDealer>) {
        items = filteredList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.size
    }

}

class SalesmanSelectedAdapter constructor(
    var items: ArrayList<Salesman>,
    val activity: Context,
    private val listener: (Int) -> Unit
) :
    RecyclerView.Adapter<SalesmanSelectedAdapter.MyViewHolder>() {

    inner class MyViewHolder(vi: View) : RecyclerView.ViewHolder(vi) {

        //TODO init View
        val tvNameDealer = vi.findViewById<TextView>(R.id.tvNameDealer)
        val ivClose = vi.findViewById<ImageView>(R.id.ivClose)
        val spView = vi.findViewById<Space>(R.id.spView)

        fun bindItem(position: Int) {
            itemView.setOnClickListener {
                listener(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView: View? = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_kreditlimit_dealer_selected, parent, false)

        var viewHolder: MyViewHolder? = null

        viewHolder = if (viewHolder == null)
            MyViewHolder(itemView!!)
        else
            parent.tag as MyViewHolder


        return viewHolder
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindItem(position)

        val item = items[position]

        if(position == 0){
            holder.spView.hide()
        }

        holder.tvNameDealer text item.name

        holder.ivClose.onClick {
            items.remove(item)
            notifyDataSetChanged()
        }

    }

    fun filterList(filteredList: ArrayList<Salesman>) {
        items = filteredList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.size
    }

}

class CoordinatorSelectedAdapter constructor(
    var items: ArrayList<KoordinatorSalesman>,
    val activity: Context,
    private val listener: (Int) -> Unit
) :
    RecyclerView.Adapter<CoordinatorSelectedAdapter.MyViewHolder>() {

    inner class MyViewHolder(vi: View) : RecyclerView.ViewHolder(vi) {

        //TODO init View
        val tvNameDealer = vi.findViewById<TextView>(R.id.tvNameDealer)
        val ivClose = vi.findViewById<ImageView>(R.id.ivClose)
        val spView = vi.findViewById<Space>(R.id.spView)

        fun bindItem(position: Int) {
            itemView.setOnClickListener {
                listener(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView: View? = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_kreditlimit_dealer_selected, parent, false)

        var viewHolder: MyViewHolder? = null

        viewHolder = if (viewHolder == null)
            MyViewHolder(itemView!!)
        else
            parent.tag as MyViewHolder


        return viewHolder
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindItem(position)

        val item = items[position]

        if(position == 0){
            holder.spView.hide()
        }

        holder.tvNameDealer text item.name

        holder.ivClose.onClick {
            items.remove(item)
            notifyDataSetChanged()
        }

    }

    fun filterList(filteredList: ArrayList<KoordinatorSalesman>) {
        items = filteredList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.size
    }

}
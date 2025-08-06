package ahm.parts.ordering.ui.home.dealer.salesmanvisit.efektivitas.adapter

import ahm.parts.ordering.R
import ahm.parts.ordering.data.constant.Constants
import ahm.parts.ordering.data.model.home.dashboard.partnumber.kodedealer.AllDealer
import ahm.parts.ordering.data.model.home.dealer.efektivitas.*
import ahm.parts.ordering.data.model.home.dealer.realisasivisit.KoordinatorSalesman
import ahm.parts.ordering.data.model.home.dealer.realisasivisit.Salesman
import ahm.parts.ordering.helper.*
import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Space
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class EfektivitasPlanActualAdapter constructor(
    var items: ArrayList<EfData>,
    val activity: Activity,
    val role : String,
    private val listener: (Int) -> Unit
) :
    RecyclerView.Adapter<EfektivitasPlanActualAdapter.MyViewHolder>() {


    inner class MyViewHolder(vi: View) : RecyclerView.ViewHolder(vi) {

        val tvSalesName = vi.findViewById<TextView>(R.id.tvSalesName)
        val tvSalesKode = vi.findViewById<TextView>(R.id.tvSalesKode)
        val tvAddress = vi.findViewById<TextView>(R.id.tvAddress)
        val tvReport = vi.findViewById<TextView>(R.id.tvReport)
        val tvAmountTotal = vi.findViewById<TextView>(R.id.tvAmountTotal)

        fun bindItem(position: Int) {
            itemView.setOnClickListener {
                listener(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView: View? = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_efektivitas_plan_actual, parent, false)

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

        //holder.tvAmountTotal text "Total Amount " + StringMasker().addRp(item.amountTotal)

        holder.tvReport text item.plan + " Plan Visit • "+ item.actual + " Actual Visit " + "\nRealisasi " + item.realisasi + "% • Efektivitas " + item.efectivitas + "%\nOrder " + StringMasker().addRp(item.order)

        if(role == Constants.ROLE.KOORDINATOR){
            holder.tvSalesKode.hide()
            holder.tvAddress.hide()
        }else if(role == Constants.ROLE.SALESMAN){
            holder.tvSalesName.hide()
        }
    }

    fun filterList(filteredList: ArrayList<EfData>) {
        items = filteredList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.size
    }
}

class VisitDetailsAdapter constructor(
    var items: ArrayList<EfVisit>,
    val activity: Activity,
    private val listener: (Int) -> Unit
) :
    RecyclerView.Adapter<VisitDetailsAdapter.MyViewHolder>() {


    inner class MyViewHolder(vi: View) : RecyclerView.ViewHolder(vi) {

        val tvReport = vi.findViewById<TextView>(R.id.tvReport)
        val tvRencanaVisit = vi.findViewById<TextView>(R.id.tvRencanaVisit)
        val tvActualVisit = vi.findViewById<TextView>(R.id.tvActualVisit)
        val tvAmountOrder = vi.findViewById<TextView>(R.id.tvAmountOrder)
        val tvRealisasi = vi.findViewById<TextView>(R.id.tvRealisasi)
        val tvEfektivitas = vi.findViewById<TextView>(R.id.tvEfektivitas)

        fun bindItem(position: Int) {
            itemView.setOnClickListener {
                listener(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView: View? = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_efektivitas_visit_details, parent, false)

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
        holder.tvAmountOrder text StringMasker().addRp(item.amount)

        holder.tvRencanaVisit text CalendarUtils.setFormatDate(item.planVisit,
            SERVER_DATE_FORMAT,
            VIEW_DATE_SHORT_FORMAT
        )
        holder.tvActualVisit text CalendarUtils.setFormatDate(item.actualVisit,
            SERVER_DATE_FORMAT,
            VIEW_DATE_SHORT_FORMAT
        )

        holder.tvRealisasi text item.realisasiPersentase + "%"
        holder.tvEfektivitas text item.efectivitasPersentase + "%"



    }

    fun filterList(filteredList: ArrayList<EfVisit>) {
        items = filteredList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.size
    }
}

class VisitDetailsManagerAdapter constructor(
    var items: ArrayList<VisitEfektivitas>,
    val activity: Activity,
    private val listener: (Int) -> Unit
) :
    RecyclerView.Adapter<VisitDetailsManagerAdapter.MyViewHolder>() {


    inner class MyViewHolder(vi: View) : RecyclerView.ViewHolder(vi) {

        val tvReport = vi.findViewById<TextView>(R.id.tvReport)
        val tvRencanaVisit = vi.findViewById<TextView>(R.id.tvRencanaVisit)
        val tvActualVisit = vi.findViewById<TextView>(R.id.tvActualVisit)
        val tvAmountOrder = vi.findViewById<TextView>(R.id.tvAmountOrder)
        val tvRealisasi = vi.findViewById<TextView>(R.id.tvRealisasi)
        val tvEfektivitas = vi.findViewById<TextView>(R.id.tvEfektivitas)

        fun bindItem(position: Int) {
            itemView.setOnClickListener {
                listener(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView: View? = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_efektivitas_visit_details, parent, false)

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
        holder.tvAmountOrder text StringMasker().addRp(item.amount)

        holder.tvRencanaVisit text CalendarUtils.setFormatDate(item.planVisit,
            SERVER_DATE_FORMAT,
            VIEW_DATE_SHORT_FORMAT
        )
        holder.tvActualVisit text CalendarUtils.setFormatDate(item.actualVisit,
            SERVER_DATE_FORMAT,
            VIEW_DATE_SHORT_FORMAT
        )

        holder.tvRealisasi text item.realisasiPersentase + "%"
        holder.tvEfektivitas text item.efectivitasPersentase + "%"



    }

    fun filterList(filteredList: ArrayList<VisitEfektivitas>) {
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


class EfektivitasPlanActualCoordinatorManagerAdapter constructor(
    var items: ArrayList<Coordinator>,
    val activity: Activity,
    val role : String,
    private val listener: (Int) -> Unit
) :
    RecyclerView.Adapter<EfektivitasPlanActualCoordinatorManagerAdapter.MyViewHolder>() {


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

        //holder.tvReport text item.plan + " Plan Visit • Actual Visit " + item.actual + "\nRealisasi " + item.realisasi + "%"

        holder.tvReport text item.plan + " Plan Visit • " + item.actual+" Actual Visit " + "\nRealisasi " + item.realisasi + "% • Efektivitas " + item.efectivitas + "%\nOrder " + StringMasker().addRp(item.order)

    }

    fun filterList(filteredList: ArrayList<Coordinator>) {
        items = filteredList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.size
    }
}


class EfektivitasPlanActualSalesmanManagerAdapter constructor(
    var items: ArrayList<Sales>,
    val activity: Activity,
    val role : String,
    private val listener: (Int) -> Unit
) :
    RecyclerView.Adapter<EfektivitasPlanActualSalesmanManagerAdapter.MyViewHolder>() {


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

        holder.tvReport text item.plan + " Plan Visit • " + item.actual + " Actual Visit " + "\nRealisasi " + item.realisasi + "% • Efektivitas " + item.efectivitas+ "%\nOrder " + StringMasker().addRp(item.order)

    }

    fun filterList(filteredList: ArrayList<Sales>) {
        items = filteredList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.size
    }
}

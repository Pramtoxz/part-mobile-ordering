package ahm.parts.ordering.ui.home.dealer.kreditlimit.adapter

import ahm.parts.ordering.R
import ahm.parts.ordering.data.model.home.dashboard.partnumber.kodedealer.AllDealer
import ahm.parts.ordering.data.model.home.dealer.kreditlimit.KreditJatuhTempo
import ahm.parts.ordering.data.model.home.dealer.kreditlimit.KreditLimit
import ahm.parts.ordering.helper.*
import ahm.parts.ordering.ui.home.dealer.kreditlimit.fragment.CheckKreditJatuhTempoFragment
import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Space
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class KreditLimitCategoryAdapter constructor(
    var items: ArrayList<String>,
    val activity: Activity,
    private val listener: (Int) -> Unit
) :
    RecyclerView.Adapter<KreditLimitCategoryAdapter.MyViewHolder>() {

    private var selectedCategory = 0

    inner class MyViewHolder(vi: View) : RecyclerView.ViewHolder(vi) {

        //TODO init View
        val spFirst = vi.findViewById(R.id.spFirst) as Space
        val bgTab = vi.findViewById(R.id.bgTab) as LinearLayout
        val tvNameTab = vi.findViewById(R.id.tvNameTab) as TextView

        fun bindItem(position: Int) {
            itemView.setOnClickListener {
                listener(position)
                if (selectedCategory != position) {
                    selectedCategory = position
                    notifyDataSetChanged()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView: View? = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_campaign_promo_tab, parent, false)

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

        holder.apply {
            tvNameTab text item

            if (position == 0) {
                spFirst.show()
            } else {
                spFirst.hide()
            }

            if (selectedCategory == position) {
                bgTab.setBackgroundResource(R.drawable.bg_tab_campaign_selected)
            } else {
                bgTab.setBackgroundResource(0)
            }
        }

    }

    fun filterList(filteredList: ArrayList<String>) {
        items = filteredList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.size
    }

}


class KodeDealerAdapter constructor(
    var items: ArrayList<AllDealer>,
    val activity: Activity,
    private val listener: (Int) -> Unit
) :
    RecyclerView.Adapter<KodeDealerAdapter.MyViewHolder>() {

    var dealerSelected : AllDealer? = null
    var selectedDealer = -1

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
                    }else{
                        items.forEach {
                            it.selectedDealer = true
                        }
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

class KodeDealerSingleSelectAdapter constructor(
    var items: ArrayList<AllDealer>,
    val activity: KodeDealerKreditLimitSingleActivity,
    var selectedPosDealer : Int,
    private val listener: (Int) -> Unit
) :
    RecyclerView.Adapter<KodeDealerSingleSelectAdapter.MyViewHolder>() {

    var dealerSelected : AllDealer? = null

    inner class MyViewHolder(vi: View) : RecyclerView.ViewHolder(vi) {

        val tvKodeDealer = vi.findViewById(R.id.tvKodeDealer) as TextView
        val ivCheck = vi.findViewById(R.id.ivCheck) as ImageView

        fun bindItem(position: Int) {
            itemView.setOnClickListener {
                listener(position)
                if (selectedPosDealer == position) {
                    dealerSelected = null
                    selectedPosDealer = -1
                    activity.kodeDealerSelectedKreditLimit = null
                } else {
                    dealerSelected = items[position]
                    selectedPosDealer = position
                    activity.kodeDealerSelectedKreditLimit = items[position]
                }
                notifyDataSetChanged()
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

        holder.tvKodeDealer text item.code + " ~ " + item.name

        if (selectedPosDealer == position) {
            activity.kodeDealerSelectedKreditLimit = item
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


class KreditLimitKodeDealerSelectedAdapter constructor(
    var items: ArrayList<AllDealer>,
    val activity: Context,
    private val listener: (Int) -> Unit
) :
    RecyclerView.Adapter<KreditLimitKodeDealerSelectedAdapter.MyViewHolder>() {

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

        /*if(position == 0){
            holder.spView.hide()
        }else{
            holder.spView.show()
        }*/


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

class KreditLimitDetailListAdapter constructor(
    var items: ArrayList<KreditLimit>,
    val activity: Context,
    private val listener: (Int) -> Unit
) :
    RecyclerView.Adapter<KreditLimitDetailListAdapter.MyViewHolder>() {

    inner class MyViewHolder(vi: View) : RecyclerView.ViewHolder(vi) {

        //TODO init View
        val tvKodeDealer = vi.findViewById<TextView>(R.id.tvKodeDealer)
        val tvDate = vi.findViewById<TextView>(R.id.tvDate)
        val tvPlafonKredit = vi.findViewById<TextView>(R.id.tvPlafonKredit)
        val tvMaximumOpen = vi.findViewById<TextView>(R.id.tvMaximumOpen)

        fun bindItem(position: Int) {
            itemView.setOnClickListener {
                listener(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView: View? = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_kredit_limit, parent, false)

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

        holder.tvKodeDealer text item.dealerCode + " ~ " + item.dealerName
        holder.tvPlafonKredit text StringMasker().addRp(item.plafonKreditLimit)
        holder.tvMaximumOpen text StringMasker().addRp(item.maximumOpen)
        holder.tvDate text CalendarUtils.setFormatDate(item.date,SERVER_DATE_FORMAT,VIEW_DATE_FORMAT)


    }

    fun filterList(filteredList: ArrayList<KreditLimit>) {
        items = filteredList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.size
    }

}


class KreditJatuhTempoListAdapter constructor(
    var items: ArrayList<KreditJatuhTempo>,
    val activity: Context,
    private val listener: (Int) -> Unit
) :
    RecyclerView.Adapter<KreditJatuhTempoListAdapter.MyViewHolder>() {

    inner class MyViewHolder(vi: View) : RecyclerView.ViewHolder(vi) {

        //TODO init View
        val tvKodeDealer = vi.findViewById<TextView>(R.id.tvKodeDealer)
        val tvDate = vi.findViewById<TextView>(R.id.tvDate)
        val tvOverdue = vi.findViewById<TextView>(R.id.tvOverdue)
        val tvTanggalJatuhTempo = vi.findViewById<TextView>(R.id.tvTanggalJatuhTempo)

        val ivWarning = vi.findViewById<ImageView>(R.id.ivWarning)

        fun bindItem(position: Int) {
            itemView.setOnClickListener {
                listener(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView: View? = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_jatuh_tempo, parent, false)

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

        holder.tvKodeDealer text item.dealerCode + " ~ " + item.dealerName
        holder.tvOverdue text StringMasker().addRp(item.amount)
        holder.tvTanggalJatuhTempo text CalendarUtils.setFormatDate(item.dateOver,SERVER_DATE_FORMAT,VIEW_DATE_FORMAT)
        holder.tvDate text CalendarUtils.setFormatDate(item.date,SERVER_DATE_FORMAT,VIEW_DATE_FORMAT)

        if(item.flag.equals("green",ignoreCase = true)){
            holder.ivWarning.hide()
            holder.tvTanggalJatuhTempo.setTextColor(activity.resources.getColor(R.color.green_dark))
        }else{
            holder.ivWarning.show()
            holder.tvTanggalJatuhTempo.setTextColor(activity.resources.getColor(R.color.red_amount_order))
        }

    }

    fun filterList(filteredList: ArrayList<KreditJatuhTempo>) {
        items = filteredList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.size
    }

}


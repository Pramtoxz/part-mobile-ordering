package ahm.parts.ordering.ui.home.notification.adapter

import ahm.parts.ordering.R
import ahm.parts.ordering.data.constant.Constants
import ahm.parts.ordering.data.model.notification.Notification
import ahm.parts.ordering.helper.*
import ahm.parts.ordering.ui.home.dealer.kreditlimit.detail.jatuhtempo.KreditJatuhTempoDetailActivity
import ahm.parts.ordering.ui.home.home.campaignpromo.fragment.part.PartPromoDetailActivity
import ahm.parts.ordering.ui.home.notification.detail.NotificationInformationActivity
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class NotificationAdapter constructor(
    var items: ArrayList<Notification>,
    val activity: Activity,
    private val listener: (Int) -> Unit
) :
    RecyclerView.Adapter<NotificationAdapter.MyViewHolder>() {

    inner class MyViewHolder(vi: View) : RecyclerView.ViewHolder(vi) {

        val vNotifPromo = vi.findViewById(R.id.vNotifPromo) as CardView
        val vNotifKredit = vi.findViewById(R.id.vNotifKredit) as CardView
        val vNotifInformation = vi.findViewById(R.id.vNotifInformation) as CardView

        val tvNamePartPromo = vi.findViewById(R.id.tvNamePartPromo) as TextView
        val tvPartNumberPromo = vi.findViewById(R.id.tvPartNumberPromo) as TextView
        val tvHetPromo = vi.findViewById(R.id.tvHetPromo) as TextView
        val tvDiscountPromo = vi.findViewById(R.id.tvDiscountPromo) as TextView
        val tvBeginDatePromo = vi.findViewById(R.id.tvBeginDatePromo) as TextView
        val tvEndDatePromo = vi.findViewById(R.id.tvEndDatePromo) as TextView
        val tvNotePartPromo = vi.findViewById(R.id.tvNotePartPromo) as TextView

        val tvDealerKreditLimit = vi.findViewById(R.id.tvDealerKreditLimit) as TextView
        val tvDateKreditLimit = vi.findViewById(R.id.tvDateKreditLimit) as TextView
        val tvAmountKreditLimit = vi.findViewById(R.id.tvAmountKreditLimit) as TextView

        val tvTitleNotifInformation = vi.findViewById(R.id.tvTitleNotifInformation) as TextView
        val tvDescriptionInformation = vi.findViewById(R.id.tvDescriptionInformation) as TextView

        fun bindItem(position: Int) {
            itemView.setOnClickListener {
                listener(position)
                when {
                    items[position].typeNotice.equals("New Promo",ignoreCase = true) -> activity.goTo<PartPromoDetailActivity> {
                        putExtra(Constants.BUNDLE.JSON,items[position].newPromo.getString())
                    }
                    items[position].typeNotice.equals("Kredit Limit",ignoreCase = true) -> activity.goTo<KreditJatuhTempoDetailActivity> {
                        putExtra(Constants.BUNDLE.JSON, items[position].kreditLimit.getString())
                        putExtra(Constants.BUNDLE.TOOLBAR, activity.getString(R.string.lbl_title_kredit_limit_details))
                    }
                    else -> activity.goTo<NotificationInformationActivity> {
                        putExtra(Constants.BUNDLE.JSON, items[position].information.getString())
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView: View? = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_notification, parent, false)

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

        when {
            item.typeNotice.equals("New Promo",ignoreCase = true) -> {
                holder.vNotifPromo.show()
                holder.vNotifKredit.hide()
                holder.vNotifInformation.hide()
            }
            item.typeNotice.equals("Kredit Limit",ignoreCase = true) -> {
                holder.vNotifPromo.hide()
                holder.vNotifKredit.show()
                holder.vNotifInformation.hide()
            }
            else -> {
                holder.vNotifPromo.hide()
                holder.vNotifKredit.hide()
                holder.vNotifInformation.show()
            }
        }

        item.newPromo.apply {
            holder.tvNamePartPromo text "$code • $name"
            holder.tvPartNumberPromo text "$partNumber • $itemGroup"

            try {
                holder.tvHetPromo text StringMasker().addRp(het.toDouble())
            } catch (e: Exception) {
            }

            holder.tvDiscountPromo text "$discount%"

            holder.tvBeginDatePromo text CalendarUtils.setFormatDate(beginEffdate,"yyyy-MM-dd","MMMM dd, yyyy")
            holder.tvEndDatePromo text CalendarUtils.setFormatDate(endEffdate,"yyyy-MM-dd","MMMM dd, yyyy")

            holder.tvNotePartPromo text "*$note"
        }

        item.kreditLimit.apply {
            holder.tvDealerKreditLimit text "$dealerCode • $dealerName"
            holder.tvAmountKreditLimit text StringMasker().addRp(amount)
            holder.tvDateKreditLimit text CalendarUtils.setFormatDate(dateOver,SERVER_DATE_FORMAT,VIEW_DATE_FORMAT)

            if(flag.equals("green",ignoreCase = true)){
                holder.tvDateKreditLimit.setTextColor(activity.resources.getColor(R.color.green_dark))
            }else{
                holder.tvDateKreditLimit.setTextColor(activity.resources.getColor(R.color.red_amount_order))
            }
        }

        item.information.apply {
            holder.tvTitleNotifInformation text name
            holder.tvDescriptionInformation text smallInfo
        }
    }

    fun filterList(filteredList: ArrayList<Notification>) {
        items = filteredList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.size
    }
}
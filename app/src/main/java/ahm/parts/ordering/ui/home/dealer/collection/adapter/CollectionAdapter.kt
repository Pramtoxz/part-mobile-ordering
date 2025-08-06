package ahm.parts.ordering.ui.home.dealer.collection.adapter

import ahm.parts.ordering.R
import ahm.parts.ordering.data.constant.Constants
import ahm.parts.ordering.data.model.home.dashboard.partnumber.kodedealer.AllDealer
import ahm.parts.ordering.data.model.home.dealer.collection.Collection1
import ahm.parts.ordering.data.model.home.dealer.collection.CollectionPhoto
import ahm.parts.ordering.helper.*
import ahm.parts.ordering.ui.base.OnChangeItem
import ahm.parts.ordering.ui.home.dealer.collection.list.CollectionActivityList
import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.RecyclerView
import com.livinglifetechway.quickpermissions_kotlin.runWithPermissions
import io.rmiri.skeleton.SkeletonGroup
import pl.aprilapps.easyphotopicker.EasyImage
import kotlin.coroutines.coroutineContext

class CollectionPhotoAdapter constructor(
    var items: ArrayList<CollectionPhoto>,
    val activity: Activity,
    private val listener: (Int) -> Unit
) :
    RecyclerView.Adapter<CollectionPhotoAdapter.MyViewHolder>() {

    inner class MyViewHolder(vi: View) : RecyclerView.ViewHolder(vi) {

        //TODO init View
        val spView = vi.findViewById(R.id.spView) as Space
        val ivPhoto = vi.findViewById(R.id.ivPhoto) as ImageView
        val ivCamera = vi.findViewById(R.id.ivCamera) as ImageView
        val tvUploadFoto = vi.findViewById(R.id.tvUploadFoto) as TextView
        val vBgOpacity = vi.findViewById(R.id.vBgOpacity) as View
        val skGroup = vi.findViewById(R.id.skGroup) as SkeletonGroup

        fun bindItem(position: Int) {
            itemView.setOnClickListener {
                listener(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView: View? = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_competitor_photo_add, parent, false)

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
            holder.spView.show()
        }else{
            holder.spView.hide()
        }

        if(item.photoPath != ""){
            holder.ivCamera.setImageResource(R.drawable.ic_camera_white)

            holder.tvUploadFoto text "Ganti Foto"

            holder.tvUploadFoto.setTextColor(activity.resources.getColor(R.color.white))

            holder.skGroup.show()
            holder.vBgOpacity.show()

            holder.ivPhoto.loadImage(item.photoPath,holder.skGroup)
        }else{
            holder.skGroup.hide()
        }

        holder.itemView.onClick {
            activity.runWithPermissions(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) {
                val bottomSheet = activity.bottomSheet(R.layout.dialog_bottom_camera) {}

                val btnCamera = bottomSheet.findViewById<Button>(R.id.btnCamera)!!
                val btnGallery = bottomSheet.findViewById<Button>(R.id.btnGallery)!!
                val btnPreview = bottomSheet.findViewById<Button>(R.id.btnPreview)!!

                btnCamera.onClick {
                    bottomSheet.dismiss()
                    EasyImage.openCamera(activity, item.id)
                }

                btnGallery.onClick {
                    bottomSheet.dismiss()
                    EasyImage.openGallery(activity, item.id)
                }

                if (item.photoPath == "") {
                    btnPreview.hide()
                } else {
                    btnPreview.show()
                }

                btnPreview.onClick {
                    bottomSheet.dismiss()
                    activity.dialogSheetPreview(item.photoPath)
                }
            }
        }

    }

    fun filterList(filteredList: ArrayList<CollectionPhoto>) {
        items = filteredList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.size
    }
}



class CollectionAdapter constructor(
    var items: ArrayList<Collection1>,
    val activity: Activity,
//    private val context: Context
    private val listener: (Int) -> Unit
) :
    RecyclerView.Adapter<CollectionAdapter.MyViewHolder>() {

    var dealerSelected : Collection1? = null
    var selectedDealer = -1
    var isAllDealer = false



    inner class MyViewHolder(vi: View) : RecyclerView.ViewHolder(vi) {

        val tvKodeDealer = vi.findViewById(R.id.tvKodeCollect) as TextView
        val tvdate = vi.findViewById(R.id.tvDate) as TextView
        val tvjumlah = vi.findViewById(R.id.tvJumlahBarang) as TextView
        val tvpcs = vi.findViewById(R.id.tvJumlahPcs) as TextView
        val tvtotalpiutang = vi.findViewById(R.id.tvTotalPiutang) as TextView
        val ivCheck = vi.findViewById(R.id.ivCheck) as ImageView
        val llroot = vi.findViewById(R.id.crdItemCollection) as CardView


        fun bindItem(position: Int) {

            itemView.setOnClickListener {
                listener(position)
                if(position == 0){
                    if(items[position].isSelected){
                        items.forEach {
                            it.isSelected = false
                        }
                        isAllDealer = false
                    }else{
                        items.forEach {
                            it.isSelected = true
                        }
                        isAllDealer = true
                    }
                    notifyDataSetChanged()
                }else{
                    if(items[position].isSelected){
                        selectedDealer = -1
                        dealerSelected = null
                        items[position].isSelected = false
                    }else{
                        selectedDealer = position
                        dealerSelected = items[position]
                        items[position].isSelected = true
                    }
                    notifyDataSetChanged()
                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView: View? = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_collection_list, parent, false)

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
            holder.tvKodeDealer text item.noTransaksi
//            holder.tvdate text CalendarUtils.setFormatDate(
//                item.tglTransaksi,"yyyy-MM-dd","MMMM dd, yyyy"
//            )
//            holder.tvjumlah text item.jumlahItem
//            holder.tvpcs text item.jumlahPcs
//            holder.tvtotalpiutang.text = StringMasker().addRp(item.totalBayar.toDouble())
        }else{
            holder.tvKodeDealer text item.noTransaksi
            holder.tvdate text CalendarUtils.setFormatDate(
                item.tglTransaksi,"yyyy-MM-dd","MMMM dd, yyyy"
            )
            holder.tvjumlah text item.jumlahItem
            holder.tvpcs text item.jumlahPcs
            holder.tvtotalpiutang.text = StringMasker().addRp(item.totalBayar.toDouble())

        }
        if (item.isSelected) {
            holder.llroot.setBackgroundResource(R.drawable.bg_fill_round_disable_gray)
            holder.ivCheck.visibility=View.VISIBLE
            holder.ivCheck.setImageResource(R.drawable.bg_fill_sorting_red)

        }else{
            holder.llroot.setBackgroundResource(R.drawable.bg_outline_round_search_gray)
            holder.ivCheck.visibility=View.GONE
            holder.ivCheck.setImageResource(R.drawable.bg_fill_sorting_grey)

        }
    }

    fun getSelectedItemsCollection(items: List<Collection1>?): List<Collection1> {
        val results = ArrayList<Collection1>()
        items?.forEach {
            if (it.isSelected == true) results.add(it)
        }
        return results
    }

    fun getCodeServicesTypeSelected(items: List<Collection1>): List<String> {
        val itemsResult = ArrayList<String>()
        items.forEach {
            if (it.isSelected) itemsResult.add(it.noTransaksi.toString())
        }
        return itemsResult
    }

    fun notifyItemCollectionSelected(source: List<Collection1>, item: Collection1) {
        for (j in source.indices) {
            val it = source[j]
            if (item.noTransaksi.equals(it.noTransaksi)) {
                it.isSelected = item.isSelected
                break
            }
        }
    }

    fun filterList(filteredList: ArrayList<Collection1>) {
        items = filteredList
        notifyDataSetChanged()
    }

    /**
     * mendapatkan biaya total servis
     * @param items list jenis servis terpilih
     * @return biaya total servis
     */
    fun getCollectionFee(items: List<Collection1>): Double {
        var totalFee = 0.0
        items.forEach {
            if (it.isSelected) totalFee += it.totalBayar
        }
        return totalFee
    }

    override fun getItemCount(): Int {
        return items.size
    }
}
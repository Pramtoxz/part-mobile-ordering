package ahm.parts.ordering.ui.home.dealer.competitor.adapter

import ahm.parts.ordering.R
import ahm.parts.ordering.data.constant.Constants
import ahm.parts.ordering.data.model.home.dashboard.partnumber.kodedealer.AllDealer
import ahm.parts.ordering.data.model.home.dealer.competitor.CompetitorPhoto
import ahm.parts.ordering.data.model.home.dealer.kreditlimit.KreditJatuhTempo
import ahm.parts.ordering.data.model.home.dealer.kreditlimit.KreditLimit
import ahm.parts.ordering.helper.*
import ahm.parts.ordering.ui.home.dealer.kreditlimit.fragment.CheckKreditJatuhTempoFragment
import android.Manifest
import android.app.Activity
import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.livinglifetechway.quickpermissions_kotlin.runWithPermissions
import io.rmiri.skeleton.SkeletonGroup
import pl.aprilapps.easyphotopicker.EasyImage

class CompetitorPhotoAdapter constructor(
    var items: ArrayList<CompetitorPhoto>,
    val activity: Activity,
    private val listener: (Int) -> Unit
) :
    RecyclerView.Adapter<CompetitorPhotoAdapter.MyViewHolder>() {

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

    fun filterList(filteredList: ArrayList<CompetitorPhoto>) {
        items = filteredList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.size
    }

}
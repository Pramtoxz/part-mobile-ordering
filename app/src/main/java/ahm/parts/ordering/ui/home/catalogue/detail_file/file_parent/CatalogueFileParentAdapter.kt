package ahm.parts.ordering.ui.home.catalogue.detail_file.file_parent

import ahm.parts.ordering.R
import ahm.parts.ordering.data.constant.Constants
import ahm.parts.ordering.data.model.catalogue.CatalogueDetailFile
import ahm.parts.ordering.helper.getString
import ahm.parts.ordering.helper.goTo
import ahm.parts.ordering.helper.onClick
import ahm.parts.ordering.ui.base.adapter.MvpHolder
import ahm.parts.ordering.ui.home.catalogue.detail_file.file_list.CatalogueFileListActivity
import android.Manifest
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.livinglifetechway.quickpermissions_kotlin.runWithPermissions
import kotlinx.android.synthetic.main.item_catalogue_list.view.*
//
//class CatalogueFileParentAdapter(
//    var contextView: CatalogueFileParentActivity,
//    var listAbout: List<CatalogueDetailFile>
//) : RecyclerView.Adapter<MvpHolder>() {
//
//    private lateinit var context: Context
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MvpHolder {
//        context = parent.context
//        return MvpHolder(
//            LayoutInflater.from(context).inflate(
//                R.layout.item_catalogue_list,
//                parent,
//                false
//            )
//        )
//    }
//
//    override fun onBindViewHolder(holder: MvpHolder, position: Int) {
//        val item = listAbout[position]
//
//        holder.itemView.tvNameCatalogue.text = item.name
//
//        holder.itemView.onClick {
//            contextView.goTo<CatalogueFileListActivity> {
//                putExtra(Constants.BUNDLE.JSON,item.getString())
//            }
//        }
//
//    }
//
//    // untuk megarahkan perubahan di hasil
//    fun filterList(filteredList: ArrayList<CatalogueDetailFile>) {
//        listAbout = filteredList
//        notifyDataSetChanged()
//    }
//
//
//    override fun getItemCount(): Int = listAbout.size
//
//}
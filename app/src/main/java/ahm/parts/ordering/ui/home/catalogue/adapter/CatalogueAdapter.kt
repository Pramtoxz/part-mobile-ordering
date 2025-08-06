package ahm.parts.ordering.ui.home.catalogue.adapter


import ahm.parts.ordering.R
import ahm.parts.ordering.data.constant.Constants
import ahm.parts.ordering.data.model.catalogue.CatalogueDetailFile
import ahm.parts.ordering.data.model.catalogue.CatalogueDetailFileList
import ahm.parts.ordering.helper.getString
import ahm.parts.ordering.helper.goTo
import ahm.parts.ordering.helper.onClick
import ahm.parts.ordering.ui.base.adapter.MvpHolder
import ahm.parts.ordering.ui.home.catalogue.detail_file.file.CatalogueFileActivity
import ahm.parts.ordering.ui.home.catalogue.detail_file.file_list.CatalogueFileListActivity
import ahm.parts.ordering.ui.home.catalogue.detail_file.file_parent.CatalogueFileParentActivity
import android.Manifest
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.livinglifetechway.quickpermissions_kotlin.runWithPermissions
import kotlinx.android.synthetic.main.item_buku_pedoman_pemilik.view.*
import kotlinx.android.synthetic.main.item_buku_pedoman_pemilik.view.tvNameCatalogue
import kotlinx.android.synthetic.main.item_catalogue_list.view.*

class CatalogueFileAdapter(
    var contextView: CatalogueFileActivity,
    var listAbout: List<CatalogueDetailFile>
) : RecyclerView.Adapter<MvpHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MvpHolder {
        context = parent.context
        return MvpHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_buku_pedoman_pemilik,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MvpHolder, position: Int) {
        val item = listAbout[position]

        holder.itemView.tvNameCatalogue.text = item.name
        holder.itemView.tvSizeFile.text = item.size

        holder.itemView.ivDownload.onClick {
            contextView.runWithPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE) {
                contextView.loadingDialog.show(R.string.lbl_loading_download)
                contextView.viewModel1.downloadFile(item.url)
            }
        }
    }

    // untuk megarahkan perubahan di hasil
    fun filterList(filteredList: ArrayList<CatalogueDetailFile>) {
        listAbout = filteredList
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int = listAbout.size

}


class CatalogueFileListAdapter(
    var contextView: CatalogueFileListActivity,
    var listAbout: List<CatalogueDetailFileList>
) : RecyclerView.Adapter<MvpHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MvpHolder {
        context = parent.context
        return MvpHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_buku_pedoman_pemilik,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MvpHolder, position: Int) {
        val item = listAbout[position]

        holder.itemView.tvNameCatalogue.text = item.name
        holder.itemView.tvSizeFile.text = item.size

        holder.itemView.ivDownload.onClick {
            contextView.runWithPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE) {
                contextView.loadingDialog.show(R.string.lbl_loading_download)
                contextView.viewModel1.downloadFile(item.url)
            }
        }
    }

    // untuk megarahkan perubahan di hasil
    fun filterList(filteredList: ArrayList<CatalogueDetailFileList>) {
        listAbout = filteredList
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int = listAbout.size

}


class CatalogueFileParentAdapter(
    var contextView: CatalogueFileParentActivity,
    var listAbout: List<CatalogueDetailFile>
) : RecyclerView.Adapter<MvpHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MvpHolder {
        context = parent.context
        return MvpHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_catalogue_list,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MvpHolder, position: Int) {
        val item = listAbout[position]

        holder.itemView.tvNameCatalogue.text = item.name

        holder.itemView.onClick {
            contextView.goTo<CatalogueFileListActivity> {
                putExtra(Constants.BUNDLE.JSON,item.getString())
            }
        }

    }

    // untuk megarahkan perubahan di hasil
    fun filterList(filteredList: ArrayList<CatalogueDetailFile>) {
        listAbout = filteredList
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int = listAbout.size

}
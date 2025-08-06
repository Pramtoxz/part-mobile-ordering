package ahm.parts.ordering.ui.base.adapter

import ahm.parts.ordering.helper.inflate
import ahm.parts.ordering.helper.onClick
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<ITEM> constructor(protected var itemList: ArrayList<ITEM>,
                                             private val layoutResId: Int)
    : RecyclerView.Adapter<BaseAdapter.Holder>() {

    override fun getItemCount() = itemList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = parent inflate layoutResId
        val viewHolder = Holder(view)
        val itemView = viewHolder.itemView
        itemView.onClick {
            val adapterPosition = viewHolder.adapterPosition
            if (adapterPosition != RecyclerView.NO_POSITION) {
                onItemClick(itemView, adapterPosition)

            }
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = itemList[position]
        holder.itemView.bind(item,position)
    }

    fun update(items: List<ITEM>) {
        updateAdapterWithDiffResult(calculateDiff(items))
    }

    private fun updateAdapterWithDiffResult(result: DiffUtil.DiffResult) {
        result.dispatchUpdatesTo(this)
    }

    private fun calculateDiff(newItems: List<ITEM>) =
            DiffUtil.calculateDiff(DiffUtilCallback(itemList, newItems))

    fun add(item: ITEM) {
        itemList.toMutableList().add(item)
        notifyItemInserted(itemList.size)
    }

    fun remove(position: Int) {
        itemList.toMutableList().removeAt(position)
        notifyItemRemoved(position)
    }

    fun filterList(filteredList: java.util.ArrayList<ITEM>) {
        itemList = filteredList
        notifyDataSetChanged()
    }

    final override fun onViewRecycled(holder: Holder) {
        super.onViewRecycled(holder)
        onViewRecycled(holder.itemView)
    }

    protected open fun onViewRecycled(itemView: View) {
    }

    protected open fun onItemClick(itemView: View, position: Int) {
    }

    protected open fun View.bind(item: ITEM,position: Int) {
    }

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
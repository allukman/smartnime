package id.smartech.smartnime.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.smartech.smartnime.R
import id.smartech.smartnime.databinding.ItemListSearchBinding
import id.smartech.smartnime.ui.search.model.SearchModel
import id.smartech.smartnime.ui.search.model.SearchResponse

class SearchAdapter(private val items: ArrayList<SearchModel>): RecyclerView.Adapter<SearchAdapter.SearchHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun addList(list: List<SearchModel>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    class SearchHolder(val binding: ItemListSearchBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchHolder {
        return SearchHolder(
                DataBindingUtil.inflate(
                        (LayoutInflater.from(parent.context)),
                        R.layout.item_list_search,
                        parent, false
                )
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: SearchHolder, position: Int) {
        val item = items[position]

        holder.binding.name.text = if(item.title.isNullOrEmpty()) item.name else item.title

        Glide.with(holder.itemView)
                .load(item.imageUrl)
                .placeholder(R.drawable.white)
                .error(R.drawable.white)
                .into(holder.binding.image)

        holder.itemView.setOnClickListener {
            onItemClickCallback.onClickItem(item)
        }
    }

    interface OnItemClickCallback {
        fun onClickItem(data: SearchModel)
    }
}
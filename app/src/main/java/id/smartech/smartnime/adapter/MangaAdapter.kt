package id.smartech.smartnime.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.smartech.smartnime.R
import id.smartech.smartnime.databinding.ItemListAnimeBinding
import id.smartech.smartnime.model.TopAnimeModel
import id.smartech.smartnime.model.TopMangaModel

class MangaAdapter(private val items: ArrayList<TopMangaModel>): RecyclerView.Adapter<MangaAdapter.MangaHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun addList(list: List<TopMangaModel>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    class MangaHolder(val binding: ItemListAnimeBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MangaHolder {
        return MangaHolder(
            DataBindingUtil.inflate(
                (LayoutInflater.from(parent.context)),
                R.layout.item_list_anime,
                parent, false
            )
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: MangaHolder, position: Int) {
        val item = items[position]

        holder.binding.title.text = item.title

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
        fun onClickItem(data: TopMangaModel)
    }

}
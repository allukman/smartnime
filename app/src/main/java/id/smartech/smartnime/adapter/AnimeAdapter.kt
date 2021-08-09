package id.smartech.smartnime.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.smartech.smartnime.R
import id.smartech.smartnime.databinding.ItemListAnimeBinding
import id.smartech.smartnime.model.TopAnimeModel

class AnimeAdapter(private val items: ArrayList<TopAnimeModel>): RecyclerView.Adapter<AnimeAdapter.AnimeHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun addList(list: List<TopAnimeModel>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    class AnimeHolder(val binding: ItemListAnimeBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeHolder {
        return AnimeHolder(
            DataBindingUtil.inflate(
                (LayoutInflater.from(parent.context)),
                R.layout.item_list_anime,
                parent, false
            )
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: AnimeHolder, position: Int) {
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
        fun onClickItem(data: TopAnimeModel)
    }
}
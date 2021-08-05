package id.smartech.smartnime.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.smartech.smartnime.R
import id.smartech.smartnime.databinding.ItemListAnimeBinding
import id.smartech.smartnime.databinding.ItemListMangaBinding
import id.smartech.smartnime.model.TopAnimeModel
import id.smartech.smartnime.model.TopMangaModel

class TopMangaAdapter(private val items: ArrayList<TopMangaModel>): RecyclerView.Adapter<TopMangaAdapter.TopMangaHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun addList(list: List<TopMangaModel>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    class TopMangaHolder(val binding: ItemListMangaBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopMangaHolder {
        return TopMangaHolder(
                DataBindingUtil.inflate(
                        (LayoutInflater.from(parent.context)),
                        R.layout.item_list_manga,
                        parent, false
                )
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: TopMangaHolder, position: Int) {
        val item = items[position]

        val volumes = if(item.volumes != null) {
            " (${item.volumes})"
        } else {
            ""
        }

        holder.binding.title.text = item.title
        holder.binding.vols.text = item.type + volumes
        holder.binding.startDate.text = item.startDate
        holder.binding.members.text = item.members.toString() + " members"
        holder.binding.score.text = item.score.toString()
        holder.binding.rank.text = item.rank.toString()

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
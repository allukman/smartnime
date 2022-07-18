package id.smartech.smartnime.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.smartech.smartnime.R
import id.smartech.smartnime.databinding.ItemListSeasonalBinding
import id.smartech.smartnime.ui.nav.seasonal.model.SeasonalAnimeModel

class SeasonalAnimeAdapter(private val items: ArrayList<SeasonalAnimeModel>): RecyclerView.Adapter<SeasonalAnimeAdapter.SeasonalAnimeHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun addList(list: List<SeasonalAnimeModel>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }


    class SeasonalAnimeHolder(val binding: ItemListSeasonalBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeasonalAnimeHolder {
        return SeasonalAnimeHolder(
                DataBindingUtil.inflate(
                        (LayoutInflater.from(parent.context)),
                        R.layout.item_list_seasonal,
                        parent, false
                )
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: SeasonalAnimeHolder, position: Int) {
        val item = items[position]

        val genres = item.genres?.map {
            it.name
        }

        var genre = ""
        for(i in genres.toString()) {
            if(i == genres.toString().first() || i == genres.toString().last()) {
                continue
            } else {
                genre += i.toString()
            }
        }

        holder.binding.title.text = item.title
        holder.binding.genre.text = genre
        holder.binding.favorites.text = item.members.toString()
        holder.binding.type.text = item.type

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
        fun onClickItem(data: SeasonalAnimeModel)
    }
}
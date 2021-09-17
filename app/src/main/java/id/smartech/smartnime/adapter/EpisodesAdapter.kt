package id.smartech.smartnime.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import id.smartech.smartnime.R
import id.smartech.smartnime.databinding.ItemListEpisodesBinding
import id.smartech.smartnime.ui.detail.anime.episodes.model.EpisodesModel

class EpisodesAdapter(private val items : ArrayList<EpisodesModel>): RecyclerView.Adapter<EpisodesAdapter.EpisodesHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun addList(list: List<EpisodesModel>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    class EpisodesHolder(val binding: ItemListEpisodesBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodesHolder {
        return EpisodesHolder(
            DataBindingUtil.inflate(
                (LayoutInflater.from(parent.context)),
                R.layout.item_list_episodes,
                parent, false
            )
        )
    }

    override fun getItemCount(): Int  = items.size

    override fun onBindViewHolder(holder: EpisodesHolder, position: Int) {
        val item = items[position]

        holder.binding.episodesId.text = "#${item.episodesId}"
        holder.binding.title.text = item.title
        holder.binding.titleRomanji.text = item.titleRomanji
        holder.binding.aired.text = if(item.aired.isNullOrEmpty()) "N/A" else item.aired.split("T")[0]

        holder.binding.detail.setOnClickListener {
            onItemClickCallback.onClickItem(item)
        }
    }

    interface OnItemClickCallback {
        fun onClickItem(data: EpisodesModel)
    }
}
package id.smartech.smartnime.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.smartech.smartnime.R
import id.smartech.smartnime.databinding.ItemListAnimeBinding
import id.smartech.smartnime.databinding.ItemListCardviewAnimeBinding
import id.smartech.smartnime.model.TopAnimeModel
import id.smartech.smartnime.ui.nav.schedule.model.DayModel

class ScheduleAnimeAdapter(private val items: ArrayList<DayModel>): RecyclerView.Adapter<ScheduleAnimeAdapter.ScheduleAnimeHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun addList(list: List<DayModel>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }


    class ScheduleAnimeHolder(val binding: ItemListCardviewAnimeBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleAnimeHolder {
        return ScheduleAnimeHolder(
                DataBindingUtil.inflate(
                        (LayoutInflater.from(parent.context)),
                        R.layout.item_list_cardview_anime,
                        parent, false
                )
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ScheduleAnimeHolder, position: Int) {
        val item = items[position]

        val genres = item.genres.map {
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

        holder.binding.genre.text = genre
        holder.binding.title.text = item.title

        Glide.with(holder.itemView)
                .load(item.imageUrl)
                .placeholder(R.drawable.white)
                .error(R.drawable.white)
                .into(holder.binding.image)
    }

    interface OnItemClickCallback {
        fun onClickItem(data: DayModel)
    }
}
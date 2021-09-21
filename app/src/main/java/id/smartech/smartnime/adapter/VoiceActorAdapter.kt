package id.smartech.smartnime.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.smartech.smartnime.R
import id.smartech.smartnime.databinding.ItemListVoiceActorBinding
import id.smartech.smartnime.ui.detail.character.model.AnimeographyModel
import id.smartech.smartnime.ui.detail.character.model.VoiceActorModel

class VoiceActorAdapter(private val items: ArrayList<VoiceActorModel>): RecyclerView.Adapter<VoiceActorAdapter.VoiceActorHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun addList(list: List<VoiceActorModel>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    class VoiceActorHolder(val binding: ItemListVoiceActorBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VoiceActorHolder {
        return VoiceActorHolder(
                DataBindingUtil.inflate(
                        (LayoutInflater.from(parent.context)),
                        R.layout.item_list_voice_actor,
                        parent, false
                )
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: VoiceActorHolder, position: Int) {
        val item = items[position]

        holder.binding.name.text = item.name
        holder.binding.nameLanguage.text = "(${item.language})"

        Glide.with(holder.itemView)
                .load(item.imageUrl)
                .placeholder(R.drawable.white)
                .error(R.drawable.white)
                .into(holder.binding.imageChar)

        holder.itemView.setOnClickListener {
            onItemClickCallback.onClickItem(item)
        }
    }

    interface OnItemClickCallback {
        fun onClickItem(data: VoiceActorModel)
    }
}
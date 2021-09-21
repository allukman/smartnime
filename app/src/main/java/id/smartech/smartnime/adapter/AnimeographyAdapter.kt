package id.smartech.smartnime.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.smartech.smartnime.R
import id.smartech.smartnime.databinding.ItemListCharacterSupportBinding
import id.smartech.smartnime.model.TopAnimeModel
import id.smartech.smartnime.ui.detail.character.model.AnimeographyModel

class AnimeographyAdapter(private val items: ArrayList<AnimeographyModel>): RecyclerView.Adapter<AnimeographyAdapter.CharacterSupportHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun addList(list: List<AnimeographyModel>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    class CharacterSupportHolder(val binding: ItemListCharacterSupportBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterSupportHolder {
        return CharacterSupportHolder(
                DataBindingUtil.inflate(
                        (LayoutInflater.from(parent.context)),
                        R.layout.item_list_character_support,
                        parent, false
                )
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: CharacterSupportHolder, position: Int) {
        val item = items[position]

        holder.binding.name.text = item.name

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
        fun onClickItem(data: AnimeographyModel)
    }

}
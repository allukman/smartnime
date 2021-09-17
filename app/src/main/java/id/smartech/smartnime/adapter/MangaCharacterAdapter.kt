package id.smartech.smartnime.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.smartech.smartnime.R
import id.smartech.smartnime.databinding.ItemListCharacterMangaBinding
import id.smartech.smartnime.ui.detail.manga.characters.MangaCharactersModel

class MangaCharacterAdapter(private val items: ArrayList<MangaCharactersModel>): RecyclerView.Adapter<MangaCharacterAdapter.MangaCharacterHolder>() {

    fun addList(list: List<MangaCharactersModel>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    class MangaCharacterHolder(val binding: ItemListCharacterMangaBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MangaCharacterHolder {
        return MangaCharacterHolder(
                DataBindingUtil.inflate(
                        (LayoutInflater.from(parent.context)),
                        R.layout.item_list_character_manga,
                        parent, false
                )
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: MangaCharacterHolder, position: Int) {
        val item = items[position]

        holder.binding.nameChar.text = item.name

        Glide.with(holder.itemView)
                .load(item.imageUrl)
                .placeholder(R.drawable.white)
                .error(R.drawable.white)
                .into(holder.binding.imageChar)
    }
}
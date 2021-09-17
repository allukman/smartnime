package id.smartech.smartnime.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.smartech.smartnime.R
import id.smartech.smartnime.databinding.ItemListCharactersBinding
import id.smartech.smartnime.ui.detail.anime.characters.AnimeCharacterModel

class AnimeCharacterAdapter(private val items: ArrayList<AnimeCharacterModel>): RecyclerView.Adapter<AnimeCharacterAdapter.CharacterHolder>() {

    fun addList(list: List<AnimeCharacterModel>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    class CharacterHolder(val binding: ItemListCharactersBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterHolder {
        return CharacterHolder(
                DataBindingUtil.inflate(
                        (LayoutInflater.from(parent.context)),
                        R.layout.item_list_characters,
                        parent, false
                )
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: CharacterHolder, position: Int) {
        val item = items[position]

        holder.binding.nameChar.text = item.name
        if(item.voiceActors.size > 0) {
            holder.binding.nameVoiceActor.visibility = View.VISIBLE
            holder.binding.cardviewVoiceActor.visibility = View.VISIBLE

            holder.binding.nameVoiceActor.text = item.voiceActors[0].name

            Glide.with(holder.itemView)
                    .load(item.voiceActors[0].imageUrl?.replace("/r/42x62", ""))
                    .placeholder(R.drawable.white)
                    .error(R.drawable.white)
                    .into(holder.binding.imageVoiceActor)
        } else {
            holder.binding.nameVoiceActor.visibility = View.GONE
            holder.binding.cardviewVoiceActor.visibility = View.GONE
        }

        Glide.with(holder.itemView)
                .load(item.imageUrl)
                .placeholder(R.drawable.white)
                .error(R.drawable.white)
                .into(holder.binding.imageChar)

    }
}
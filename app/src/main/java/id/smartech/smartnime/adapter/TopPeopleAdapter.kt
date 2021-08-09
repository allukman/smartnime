package id.smartech.smartnime.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.smartech.smartnime.R
import id.smartech.smartnime.databinding.ItemListPeopleBinding
import id.smartech.smartnime.ui.nav.people.model.TopPeopleModel

class TopPeopleAdapter(private val items: ArrayList<TopPeopleModel>): RecyclerView.Adapter<TopPeopleAdapter.TopPeopleHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun addList(list: List<TopPeopleModel>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    class TopPeopleHolder(val binding: ItemListPeopleBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopPeopleHolder {
        return TopPeopleHolder(
            DataBindingUtil.inflate(
                (LayoutInflater.from(parent.context)),
                R.layout.item_list_people,
                parent, false
            )
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: TopPeopleHolder, position: Int) {
        val item = items[position]

        holder.binding.name.text = item.title
        holder.binding.kanji.text = item.nameKanji
        holder.binding.favorites.text = item.favorites.toString()

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
        fun onClickItem(data: TopPeopleModel)
    }
}
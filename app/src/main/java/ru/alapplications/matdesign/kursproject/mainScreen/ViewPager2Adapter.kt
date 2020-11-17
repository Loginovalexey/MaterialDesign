package ru.alapplications.matdesign.kursproject.mainScreen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.item_recyclerview_layout.view.*


import ru.alapplications.matdesign.kursproject.R


class ViewPager2Adapter(private val itemsList: List<Item>) :
    RecyclerView.Adapter<ViewPager2Adapter.CardViewHolder?>() {
    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): CardViewHolder {
        val v = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_pager_layout, viewGroup, false)
        return CardViewHolder(v)
    }


    override fun onBindViewHolder(
        holder: CardViewHolder,
        position: Int
    ) {
        holder.picture.setImageDrawable(holder.itemView.resources.getDrawable(itemsList.get(position).imageId))
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    fun update() {
        notifyDataSetChanged()
    }

    class CardViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var picture = view.picture
    }


}



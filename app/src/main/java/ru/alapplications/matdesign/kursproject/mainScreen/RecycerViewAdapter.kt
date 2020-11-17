package ru.alapplications.matdesign.kursproject.mainScreen


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_recyclerview_layout.view.*
import ru.alapplications.matdesign.kursproject.R


class RecyclerViewAdapter(private val itemsList: List<Item>) :
    RecyclerView.Adapter<RecyclerViewAdapter.CardViewHolder?>() {
    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): CardViewHolder {
        val v = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_recyclerview_layout, viewGroup, false)
        return CardViewHolder(v)
    }

    override fun onBindViewHolder(
        holder: CardViewHolder,
        position: Int
    ) {
        Picasso.get()
            .load(itemsList[position].imageId)
            .tag("imageView").into(holder.picture)
        holder.text.text = itemsList[position].title
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    class CardViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var picture: ImageView = view.picture
        var text: TextView = view.text
    }


}




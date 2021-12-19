package com.sapar.breakingbadapplication.Home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sapar.breakingbadapplication.CharacterItem
import com.sapar.breakingbadapplication.R
import com.squareup.picasso.Picasso

class Adaptery(
    private val characters: List<CharacterItem>,
    private val mContext: Context,
    private val mOnItemNoteListener: OnItemNoteListener
) : RecyclerView.Adapter<Adaptery.MyViewHolder?>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View
        val layoutInflater: LayoutInflater = LayoutInflater.from(mContext)
        view = layoutInflater.inflate(R.layout.character_record_item, parent, false)
        return MyViewHolder(view, mOnItemNoteListener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.name.text = (characters[position].name)
        holder.birthday.text = (characters[position].birthday)
        holder.status.text = (characters[position].status)
        holder.category.text = (characters[position].category)
//        System.out.println(characters[position].getImg())
        Picasso.with(mContext)
            .load(characters[position].img)
            .placeholder(R.drawable.back)
            .fit()
            .into(holder.image)

    }

    override fun getItemCount(): Int {
        return characters.size
    }

    class MyViewHolder(itemView: View, noteListener: OnItemNoteListener) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var name: TextView
        var birthday: TextView
        var status: TextView
        var category: TextView
        var image: ImageView
        var noteListener: OnItemNoteListener
        override fun onClick(v: View) {
            noteListener.onNoteClick(getAdapterPosition())
        }

        init {
            name = itemView.findViewById<TextView>(R.id.textViewTitle)
            birthday = itemView.findViewById<TextView>(R.id.textViewBirthDate)
            status = itemView.findViewById<TextView>(R.id.textViewStatus)
            category = itemView.findViewById<TextView>(R.id.textViewCategory)
            image = itemView.findViewById(R.id.imageViewUser)
            this.noteListener = noteListener
            itemView.setOnClickListener(this)
        }
    }

    interface OnItemNoteListener {
        fun onNoteClick(position: Int)
    }
}
package com.example.recyclerviewdroplayout

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class HITerAdapter() : RecyclerView.Adapter<HITerAdapter.ViewHolder>() {

    private val members = mutableListOf<HITer>()

    fun submitData(data : List<HITer>) {
        members.clear()
        members.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.item_user_rv,
                parent,
                false
            )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val hiter = members[position]
        holder.name.text = hiter.name
        Glide.with(holder.itemView)
            .load(hiter.avatar)
            .circleCrop()
            .into(holder.image)
    }

    override fun getItemCount(): Int = members.size

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val name : TextView = itemView.findViewById(R.id.uname)
        val image : ImageView = itemView.findViewById(R.id.uimg)
    }

}
package com.example.recyclerviewdroplayout

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.core.view.isVisible

class ClassroomAdapter (
    private val classrooms: MutableList<Classroom>
) :  RecyclerView.Adapter<ClassroomAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.item_classroom_rv,
                parent,
                false
            )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val classroom = classrooms[position]
        holder.csName.text = classroom.name

        holder.itemView.setOnClickListener {
            if (!holder.rvChild.isVisible) {
                holder.rvChild.visibility = View.VISIBLE
                holder.rvChild.translationY = -100f
                holder.rvChild.alpha = 0f

                holder.rvChild.animate()
                    .translationY(0f)
                    .alpha(1f)
                    .setDuration(100)
                    .start()

                holder.arrow.animate()
                    .rotation(180f)
                    .setDuration(100)
                    .start()
            } else {
                holder.rvChild.visibility = View.GONE

                holder.arrow.animate()
                    .rotation(0f)
                    .setDuration(100)
                    .start()
            }
        }

        holder.rvChild.adapter = holder.adapter
        holder.rvChild.layoutManager = LinearLayoutManager(holder.itemView.context)
        holder.adapter.submitData(classroom.members)
    }

    override fun getItemCount(): Int = classrooms.size

    fun submitData(data: List<Classroom>) {
        classrooms.clear()
        classrooms.addAll(data)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val csName: TextView = itemView.findViewById(R.id.csName)
        val arrow: ImageView = itemView.findViewById(R.id.arrow)
        val rvChild: RecyclerView = itemView.findViewById(R.id.childList)
        val adapter: HITerAdapter = HITerAdapter()
    }
}
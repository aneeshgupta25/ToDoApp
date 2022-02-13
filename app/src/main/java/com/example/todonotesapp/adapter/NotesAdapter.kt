package com.example.todonotesapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todonotesapp.R
import com.example.todonotesapp.clickListeners.SetClickListener
import com.example.todonotesapp.db.Notes

class NotesAdapter(private val list: List<Notes>, val setClickListener : SetClickListener) : RecyclerView.Adapter<NotesAdapter.viewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesAdapter.viewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_item_layout, parent, false)
        return viewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: NotesAdapter.viewHolder, position: Int) {
        val note = list[position]
        holder.itemTitle.text = note.title.toString()
        holder.itemDescription.text = note.description.toString()
        holder.itemView.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                setClickListener.onClick(note)
            }
        })
        holder.checkBoxMarkStatus.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener{
            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                note.isTaskCompleted = isChecked
                setClickListener.onUpdate(note)
            }

        })
    }

    inner class viewHolder(itemview : View) : RecyclerView.ViewHolder(itemview){
        val itemTitle : TextView = itemView.findViewById(R.id.itemTitle)
        val itemDescription : TextView = itemView.findViewById(R.id.itemDescription)
        val checkBoxMarkStatus : CheckBox = itemview.findViewById(R.id.checkBoxMarkStatus)
    }
}
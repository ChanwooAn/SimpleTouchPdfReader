package com.acw.simpletouchpdfreader

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView

class PdfRecyclerViewAdapter(private val onClick:(uri:Uri)->Unit, private val pdfList:List<PdfItem>) : RecyclerView.Adapter<PdfItemHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PdfItemHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.file_item,parent,false)
        return PdfItemHolder(view)
    }

    override fun getItemCount(): Int {
        return pdfList.size
    }

    override fun onBindViewHolder(holder: PdfItemHolder, position: Int) {
        val currentPdf = pdfList[position]
        holder.textView.text = currentPdf.name

        holder.itemView.setOnClickListener{uri->
            onClick(currentPdf.uri)}
        }
}

class PdfItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val textView: TextView =itemView.findViewById(R.id.item_text)
}
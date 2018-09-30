package br.ufpe.cin.if710.rss

import android.support.v7.widget.RecyclerView
import android.widget.LinearLayout
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView

class MyAdapter(private val data: List<ItemRSS>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>(){
    //referenciando
    class MyViewHolder(val v: LinearLayout) : RecyclerView.ViewHolder(v)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter.MyViewHolder {
        //criando
        val v = LayoutInflater.from(parent.context).inflate(R.layout.itemlista, parent, false) as LinearLayout
        return MyViewHolder(v)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var textView: TextView = holder.v.getChildAt(0) as TextView
        textView.text = data[position].title
    }

    // tamanho do dataset
    override fun getItemCount() = data.size

}
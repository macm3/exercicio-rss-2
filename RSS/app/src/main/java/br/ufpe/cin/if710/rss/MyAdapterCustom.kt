package br.ufpe.cin.if710.rss

import android.content.Intent
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.support.v4.content.ContextCompat.startActivity
import android.widget.LinearLayout
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import android.content.Context

class MyAdapterCustom(private val data: List<ItemRSS>) : RecyclerView.Adapter<MyAdapterCustom.MyViewHolder>(){
    //referenciando
    class MyViewHolder(val v: LinearLayout) : RecyclerView.ViewHolder(v)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapterCustom.MyViewHolder {
        //criando
        val v = LayoutInflater.from(parent.context).inflate(R.layout.itemlista, parent, false) as LinearLayout
        return MyViewHolder(v)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var textTitle: TextView = holder.v.getChildAt(0) as TextView
        var textData: TextView = holder.v.getChildAt(1) as TextView
        textTitle.text = data[position].title
        textData.text = data[position].pubDate

        textTitle.setOnClickListener{
            open(data[position].link, holder.v.context)
        }
    }

    // tamanho do dataset
    override fun getItemCount() = data.size

    //abrir a pagina
    fun open(urls:String, mContext: Context){
        val page = Uri.parse(urls)
        val i = Intent(Intent.ACTION_VIEW, page)
        mContext.startActivity(i)
    }

}


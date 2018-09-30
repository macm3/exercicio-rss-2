package br.ufpe.cin.if710.rss

import android.app.Activity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.LinearLayout
import android.widget.TextView
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

import br.ufpe.cin.if710.rss.ParserRSS.parse


class MainActivity : Activity() {

    //private val RSS_FEED = "http://leopoldomt.com/if1001/g1brasil.xml"
    //private var conteudoRSS: TextView? = null
    //private var RSS_FEED = "http://pox.globo.com/rss/g1/brasil/"

    private var RSS_FEED: String? = null

    private var conteudoRSS: RecyclerView? = null

    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //pega do arquivo de strings
        conteudoRSS = findViewById(R.id.conteudoRSS)
        RSS_FEED = getString(R.string.rssfeed)

        linearLayoutManager = LinearLayoutManager(this)
        //colocando tipo layout no recycler
        conteudoRSS?.layoutManager = linearLayoutManager
    }

    override fun onStart() {
        super.onStart()
        try {
            //Esse código dá pau, por fazer operação de rede na thread principal...
            //val feedXML = getRssFeed(RSS_FEED)
            //conteudoRSS!!.text = feedXML

            doAsync {
                val feedXML = parse(getRssFeed(RSS_FEED!!))
                //val adapter = MyAdapter(feedXML)
                val adapter = MyAdapterCustom(feedXML)
                uiThread {
                    //conteudoRSS!!.text = feedXML.toString()
                    conteudoRSS!!.adapter = adapter
                }
            }

        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    @Throws(IOException::class)
    private fun getRssFeed(feed:String):String {
        var in_: InputStream? = null
        var rssFeed = ""
        try{
            val url= URL(feed)
            val conn: HttpURLConnection = url.openConnection() as HttpURLConnection
            in_ = conn.getInputStream()
            val out = ByteArrayOutputStream()
            val buffer = ByteArray(1024)
            var count:Int = in_.read(buffer)
            while (count != -1) {
                out.write(buffer, 0, count)
                count = in_.read(buffer)
            }
            val response = out.toByteArray()
            rssFeed = String(response, charset("UTF-8"))
        }finally {
            if(in_ != null) {
                in_.close()
            }
        }
        return rssFeed
    }


}

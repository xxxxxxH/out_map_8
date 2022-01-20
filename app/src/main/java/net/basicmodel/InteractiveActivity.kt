package net.basicmodel

import android.os.Bundle
import android.text.TextUtils
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.TypeReference
import com.drake.net.Get
import com.drake.net.utils.scopeNetLife
import com.jessewu.library.SuperAdapter
import com.jessewu.library.view.ViewHolder
import com.shehuan.niv.NiceImageView
import com.squareup.picasso.Picasso
import entity.DataEntity
import kotlinx.android.synthetic.main.activity_interactive.*

class InteractiveActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interactive)
        scopeNetLife {
            val response =
                Get<String>("https://www.google.com/streetview/feed/gallery/data.json").await()
            val data = ArrayList<DataEntity>()
            val map: Map<String, DataEntity> =
                JSON.parseObject(
                    response,
                    object : TypeReference<Map<String, DataEntity>>() {})
            val m: Set<Map.Entry<String, DataEntity>> = map.entries
            val it: Iterator<Map.Entry<String, DataEntity>> = m.iterator()
            do {
                val en: Map.Entry<String, DataEntity> = it.next()
                val json = JSON.toJSON(en.value)
                val entity: DataEntity =
                    JSON.parseObject(json.toString(), DataEntity::class.java)
                entity.key = en.key
                if (TextUtils.isEmpty(entity.panoid)) {
                    continue
                } else {
                    if (entity.panoid == "LiAWseC5n46JieDt9Dkevw") {
                        continue
                    }
                }
                if (entity.fife) {
                    entity.imageUrl =
                        "https://lh4.googleusercontent.com/" + entity.panoid + "/w400-h300-fo90-ya0-pi0/"
                    continue
                } else {
                    entity.imageUrl =
                        "https://geo0.ggpht.com/cbk?output=thumbnail&thumb=2&panoid=" + entity.panoid
                }
                data.add(entity)
            } while (it.hasNext())

            val adapter: SuperAdapter<DataEntity> =
                object : SuperAdapter<DataEntity>(R.layout.item_inter) {
                    override fun bindView(itemView: ViewHolder, data: DataEntity, position: Int) {
                        val iv = itemView.getView<NiceImageView>(R.id.image)
                        Picasso.get().load(data.imageUrl).into(iv)
                        val tv = itemView.getView<TextView>(R.id.itemTv)
                        tv.text = data.title
                    }
                }
            adapter.addData(data)
            recycler.layoutManager = LinearLayoutManager(this@InteractiveActivity)
            recycler.adapter = adapter
            adapter.setOnItemClickListener { i, dataEntity ->
                
            }
        }
    }
}
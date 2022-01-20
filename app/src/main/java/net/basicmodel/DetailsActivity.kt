package net.basicmodel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.TypeReference
import com.drake.net.Get
import com.drake.net.utils.scopeNetLife
import com.jessewu.library.SuperAdapter
import com.jessewu.library.view.ViewHolder
import com.shehuan.niv.NiceImageView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_details.*
import net.entity.DataEntity

class DetailsActivity : AppCompatActivity() {
    var bigEntity: DataEntity?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        val i = intent
        bigEntity = i.getSerializableExtra("data") as DataEntity
        Picasso.get().load(bigEntity!!.imageUrl).into(detailsIv)
        val key = bigEntity!!.key
        val url = "https://www.google.com/streetview/feed/gallery/collection/$key.json"
        scopeNetLife{
            val result = Get<String>(url).await()
            val data = ArrayList<DataEntity>()
            val map: Map<String, DataEntity> =
                JSON.parseObject(
                    result.toString(),
                    object : TypeReference<Map<String, DataEntity>>() {})
            val m: Set<Map.Entry<String, DataEntity>> = map.entries
            val it: Iterator<Map.Entry<String, DataEntity>> = m.iterator()
            do {
                val en: Map.Entry<String, DataEntity> = it.next()
                val json = JSON.toJSON(en.value)
                val entity1: DataEntity =
                    JSON.parseObject(json.toString(), DataEntity::class.java)
                entity1.pannoId = entity1.panoid
                if (bigEntity!!.fife) {
                    entity1.imageUrl =
                        "https://lh4.googleusercontent.com/" + entity1.pannoId + "/w400-h300-fo90-ya0-pi0/"
                } else {
                    entity1.imageUrl =
                        "https://geo0.ggpht.com/cbk?output=thumbnail&thumb=2&panoid=" + entity1.panoid
                }
                data.add(entity1)
            } while (it.hasNext())

            val adapter: SuperAdapter<DataEntity> =
                object : SuperAdapter<DataEntity>(R.layout.item_details) {
                    override fun bindView(itemView: ViewHolder, data: DataEntity, position: Int) {
                        val iv = itemView.getView<NiceImageView>(R.id.image)
                        Picasso.get().load(data.imageUrl).into(iv)
                    }
                }
            adapter.addData(data)
            recycler.layoutManager = GridLayoutManager(this@DetailsActivity, 2)
            recycler.adapter = adapter
            adapter.setOnItemClickListener { _, dataEntity ->
                val inient = Intent(this@DetailsActivity,PreviewActivity::class.java)
                inient.putExtra("url",dataEntity.imageUrl)
                startActivity(inient)
            }
        }
    }
}
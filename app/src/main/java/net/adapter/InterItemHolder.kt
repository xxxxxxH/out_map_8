package net.adapter

import android.widget.TextView
import com.shehuan.niv.NiceImageView
import com.zuzi.adapter.FastBaseHolder
import com.zuzi.adapter.annotation.FastAttribute
import com.zuzi.adapter.annotation.RecyclerItemLayoutId
import net.basicmodel.R

@RecyclerItemLayoutId(R.layout.item_inter)
class InterItemHolder :FastBaseHolder(){
    @FastAttribute(bindViewId = R.id.itemIv)
    var itemIv:NiceImageView?=null

    @FastAttribute(bindViewId = R.id.itemTv)
    var itemTv:TextView?=null
}
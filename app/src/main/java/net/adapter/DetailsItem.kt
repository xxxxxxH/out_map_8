package net.adapter

import com.zuzi.adapter.FastBaseHolder
import com.zuzi.adapter.annotation.FastAttribute
import com.zuzi.adapter.annotation.RecyclerItemLayoutId
import de.hdodenhof.circleimageview.CircleImageView
import net.basicmodel.R

@RecyclerItemLayoutId(R.layout.item_details)
class DetailsItem:FastBaseHolder() {
    @FastAttribute(bindViewId = R.id.image)
    var image: CircleImageView?=null
}
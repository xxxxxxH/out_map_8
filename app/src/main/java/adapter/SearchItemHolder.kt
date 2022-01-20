package adapter

import com.zuzi.adapter.FastBaseHolder
import com.zuzi.adapter.annotation.FastAttribute
import com.zuzi.adapter.annotation.RecyclerItemLayoutId
import net.basicmodel.R

@RecyclerItemLayoutId(R.layout.item_search)
class SearchItemHolder : FastBaseHolder() {
    @FastAttribute(bindViewId = R.id.itemTv)
    var title: String? = null
}
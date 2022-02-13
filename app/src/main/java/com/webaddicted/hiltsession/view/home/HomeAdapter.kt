package com.webaddicted.hiltsession.view.home

import androidx.databinding.ViewDataBinding
import com.webaddicted.hiltsession.R
import com.webaddicted.hiltsession.data.model.img.Photo
import com.webaddicted.hiltsession.databinding.RowHomeItemBinding
import com.webaddicted.hiltsession.utils.common.GlobalUtils
import com.webaddicted.hiltsession.utils.constant.ApiConstant
import com.webaddicted.hiltsession.view.base.BaseAdapter

class HomeAdapter(private val itemList: ArrayList<Photo>, val clickListener: (Photo) -> Unit) :
    BaseAdapter(R.layout.row_home_item) {
    private lateinit var mBinding: RowHomeItemBinding

    override fun getListSize() = itemList.size

    override fun onBindTo(binding: ViewDataBinding, position: Int) {
        mBinding = binding as RowHomeItemBinding
        val item = itemList[position]
        mBinding.prodNameTv.text = item.title
//        mBinding.txtDesc.text = item.server
        val url = ApiConstant.IMG_URL + "${item.server}/${item.id}_${item.secret}.jpg"
        GlobalUtils.showImage(mBinding.imgPic, url)
        mBinding.root.setOnClickListener {
            clickListener(item)
        }


    }
}
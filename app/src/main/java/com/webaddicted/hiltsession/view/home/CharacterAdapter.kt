package com.webaddicted.hiltsession.view.home

import androidx.databinding.ViewDataBinding
import com.webaddicted.hiltsession.R
import com.webaddicted.hiltsession.data.model.character.Result
import com.webaddicted.hiltsession.databinding.RowHomeItemBinding
import com.webaddicted.hiltsession.utils.common.GlobalUtils
import com.webaddicted.hiltsession.view.base.BaseAdapter
import java.util.*
import kotlin.collections.ArrayList

class CharacterAdapter(private val itemList: ArrayList<Result>?) :
    BaseAdapter(R.layout.row_home_item) {
    private lateinit var mBinding: RowHomeItemBinding
    var searchList: ArrayList<Result>? = null
    override fun getListSize() = itemList?.size

    init {
        searchList = ArrayList()
        itemList?.let { searchList?.addAll(it) }
    }

    override fun onBindTo(binding: ViewDataBinding, position: Int) {
        mBinding = binding as RowHomeItemBinding
        val item = itemList?.get(position)
        mBinding.prodNameTv.text = item?.name
//        mBinding.txtDesc.text = item.server
        item?.image?.let { GlobalUtils.showImage(mBinding.imgPic, it) }
    }

    fun filter(textStr: String) {
        val charStr :String= textStr.lowercase(Locale.getDefault())
        itemList?.clear()
        if (charStr == null || charStr == "") {
            searchList?.let { itemList?.addAll(it) }
        } else {
            for (item in searchList!!) {
                if (item.name.lowercase().contains(charStr)) {
                    itemList?.add(item)
                }
            }
        }
        notifyDataSetChanged()
    }
}
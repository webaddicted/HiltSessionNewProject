package com.webaddicted.hiltsession.view.base

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter(private val layoutId: Int) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    protected lateinit var mContext: Context
    protected abstract fun getListSize(): Int?

    //    protected abstract fun getLayoutId(viewType: Int): Int
    protected abstract fun onBindTo(binding: ViewDataBinding, position: Int)

    companion object {
        val TAG = BaseAdapter::class.qualifiedName
    }

    override fun getItemCount(): Int {
        return getListSize() ?: 0
    }

    @NonNull
    override fun onCreateViewHolder(
        @NonNull parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        mContext = parent.context
        val rowBindingUtil: ViewDataBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            layoutId,
            parent,
            false
        )
        return ViewHolder(rowBindingUtil)
    }

    override fun onBindViewHolder(@NonNull holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder)
            holder.binding(position)
    }


    /**
     * view holder
     */
    inner class ViewHolder(private val mBinding: ViewDataBinding) :
        RecyclerView.ViewHolder(mBinding.root) {
        /**
         * @param position current item position
         */
        fun binding(position: Int) {
//            sometime adapter position  is -1 that case handle by position
            if (adapterPosition >= 0) onBindTo(mBinding, adapterPosition)
            else onBindTo(mBinding, position)
        }
    }

    protected open fun onClickListener(mBinding: ViewDataBinding, view: View?, position: Int) {
        view?.setOnClickListener { getClickEvent(mBinding, view, position) }
    }

    protected open fun getClickEvent(mBinding: ViewDataBinding, view: View?, position: Int) {

    }

}
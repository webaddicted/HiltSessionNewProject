package com.webaddicted.hiltsession.view.home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.webaddicted.hiltsession.R
import com.webaddicted.hiltsession.data.model.character.CharacterRespo
import com.webaddicted.hiltsession.data.model.character.Result
import com.webaddicted.hiltsession.databinding.FrmHomeBinding
import com.webaddicted.hiltsession.utils.apiutils.ApiResponse
import com.webaddicted.hiltsession.view.base.BaseFragment
import com.webaddicted.hiltsession.view.base.ScrollListener
import com.webaddicted.hiltsession.viewmodel.HomeViewModel

class CharacterFragment : BaseFragment(R.layout.frm_home), TextWatcher {
    private var photoList: ArrayList<Result>? = null
    private var characterAdapter: CharacterAdapter? = null
    private lateinit var mBinding: FrmHomeBinding
    val homeViewModel: HomeViewModel by viewModels()

    companion object {
        val TAG = CharacterFragment::class.qualifiedName
        fun getInstance(bundle: Bundle): CharacterFragment {
            val fragment = CharacterFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onBindTo(binding: ViewDataBinding) {
        mBinding = binding as FrmHomeBinding
        init()
        getImages()
    }

    private fun init() {
        mBinding.flaotClearData.setOnClickListener(this)
        mBinding.edtSearch.addTextChangedListener(this)
    }

    override fun onClick(v: View) {
        super.onClick(v)
        when (v.id) {
            R.id.flaot_clear_data -> {
                com.webaddicted.hiltsession.Test.main()
//                WelcomeActivity.newClearLogin(mActivity)
            }
        }
    }

    private fun setAdapter() {
        characterAdapter = CharacterAdapter(photoList)
        mBinding.itemRv.run {
            layoutManager = LinearLayoutManager(mActivity)
            adapter = characterAdapter
        }
        mBinding.itemRv.addOnScrollListener(object :
            ScrollListener(mBinding.itemRv.layoutManager as LinearLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                getImages()
            }
        })
    }

    private fun getImages() {
        homeViewModel.getCharacters()
        homeViewModel.getCharacterRespo.observe(this) {
            handleApiRespo(
                it,
                mBinding.loadingTyreIv
            ) { isFailure: Boolean, result: ApiResponse<CharacterRespo>? ->
                if (isFailure) getImages()
                else {
                    if (photoList == null) {
                        photoList = result?.data?.results as ArrayList<Result>?
                    } else {
                        (result?.data?.results as ArrayList<Result>?)?.let { it1 ->
                            photoList?.addAll(
                                it1
                            )
                        }
                    }
                    setAdapter()
                }
            }
        }
    }

    private fun navigateScreen(tag: String?) {
        val frm: Fragment? = null
        when (tag) {
//                  UserTypeFragment.TAG -> frm = UserTypeFragment.getInstance(Bundle())
        }
        if (frm != null) navigateFragment(R.id.container, frm, false)
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(charact: CharSequence?, p1: Int, p2: Int, p3: Int) {
        characterAdapter?.filter(charact.toString())
    }

    override fun afterTextChanged(p0: Editable?) {
    }
}
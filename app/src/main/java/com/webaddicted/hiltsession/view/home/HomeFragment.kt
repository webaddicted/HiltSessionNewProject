package com.webaddicted.hiltsession.view.home

import android.os.Bundle
import android.util.Log
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.webaddicted.hiltsession.R
import com.webaddicted.hiltsession.data.model.img.Photo
import com.webaddicted.hiltsession.data.model.img.SearchReq
import com.webaddicted.hiltsession.data.model.img.SearchRespo
import com.webaddicted.hiltsession.databinding.FrmHomeBinding
import com.webaddicted.hiltsession.utils.apiutils.ApiResponse
import com.webaddicted.hiltsession.view.base.BaseFragment
import com.webaddicted.hiltsession.viewmodel.HomeViewModel

class HomeFragment : BaseFragment(R.layout.frm_home) {
    private var photoList: ArrayList<Photo>? = null
    private var homeAdapter: HomeAdapter? = null
    private lateinit var mBinding: FrmHomeBinding
    val homeViewModel: HomeViewModel by viewModels()
//    val homeVM: HomeViewModel by activityViewModels()

    companion object {
        val TAG = HomeFragment::class.qualifiedName
        fun getInstance(bundle: Bundle): HomeFragment {
            val fragment = HomeFragment()
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
    }

    private fun setAdapter() {
        homeAdapter = HomeAdapter(photoList!!) { menuItem: Photo -> onItemClicked(menuItem) }
        mBinding.itemRv.run {
            layoutManager = LinearLayoutManager(mActivity)
            adapter = homeAdapter
        }
    }

    private fun onItemClicked(menuItem: Photo) {

    }

    private fun getImages() {
        val searchReq = SearchReq().apply {
            text = "Love"
            page = 1
        }
        homeViewModel.getImages(searchReq)
        homeViewModel.getImageRespo.observe(this, {
            handleApiRespo(
                it,
                mBinding.loadingTyreIv
            ) { isFailure: Boolean, result: ApiResponse<SearchRespo>? ->
                if (isFailure) getImages()
                else {
                    photoList = result?.data?.photos?.photo as ArrayList<Photo>?
                    setAdapter()
//                    homeViewModel.setPrefUserInfo(it.data?.respDetails)
                    Log.d(
                        TAG,
                        "User Info : ${it.data?.photos?.perpage}"
                    )
                }
            }
        })
    }

    private fun navigateScreen(tag: String?) {
        val frm: Fragment? = null
        when (tag) {
//                  UserTypeFragment.TAG -> frm = UserTypeFragment.getInstance(Bundle())
        }
        if (frm != null) navigateFragment(R.id.container, frm, false)
    }
}
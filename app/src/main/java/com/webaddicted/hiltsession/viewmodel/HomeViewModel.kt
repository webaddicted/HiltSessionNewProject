package com.webaddicted.hiltsession.viewmodel

import androidx.lifecycle.MutableLiveData
import com.webaddicted.hiltsession.data.model.character.CharacterRespo
import com.webaddicted.hiltsession.data.model.img.SearchReq
import com.webaddicted.hiltsession.data.model.img.SearchRespo
import com.webaddicted.hiltsession.data.respo.HomeRepository
import com.webaddicted.hiltsession.utils.apiutils.ApiResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repo: HomeRepository) :
    BaseViewModel() {
    var getImageRespo = MutableLiveData<ApiResponse<SearchRespo>>()
    var getCharacterRespo = MutableLiveData<ApiResponse<CharacterRespo>>()

    fun getImages(req: SearchReq) {
        getImageRespo = MutableLiveData<ApiResponse<SearchRespo>>()
        repo.getImages(req, getImageRespo)
    }

    fun getCharacters() {
        getCharacterRespo = MutableLiveData<ApiResponse<CharacterRespo>>()
        repo.getCharacterList(getCharacterRespo)
    }

}
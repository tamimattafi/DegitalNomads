package com.tamimattafi.degitalnomads.presentation.home

import com.tamimattafi.degitalnomads.mvp.MvpRecyclerContract

interface MvpHomeContract {

    interface MvpHomePresenter : MvpRecyclerContract.MvpPresenter<MvpNewsHolder>
    interface MvpHomeView : MvpRecyclerContract.MvpView<MvpNewsHolder>

    interface MvpNewsHolder : MvpRecyclerContract.MvpHolder {
        fun setAuthor(author : String?)
        fun setTitle(title : String?)
        fun setDescription(description : String?)
        fun setImage(urlToImage : String?)
        fun setPublishDate(date : String?)
    }
}
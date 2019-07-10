package com.tamimattafi.degitalnomads.presentation.home

import com.tamimattafi.degitalnomads.model.Article
import com.tamimattafi.degitalnomads.mvp.MvpRecyclerPresenter
import com.tamimattafi.degitalnomads.repository.global.MvpListRepository

class MvpHomePresenter (view : MvpHomeContract.MvpHomeView, repository : MvpListRepository<Article>) :
    MvpRecyclerPresenter<Article, MvpHomeContract.MvpNewsHolder>(view, repository),
    MvpHomeContract.MvpHomePresenter {

    override fun requestRepositoryData(paginationSize: Int) {
            repository.getNextPage(paginationSize)
    }

    override fun bindRecyclerHolder(holder: MvpHomeContract.MvpNewsHolder) {
        with(holder) {
            if (data.size > listPosition) {
                data[listPosition].apply {
                    setTitle(title)
                    setAuthor(author)
                    setImage(urlToImage)
                    setDescription(description)
                    setPublishDate(publishedAt)
                }
            }
        }
    }

}
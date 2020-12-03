package com.example.newsmate

//Basic model for adapter to access/ assign article values
class ArticleModel {
    var articleTitle: String? = null
    var publisher: String? = null
    var summaryText: String? = null
    private var imageURL: String? = null

    fun getTitles(): String {
        return articleTitle.toString()
    }

    fun getPublishers(): String {
        return publisher.toString()
    }

    fun getSummaries(): String {
        return summaryText.toString()
    }

    fun getImages(): String {
        return imageURL.toString()
    }

    fun setTitles(name: String) {
        this.articleTitle = name
    }

    fun setPublishers(pub: String) {
        this.publisher = pub
    }

    fun setSummaries(sum: String) {
        this.summaryText = sum
    }

    fun setImages(image_url: String) {
        this.imageURL = image_url
    }
}
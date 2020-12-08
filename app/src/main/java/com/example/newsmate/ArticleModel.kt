package com.example.newsmate

//Basic model for adapter to access/ assign article values
class ArticleModel(val id: Int, var articleTitle: String, var publisher: String, var summaryText: String, var imageURL: String) {
    //getters and setters
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
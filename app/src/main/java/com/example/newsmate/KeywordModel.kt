package com.example.newsmate

    class KeywordModel {
        var keyword: String? = null

        fun getKeywords(): String {
            return keyword.toString()
        }

        fun setKeywords(word: String) {
            this.keyword = word
        }
}
package com.example.newsmate

    class KeywordModel(val id: Int,var keyword: String, var notify: Boolean) {
        //getters and setters
        fun getKeywords(): String {
            return keyword.toString()
        }

        fun setKeywords(word: String) {
            this.keyword = word
        }

        fun getNotifies(): Boolean {
            return notify
        }
        fun setNotifies(not: Boolean){
            this.notify = not
        }
}
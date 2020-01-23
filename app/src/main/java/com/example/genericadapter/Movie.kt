package com.example.genericadapter

class Movie(
    val title: String,
    val year: Int,
    val director: String,
    val studio: String): AdapterObject {

    override fun layoutId(): Int {
        return R.layout.movie_item_list
    }

    override fun isFilterable(filter: String): Boolean {
        return title.toLowerCase().contains(filter) ||
                year.toString().toLowerCase().contains(filter) ||
                director.toLowerCase().contains(filter) ||
                studio.toLowerCase().contains(filter)
    }
}
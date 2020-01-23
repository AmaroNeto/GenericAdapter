package com.example.genericadapter

interface AdapterObject {
    fun layoutId() : Int
    fun isFilterable(filter: String) : Boolean
}
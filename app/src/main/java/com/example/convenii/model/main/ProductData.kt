package com.example.convenii.model.main

class ProductData {


    data class ProductResponse(
        val data: List<ProductData>
    )

    data class ProductData(
        val idx: Int,
        val categoryIdx: Int,
        val name: String,
        val price: String,
        val productIdx: Int,
        val score: Int,
        val createdAt: String,
        val bookmarked: Boolean,
        val eventHistory: List<EventHistoryData>
    )

    data class EventHistoryData(
        val companyIdx: Int,
        val eventIdx: Int,
        val price: String?
    )
}
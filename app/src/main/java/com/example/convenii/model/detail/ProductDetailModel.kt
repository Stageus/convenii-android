package com.example.convenii.model.detail

class ProductDetailModel {

    data class ProductDetailResponseData(
        val data: ProductData,
        val authStatus: String,
        val rankIdx: Int
    )

    data class ProductData(
        val product: ProductDetailData
    )

    data class ProductDetailData(
        val idx: Int,
        val categoryIdx: Int,
        val name: String,
        val price: String,
        val productImg: String,
        val score: String,
        val createdAt: String,
        var bookmarked: Boolean,
        val eventInfo: List<EventInfoData>
    )

    data class EventInfoData(
        val month: String,
        val events: List<EventsData>
    )

    data class EventsData(
        val companyIdx: Int,
        val eventIdx: Int,
        val price: String?
    )
}
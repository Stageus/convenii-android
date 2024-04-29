package com.example.convenii.model.main

class ProductData {


    data class ProductDetailResponseData(
        val data: List<ProductDetailData>
    )

    data class ProductDetailData(
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

    data class ProductCompanyRequestData(
        val companyIdx: Int,
        val page: Int,
        val option: String
    )

    data class ProductCompanyResponseData(
        val data: ProductListData
    )


    data class ProductListData(
        val productList: List<ProductCompanyData>
    )

    data class ProductCompanyData(
        val idx: Int,
        val categoryIdx: Int,
        val name: String,
        val price: String,
        val productImg: String,
        val score: String,
        val createdAt: String,
        val bookmarked: Boolean,
        val events: List<EventData>
    )

    data class EventData(
        val companyIdx: Int,
        val eventIdx: Int,
        val price: String?
    )
}

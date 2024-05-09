package com.example.convenii.model.product

class ProductAddModel {
    data class ProductAddRequestBody(
        val categoryIdx: Int,
        val name: String,
        val price: String,
        val eventInfo: List<EventInfoData>

    )

    data class EventInfoData(
        val companyIdx: Int,
        val eventIdx: Int,
        val eventPrice: Int?
    )
}
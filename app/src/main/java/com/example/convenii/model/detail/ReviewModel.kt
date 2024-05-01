package com.example.convenii.model.detail

class ReviewModel {

    data class ReviewResponseData(
        val data: ReviewsData
    )

    data class ReviewsData(
        val reviews: List<ReviewData>
    )

    data class ReviewData(
        val productIdx: Int,
        val nickname: String,
        val content: String,
        val score: Int,
        val createdAt: String
    )
}
package com.example.convenii.model.detail

class ReviewModel {

    data class GetReviewResponseData(
        val data: ReviewsData,
        val authStatus: String
    )

    data class ReviewsData(
        val reviews: List<ReviewData>
    )

    data class ReviewData(
        val productIdx: Int,
        val nickname: String,
        val content: String,
        val score: Int,
        var createdAt: String
    )

    data class PostReviewRequestData(
        val score: Int,
        val content: String
    )
}
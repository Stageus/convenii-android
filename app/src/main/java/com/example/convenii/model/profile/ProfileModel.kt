package com.example.convenii.model.profile

class ProfileModel {

    data class ProfileResponseData(
        val data: ProfileData,
        val authStatus: String,
        val rankIdx: Int
    )

    data class ProfileData(
        val idx: Int,
        val email: String,
        val name: String,
        val createdAt: String,
    )
}
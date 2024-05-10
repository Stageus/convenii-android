package com.example.convenii.dataSource

import com.example.convenii.model.CommonResponseData
import com.example.convenii.model.account.RegisterModel
import com.example.convenii.model.account.SignInModel
import com.example.convenii.model.detail.ProductDetailModel
import com.example.convenii.model.detail.ReviewModel
import com.example.convenii.model.main.ProductModel
import com.example.convenii.model.product.ProductAddModel
import com.example.convenii.model.profile.ProfileModel
import com.example.convenii.service.ApiService
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response
import javax.inject.Inject


interface RemoteDataSource {
    // account------------------------------------------------------------------------------
    suspend fun signIn(email: String, pw: String): Response<SignInModel.ResponseBody>
    suspend fun verifyEmailSend(email: String): Response<RegisterModel.VerifyEmailSendResponseBody>
    suspend fun verifyEmailCheck(
        email: String,
        verificationCode: String
    ): Response<CommonResponseData.Response>

    suspend fun register(
        email: String,
        pw: String,
        nickname: String
    ): Response<RegisterModel.RegisterResponseBody>

    suspend fun changePwVerifyEmailSend(email: String): Response<CommonResponseData.Response>

    suspend fun changePw(email: String, pw: String): Response<CommonResponseData.Response>

    //main -------------------------------------
    suspend fun getProductCompany(
        companyIdx: Int,
        page: Int,
        option: String
    ): Response<ProductModel.ProductCompanyResponseData>

    //detail -------------------------------------
    suspend fun getProductDetail(productIdx: Int): Response<ProductDetailModel.ProductDetailResponseData>

    suspend fun getProductReview(
        productIdx: Int,
        page: Int
    ): Response<ReviewModel.GetReviewResponseData>

    suspend fun postProductReview(
        productIdx: Int,
        body: ReviewModel.PostReviewRequestData
    ): Response<CommonResponseData.Response>

    suspend fun deleteProduct(
        productIdx: Int
    ): Response<CommonResponseData.Response>


    //bookmark -------------------------------------
    suspend fun getAllBookmark(
        page: Int
    ): Response<ProductModel.ProductCompanyResponseData>

    suspend fun postBookmark(productIdx: Int): Response<CommonResponseData.Response>
    suspend fun deleteBookmark(productIdx: Int): Response<CommonResponseData.Response>

    //search -------------------------------------
    suspend fun getSearchData(
        keyword: String?,
        eventFilter: String?,
        categoryFilter: String?,
        page: Int
    ): Response<ProductModel.ProductCompanyResponseData>

    //profile -------------------------------------
    suspend fun getProfileData(): Response<ProfileModel.ProfileResponseData>
    suspend fun deleteAccount(): Response<CommonResponseData.Response>

    //product -------------------------------------
    suspend fun addProduct(
        categoryIdx: Int,
        name: String,
        price: String,
        image: MultipartBody.Part,
        eventInfo: List<ProductAddModel.EventInfoData>
    ): Response<CommonResponseData.Response>

    suspend fun editProduct(
        productIdx: Int,
        categoryIdx: Int,
        name: String,
        price: String,
        image: MultipartBody.Part?,
        eventInfo: List<ProductAddModel.EventInfoData>
    ): Response<CommonResponseData.Response>

}


class RemoteDataSourceImpl @Inject constructor(private val apiService: ApiService) :
    RemoteDataSource {
    // account --------------------------------------------------------------------------
    override suspend fun signIn(email: String, pw: String): Response<SignInModel.ResponseBody> {
        val requestBody = SignInModel.RequestBody(email = email, pw = pw)
        return apiService.signIn(requestBody)
    }

    override suspend fun verifyEmailSend(email: String): Response<RegisterModel.VerifyEmailSendResponseBody> {
        val requestBody = RegisterModel.VerifyEmailSendRequestBody(email = email)
        return apiService.verifyEmailSend(requestBody)
    }

    override suspend fun verifyEmailCheck(
        email: String,
        verificationCode: String
    ): Response<CommonResponseData.Response> {
        val requestBody = RegisterModel.VerifyEmailCheckRequestBody(
            email = email,
            verificationCode = verificationCode
        )
        return apiService.verifyEmailCheck(requestBody)
    }

    override suspend fun register(
        email: String,
        pw: String,
        nickname: String
    ): Response<RegisterModel.RegisterResponseBody> {
        val requestBody = RegisterModel.RegisterRequestBody(
            email = email,
            pw = pw,
            nickname = nickname
        )
        return apiService.register(requestBody)
    }

    override suspend fun changePwVerifyEmailSend(email: String): Response<CommonResponseData.Response> {
        val requestBody = RegisterModel.VerifyEmailSendRequestBody(email = email)
        return apiService.changePwEmailSend(requestBody)
    }

    override suspend fun changePw(
        email: String,
        pw: String
    ): Response<CommonResponseData.Response> {
        val requestBody = RegisterModel.ChangePwRequestBody(email = email, pw = pw)
        return apiService.changePw(requestBody)
    }

    //main -------------------------------------
    override suspend fun getProductCompany(
        companyIdx: Int,
        page: Int,
        option: String
    ): Response<ProductModel.ProductCompanyResponseData> {
        return apiService.getProductCompany(companyIdx, page, option)
    }

    //detail -------------------------------------
    override suspend fun getProductDetail(productIdx: Int): Response<ProductDetailModel.ProductDetailResponseData> {
        return apiService.getProductDetail(productIdx)
    }

    override suspend fun getProductReview(
        productIdx: Int,
        page: Int
    ): Response<ReviewModel.GetReviewResponseData> {
        return apiService.getProductReview(productIdx, page)
    }

    override suspend fun postProductReview(
        productIdx: Int,
        body: ReviewModel.PostReviewRequestData
    ): Response<CommonResponseData.Response> {
        return apiService.postProductReview(productIdx, body)
    }

    override suspend fun deleteProduct(productIdx: Int): Response<CommonResponseData.Response> {
        return apiService.deleteProduct(productIdx)
    }

    //bookmark -------------------------------------
    override suspend fun getAllBookmark(
        page: Int
    ): Response<ProductModel.ProductCompanyResponseData> {
        return apiService.getAllBookmark(
            page = page
        )
    }

    override suspend fun postBookmark(productIdx: Int): Response<CommonResponseData.Response> {
        return apiService.postBookmark(productIdx)
    }

    override suspend fun deleteBookmark(productIdx: Int): Response<CommonResponseData.Response> {
        return apiService.deleteBookmark(productIdx)
    }

    //search -------------------------------------
    override suspend fun getSearchData(
        keyword: String?,
        eventFilter: String?,
        categoryFilter: String?,
        page: Int
    ): Response<ProductModel.ProductCompanyResponseData> {
        return apiService.getSearchData(keyword, eventFilter, categoryFilter, page)
    }

    //profile -------------------------------------
    override suspend fun getProfileData(): Response<ProfileModel.ProfileResponseData> {
        return apiService.getProfileData()
    }

    override suspend fun deleteAccount(): Response<CommonResponseData.Response> {
        return apiService.deleteAccount()
    }

    //product -------------------------------------
    override suspend fun addProduct(
        categoryIdx: Int,
        name: String,
        price: String,
        image: MultipartBody.Part,
        eventInfo: List<ProductAddModel.EventInfoData>
    ): Response<CommonResponseData.Response> {
        val nameRequestBody = name.toRequestBody("text/plain".toMediaType())
        val priceRequestBody = price.toRequestBody("text/plain".toMediaType())
        val eventInfoRequestBody = createEventInfoParts(eventInfo)

        return apiService.addProduct(
            categoryIdx,
            nameRequestBody,
            priceRequestBody,
            image,
            eventInfoRequestBody
        )
    }

    override suspend fun editProduct(
        productIdx: Int,
        categoryIdx: Int,
        name: String,
        price: String,
        image: MultipartBody.Part?,
        eventInfo: List<ProductAddModel.EventInfoData>
    ): Response<CommonResponseData.Response> {
        val nameRequestBody = name.toRequestBody("text/plain".toMediaType())
        val priceRequestBody = price.toRequestBody("text/plain".toMediaType())
        val eventInfoRequestBody = createEventInfoParts(eventInfo)

        return apiService.editProduct(
            productIdx,
            categoryIdx,
            nameRequestBody,
            priceRequestBody,
            image,
            eventInfoRequestBody
        )
    }


    private fun createPartFromString(name: String, value: String): MultipartBody.Part {
        val req = value.toRequestBody("text/plain".toMediaType())
        return MultipartBody.Part.createFormData(name, null, req)
    }

    private fun createEventInfoParts(eventInfoData: List<ProductAddModel.EventInfoData>): List<MultipartBody.Part> {
        val parts = mutableListOf<MultipartBody.Part>()
        for ((index, data) in eventInfoData.withIndex()) {
            // Assuming EventInfoData has properties like `companyIdx`, `eventIdx`, `eventPrice`
            parts.add(
                createPartFromString(
                    "eventInfo[$index][companyIdx]",
                    data.companyIdx.toString()
                )
            )
            parts.add(createPartFromString("eventInfo[$index][eventIdx]", data.eventIdx.toString()))
            parts.add(
                createPartFromString(
                    "eventInfo[$index][eventPrice]",
                    data.eventPrice.toString()
                )
            )
        }
        return parts
    }


}
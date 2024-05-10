package com.example.convenii.service

import com.example.convenii.model.CommonResponseData
import com.example.convenii.model.account.RegisterModel
import com.example.convenii.model.account.SignInModel
import com.example.convenii.model.detail.ProductDetailModel
import com.example.convenii.model.detail.ReviewModel
import com.example.convenii.model.main.ProductModel
import com.example.convenii.model.profile.ProfileModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    //Account ---------------------------------------------------------
    @POST("account/login")
    suspend fun signIn(
        @Body body: SignInModel.RequestBody
    ): retrofit2.Response<SignInModel.ResponseBody>

    @POST("account/verify-email/send")
    suspend fun verifyEmailSend(
        @Body body: RegisterModel.VerifyEmailSendRequestBody
    ): retrofit2.Response<RegisterModel.VerifyEmailSendResponseBody>

    @POST("account/verify-email/check")
    suspend fun verifyEmailCheck(
        @Body body: RegisterModel.VerifyEmailCheckRequestBody
    ): retrofit2.Response<CommonResponseData.Response>

    @POST("account")
    suspend fun register(
        @Body body: RegisterModel.RegisterRequestBody
    ): retrofit2.Response<RegisterModel.RegisterResponseBody>

    @POST("account/verify-email/send/recovery")
    suspend fun changePwEmailSend(
        @Body body: RegisterModel.VerifyEmailSendRequestBody
    ): retrofit2.Response<CommonResponseData.Response>

    @PUT("account/pw")
    suspend fun changePw(
        @Body body: RegisterModel.ChangePwRequestBody
    ): retrofit2.Response<CommonResponseData.Response>

    //Main -------------------------------------
    @GET("product/company/{companyIdx}")
    suspend fun getProductCompany(
        @Path("companyIdx") companyIdx: Int,
        @Query("page") page: Int,
        @Query("option") option: String
    ): retrofit2.Response<ProductModel.ProductCompanyResponseData>

    //Detail -------------------------------------
    @GET("product/{productIdx}")
    suspend fun getProductDetail(
        @Path("productIdx") productIdx: Int
    ): retrofit2.Response<ProductDetailModel.ProductDetailResponseData>

    @GET("review/product/{productIdx}")
    suspend fun getProductReview(
        @Path("productIdx") productIdx: Int,
        @Query("page") page: Int
    ): retrofit2.Response<ReviewModel.GetReviewResponseData>

    @POST("review/product/{productIdx}")
    suspend fun postProductReview(
        @Path("productIdx") productIdx: Int,
        @Body body: ReviewModel.PostReviewRequestData
    ): retrofit2.Response<CommonResponseData.Response>

    @DELETE("product/{productIdx}")
    suspend fun deleteProduct(
        @Path("productIdx") productIdx: Int
    ): retrofit2.Response<CommonResponseData.Response>

    //bookmark -------------------------------------
    @GET("bookmark/all")
    suspend fun getAllBookmark(
        @Query("page") page: Int
    ): retrofit2.Response<ProductModel.ProductCompanyResponseData>

    @POST("bookmark/product/{productIdx}")
    suspend fun postBookmark(
        @Path("productIdx") productIdx: Int
    ): retrofit2.Response<CommonResponseData.Response>


    @DELETE("bookmark/product/{productIdx}")
    suspend fun deleteBookmark(
        @Path("productIdx") productIdx: Int
    ): retrofit2.Response<CommonResponseData.Response>

    //Search -------------------------------------
    @GET("product/search")
    suspend fun getSearchData(
        @Query("keyword") keyword: String?,
        @Query("eventFilter") eventFilter: String?,
        @Query("categoryFilter") categoryFilter: String?,
        @Query("page") page: Int
    ): retrofit2.Response<ProductModel.ProductCompanyResponseData>

    //profile -------------------------------------
    @GET("account")
    suspend fun getProfileData(): retrofit2.Response<ProfileModel.ProfileResponseData>

    @DELETE("account")
    suspend fun deleteAccount(): retrofit2.Response<CommonResponseData.Response>

    //product -------------------------------------
    @Multipart
    @POST("product")
    suspend fun addProduct(
        @Part("categoryIdx") categoryIdx: Int,
        @Part("name") name: RequestBody,
        @Part("price") price: RequestBody,
        @Part image: MultipartBody.Part,
        @Part parts: List<MultipartBody.Part>
    ): retrofit2.Response<CommonResponseData.Response>

    @Multipart
    @PUT("product/{productIdx}")
    suspend fun editProduct(
        @Path("productIdx") productIdx: Int,
        @Part("categoryIdx") categoryIdx: Int,
        @Part("name") name: RequestBody,
        @Part("price") price: RequestBody,
        @Part image: MultipartBody.Part?,
        @Part parts: List<MultipartBody.Part>
    ): retrofit2.Response<CommonResponseData.Response>


}
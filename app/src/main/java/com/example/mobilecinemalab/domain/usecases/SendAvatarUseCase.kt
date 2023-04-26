package com.example.mobilecinemalab.domain.usecases

import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import com.example.mobilecinemalab.datasource.network.ApiResponse
import com.example.mobilecinemalab.domain.repositoryinterfaces.IProfileRepository
import com.example.mobilecinemalab.repositories.ProfileRepository
import kotlinx.coroutines.flow.Flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import java.io.ByteArrayOutputStream


class SendAvatarUseCase(private val image: Bitmap) {
    private val profileRepository: IProfileRepository = ProfileRepository()

    operator fun invoke(): Flow<ApiResponse<ResponseBody>> {

        val stream = ByteArrayOutputStream()
        image.compress(CompressFormat.PNG, 100, stream)
        val byteArray = stream.toByteArray()

        val multipart = MultipartBody.Part.createFormData(
            "photo[content]", "photo",
            byteArray.toRequestBody("image/png".toMediaTypeOrNull(), 0, byteArray.size)
        )

        return profileRepository.loadUserPhoto(multipart)
    }
}
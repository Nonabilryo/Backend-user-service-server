package nonabili.userserviceserver.service

import nonabili.userserviceserver.dto.request.DeleteUserRequest
import nonabili.userserviceserver.dto.request.PatchUserNameRequest
import nonabili.userserviceserver.dto.request.PatchUserPasswordRequest
import nonabili.userserviceserver.dto.request.PutUserImageRequest
import nonabili.userserviceserver.dto.response.UserInfoResponse
import nonabili.userserviceserver.dto.response.UserIdxResponse
import nonabili.userserviceserver.dto.response.UserNameAndImageResponse
import nonabili.userserviceserver.repository.UserRepository
import nonabili.userserviceserver.util.error.CustomError
import nonabili.userserviceserver.util.error.ErrorState
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class UserService(val userRepository: UserRepository, val passwordEncoder: BCryptPasswordEncoder, val s3UploadService: S3UploadService) {
    fun getUserInfo(userIdx: String): UserInfoResponse {
        val user = userRepository.findUserByIdx(UUID.fromString(userIdx)) ?: throw CustomError(ErrorState.NOT_FOUND_USERNAME)
        return UserInfoResponse(
                idx = user.idx.toString(),
                name = user.name,
                imageUrl = user.image?.url,
                description = user.description,
                signed = user.signed
        )
    }

    fun getUserIdxByName(userName: String): UserIdxResponse {
        val user = userRepository.findUserByName(userName) ?: throw CustomError(ErrorState.NOT_FOUND_USERNAME)
        return UserIdxResponse(
            idx = user.idx.toString()
        )
    }

    fun getUserIdxById(userId: String): UserIdxResponse {
        val user = userRepository.findUserById(userId) ?: throw CustomError(ErrorState.NOT_FOUND_ID)
        return UserIdxResponse(
            idx = user.idx.toString()
        )
    }

    fun getUserInfoByIdx(userIdx: String): UserNameAndImageResponse {
        val user = userRepository.findUserByIdx(UUID.fromString(userIdx)) ?: throw CustomError(ErrorState.NOT_FOUND_IDX)
        return UserNameAndImageResponse(
            userName = user.name,
            image = user.image?.url
        )
    }

    fun patchUserPassword(userIdx: String, request: PatchUserPasswordRequest) {
        val user = userRepository.findUserByIdx(UUID.fromString(userIdx)) ?: throw CustomError(ErrorState.NOT_FOUND_ID)
        if (!passwordEncoder.matches(request.passwordNow, user.password)) throw CustomError(ErrorState.WRONG_PASSWORD)
        userRepository.save(
                user.copy(
                        password = passwordEncoder.encode(request.passwordAfter)
                )
        )
    }

    fun patchUserName(userIdx: String, request: PatchUserNameRequest) {
        userRepository.findUserByName(request.name)?.let { throw CustomError(ErrorState.NAME_IS_ALREADY_USED) }
        val user = userRepository.findUserByIdx(UUID.fromString(userIdx)) ?: throw CustomError(ErrorState.NOT_FOUND_ID)
        userRepository.save(
            user.copy(
                name = request.name
            )
        )
    }

    fun putUserImage(userIdx: String, request: PutUserImageRequest) {
        val user = userRepository.findUserByIdx(UUID.fromString(userIdx)) ?: throw CustomError(ErrorState.NOT_FOUND_ID)
        userRepository.save(user.copy(
            image = s3UploadService.saveImage(request.image, "profile_image")
        ))
    }

    fun deleteUser(userIdx: String, request: DeleteUserRequest) {
        val user = userRepository.findUserByIdx(UUID.fromString(userIdx)) ?: throw CustomError(ErrorState.NOT_FOUND_ID)
        if (!passwordEncoder.matches(request.password, user.password)) throw CustomError(ErrorState.WRONG_PASSWORD)
        userRepository.delete(user)
    }
}
package nonabili.userserviceserver.controller

import jakarta.validation.Valid
import nonabili.userserviceserver.dto.request.DeleteUserRequest
import nonabili.userserviceserver.dto.request.PatchUserNameRequest
import nonabili.userserviceserver.dto.request.PatchUserPasswordRequest
import nonabili.userserviceserver.dto.request.PutUserImageRequest
import nonabili.userserviceserver.dto.response.UserInfoResponse
import nonabili.userserviceserver.dto.response.UserIdxResponse
import nonabili.userserviceserver.dto.response.UserNameAndImageResponse
import nonabili.userserviceserver.service.UserService
import nonabili.userserviceserver.util.ResponseFormat
import nonabili.userserviceserver.util.ResponseFormatBuilder
import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
class UserController(val userService: UserService) {
    val log = LoggerFactory.getLogger(javaClass)
    @GetMapping()
    fun getUserInfo(@RequestHeader requestHeaders: HttpHeaders): ResponseEntity<ResponseFormat<UserInfoResponse>> {
        val userIdx = requestHeaders.get("userIdx")!![0]
        val result = userService.getUserInfo(userIdx)
        return ResponseEntity.ok(ResponseFormatBuilder { message = "success" }.build(result))
    }
    @GetMapping("/{userIdx}")
    fun getUserInfoByIdx(@PathVariable userIdx: String): ResponseEntity<ResponseFormat<UserInfoResponse>> {
        val result = userService.getUserInfo(userIdx)
        return ResponseEntity.ok(ResponseFormatBuilder { message = "success" }.build(result))
    }


    @GetMapping("/NameToIdx/{userName}")  /// todo 중요 gateway에서 접근 제한
    fun getUserIdxByName(@PathVariable userName: String): ResponseEntity<UserIdxResponse> {
        val result = userService.getUserIdxByName(userName)
        return ResponseEntity.ok(result)
    }

    @GetMapping("/IdToIdx/{userId}")  /// todo 중요 gateway에서 접근 제한
    fun getUserIdxById(@PathVariable userId: String): ResponseEntity<UserIdxResponse> {
        val result = userService.getUserIdxById(userId)
        return ResponseEntity.ok(result)
    }

    @GetMapping("/IdxToUserInfo/{userIdx}")  /// todo 중요 gateway에서 접근 제한 시발 2
    fun getUserInfoByIdx2(@PathVariable userIdx: String): ResponseEntity<UserNameAndImageResponse> {
        val result = userService.getUserInfoByIdx(userIdx)
        return ResponseEntity.ok(result)
    }

    @PatchMapping("/patchPassword")
    fun patchUserPassword(@RequestHeader requestHeaders: HttpHeaders, @RequestBody @Valid request: PatchUserPasswordRequest): ResponseEntity<ResponseFormat<Any>> {
        val userIdx = requestHeaders.get("userIdx")!![0]
        userService.patchUserPassword(userIdx, request)
        return ResponseEntity.ok(ResponseFormatBuilder { message = "success" }.noData())
    }

    @PatchMapping("/patchName")
    fun patchUserName(@RequestHeader requestHeaders: HttpHeaders, @RequestBody @Valid request: PatchUserNameRequest): ResponseEntity<ResponseFormat<Any>>  {
        val userIdx = requestHeaders.get("userIdx")!![0]
        userService.patchUserName(userIdx, request)
        return ResponseEntity.ok(ResponseFormatBuilder { message = "success" }.noData())
    }

    @PutMapping("/putImage")
    fun putUserImage(@RequestHeader requestHeaders: HttpHeaders, request: PutUserImageRequest): ResponseEntity<ResponseFormat<Any>>  {
        val userIdx = requestHeaders.get("userIdx")!![0]
        userService.putUserImage(userIdx, request)
        return ResponseEntity.ok(ResponseFormatBuilder { message = "success" }.noData())
    }

    //ㅍㅡ로필 사진, 탈
    @PostMapping("/delete")
    fun deleteUser(@RequestHeader requestHeaders: HttpHeaders, @RequestBody @Valid request: DeleteUserRequest): ResponseEntity<ResponseFormat<Any>> {
        val userIdx = requestHeaders.get("userIdx")!![0]
        userService.deleteUser(userIdx, request)
        return ResponseEntity.ok(ResponseFormatBuilder { message = "success" }.noData())
    }

}
package nonabili.userserviceserver.controller

import nonabili.userserviceserver.service.UserService
import nonabili.userserviceserver.util.ResponseFormat
import nonabili.userserviceserver.util.ResponseFormatBuilder
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/user")
class UserController(val userService: UserService) {
    @GetMapping("/{userName}")
    fun getUserInfo(@PathVariable userName: String): ResponseEntity<ResponseFormat<Any>> {
        val result = userService.getUserInfo()
        return ResponseEntity.ok(ResponseFormatBuilder { message = "success" }.build(result))
    }
}
package nonabili.userserviceserver.controller

import com.fasterxml.jackson.databind.ObjectMapper
import nonabili.userserviceserver.dto.request.DeleteUserRequest
import nonabili.userserviceserver.dto.request.PatchUserNameRequest
import nonabili.userserviceserver.dto.request.PatchUserPasswordRequest
import nonabili.userserviceserver.dto.request.PutUserImageRequest
import nonabili.userserviceserver.repository.UserRepository
import nonabili.userserviceserver.service.UserService
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.mock.web.MockMultipartFile
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.RestDocumentationExtension
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import org.springframework.web.multipart.MultipartFile
import java.io.FileInputStream

@ExtendWith(RestDocumentationExtension::class, SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class UserControllerTest {

    var mockMvc: MockMvc? = null
    val log = LoggerFactory.getLogger(javaClass)

    private val objectMapper = ObjectMapper()

    @Autowired
    private val wac: WebApplicationContext? = null

    @Autowired
    private val userService: UserService? = null

    @Autowired
    private val userRepository: UserRepository? = null

    @BeforeEach
    fun setUp(restDocumentation: RestDocumentationContextProvider?) {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac!!)
            .apply<DefaultMockMvcBuilder>(MockMvcRestDocumentation.documentationConfiguration(restDocumentation)) // (2)
            .build()
    }

    @Test
    fun getUserInfo() {
        val userName = "문가인3"
        mockMvc?.perform(
            get("/user/$userName")
                .contentType(MediaType.APPLICATION_JSON)
        )
            ?.andExpect(MockMvcResultMatchers.status().isOk)
            ?.andDo(document("getUserInfo/success"))
    }

    @Test
    fun patchUserPassword() {
        val userId = "ansrkdls"
        val content = objectMapper.writeValueAsString(PatchUserPasswordRequest(
            passwordNow = "ansrkdls",
            passwordAfter = "ansrkdls"
        ))
        mockMvc?.perform(
            patch("/user/patchPassword")
                .contentType(MediaType.APPLICATION_JSON)
                .header("userId", userId)
                .content(content)
        )
            ?.andExpect(MockMvcResultMatchers.status().isOk)
            ?.andDo(document("patchPassword/success"))
    }

    @Test
    fun patchUserName() {
        val userId = "ansrkdls"
        var content = objectMapper.writeValueAsString(PatchUserNameRequest(
            name = "문가인입니다"
        ))
        mockMvc?.perform(
            patch("/user/patchName")
                .contentType(MediaType.APPLICATION_JSON)
                .header("userId", userId)
                .content(content)
        )
            ?.andExpect(MockMvcResultMatchers.status().isOk)
            ?.andDo(document("patchName/success"))

        content = objectMapper.writeValueAsString(PatchUserNameRequest(
            name = "문가인"
        ))
        mockMvc?.perform(
            patch("/user/patchName")
                .contentType(MediaType.APPLICATION_JSON)
                .header("userId", userId)
                .content(content)
        )
    }


    @Test
    fun putUserImage() {
        val userId = "ansrkdls"
        val fileInputStream = FileInputStream("src/test/images/eye.jpg")
        val content = MockMultipartFile("image", fileInputStream)
//        mockMvc?.perform(
//            put("/user/putImage")
//                .contentType("multipart/form-data")
//                .header("userId", userId)
//                .content(content.bytes)
//        )
//            ?.andExpect(MockMvcResultMatchers.status().isOk)
//            ?.andDo(document("putImage/success"))
//

        val builder = MockMvcRequestBuilders.multipart("/user/putImage")
        builder.with { request ->
            request.method = "PUT"
            request.addHeader("userId", userId)
            request
        }
        mockMvc?.perform(builder.file(content))
            ?.andExpectAll(
                MockMvcResultMatchers.status().isOk,
            )
            ?.andDo(document("putImage/success"))
    }

    @Test
    fun deleteUser() {
        var userId = "ansrkdls"
        var content = objectMapper.writeValueAsString(DeleteUserRequest(
            password = "ansrkdls"
        ))
        mockMvc?.perform(
            post("/user/delete")
                .header("userId", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
        )
            ?.andExpect(MockMvcResultMatchers.status().isOk)
            ?.andDo(document("deleteUser/success"))
        userId = "ansrkdls2"
        content = objectMapper.writeValueAsString(DeleteUserRequest(
            password = "ansrkdls2"
        ))
        mockMvc?.perform(
            post("/user/delete")
                .header("userId", userId)
                .content(content)
        )
    }
}
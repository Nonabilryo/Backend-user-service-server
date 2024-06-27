package nonabili.userserviceserver.dto.request

import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.multipart.MultipartFile


data class PutUserImageRequest(
    val image: MultipartFile
)

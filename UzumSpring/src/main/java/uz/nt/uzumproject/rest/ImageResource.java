package uz.nt.uzumproject.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import uz.nt.uzumproject.dto.ResponseDto;
import uz.nt.uzumproject.model.Image;
import uz.nt.uzumproject.service.ImageService;

@RestController
@RequestMapping("image")
@RequiredArgsConstructor
public class ImageResource {

    private final ImageService imageService;

    @PostMapping
    public ResponseDto<Image> uploadImage(@RequestBody MultipartFile image){
        return imageService.saveImage(image);
    }
}

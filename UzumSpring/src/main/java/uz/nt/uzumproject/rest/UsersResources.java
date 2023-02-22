package uz.nt.uzumproject.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.nt.uzumproject.dto.LoginDto;
import uz.nt.uzumproject.dto.ResponseDto;
import uz.nt.uzumproject.dto.UsersDto;
import uz.nt.uzumproject.service.UsersService;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UsersResources {
    private final UsersService usersService;
    @PostMapping
    public ResponseDto<UsersDto> addUsers(@RequestBody @Valid UsersDto usersDto) {
        return usersService.addUser(usersDto);
    }

    @PatchMapping
    public ResponseDto<UsersDto> updateUser(@RequestBody UsersDto usersDto){
        return usersService.updateUser(usersDto);
    }

    @GetMapping("by-phone-number")
    public ResponseDto<UsersDto> getUserByPhoneNumber(@RequestParam String phoneNumber){
        return usersService.getUserByPhoneNumber(phoneNumber);
    }
    @GetMapping
    public ResponseDto<UsersDto> getAll() {
        return ResponseDto.<UsersDto>builder()
                .message("Hi")
                .build();
    }

    @GetMapping("login")
    public ResponseDto<String> login(@RequestBody LoginDto loginDto) {
        return usersService.login(loginDto);
    }
}
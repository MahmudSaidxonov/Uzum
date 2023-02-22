package uz.nt.uzumproject.rest;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.nt.uzumproject.dto.ResponseDto;
import uz.nt.uzumproject.service.AuthorityService;

@RestController
@RequestMapping("authority")

public class AuthorityResources {

    private final AuthorityService authorityService;

    public AuthorityResources(AuthorityService authorityService) {
        this.authorityService = authorityService;
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN')")
    public ResponseDto<Void> addAuthority(@RequestParam String username, @RequestParam String authorityName) {
        return authorityService.addAuthorityToUser(username, authorityName);
    }
}

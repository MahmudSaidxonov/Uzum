package uz.nt.uzumproject.service;

import org.springframework.stereotype.Service;
import uz.nt.uzumproject.dto.ResponseDto;
import uz.nt.uzumproject.dto.UsersDto;
import uz.nt.uzumproject.model.Authorities;
import uz.nt.uzumproject.repository.AuthorityRepository;
import uz.nt.uzumproject.security.UserRoles;
import uz.nt.uzumproject.service.validator.AppStatusCodes;
import uz.nt.uzumproject.service.validator.AppStatusMessages;

import java.util.List;

@Service
public record AuthorityService(AuthorityRepository authorityRepository) {
    public ResponseDto<Void> addAuthorityToUser(String username, String authorityName) {
        if (authorityRepository.existsByUsernameAndAuthority(username, authorityName)) {
            return ResponseDto.<Void>builder()
                    .code(AppStatusCodes.VALIDATION_ERROR_CODE)
                    .message(AppStatusMessages.DATABASE_ERROR)
                    .build();
        }
//        UserRoles.valueOf("MODERATOR");
        Authorities auth = new Authorities();
        auth.setUsername(username);
        auth.setAuthority(authorityName);
        authorityRepository.save(auth);

        return ResponseDto.<Void>builder()
                .message(AppStatusMessages.OK)
                .success(true)
                .build();
    }
}

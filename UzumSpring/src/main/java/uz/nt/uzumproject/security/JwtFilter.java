package uz.nt.uzumproject.security;


import com.google.gson.Gson;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import uz.nt.uzumproject.dto.ResponseDto;
import uz.nt.uzumproject.dto.UsersDto;
import uz.nt.uzumproject.service.validator.AppStatusCodes;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Autowired
    private Gson gson;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse resp, FilterChain filter) throws ServletException, IOException {
       String authorization = req.getHeader("Authorization");

       if (authorization != null && authorization.startsWith("Bearer ")) {
            String token = authorization.substring(7);
            if (jwtService.isExpired(token)) {
                resp.getWriter().
                        println(gson.toJson(ResponseDto.builder()
                                .message("Token is expired")
                                .code(AppStatusCodes.VALIDATION_ERROR_CODE)
                                .build()));
                resp.setContentType("application/json");
                resp.setStatus(400);
            } else {
                UsersDto user = jwtService.getSubjects(token);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
       }
       filter.doFilter(req, resp);
    }
}

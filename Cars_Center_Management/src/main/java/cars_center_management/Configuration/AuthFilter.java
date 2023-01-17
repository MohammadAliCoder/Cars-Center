package cars_center_management.Configuration;

import cars_center_management.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthFilter extends OncePerRequestFilter {
    private String Token_header="Authorization";

    @Autowired
    UserToken userToken;

    @Autowired
    UserService userService;
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
    String header=httpServletRequest.getHeader(Token_header);
        SecurityContext securityContext=   SecurityContextHolder.getContext();
        if (header!=null && securityContext==null){
            String token=header.substring("Bearer".length());
            String username=userToken.getUsernameFromToken(token);
            if (username!=null){
                UserDetails userDetails= userService.loadUserByUsername(username);
                if(userToken.isTokenValid(token,userDetails)){
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=
                            new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());

                     usernamePasswordAuthenticationToken.setDetails(
                         new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));

                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }

        }

        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }
}

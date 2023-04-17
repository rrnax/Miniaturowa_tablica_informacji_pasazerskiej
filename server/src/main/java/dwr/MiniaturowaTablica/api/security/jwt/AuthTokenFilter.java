package dwr.MiniaturowaTablica.api.security.jwt;

import dwr.MiniaturowaTablica.api.services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.validation.constraints.NotNull;
import java.io.IOException;

@Component
@CrossOrigin()
@RequiredArgsConstructor
public class AuthTokenFilter extends OncePerRequestFilter {
   private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

   private final JwtUtils jwtUtils;

   private final UserService userService;

   private String parseJwt(HttpServletRequest request) {
      String headerAuth = request.getHeader("Authorization");

      if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
         return headerAuth.substring(7);
      }

      return null;
   }

   @Override
   protected void doFilterInternal(
         @NotNull HttpServletRequest request,
         @NotNull HttpServletResponse response,
         @NotNull FilterChain filterChain
   ) throws ServletException, IOException {
      try {
         String jwt = parseJwt(request);
         if (jwt != null && jwtUtils.validateJwtToken(jwt) && !jwtUtils.jwtIsBlackListed(jwt)) {
            String username = jwtUtils.getUserNameFromJwtToken(jwt);

            UserDetails userDetails = userService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
                  userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authentication);
         }
      } catch (Exception e) {
         logger.error("Cannot set user authentication: {}", e);
      }

      filterChain.doFilter(request, response);
   }
}
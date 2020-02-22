package com.dsf.escalade.service.global;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Slf4j
@Service("SecurityService")
public class SecurityServiceImpl implements SecurityService {

   private final AuthenticationManager authenticationManager;
   private final UserDetailsServiceImpl userDetailsService;

   @Autowired
   public SecurityServiceImpl(AuthenticationManager authenticationManager, UserDetailsServiceImpl userDetailsService) {
      this.authenticationManager = authenticationManager;
      this.userDetailsService = userDetailsService;
   }

   @Override
   public String findLoggedInUsername() {
      Object userDetails = SecurityContextHolder.getContext().getAuthentication().getDetails();

      if (userDetails instanceof UserDetails) {
         return ((UserDetails)userDetails).getUsername();
      }

      return null;
   }

   @Override
   public void autoLogin(String email, String motDePasse) {
      UserDetails userDetails = userDetailsService.loadUserByUsername(email);
      log.info("userDetails :" + userDetails);

      UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, motDePasse, userDetails.getAuthorities());
      log.info("usernamePasswordAuthenticationToken :" + usernamePasswordAuthenticationToken);

      authenticationManager.authenticate(usernamePasswordAuthenticationToken);


      if (usernamePasswordAuthenticationToken.isAuthenticated()) {
         SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
         log.debug(String.format("Auto login %s successfully!", email));
      }
   }
}

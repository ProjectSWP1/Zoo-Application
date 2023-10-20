package com.thezookaycompany.zookayproject.security;

import com.thezookaycompany.zookayproject.repositories.AccountRepository;
import com.thezookaycompany.zookayproject.repositories.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Service;

@Service
public class UserServices implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("In the user details service");
        System.out.println(username);
        return accountRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("user is not valid"));
    }

    public UserDetails loadUserFromOAuth2() {
        DefaultOAuth2User oauth2User = (DefaultOAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = oauth2User.getAttribute("email");
        // You can access other OAuth2 attributes as needed

        // Create or load the UserDetails from your database or user repository
        UserDetails userDetails = accountRepository.findByEmail(email).orElse(null);
        if (userDetails == null) {
            // Handle the case where the user is not found in your system
            throw new UsernameNotFoundException("User not found");
        }
        return userDetails;
    }
}

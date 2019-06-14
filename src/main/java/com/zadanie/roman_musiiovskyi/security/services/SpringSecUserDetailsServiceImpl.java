package com.zadanie.roman_musiiovskyi.security.services;


import com.zadanie.roman_musiiovskyi.models.User;
import com.zadanie.roman_musiiovskyi.services.interfaces.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;

@Service("userDetailService")
public class SpringSecUserDetailsServiceImpl implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(SpringSecUserDetailsServiceImpl.class);


    private UserService userService;
//    private UserDTOToUserDetails userDTOToUserDetails;


    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        logger.info("Input login = " + username);

        User user = userService.findByUserName(username);
        if (user == null){
            throw new UsernameNotFoundException("User Name "+ username +" Not Found");
        }

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getRole()));
            System.out.println("Role of user log in"+role.toString());
        });


        logger.info(user.toString());
        return new org.springframework.security.core.userdetails.User(user.getUserName()
                ,user.getEncryptedPassword(), authorities);
//        return userDTOToUserDetails.convert(userService.findByUserName(username));
    }


}

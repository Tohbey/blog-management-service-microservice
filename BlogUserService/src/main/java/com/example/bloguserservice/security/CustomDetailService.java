package com.example.bloguserservice.security;

import com.example.bloguserservice.model.User;
import com.example.bloguserservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomDetailService implements UserDetailsService {
    @Autowired
    private UserRepository userDao;

    @Override
    public CustomDetail loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = this.userDao.findUserByEmail(email);
        CustomDetail userDetail = null;
        if (user.isPresent()) {
            userDetail = new CustomDetail();
            userDetail.setUser(user.get());
        } else {
            throw new UsernameNotFoundException("user not exist with email: " + email);
        }
        return userDetail;
    }
}

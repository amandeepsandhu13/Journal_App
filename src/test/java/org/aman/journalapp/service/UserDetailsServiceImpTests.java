package org.aman.journalapp.service;
import org.aman.journalapp.entity.User;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mock;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.aman.journalapp.Repository.UserRepo;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;


import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserDetailsServiceImpTests {

    @Autowired
    private UserDetailsService userDetailsService;

    @MockBean
    private UserRepo userRepo;

    @Test
    public void testLoadUserByUsername() {
        String username = "testUser";
        User user = User.builder()
                .userName(username)
                .password("12345")
                .roles(List.of("USER"))
                .build();

        when(userRepo.findByUserName(username)).thenReturn(Optional.of(user));

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        assertNotNull(userDetails);
    }

}

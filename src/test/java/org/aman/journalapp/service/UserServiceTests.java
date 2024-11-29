package org.aman.journalapp.service;

import org.aman.journalapp.Repository.UserRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class UserServiceTests {

    @Autowired
    UserRepo userRepo;

    @Test
    public void testFindByUsername() {
       // assertEquals(3,2+1);
        assertNotNull( userRepo.findByUserName("Aman11"));
    }

    @ParameterizedTest
    @CsvSource({ "1,2,3",
            "4,5,3"                })
    public void testExp(int a, int b , int expected) {
        assertEquals(expected, a+b);
    }
}

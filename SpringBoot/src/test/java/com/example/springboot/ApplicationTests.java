package com.example.springboot;

import com.example.springboot.domain.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

    @Autowired
    private Person person;


    @Test
    public void contextLoads() {
        System.out.println(person);
    }

    @Test
    public void testPerson() {
        System.out.println(person.getId());
    }
}

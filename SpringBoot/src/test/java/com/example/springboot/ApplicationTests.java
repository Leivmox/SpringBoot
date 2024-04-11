package com.example.springboot;

import com.example.springboot.domain.testProrperties.MyProperties;
import com.example.springboot.domain.unnamed.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
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

    @Autowired
    private MyProperties myProperties;
    @Test
    public void testproperties() {
        System.out.println(myProperties);
    }

    @Autowired
    private ApplicationContext applicationContext;
    @Test
    public void ioctest() {
        boolean myService = applicationContext.containsBean("myService2");
        System.out.println(myService);
    }
}

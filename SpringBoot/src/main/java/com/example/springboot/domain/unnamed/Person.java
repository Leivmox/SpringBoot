package com.example.springboot.domain.unnamed;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.*;

@Component
@ConfigurationProperties(prefix = "person")

public class Person {
    private String name;
    private int id;
    private List hobby;
    private String[] family;
    private Map map;
    private Pet pet;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List getHobby() {
        return hobby;
    }

    public void setHobby(List hobby) {
        this.hobby = hobby;
    }

    public String[] getFamily() {
        return family;
    }

    public void setFamily(String[] family) {
        this.family = family;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", hobby=" + hobby +
                ", family=" + Arrays.toString(family) +
                ", map=" + map +
                ", pet=" + pet +
                '}';
    }
}

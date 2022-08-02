package com.github.fabriciolfj.entity;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "person")
public class PersonEntity extends PanacheEntity {

    @Column(unique = true)
    public String name;

    public int age;
}

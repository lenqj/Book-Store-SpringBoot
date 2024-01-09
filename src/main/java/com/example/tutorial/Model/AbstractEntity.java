package com.example.tutorial.Model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

import java.util.Objects;
@MappedSuperclass
public abstract class AbstractEntity {
    @Id
    @GeneratedValue
    public int ID;
    public int getID() {
        return ID;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractEntity entity = (AbstractEntity) o;
        return ID == entity.ID;
    }
    @Override
    public int hashCode() {
        return Objects.hash(ID);
    }
}

package com.atlassian.tutorial.myPlugin5.model;

import net.java.ao.Entity;

public interface PaddockToDog extends Entity {
    Paddock getPaddock();

    void setPaddock(Paddock paddock);

    Dog getDog();

    void setDog(Dog dog);
}
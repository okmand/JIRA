package com.atlassian.tutorial.myPlugin5.model;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.sal.api.transaction.TransactionCallback;
import net.java.ao.Query;

import java.util.Date;

public class DogManager {
    private final ActiveObjects ao;

    public DogManager(ActiveObjects ao) {
        this.ao = ao;
    }

    public Dog[] getAllDogs() {
        return ao.executeInTransaction(new TransactionCallback<Dog[]>() {
            @Override
            public Dog[] doInTransaction() {
                return ao.find(Dog.class, Query.select().where(" ID > ? ", 0));
            }
        });
    }

    public Dog getDogById(final Integer id) {
        return ao.executeInTransaction(new TransactionCallback<Dog>() {
            @Override
            public Dog doInTransaction() {
                return ao.get(Dog.class, id);
            }
        });
    }

    public Dog addDog(final String name, final String gender, final Date birthday,
                      final String breed, final String color, final String character, final Client client) {
        return ao.executeInTransaction(new TransactionCallback<Dog>() {
            @Override
            public Dog doInTransaction() {
                final Dog dog = ao.create(Dog.class);
                dog.setName(name);
                dog.setGender(gender);
                dog.setBirthday(birthday);
                dog.setBreed(breed);
                dog.setColor(color);
                dog.setCharacter(character);
                dog.setClient(client);
                dog.save();
                return dog;
            }
        });
    }

    public Dog updateDog(final Integer id, final String name, final String gender, final Date birthday,
                         final String breed, final String color, final String character, final Client client) {
        return ao.executeInTransaction(new TransactionCallback<Dog>() {
            @Override
            public Dog doInTransaction() {
                Dog dog = ao.get(Dog.class, id);
                dog.setName(name);
                dog.setGender(gender);
                dog.setBirthday(birthday);
                dog.setBreed(breed);
                dog.setColor(color);
                dog.setCharacter(character);
                dog.setClient(client);
                dog.save();
                return dog;
            }
        });
    }

    public void deleteDog(final Integer id) {
        ao.executeInTransaction(new TransactionCallback<Dog>() {
            @Override
            public Dog doInTransaction() {
                Dog dog = ao.get(Dog.class, id);
                ao.delete(dog);
                return null;
            }
        });
    }
}
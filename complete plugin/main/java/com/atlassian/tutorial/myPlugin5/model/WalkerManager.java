package com.atlassian.tutorial.myPlugin5.model;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.sal.api.transaction.TransactionCallback;
import net.java.ao.Query;

import java.util.Date;

public class WalkerManager {
    private final ActiveObjects ao;

    public WalkerManager(ActiveObjects ao) {
        this.ao = ao;
    }

    public Walker[] getAllWalkers() {
        return ao.executeInTransaction(new TransactionCallback<Walker[]>() {
            @Override
            public Walker[] doInTransaction() {
                return ao.find(Walker.class, Query.select().where(" ID > ? ", 0));
            }
        });
    }

    public Walker[] getWalkerByLastNameFirstName(final String lastName, final String firstName) {
        return ao.executeInTransaction(new TransactionCallback<Walker[]>() {
            @Override
            public Walker[] doInTransaction() {
                return ao.find(Walker.class, Query.select().where("LAST_NAME = ? AND FIRST_NAME = ?", lastName, firstName));
            }
        });
    }

    public Walker getWalkerById(final Integer id) {
        return ao.executeInTransaction(new TransactionCallback<Walker>() {
            @Override
            public Walker doInTransaction() {
                return ao.get(Walker.class, id);
            }
        });
    }

    public Walker addWalker(final String lastName, final String firstName,
                            final String middleName, final Date birthday,
                            final String phone, final String email) {
        return ao.executeInTransaction(new TransactionCallback<Walker>() {
            @Override
            public Walker doInTransaction() {
                final Walker walker = ao.create(Walker.class);
                walker.setLastName(lastName);
                walker.setFirstName(firstName);
                walker.setMiddleName(middleName);
                walker.setBirthday(birthday);
                walker.setPhone(phone);
                walker.setEmail(email);
                walker.save();
                return walker;
            }
        });
    }

    public Walker updateWalker(final Integer id, final String lastName,
                               final String firstName, final String middleName,
                               final Date birthday, final String phone, String email) {
        return ao.executeInTransaction(new TransactionCallback<Walker>() {
            @Override
            public Walker doInTransaction() {
                final Walker walker = ao.get(Walker.class, id);
                walker.setLastName(lastName);
                walker.setFirstName(firstName);
                walker.setMiddleName(middleName);
                walker.setBirthday(birthday);
                walker.setPhone(phone);
                walker.setEmail(email);
                walker.save();
                return walker;
            }
        });
    }

    public void deleteWalker(final Integer id) {
        ao.executeInTransaction(new TransactionCallback<Walker>() {
            @Override
            public Walker doInTransaction() {
                final Walker walker = ao.get(Walker.class, id);
                ao.delete(walker);
                return null;
            }
        });
    }
}
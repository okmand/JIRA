package com.atlassian.tutorial.myPlugin5.model;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.sal.api.transaction.TransactionCallback;
import net.java.ao.Query;

import java.util.Date;

public class ClientManager {
    private final ActiveObjects ao;

    public ClientManager(ActiveObjects ao) {
        this.ao = ao;
    }

    public Client[] getAllClients() {
        return ao.executeInTransaction(new TransactionCallback<Client[]>() {
            @Override
            public Client[] doInTransaction() {
                return ao.find(Client.class, Query.select().where(" ID > ? ", 0));
            }
        });
    }

    public Client[] getClientByLastNameFirstName(final String lastName, final String firstName) {
        return ao.executeInTransaction(new TransactionCallback<Client[]>() {
            @Override
            public Client[] doInTransaction() {
                return ao.find(Client.class, Query.select().where("LAST_NAME = ? AND FIRST_NAME = ?", lastName, firstName));
            }
        });
    }

    //поиск по id
    public Client getClientById(final Integer id) {
        return ao.executeInTransaction(new TransactionCallback<Client>() {
            @Override
            public Client doInTransaction() {
                return ao.get(Client.class, id);
            }
        });
    }

    //добавить клиента
    public Client addClient(final String lastName, final String firstName,
                            final String middleName, final Date birthday, final String address,
                            final String phone, final String email) {
        return ao.executeInTransaction(new TransactionCallback<Client>() {
            @Override
            public Client doInTransaction() {
                final Client client = ao.create(Client.class);
                client.setLastName(lastName);
                client.setFirstName(firstName);
                client.setMiddleName(middleName);
                client.setBirthday(birthday);
                client.setAddress(address);
                client.setPhone(phone);
                client.setEmail(email);
                client.save();
                return client;
            }
        });
    }

    public Client updateClient(final Integer id, final String lastName, final String firstName,
                               final String middleName, final Date birthday, final String address,
                               final String phone, final String email) {
        return ao.executeInTransaction(new TransactionCallback<Client>() {
            @Override
            public Client doInTransaction() {
                Client client = ao.get(Client.class, id);
                client.setLastName(lastName);
                client.setFirstName(firstName);
                client.setMiddleName(middleName);
                client.setBirthday(birthday);
                client.setAddress(address);
                client.setPhone(phone);
                client.setEmail(email);
                client.save();
                return client;
            }
        });
    }

    public void deleteClient(final Integer id) {
        ao.executeInTransaction(new TransactionCallback<Client>() {
            @Override
            public Client doInTransaction() {
                Client client = ao.get(Client.class, id);
                Dog[] dogs = client.getDogs();
                for (Dog dog : dogs) {
                    ao.delete(dog);
                }
                ao.delete(client);
                return null;
            }
        });
    }
}
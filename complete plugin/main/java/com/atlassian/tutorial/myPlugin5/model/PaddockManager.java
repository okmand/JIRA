package com.atlassian.tutorial.myPlugin5.model;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.jira.bc.issue.IssueService;
import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.event.type.EventDispatchOption;
import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.issue.IssueInputParameters;
import com.atlassian.jira.issue.IssueManager;
import com.atlassian.jira.issue.MutableIssue;
import com.atlassian.jira.user.ApplicationUser;
import com.atlassian.jira.util.ErrorCollection;
import com.atlassian.sal.api.transaction.TransactionCallback;
import net.java.ao.Query;
import org.jfree.util.Log;

import java.sql.Timestamp;
import java.util.Date;

public class PaddockManager {
    private final ActiveObjects ao;
    private final Long pidPlace = 10307L;
    private final Long pidDuration = 10303L;
    private final Long pidDogs = 10306L;
    static int countSwitch = 0;

    public PaddockManager(ActiveObjects ao) {
        this.ao = ao;
    }

    public Paddock[] getAllPaddocks() {
        return ao.executeInTransaction(new TransactionCallback<Paddock[]>() {
            @Override
            public Paddock[] doInTransaction() {
                return ao.find(Paddock.class, Query.select().where(" ID > ? ", 0));
            }
        });
    }

    public Paddock getPaddockById(final Integer id) {
        return ao.executeInTransaction(new TransactionCallback<Paddock>() {
            @Override
            public Paddock doInTransaction() {
                return ao.get(Paddock.class, id);
            }
        });
    }

    public Paddock[] getPaddockByWalker(final Integer walkerId) {
        return ao.executeInTransaction(new TransactionCallback<Paddock[]>() {
            @Override
            public Paddock[] doInTransaction() {
                return ao.find(Paddock.class, Query.select().where(" WALKER_ID = ?", walkerId));
            }
        });
    }

    public static Issue createIssue(IssueInputParameters params, ApplicationUser user) {
        IssueService issueService = ComponentAccessor.getIssueService();
        IssueService.CreateValidationResult createValidationResult = issueService.validateCreate(user, params);

        if (createValidationResult.isValid()) {
            IssueService.IssueResult createResult = issueService.create(user, createValidationResult);
            if (!createResult.isValid()) {
                Log.warn("Something went wrong");
            } else {
                return createResult.getIssue();
            }
        } else {
            Log.warn("Parameters is not valid");
            ErrorCollection error = createValidationResult.getErrorCollection();
            Log.warn("Error collection: " + error.toString());
        }

        return null;
    }

    public Paddock addPaddock(final String place, final Date walkingTime, final Long wTime, final Integer duration,
                               final String status, final Walker walker, final Client client, final String idDogs) {
        return ao.executeInTransaction(new TransactionCallback<Paddock>() {
            @Override
            public Paddock doInTransaction() {
                String[] tempIdDog = idDogs.split("&");
                Integer[] idDog = new Integer[tempIdDog.length];
                for (int i = 0; i < idDog.length; i++) {
                    idDog[i] = Integer.parseInt(tempIdDog[i]);
                }
                Dog[] trueDogs = client.getDogs();
                String dogsForCustomField = "";
                boolean checkCreate = false;    //проверка, есть ли хоть одна собака - клиентовская
                for (Integer id : idDog) {
                    Dog dog = new DogManager(ao).getDogById(id);
                    for (Dog d : trueDogs) {
                        if (dog.getID() == d.getID())
                            checkCreate = true;
                    }
                    if (checkCreate)
                        break;
                }

                if (checkCreate) {
                    final Paddock paddock = ao.create(Paddock.class);
                    for (Integer id : idDog) {
                        Dog dog = new DogManager(ao).getDogById(id);
                        boolean check = false;
                        for (Dog d : trueDogs) {
                            if (dog.getID() == d.getID())
                                check = true;
                        }

                        if (check) {
                            dogsForCustomField += dog.getName() + "\n";
                            final PaddockToDog paddockToDog = ao.create(PaddockToDog.class);
                            paddockToDog.setPaddock(paddock);
                            paddockToDog.setDog(dog);
                            paddockToDog.save();
                        }
                    }

                    paddock.setPlace(place);
                    paddock.setWalkingTime(walkingTime);
                    paddock.setDuration(duration);
                    paddock.setStatus(status);
                    paddock.setWalker(walker);
                    paddock.setClient(client);

                    ApplicationUser user = ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser();

                    String nameWalker = walker.getFirstName() + " " + walker.getLastName();
                    String nameClient = client.getFirstName() + " " + client.getLastName();

                    IssueInputParameters issueInputParameters = ComponentAccessor.getIssueService().newIssueInputParameters();
                    issueInputParameters.setProjectId(10100L)
                            .setIssueTypeId("10200")
                            .setSummary("from " + nameClient + " to " + nameWalker)
                            .setDescription("Please take a walk with my dog")
                            .addCustomFieldValue(pidDuration, "" + duration)
                            .addCustomFieldValue(pidPlace, "" + place)
                            .addCustomFieldValue(pidDogs, dogsForCustomField)
                            .setAssigneeId(nameWalker);

                    Issue createdIssue = createIssue(issueInputParameters, user);

                    paddock.setLink("http://localhost:8080/browse/" + createdIssue.getKey());
                    paddock.setPID(createdIssue.getId());
                    paddock.save();

                    Timestamp timestamp = new Timestamp(wTime);
                    MutableIssue mutableCreatedIssue = (MutableIssue) createdIssue;
                    mutableCreatedIssue.setDueDate(timestamp);

                    IssueManager issueManager = ComponentAccessor.getIssueManager();
                    issueManager.updateIssue(user, mutableCreatedIssue, EventDispatchOption.DO_NOT_DISPATCH, true);

                    return paddock;
                } else
                    return null;
            }
        });
    }

    public static void updateIssue(IssueInputParameters params, ApplicationUser user, Long pidIssue) {
        IssueService issueService = ComponentAccessor.getIssueService();
        IssueService.UpdateValidationResult updateValidationResult = issueService.validateUpdate(user, pidIssue, params);

        if (updateValidationResult.isValid()) {
            IssueService.IssueResult updateResult = issueService.update(user, updateValidationResult);
            if (!updateResult.isValid()) {
                Log.warn("Something went wrong");
            }
        } else {
            Log.warn("Parameters is not valid");
            ErrorCollection error = updateValidationResult.getErrorCollection();
            Log.warn("Error collection: " + error.toString());
        }
    }

    public static void updateStatusIssue(ApplicationUser user, Long pidIssue) {
        IssueService issueService = ComponentAccessor.getIssueService();
        IssueInputParameters issueInputParameters = issueService.newIssueInputParameters();
        issueInputParameters.setSummary("I am a new summary");

        IssueService.TransitionValidationResult transitionValidationResult = issueService.validateTransition(user, pidIssue, 41, issueInputParameters);


        if (transitionValidationResult.isValid()) {
            IssueService.IssueResult transitionResult = issueService.transition(user, transitionValidationResult);

            if (!transitionResult.isValid()) {
                Log.warn("Something went wrong");
            }
        } else {
            Log.warn("Parameters is not valid");
        }

        ////
        IssueService issueService2 = ComponentAccessor.getIssueService();
        IssueInputParameters issueInputParameters2 = issueService2.newIssueInputParameters();
        issueInputParameters2.setSummary("I am a new summary");
        IssueService.TransitionValidationResult transitionValidationResult2;
        if (countSwitch % 2 == 0) {
            transitionValidationResult2 = issueService2.validateTransition(user, 10538L, 61, issueInputParameters2);
        } else {
            transitionValidationResult2 = issueService2.validateTransition(user, 10538L, 71, issueInputParameters2);
        }
        countSwitch++;
        if (transitionValidationResult2.isValid()) {
            IssueService.IssueResult transitionResult2 = issueService2.transition(user, transitionValidationResult2);

            if (!transitionResult2.isValid()) {
                Log.warn("Something went wrong");
            }
        } else {
            Log.warn("Parameters is not valid");
        }
        ////
    }

    public Paddock updateStatusPaddock(final Integer id, final String status) {
        return ao.executeInTransaction(new TransactionCallback<Paddock>() {
            @Override
            public Paddock doInTransaction() {
                final Paddock paddock = ao.get(Paddock.class, id);

                ApplicationUser user = ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser();
                updateStatusIssue(user, paddock.getPID());

                paddock.setStatus(status);
                paddock.save();
                return paddock;
            }
        });
    }

    public Paddock updatePaddock(final Integer id, final String place, final Date walkingTime, final long wTime, final Integer duration,
                                 final String status, final Walker walker, final Client client) {
        return ao.executeInTransaction(new TransactionCallback<Paddock>() {
            @Override
            public Paddock doInTransaction() {
                final Paddock paddock = ao.get(Paddock.class, id);
                paddock.setPlace(place);
                paddock.setWalkingTime(walkingTime);
                paddock.setDuration(duration);
                paddock.setStatus(status);
                paddock.setWalker(walker);
                paddock.setClient(client);

                ApplicationUser user = ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser();
                IssueService issueService = ComponentAccessor.getIssueService();
                IssueService.IssueResult issueResult = issueService.getIssue(user, paddock.getPID());

                MutableIssue mutableIssue = issueResult.getIssue();

                Timestamp timestamp = new Timestamp(wTime);
                mutableIssue.setDueDate(timestamp);
                IssueManager issueManager = ComponentAccessor.getIssueManager();
                issueManager.updateIssue(user, mutableIssue, EventDispatchOption.DO_NOT_DISPATCH, true);

                IssueInputParameters issueInputParameters = issueService.newIssueInputParameters();
                issueInputParameters.addCustomFieldValue(pidDuration, "" + duration)
                        .addCustomFieldValue(pidPlace, "" + place);

                updateIssue(issueInputParameters, user, paddock.getPID());
                ///
                issueInputParameters.setSummary("I am a new summary");

                IssueService.TransitionValidationResult transitionValidationResult = issueService.validateTransition(user, paddock.getPID(), 11, issueInputParameters);

                if (transitionValidationResult.isValid()) {
                    IssueService.IssueResult transitionResult = issueService.transition(user, transitionValidationResult);

                    if (!transitionResult.isValid()) {
                        Log.warn("Something went wrong");
                    }
                } else {
                    Log.warn("Parameters is not valid");
                }
                ///

                paddock.save();
                return paddock;
            }
        });
    }

    public Paddock deletePaddock(final Integer id) {
        return ao.executeInTransaction(new TransactionCallback<Paddock>() {
            @Override
            public Paddock doInTransaction() {
                final Paddock paddock = ao.get(Paddock.class, id);
                PaddockToDog[] paddockToDogs = ao.find(PaddockToDog.class, Query.select().where("PADDOCK_ID = ?", id));
                for (PaddockToDog paddockToDog : paddockToDogs) {
                    ao.delete(paddockToDog);
                }
                ao.delete(paddock);
                return null;
            }
        });
    }
}
package com.atlassian.tutorial.myPlugin5.servlet;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.jira.bc.user.search.UserSearchService;
import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.issue.MutableIssue;
import com.atlassian.jira.user.ApplicationUser;
import com.atlassian.jira.user.util.UserManager;
import com.atlassian.plugin.Application;
import com.atlassian.templaterenderer.TemplateRenderer;
import com.atlassian.tutorial.myPlugin5.model.*;
import com.atlassian.tutorial.myPlugin5.model_OLD.Product;
import com.atlassian.tutorial.myPlugin5.model_OLD.ProductManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServletChoice extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(ServletChoice.class);
    private final TemplateRenderer templateRenderer;
    private final ActiveObjects ao;

    @Inject
    public ServletChoice(TemplateRenderer templateRenderer, ActiveObjects ao) {
        this.templateRenderer = templateRenderer;
        this.ao = ao;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //разные возможности ComponentAccessor + user:
        //getKey()
       /* final PrintWriter w = resp.getWriter();
        w.write("<h2>" + ComponentAccessor.getGroupManager().getGroup("client")+"</h2>");
        w.write("<br>");
        ApplicationUser user = ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser();
        w.write("<h1>" + ComponentAccessor.getGroupManager() + ",\t" + user.getDisplayName() + ",\t" + user.getName() +
                ",\t" + user.getEmailAddress() + ",\t" + user.getKey() + ",\t" + user.getUsername() + ",\t" + user.getDirectoryId() +
                ",\t" + user.getDirectoryUser() + ",\t" + user + ",\t" + user.getId() + ",\t" + user.getClass() + "</h1>");
        ComponentAccessor.getGroupManager();
        w.write("<br>");
        w.write("<br>");
        w.write("<br>");

        String fullNam = "Jack Miller";
        ApplicationUser us = ComponentAccessor.getUserSearchService().getUserByName();
        UserSearchService userSearchService = new DefaultUser;
        Iterable<String> jkj = UserSearchService.findUserKeysByFullName(fullNam);
        ApplicationUser user2;*/


        Map<String, Object> context = new HashMap<>();
        boolean checkClient = false;
        boolean checkWalker = false;
        Collection<String> groups = ComponentAccessor.getGroupManager().getGroupNamesForUser(
                ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser().getName());
        for (String group : groups) {
            if (group.equals("client"))
                checkClient = true;

            if (group.equals("walker"))
                checkWalker = true;
        }


        ApplicationUser user = ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser();
        String userName = user.getDisplayName();
        context.put("userName", userName); //имя пользователя
        String firstName = null, lastName = null;
        if (checkClient || checkWalker) {
            String[] words = userName.split(" ");
            firstName = words[0];
            lastName = words[1];
        }
        if (checkClient) {
            Client[] clients = new ClientManager(ao).getClientByLastNameFirstName(lastName, firstName);
            if (clients != null) {
                Client client = clients[0];
                Dog[] dogs = client.getDogs();
                Paddock[] paddocks = client.getPaddock();
                Walker[] walkers = new WalkerManager(ao).getAllWalkers();
                context.put("dogs", dogs);
                context.put("client", client);
                context.put("paddocks", paddocks);
                context.put("walkers", walkers);
                templateRenderer.render("/templates/forclient.vm", context, resp.getWriter());
            } else {
                final PrintWriter w = resp.getWriter();
                w.write("<h1>Your user name does not match the name in the database, please contact your administrator</h1>");
            }
        } else if (checkWalker) {
            Walker[] walkers = new WalkerManager(ao).getWalkerByLastNameFirstName(lastName, firstName);
            if (walkers != null) {
                Walker walker = walkers[0];
                Paddock[] paddocks = walker.getPaddock();
                context.put("walker", walker);
                context.put("paddocks", paddocks);
                templateRenderer.render("/templates/forwalker.vm", context, resp.getWriter());
            } else {
                final PrintWriter w = resp.getWriter();
                w.write("<h1>Your user name does not match the name in the database, please contact your administrator</h1>");
            }
        } else {
            final PrintWriter w = resp.getWriter();
            w.write("<h1>I am sorry, but you are neither a Client nor a Walker</h1>");
        }
    }
}
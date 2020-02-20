package com.atlassian.tutorial.myPlugin5.servlet;
import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.templaterenderer.TemplateRenderer;
import com.atlassian.tutorial.myPlugin5.model.Walker;
import com.atlassian.tutorial.myPlugin5.model.WalkerManager;
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
import java.util.Map;

public class ServletWalker extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(ServletWalker.class);
    private final TemplateRenderer templateRenderer;
    private final ActiveObjects ao;

    @Inject
    public ServletWalker(TemplateRenderer templateRenderer, ActiveObjects ao) {
        this.templateRenderer = templateRenderer;
        this.ao = ao;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        boolean checkAdmin = false;
        Collection<String> groups = ComponentAccessor.getGroupManager().getGroupNamesForUser(
                ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser().getName());
        for (String group : groups) {
            if (group.equals("jira-administrators"))
                checkAdmin = true;
        }
        if (checkAdmin) {
            Map<String, Object> context = new HashMap<>();
            Walker[] walkers = new WalkerManager(ao).getAllWalkers();
            context.put("walkers", walkers);
            templateRenderer.render("/templates/walker.vm", context, resp.getWriter());
        } else {
            final PrintWriter w = resp.getWriter();
            w.write("<h1>I am sorry, but you are not an Administrator</h1>");
        }
    }
}
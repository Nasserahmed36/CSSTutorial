package com.atypon.web.listener;


import com.atypon.backstage.BackstageConsumer;
import com.atypon.backstage.BackstageConsumerImpl;
import com.atypon.domain.ArticleSubmission;
import com.atypon.domain.dao.*;
import com.atypon.service.*;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

@WebListener
public class AppListener implements ServletContextListener {

    private BackstageConsumer backstageConsumer;


    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();

        DataSource dataSource = getDataSource(servletContext);
        servletContext.setAttribute("datasource", dataSource);

        ArticleSubmissionDao articleSubmissionDao = new ArticleSubmissionDaoImpl(dataSource, );
        servletContext.setAttribute("articleSubmissionDao", articleSubmissionDao);

        ArticleSubmissionService articleSubmissionService = new ArticleSubmissionServiceImpl(articleSubmissionDao);
        servletContext.setAttribute("articleSubmissionService", articleSubmissionService);

        IdentityDao identityDao = new IdentityDaoImpl(dataSource);
        servletContext.setAttribute("identityDao", identityDao);

        IdentityService identityService = new IdentityServiceImpl(identityDao);
        servletContext.setAttribute("identityService", identityService);

        JournalDao journalDao = new JournalDaoImpl(dataSource);
        servletContext.setAttribute("journalDao", journalDao);

        JournalService journalService = new JournalServiceImpl(journalDao);
        servletContext.setAttribute("journalService", journalService);

        int queueSize = Integer.parseInt(sce.getServletContext().getInitParameter("submissionsQueueSize"));
        BlockingQueue<ArticleSubmission> submissionsQueue = new ArrayBlockingQueue<>(queueSize);
        servletContext.setAttribute("submissionsQueue", submissionsQueue);

        backstageConsumer = new BackstageConsumerImpl(submissionsQueue);
        backstageConsumer.start();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        backstageConsumer.stop();
    }

    private DataSource getDataSource(ServletContext servletContext) {
        DataSource ds = null;
        try {
            Context context = new InitialContext();
            ds = (DataSource) context.lookup("java:/comp/env/" +
                    servletContext.getInitParameter("datasourceEnv"));
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
        return ds;
    }

}
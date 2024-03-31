package test;

import java.time.LocalDateTime;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ContextListener implements ServletContextListener {
    private static final String MAIN_HEADER = "C H I P M U N C K S";
    private static final String MAIN_FOOTER = "© 2024";
    private static final String FOOTER_MESSAGE = "SOMETIMES IT TAKES A REAL MAN TO BECOME BEST GIRL";
    private static final String HEADER_ATTRIBUTE = "header";
    private static final String FOOTER_ATTRIBUTE = "footer";
    private static final String INITIALIZATION_TIME_ATTRIBUTE = "hiddenheader";
    private static final String HIDDEN_FOOTER_ATTRIBUTE = "hiddenfooter";

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        if (servletContext != null) {
            servletContext.setAttribute(HEADER_ATTRIBUTE, MAIN_HEADER);
            servletContext.setAttribute(FOOTER_ATTRIBUTE, MAIN_FOOTER);
            servletContext.setAttribute(INITIALIZATION_TIME_ATTRIBUTE, LocalDateTime.now().toString());
            servletContext.setAttribute(HIDDEN_FOOTER_ATTRIBUTE, FOOTER_MESSAGE);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        if (servletContext != null) {
            String[] attributeNames = {HEADER_ATTRIBUTE, FOOTER_ATTRIBUTE, INITIALIZATION_TIME_ATTRIBUTE, HIDDEN_FOOTER_ATTRIBUTE};
            for (String attributeName : attributeNames) {
                servletContext.removeAttribute(attributeName);
            }
        }
    }
}
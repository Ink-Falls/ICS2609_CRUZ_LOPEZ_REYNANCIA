package test;

import java.time.LocalDateTime;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ContextListener implements ServletContextListener {
    private static final String MAIN_HEADER = "C H I P M U N C K S";
    private static final String MAIN_FOOTER = "© 2024";
    private static final String HIDDEN_FOOTER_MESSAGE = "SOMETIMES IT TAKES A REAL MAN TO BECOME BEST GIRL";

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        if (servletContext != null) {
            servletContext.setAttribute("header", MAIN_HEADER);
            servletContext.setAttribute("footer", MAIN_FOOTER);
            servletContext.setAttribute("hiddenheader", LocalDateTime.now().toString());
            servletContext.setAttribute("hiddenfooter", HIDDEN_FOOTER_MESSAGE);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        if (servletContext != null) {
            String[] attributeNames = {"header", "footer", "hiddenheader", "hiddenfooter"};
            for (String attributeName : attributeNames) {
                servletContext.removeAttribute(attributeName);
            }
        }
    }
}
package test;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext attribute = sce.getServletContext();
        attribute.setAttribute("header", "C H I P M U N C K S");
        attribute.setAttribute("footer", "© 2024");
        attribute.setAttribute("hiddenheader", new java.util.Date());
        attribute.setAttribute("hiddenfooter", "SOMETIMES IT TAKES A REAL MAN TO BECOME BEST GIRL");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        
    }
}
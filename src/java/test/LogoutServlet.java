package test;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutServlet extends HttpServlet {

    private static final String REDIRECT_URL = "/index.jsp";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        invalidateSession(request);
        redirectToIndex(response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!invalidateSession(request)) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid session");
            return;
        }
        response.setHeader("Cache-Control", "no-store");
        redirectToIndex(response);
    }

    private boolean invalidateSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
            return true;
        }
        return false;
    }

    private void redirectToIndex(HttpServletResponse response) throws IOException {
        response.sendRedirect(REDIRECT_URL);
    }
}

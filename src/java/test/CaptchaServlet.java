package test;

import java.util.*;
import java.io.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/captcha")
public class CaptchaServlet extends HttpServlet {

    // Returns true if given two strings are same
    static boolean checkCaptcha(String captcha, String user_captcha) {
        return captcha.equals(user_captcha);
    }

    // Generates a CAPTCHA of given length
    static String generateCaptcha(int n) {
        Random rand = new Random();

        // Characters to be included
        String chrs = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

        // Generate n characters from above set and
        // add these characters to captcha.
        String captcha = "";
        while (n-- > 0) {
            int index = rand.nextInt(chrs.length());
            captcha += chrs.charAt(index);
        }

        return captcha;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Generate a random CAPTCHA
        String captcha = generateCaptcha(6);
        // Store the captcha in the session
        HttpSession session = req.getSession();
        session.setAttribute("captcha", captcha);
        // For simplicity, send the captcha as plain text
        resp.setContentType("text/plain");
        resp.getWriter().write(captcha);
    }
}
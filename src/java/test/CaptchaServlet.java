package test;

import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Generate a random CAPTCHA
        String captcha = generateCaptcha(6);

        // Store the captcha in the session
        HttpSession session = request.getSession();

        session.setAttribute("captcha", captcha);

        // Generate CAPTCHA image
        int width = 160, height = 40;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = image.createGraphics();

        // Set background and text color, font etc.
        graphics.setColor(Color.LIGHT_GRAY);
        graphics.fillRect(0, 0, width, height);
        graphics.setColor(Color.BLACK);
        graphics.setFont(new Font("Arial", Font.BOLD, 24));

        // Draw the CAPTCHA string
        graphics.drawString(captcha, 10, 25);

        // Finish up
        graphics.dispose();

        // Set response content type to image

        response.setContentType("image/jpeg");

        ImageIO.write(image, "jpeg", response.getOutputStream());
    }
}
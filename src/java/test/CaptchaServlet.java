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
        String captcha = generateCaptcha(5);

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
        // Add noise
        for (int i = 0; i < 150; i++) {
            int x = (int) (Math.random() * width);
            int y = (int) (Math.random() * height);
            int gray = (int) (Math.random() * 255);
            graphics.setColor(new Color(gray, gray, gray));
            graphics.fillRect(x, y, 1, 1);
        }
        // Draw the CAPTCHA string with some distortion and bigger font
        for (int i = 0; i < captcha.length(); i++) {
            int xOffset = i * 30 + 10; // Increase the horizontal spacing for larger text
            int yOffset = 35 + (int) (Math.random() * 10) - 5; // Add some vertical distortion
            // Set a darker color for the text
            int darkGray1 = (int) (Math.random() * 200);
            int darkGray2 = (int) (Math.random() * 200);
            int darkGray3 = (int) (Math.random() * 200);
            graphics.setColor(new Color(darkGray1, darkGray2, darkGray3));
            // Set a bigger font size
            graphics.setFont(new Font("Tahoma", Font.BOLD, 30)); // Adjust the font size as needed
            graphics.drawString(String.valueOf(captcha.charAt(i)), xOffset, yOffset);
        }

        // Draw random lines
        for (int i = 0; i < 4; i++) {
            graphics.setColor(
                    new Color((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255)));
            graphics.drawLine((int) (Math.random() * width), (int) (Math.random() * height),
                    (int) (Math.random() * width), (int) (Math.random() * height));
        }

        // Finish up
        graphics.dispose();

        // Set response content type to image

        response.setContentType("image/jpeg");

        ImageIO.write(image, "jpeg", response.getOutputStream());
    }
}
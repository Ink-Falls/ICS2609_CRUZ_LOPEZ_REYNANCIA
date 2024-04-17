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

    private static final String CHRS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final Random rand = new Random();

    // Generates a CAPTCHA of given length
    static String generateCaptcha(int n) {
        String captcha = "";
        while (n-- > 0) {
            int index = rand.nextInt(CHRS.length());
            captcha += CHRS.charAt(index);
        }

        return captcha;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Generate a random CAPTCHA
        String length = request.getServletContext().getInitParameter("CAPTCHA_LENGTH");
        System.out.println("CAPTCHA Length from context: " + length); // Debugging line
        int captchaLength = Integer.parseInt(length);
        String captcha = generateCaptcha(captchaLength);

        // Store the captcha in the session
        HttpSession session = request.getSession();

        session.setAttribute("captcha", captcha);

        // Generate CAPTCHA image
        final int CAPTCHA_WIDTH_MULTIPLIER = 30;
        final int CAPTCHA_HEIGHT = 40;
        final int MAX_NOISE = 150;
        final int MAX_COLOR_VALUE = 255;

        int width = CAPTCHA_WIDTH_MULTIPLIER * captcha.length() + 20;
        int height = CAPTCHA_HEIGHT;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = image.createGraphics();

        // Set background and text color, font etc.
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, width, height);

        // Add noise
        Random random = new Random();
        for (int i = 0; i < MAX_NOISE; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int gray = random.nextInt(MAX_COLOR_VALUE);
            graphics.setColor(new Color(gray, gray, gray));
            graphics.fillRect(x, y, 1, 1);
        }

        // Draw the CAPTCHA string with some distortion and bigger font
        for (int i = 0; i < captcha.length(); i++) {
            int xOffset = i * CAPTCHA_WIDTH_MULTIPLIER + 10; // Increase the horizontal spacing for larger text
            if (xOffset + CAPTCHA_WIDTH_MULTIPLIER > width) {
                width = xOffset + CAPTCHA_WIDTH_MULTIPLIER; // Ensure the image width accommodates the CAPTCHA
                image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                graphics = image.createGraphics();

                graphics.setColor(Color.WHITE);

                graphics.fillRect(0, 0, width, height);
            }
            int yOffset = 35 + random.nextInt(10) - 5; // Add some vertical distortion
            // Set a darker color for the text
            int darkGray1 = random.nextInt(200);
            int darkGray2 = random.nextInt(200);
            int darkGray3 = random.nextInt(200);
            graphics.setColor(new Color(darkGray1, darkGray2, darkGray3));
            // Set a bigger font size
            graphics.setFont(new Font("Tahoma", Font.BOLD, 30)); // Adjust the font size as needed
            graphics.drawString(String.valueOf(captcha.charAt(i)), xOffset, yOffset);
        }

        // Draw random lines
        for (int i = 0; i < 4; i++) {
            graphics.setColor(
                    new Color(random.nextInt(MAX_COLOR_VALUE), random.nextInt(MAX_COLOR_VALUE),
                            random.nextInt(MAX_COLOR_VALUE)));
            graphics.drawLine(random.nextInt(width), random.nextInt(height),
                    random.nextInt(width), random.nextInt(height));
        }

        // Finish up
        graphics.dispose();

        // Set response content type to image
        response.setContentType("image/jpeg");

        ImageIO.write(image, "jpeg", response.getOutputStream());
    }
}

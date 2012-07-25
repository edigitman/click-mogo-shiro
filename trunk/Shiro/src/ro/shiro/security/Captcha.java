package ro.shiro.security;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Captcha
 */
@WebServlet("/servlet/capt")
public class Captcha extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Captcha() {
		super();
	}

	private char[] special = { '*', '@', '#', '$', '%', '^', '&', '=', '+',
			'!', '?' };

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String key = getKey();

		request.getSession().setAttribute("captcha", key.replaceAll(" ", ""));

		int X = 100;
		int Y = 22;

		BufferedImage bi = new BufferedImage(X, Y, BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D g = (Graphics2D) bi.getGraphics();

		g.setColor(Color.gray);
		g.fillRect(0, 0, X, Y);
		g.setFont(new Font("SansSerif", Font.BOLD, 20));
		g.setColor(new Color(123));
		g.drawString(key, 5, 20);
		
		g.setColor(Color.ORANGE);
		g.fillRect(0, 8, X, 1);
		g.fillRect(0, 14, X, 1);
		
		response.setContentType("image/jpeg");
		response.setContentLength(X * Y);

		OutputStream out = response.getOutputStream();
		ImageIO.write(bi, "jpg", out);
		out.close();
		g.dispose();
	}

	private String getKey() {
		Random random = new Random(System.currentTimeMillis());
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < 5; i++) {
			switch (random.nextInt(3)) {
			case 0:
				sb.append(getNumber(random.nextInt()));
				break;
			case 1:
				sb.append(getAlfa(random.nextInt()));
				break;
			case 2:
				sb.append(getSpecial(random.nextInt()));
				break;
			}
			sb.append(" ");
		}

		return sb.toString();
	}

	private String getNumber(int n) {
		return String.valueOf(Math.abs(n % 10));
	}

	private String getAlfa(int n) {
		return String.valueOf((char) (97 + Math.abs(n % 24)));
	}

	private String getSpecial(int n) {
		return String.valueOf(special[Math.abs(n % special.length)]);
	}
}

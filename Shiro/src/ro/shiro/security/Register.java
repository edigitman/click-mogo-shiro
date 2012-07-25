package ro.shiro.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ro.shiro.custom.AppUtils;

/**
 * Servlet implementation class Register
 */
@WebServlet("/servlet/register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Register() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String key = req.getParameter("key");
		String user = req.getParameter("u");
		boolean isLogedIn;
		try {
			String ctx = req.getContextPath();
			isLogedIn = AppUtils.validateAccount(user, key);
			if (isLogedIn) {
				resp.sendRedirect(ctx + "/p/main.htm");
				return;
			} else {
				resp.sendRedirect(ctx + "/newAccout.htm");
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

package com.olympus.nbva.update;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/nbvapost")
public class NVBAdoPost extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String dispatchJSP = "/nbvadetail_flex.jsp";
		
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(dispatchJSP);
		dispatcher.forward(request, response);
		
	}
}

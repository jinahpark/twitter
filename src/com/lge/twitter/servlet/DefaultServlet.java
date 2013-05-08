package com.lge.twitter.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lge.twitter.cmd.CreateCmd;
import com.lge.twitter.cmd.DeleteCmd;
import com.lge.twitter.cmd.ListCmd;
import com.lge.twitter.cmd.UpdateCmd;


/**
 * Servlet implementation class defaultServlet
 */
public class DefaultServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DefaultServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doService(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doService(request, response);
	}
	
	void doService(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		if(request.getRequestURI().startsWith("/twitter/api/list")){
			new ListCmd().execute(request, response);

		}
		else if(request.getRequestURI().startsWith("/twitter/api/update")){
			new UpdateCmd().execute(request, response);
		}
		else if(request.getRequestURI().startsWith("/twitter/api/create")){
			new CreateCmd().execute(request, response);
			
		}
		else if(request.getRequestURI().startsWith("/twitter/api/delete")){
			new DeleteCmd().execute(request, response);
		}
		else{
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
	}
}

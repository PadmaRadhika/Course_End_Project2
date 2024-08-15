package com.example.zumbaproject.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.example.zumbaproject.database.BatchDAO;
import com.example.zumbaproject.database.ParticipantDAO;
import com.example.zumbaproject.model.Batch;
import com.example.zumbaproject.model.Participant;

/**
 * Servlet implementation class LandingServlet
 */
public class LandingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BatchDAO batchDao = new BatchDAO();
	private ParticipantDAO participantDao = new ParticipantDAO();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LandingServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Gets the batch and participants information from database and stores in session object
		// to populate dashboard, participants and batch dropdowns in update batch and update participant functionalities
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		List<Batch> list = new ArrayList<Batch>();
		List<Participant> participantList = new ArrayList<Participant>();
		HttpSession session = request.getSession();		
		String requestType = request.getParameter("type");
		try {
			list = batchDao.getAll();	
			participantList = participantDao.getAll();
			session.setAttribute("batches", list);
			session.setAttribute("participants", participantList);
			/*if(("landingPage").equals(requestType))
			{*/
				RequestDispatcher dispatcher = request.getRequestDispatcher("/dashboard.jsp");
				dispatcher.forward(request, response);
			//}
           
			
		}catch(Exception e){
			throw new ServletException("Database error: "+e);	
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

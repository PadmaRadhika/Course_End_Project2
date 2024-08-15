package com.example.zumbaproject.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.example.zumbaproject.database.ParticipantDAO;
import com.example.zumbaproject.model.Batch;
import com.example.zumbaproject.model.Participant;

/**
 * Servlet implementation class ParticipantServlet
 */
public class ParticipantServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ParticipantDAO participantDao = new ParticipantDAO();
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ParticipantServlet() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * This method is to check if a string is null or empty
	 * @param str
	 * @return
	 */
	private boolean isStringNotNull(String str) {
		if(str != null && !str.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * This method is to replace a null string with empty string
	 * @param str
	 * @return
	 */
	private String replaceNullString(String str) {
		return isStringNotNull(str) ? str : "";
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		Participant participant = new Participant();
		int selectedParticipantId = 0;
		String requestType = request.getParameter("type");
		String selectedParticipant = request.getParameter("namelist");
		if(isStringNotNull(selectedParticipant) && ("populateParticipant").equals(requestType)) {
			try {
				selectedParticipantId = Integer.parseInt(selectedParticipant);
				participant = participantDao.get(selectedParticipantId);
				request.setAttribute("selectedParticipantObject", participant);			
				RequestDispatcher dispatcher = request.getRequestDispatcher("/updateparticipant.jsp");
				dispatcher.forward(request, response);
			} catch(Exception e) {
				request.setAttribute("errorMessage", e.getMessage());
				request.getRequestDispatcher("/updateparticipant.jsp").forward(request, response);
				return;
			}
		}
		
	}
	/**
	 * This method is to load the Participant Object to be processed by Database Prepared Statement
	 * @param request
	 * @param batch
	 * @return
	 */
	private Participant loadParticipantObect(HttpServletRequest request, Participant participant) {
		participant.setName(replaceNullString(request.getParameter("name")));
		participant.setPhone(replaceNullString(request.getParameter("phone")));
		participant.setEmail(replaceNullString(request.getParameter("email")));
		participant.setBid(Integer.parseInt(request.getParameter("bid")));
		return participant;
	}	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {	
		HttpSession session = request.getSession();
		List<Participant> pList = new ArrayList<Participant>();
		String requestType = request.getParameter("type");
		String pid = request.getParameter("pid");
		Participant participant = new Participant();
		int selectedParticipantId = 0;
		List<Integer> batchCountList = new ArrayList<Integer>();
		int result = 0;
		boolean isBatchFull = false;
		//if the request is from update participant page, and the selected participant id is not null
		//then update db with partcipant's modified information.
		if(("updateParticipant").equals(requestType) && isStringNotNull(pid))
		{	
			participant = loadParticipantObect(request, participant);
			selectedParticipantId = Integer.parseInt(pid);
			participant.setPid(selectedParticipantId);
			try {
				batchCountList = participantDao.getParticipantsPerBatch();
				//if the selected batch is full(capacity attribute in batch object), then prompt the user to select another batch
				if(batchCountList != null && batchCountList.size()>0){
					if(!batchCountList.contains(participant.getBid()) || (Integer.parseInt(request.getParameter("selectedParticipantBid")) == participant.getBid())){
						result = participantDao.update(participant);
					}
					else
					{
						request.setAttribute("errorMessage", "Selected batch is full, Please select another batch");
						isBatchFull = true;
					}
				} else {
					result = participantDao.update(participant);
				}				
				if(result > 0) {
					request.setAttribute("successMessage", "Participant Object updated successfully!");
				} else {
					if(!isBatchFull)
						request.setAttribute("errorMessage", "Participant Object not updated successfully");
				}
				RequestDispatcher dispatcher = request.getRequestDispatcher("/updateparticipant.jsp");
				dispatcher.forward(request, response);
				
			}catch(Exception e){
				request.setAttribute("errorMessage", e.getMessage());
				request.getRequestDispatcher("/updateparticipant.jsp").forward(request, response);
				return;	
			}
		} 
		else if (("addParticipant").equals(requestType)) //if the request is from add participant page, then save the participant info in db
		{						
			
			participant = loadParticipantObect(request, participant);
			
			try {				
					batchCountList = participantDao.getParticipantsPerBatch();
					if(batchCountList != null && batchCountList.size()>0){ //checks if the selected batch is full or available
						if(!batchCountList.contains(participant.getBid()))
						{
							result = participantDao.save(participant);
						}
						else
						{
							request.setAttribute("errorMessage", "Selected batch is full, Please select another batch");
							isBatchFull = true;
						}
					}
					else 
					{
						result = participantDao.save(participant);
					}		
		
					if(result > 0) {
						request.setAttribute("successMessage", "Participant Object saved successfully!");
					} else {
						if(!isBatchFull)
							request.setAttribute("errorMessage", "Participant Object not saved successfully");
					}
					pList = participantDao.getAll();
					session.setAttribute("participants", pList);
					RequestDispatcher dispatcher = request.getRequestDispatcher("/addparticipant.jsp");
					dispatcher.forward(request, response);
				}catch(Exception e){
					request.setAttribute("errorMessage", e.getMessage());
					request.getRequestDispatcher("/addparticipant.jsp").forward(request, response);
					return;	
				}
		} else {
			request.setAttribute("errorMessage", "Participant Object not updated successfully, Please select a participant to update");
			request.getRequestDispatcher("/updateparticipant.jsp").forward(request, response);
		}
	}

}

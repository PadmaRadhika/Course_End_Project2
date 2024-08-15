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

import com.example.zumbaproject.database.BatchDAO;
import com.example.zumbaproject.database.DAO;
import com.example.zumbaproject.model.Batch;

/**
 * Servlet implementation class BatchServlet
 */
public class BatchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BatchDAO batchDao = new BatchDAO();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BatchServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Gets existing Batches information from database to populate batches dropdown in update batch functionality		
		Batch batch = new Batch();
		int selectedBatchId = 0;
		String requestType = request.getParameter("type");
		String selectedBatch = request.getParameter("batchlist");
		if(isStringNotNull(selectedBatch) && ("populateBatch").equals(requestType)) {
			try {
				selectedBatchId = Integer.parseInt(selectedBatch);
				batch = batchDao.get(selectedBatchId);
				request.setAttribute("selectedBatchObject", batch);			
				RequestDispatcher dispatcher = request.getRequestDispatcher("/updatebatch.jsp");
				dispatcher.forward(request, response);
			} catch(Exception e) {
				request.setAttribute("errorMessage", e.getMessage());
				request.getRequestDispatcher("/updatebatch.jsp").forward(request, response);
				return;
			}
		}
	}
	
	/**
	 * This method is to load the Batch Object to be processed by Database Prepared Statement
	 * @param request
	 * @param batch
	 * @return
	 */
	private Batch loadBatchObject(HttpServletRequest request, Batch batch) {
		batch.setBatchName(replaceNullString(request.getParameter("name")));
		batch.setBatchTime(replaceNullString(request.getParameter("time")));		
		batch.setCapacity(Integer.parseInt(!isStringNotNull(request.getParameter("capacity")) ? "0" : request.getParameter("capacity")));		
		batch.setInstructorName(replaceNullString(request.getParameter("instructor")));
		return batch;
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
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		HttpSession session = request.getSession();
		List<Batch> bList = new ArrayList<Batch>();
		Batch batch = new Batch();
		String bid = request.getParameter("bid");
		String requestType = request.getParameter("type");
		int result = 0;	
		int selectedBatchId = 0;
		//If the request is from update batch and the selected batch id(from batch dropdown in update batch page) is not null
		//then update the selected batch with the modified information
		if(("updateBatch").equals(requestType) && isStringNotNull(bid))
		{
			batch = loadBatchObject(request, batch);
			try {
				selectedBatchId = Integer.parseInt(bid);
				batch.setBid(selectedBatchId);
				result = batchDao.update(batch);
				if(result > 0) {
					request.setAttribute("successMessage", "Batch Object updated successfully!");
				} else {
					request.setAttribute("errorMessage", "Batch Object not updated successfully");
				}
				
				RequestDispatcher dispatcher = request.getRequestDispatcher("/updatebatch.jsp");
				dispatcher.forward(request, response);
				
			}catch(Exception e){				
				request.setAttribute("errorMessage", e.getMessage());
				request.getRequestDispatcher("/updatebatch.jsp").forward(request, response);
				return;	
			}
		} 
		else if (("addBatch").equals(requestType))//If the request is from add batch page, save the batch in database
		{
						
			batch = loadBatchObject(request, batch);
			try {
				result = batchDao.save(batch);
				if(result > 0) {
					request.setAttribute("successMessage", "Batch Object saved successfully!");
				} else {
					request.setAttribute("errorMessage", "Batch Object not saved successfully");
				}				
				bList = batchDao.getAll();
				session.setAttribute("batches", bList);
				}catch(Exception e){					
					request.setAttribute("errorMessage", e.getMessage());
					request.getRequestDispatcher("/addbatch.jsp").forward(request, response);
					return;	
				}
			RequestDispatcher dispatcher = request.getRequestDispatcher("/addbatch.jsp");
			dispatcher.forward(request, response);
		}
	}

}

package com.example.zumbaproject.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.zumbaproject.model.Batch;

public class BatchDAO implements DAO<Batch>{
	Database db = Database.getInstance();
	/**
	 * To load the java object from database result set
	 * @param rs
	 * @param batch
	 * @return
	 */
	private Batch loadBatchObject(ResultSet rs, Batch batch) {
		try {
			batch.setBid(rs.getInt("id"));
			batch.setBatchName(rs.getString("batch_name"));
			batch.setBatchTime(rs.getString("batch_time"));
			batch.setCapacity(rs.getInt("capacity"));			
			batch.setInstructorName(rs.getString("instructor_name"));
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
			return batch;
	}
	/**
	 * To get a record from database by passing its id
	 */
	@Override
	public Batch get(long id) {
		String selectSql = "SELECT * FROM Batch WHERE id = ?";
		try (Connection connection = db.getConnection();
				PreparedStatement ps = connection.prepareStatement(selectSql)) {
			ps.setLong(1, id);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					Batch batch = new Batch();
					batch = loadBatchObject(rs, batch);				
					return batch;
				} else {
					return null;
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * To get all the batch records from database
	 */
	@Override
	public List<Batch> getAll() {
		String selectSql = "SELECT * FROM Batch";
		List<Batch> list = new ArrayList<Batch>();
		try (Connection connection = db.getConnection();
				PreparedStatement ps = connection.prepareStatement(selectSql)) {			
			try (ResultSet rs = ps.executeQuery()) {
				Batch batch = null;
				while (rs.next()) {
					batch = new Batch();
					batch = loadBatchObject(rs, batch);					
					list.add(batch);
				} 
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return list;
	}
	/**
	 *To save a record in database
	 */
	@Override
	public int save(Batch object) {
		int result = 0;
		String insertSql = "INSERT INTO Batch (batch_name, batch_time, capacity, instructor_name) VALUES (?, ?, ?, ?)";
		try (Connection connection = db.getConnection();
				PreparedStatement ps = connection.prepareStatement(insertSql)) {			
			setDBPreparedStatement(ps, object);
			result = db.executeUpdate(ps);
		} catch (Exception e) {
			// TODO: handle exception			
			throw new RuntimeException(e);
		}
		return result;
	}
	/**
	 * To update a record in database
	 *
	 */
	@Override
	public int update(Batch object) {
		int result = 0;
		String updateSql = "UPDATE Batch SET batch_name = ?, batch_time = ?, capacity = ?, instructor_name = ?  WHERE id = ?";
		try (Connection connection = db.getConnection();
				PreparedStatement ps = connection.prepareStatement(updateSql)) {			
			setDBPreparedStatement(ps, object);
			ps.setInt(5, object.getBid());			
			result = db.executeUpdate(ps);			
		} catch (Exception e) {
			// TODO: handle exception
			throw new RuntimeException(e);
		}
		return result;
	}
	/**
	 * To set the preparedstatement with the passed java object
	 * @param ps
	 * @param object
	 */
	private void setDBPreparedStatement(PreparedStatement ps, Batch object) {
		try {
			ps.setString(1, object.getBatchName());
			ps.setString(2, object.getBatchTime());
			ps.setInt(3, object.getCapacity());						
			ps.setString(4, object.getInstructorName());
		}catch (Exception e) {
			// TODO: handle exception
			throw new RuntimeException(e);
			
		}
		
	}
	/**
	 *To delete a db record
	 */
	@Override
	public void delete(long id) {
		// TODO Auto-generated method stub
		
	}
	

}

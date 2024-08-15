package com.example.zumbaproject.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.zumbaproject.model.Participant;

public class ParticipantDAO implements DAO<Participant>{
	Database db = Database.getInstance();
	
	/**
	 * To load the java object with database result set data
	 * @param rs
	 * @param participant
	 * @return
	 */
	private Participant loadParticipantObject(ResultSet rs, Participant participant) {
		try {
			participant.setPid(rs.getInt("id"));
			participant.setName(rs.getString("fullname"));
			participant.setPhone(rs.getString("phone"));
			participant.setEmail(rs.getString("email"));
			participant.setBid(rs.getInt("batchid"));
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
			return participant;
	}

	/**
	 * To get a database record using its id
	 *
	 */
	@Override
	public Participant get(long id) {
		String selectSql = "SELECT * FROM Participant WHERE id = ?";
		try (Connection connection = db.getConnection();
				PreparedStatement ps = connection.prepareStatement(selectSql)) {
			ps.setLong(1, id);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					Participant participant = new Participant();
					participant = loadParticipantObject(rs, participant);				
					return participant;
				} else {
					return null;
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
			
		}
	}

	/**
	 * To get all participant records from database
	 *
	 */
	@Override
	public List<Participant> getAll() {
		String selectSql = "SELECT * FROM Participant";
		List<Participant> list = new ArrayList<Participant>();
		try (Connection connection = db.getConnection();
				PreparedStatement ps = connection.prepareStatement(selectSql)) {			
			try (ResultSet rs = ps.executeQuery()) {
				Participant participant = null;
				while (rs.next()) {
					participant = new Participant();
					participant = loadParticipantObject(rs, participant);					
					list.add(participant);
				} 
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return list;
	}

	/**
	 * To save the participant object in database
	 *
	 */
	@Override
	public int save(Participant object) {
		int result = 0;
		String insertSql = "INSERT INTO Participant (fullname, phone, email, batchid) VALUES (?, ?, ?, ?)";
		try (Connection connection = db.getConnection();
				PreparedStatement ps = connection.prepareStatement(insertSql)) {			
			setDBPreparedStatement(ps, object);
			result = db.executeUpdate(ps);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return result;
	}

	/**
	 * To update participant object in database
	 */
	@Override
	public int update(Participant object) {
		int result = 0;
		String updateSql = "UPDATE Participant SET fullname = ?, phone = ?, email = ?,batchid = ?  WHERE id = ?";
		try (Connection connection = db.getConnection();
				PreparedStatement ps = connection.prepareStatement(updateSql)) {			
			setDBPreparedStatement(ps, object);
			ps.setInt(5, object.getPid());
			result = db.executeUpdate(ps);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return result;
	}
	
	/**
	 * To set the preparedstatement with participant object's data
	 * @param ps
	 * @param object
	 */
	private void setDBPreparedStatement(PreparedStatement ps, Participant object) {
		try {
			ps.setString(1, object.getName());
			ps.setString(2, object.getPhone());
			ps.setString(3, object.getEmail());
			ps.setInt(4, object.getBid());			
			
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println("SQL Exception occurred: ");
			e.printStackTrace();
			
		}
		
	}

	/**
	 * To get the list of batch ids whose capacity is full
	 * This is used while adding/updating a participant to only select the batches which are not full
	 * @return
	 */
	public List<Integer> getParticipantsPerBatch() {
		String selectSql = "select b.id id from(select count(batchid) noofparticipants, batchid bid from Participant group by batchid) p, batch b where p.noofparticipants = b.capacity and p.bid = b.id";
		List<Integer> list = new ArrayList<Integer>();
		int id = 0;
		try (Connection connection = db.getConnection();
				PreparedStatement ps = connection.prepareStatement(selectSql)) {
			try (ResultSet rs = ps.executeQuery()) {				
				while (rs.next()) {
					id = rs.getInt("id");
					list.add(id);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return list;
	}

	/**
	 * To delete a database record
	 */
	@Override
	public void delete(long id) {
		// TODO Auto-generated method stub
		
	}

}

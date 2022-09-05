package com.javalec.ex;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

//DAO(Data Access Object) : DB를 사용해 데이터를 조회하거나 조작하는 기능을 처리하는 오브젝트
public class MemberDao {
	public static final int MEMBER_NONEXISTENT = 0;
	public static final int MEMBER_EXISTENT = 1;
	public static final int MEMBER_JOIN_FAIL = 0;
	public static final int MEMBER_JOIN_SUCESS = 1;
	public static final int MEMBER_LOGIN_PW_NO_GOOD = 0;
	public static final int MEMBER_LOGIN_SUCESS = 1;
	public static final int MEMBER_LOGIN_IS_NOT = -1;

	private static MemberDao instance = new MemberDao();

	private MemberDao() {

	}

	public static MemberDao getInstance() {
		return instance;
	}

	public int insertMember(MemberDto dto) {
		int ri = 0;

		Connection connection = null;
		PreparedStatement pstmt = null;
		String query = "insert into members values (?,?,?,?,?,?)";

		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, dto.getId());
			pstmt.setString(2, dto.getPw());
			pstmt.setString(3, dto.getName());
			pstmt.setString(4, dto.getEmail());
			pstmt.setTimestamp(5, dto.getrDate());
			pstmt.setString(6, dto.getAddress());
			pstmt.executeUpdate();

			ri = MemberDao.MEMBER_JOIN_SUCESS;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (connection != null)
					connection.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return ri;
	}

	public int confirmId(String id) {
		int ri = 0;

		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String query = "select id from members where id=?";

		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				ri = MemberDao.MEMBER_EXISTENT; // 아이디 중복 1
			} else {
				ri = MemberDao.MEMBER_NONEXISTENT; // 아이디 중복 아님 0
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pstmt.close();
				connection.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return ri;
	}

	public int userCheck(String id, String pw) {
		int ri = 0;
		String dbgetPw = null;

		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String query = "select pw from members where id=?";

		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				dbgetPw = rs.getString("pw");
				if (dbgetPw.equals(pw)) {
					ri = MemberDao.MEMBER_LOGIN_SUCESS; // 비밀번호 일치 1
				} else {
					ri = MemberDao.MEMBER_LOGIN_PW_NO_GOOD; // 비밀번호 불일치 0
				}
			}else {
				ri=MemberDao.MEMBER_LOGIN_IS_NOT; //회원이 아닌경우 -1
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pstmt.close();
				connection.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return ri;
	}
	
	public MemberDto getMember(String id) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MemberDto dto=null;

		String query = "select * from members where id=?";

		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				//테스트!
				dto=new MemberDto();
				dto.setId(rs.getString(1));
				dto.setPw(rs.getString(2));
				dto.setName(rs.getString(3));
				dto.setEmail(rs.getString(4));
				dto.setrDate(rs.getTimestamp(5));
				dto.setAddress(rs.getString(6));
			}
				
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pstmt.close();
				connection.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return dto;
	}
	
	public int updateMember(MemberDto dto) {
		int ri=0;
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		
		String query = "Update members set pw=?,email=?,address=? where id=?";
		
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1,dto.getPw());
			pstmt.setString(2,dto.getEmail());
			pstmt.setString(3,dto.getAddress());
			pstmt.setString(4,dto.getId());
			
			ri=pstmt.executeUpdate();
			// ri에 업데이트문을 넣었을때 ri에 들어가는 값 확인하기!
			System.out.println("ri="+ri);
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				pstmt.close();
				connection.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return ri;
	}

	private Connection getConnection() {
		Context context = null;
		DataSource dataSource = null;
		Connection connection = null;

		try {
			context = new InitialContext();
			dataSource = (DataSource) context.lookup("java:comp/env/jdbc/Oracle11g");
			connection = dataSource.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
	}
}

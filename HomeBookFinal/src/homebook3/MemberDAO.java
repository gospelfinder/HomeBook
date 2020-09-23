package homebook3;

import java.sql.CallableStatement;
import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import common.MyHomeBookConnection;


public class MemberDAO implements IDAO<Member, String> {
	
	@Override
	public boolean isExists(String key, Connection conn) throws Exception {
		String sql = "select COUNT(*) from MEMBER where USERID=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, key);
		ResultSet rs = pstmt.executeQuery();
		int cnt = 0;
		while(rs.next()) {
			cnt = rs.getInt(1);
		}
		close(conn);
		return cnt>0;
	}

	@Override
	public boolean insert(Member t, Connection conn) throws Exception {
		String sql = "insert into MEMBER (USERID, USERNAME, PHONE, PASSWORD) VALUES (?,?,?,?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, t.getUserid());
		pstmt.setString(2, t.getUsername());
		pstmt.setString(3, t.getPhone());
		pstmt.setString(4, t.getPassword());
		int res = pstmt.executeUpdate();
		close(conn);
		return res>0;
	}
	
	public boolean insert2(Member t, Connection conn) throws Exception {
		String sql = "{call INSERTMEMBER(?,?,?,?)}";
		CallableStatement cstmt = conn.prepareCall(sql);
		cstmt.setString(1, t.getUserid());
		cstmt.setString(2, t.getUsername());
		cstmt.setString(3, t.getPhone());
		cstmt.setString(4, t.getPassword());
		cstmt.execute();
		close(conn);
		return true;
	}

	@Override
	public boolean delete(String key, Connection conn) throws Exception {
		String sql = "delete from MEMBER where USERID=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, key);
		int res = pstmt.executeUpdate();
		close(conn);
		return res>0;
	}

	@Override
	public boolean update(Member t, Connection conn) throws Exception {
		String sql = "update MEMBER set USERNAME, PHONE, PASSWORD where USERID=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, t.getUsername());
		pstmt.setString(2, t.getPhone());
		pstmt.setString(3, t.getUserid());
		pstmt.setString(4, t.getPassword());
		int res = pstmt.executeUpdate();
		close(conn);
		return res>0;
	}

	@Override
	public Member select(String key, Connection conn) throws Exception {
		String sql = "select * from MEMBER where USERID=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, key);
		ResultSet rs = pstmt.executeQuery();
		Member vo = new Member();
		while(rs.next()) {
			vo.setUserid(rs.getString("USERID"));
			vo.setUsername(rs.getString("USERNAME"));
			vo.setPhone(rs.getString("PHONE"));
			vo.setPassword(rs.getString("PASSWORD"));
		}
		close(conn);
		return vo;
	}

	@Override
	public List<Member> selectByCondition(Map<Object, Object> conditionMap, Connection conn) throws Exception {
		String sql = "select * from MEMBER where ";
		String sql2 = "";
		for(Object x:conditionMap.keySet()) {
			sql2 = sql2 + (String)x + " = " + conditionMap.get((String)x) + " AND ";
		}
		sql2 = sql2.substring(0, sql2.length()-5);
		sql = sql + sql2;
		System.out.println(sql);
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		List<Member> list =  new ArrayList<>();
		while(rs.next()) {
			Member vo = new Member();
			vo.setUserid(rs.getString("USERID"));
			vo.setUsername(rs.getString("USERNAME"));
			vo.setPhone(rs.getString("PHONE"));
			vo.setPassword(rs.getString("PASSWORD"));
			list.add(vo);
		}
		close(conn);
		return list;
	}

	@Override
	public List<Member> selectAll(Connection conn) throws Exception {
		String sql = "select * from MEMBER";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		List<Member> list = new ArrayList<>();
		while(rs.next()) {
			Member vo = new Member();
			vo.setUserid(rs.getString("USERID"));
			vo.setUsername(rs.getString("USERNAME"));
			vo.setPhone(rs.getString("PHONE"));
			vo.setPassword(rs.getString("PASSWORD"));
			list.add(vo);
		}
		close(conn);
		return list;
	}

	@Override
	public void close(Connection conn) throws Exception {
		conn.close();

	}
	
	@Override
	public boolean search(Member t, Connection conn) throws Exception {
		String sql = "select count(userid) from MEMBER where userid=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, t.getUserid());
		ResultSet rs = pstmt.executeQuery();
		int cnt = 0;
		while(rs.next()) {
			cnt = rs.getInt(1);
		}
		//close(conn);
		return cnt>0;
	}
}

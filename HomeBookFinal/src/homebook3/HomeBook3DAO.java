package homebook3;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import common.MyHomeBookConnection;


public class HomeBook3DAO implements IDAO<HomeBook, Long> {
	
	@Override
	public boolean isExists(Long key, Connection conn) throws Exception {
		String sql = "select * from HOMEBOOK where SERIALNO=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setLong(1, key);
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()) {
			//close(conn);
			return true;
		}
		//close(conn);
		return false;
	}

	@Override
	public boolean insert(HomeBook t, Connection conn) throws Exception {
	
		String sql = "insert into HOMEBOOK values(SEQ_HOMEBOOK.NEXTVAL,TO_DATE(?,'RR/MM/DD'),?,?,?,?,?,?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, t.getDay());
		pstmt.setString(2, t.getSection());
		pstmt.setString(3, t.getRemark());
		pstmt.setLong(4, t.getRevenue());
		pstmt.setLong(5, t.getExpense());
		pstmt.setString(6, t.getTitleid());
		pstmt.setString(7, t.getUserid());

		int res = pstmt.executeUpdate();
		close(conn);
		return res>0;
	}

	@Override
	public boolean delete(Long key, Connection conn) throws Exception {
		boolean is = isExists(key, conn);
		if(!is) return false;
		String sql = "delete from HOMEBOOK where SERIALNO=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setLong(1, key);
		int res = pstmt.executeUpdate();
		close(conn);
		return res>0;
	}

	@Override
	public boolean update(HomeBook t, Connection conn) throws Exception {
		long key = t.getSerialno();
		boolean is = isExists(key, conn);
		if(!is) return false;
		String sql = "update HOMEBOOK set DAY=TO_DATE(?,'RR/MM/DD'), SECTION=?, REMARK=?, REVENUE=?, EXPENSE=?, TITLEID=?, USERID=? where SERIALNO=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, t.getDay());
		pstmt.setString(2, t.getSection());
		pstmt.setString(3, t.getRemark());
		pstmt.setLong(4, t.getRevenue());
		pstmt.setLong(5, t.getExpense());
		pstmt.setString(6, t.getTitleid());
		pstmt.setString(7, t.getUserid());
		pstmt.setLong(8, t.getSerialno());	// 까먹지 맙시다!!!!
		int res = pstmt.executeUpdate();	//결과를 행수로 받는다.
		close(conn);
		return res==1; //한개만 업뎃하라.	res>0; 행수가 0보다 많은가? 많으면 업뎃하라.
	}

	@Override
	public HomeBook select(Long key, Connection conn) throws Exception {
		String sql = "select * from HOMEBOOK where SERIALNO=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setLong(1, key);
		HomeBook vo = new HomeBook();
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()) {
			vo.setSerialno(rs.getLong("SERIALNO"));//resultset에서 결과를 받아서 vo로 리턴시켜주는 것.
			String ss = rs.getString("DAY")+"";
			ss = ss.substring(0, ss.length()-9);
			vo.setDay(ss);
			vo.setSection(rs.getString("SECTION"));
			vo.setTitleid(rs.getString("TITLEID"));
			vo.setRemark(rs.getString("REMARK"));
			vo.setRevenue(rs.getLong("REVENUE"));
			vo.setExpense(rs.getLong("EXPENSE"));
			vo.setUserid(rs.getString("USERID"));
		}
		close(conn);
		return vo;
	}

	@Override
	public List<HomeBook> selectByCondition(Map<Object, Object> conditionMap, Connection conn) throws Exception {
		String sql = "select * from HOMEBOOK where ";
		String sql2 = "";
		for(Object x:conditionMap.keySet()) {
			sql2 += (String)x + " = " + conditionMap.get((String)x) + " AND ";
		}
		sql2 = sql2.substring(0, sql2.length()-5);	// 마지막 and를 떼어줘야 하기 때문에 쌍따옴표 안에 있는 띄어쓰기까지 총 5칸이다.
		sql += sql2;
		System.out.println(sql);
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		List<HomeBook> list = new ArrayList<>();
		while(rs.next()) {
			HomeBook vo = new HomeBook();
			vo.setSerialno(rs.getLong("SERIALNO"));
			String ss = rs.getString("DAY")+"";
			ss = ss.substring(0, ss.length()-9);
			vo.setDay(ss);
			vo.setSection(rs.getString("SECTION"));
			vo.setTitleid(rs.getString("TITLEID"));
			vo.setRemark(rs.getString("REMARK"));
			vo.setRevenue(rs.getLong("REVENUE"));
			vo.setExpense(rs.getLong("EXPENSE"));
			vo.setUserid(rs.getString("USERID"));
			list.add(vo);
		}
		close(conn);
		return list;
	}

	@Override
	public List<HomeBook> selectAll(Connection conn) throws Exception {
		String sql = "select * from HOMEBOOK";
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		List<HomeBook> list = new ArrayList<>();
		while(rs.next()) {
			HomeBook vo = new HomeBook();
			vo.setSerialno(rs.getLong("SERIALNO"));
			String ss = rs.getString("DAY")+"";
			ss = ss.substring(0, ss.length()-9);
			vo.setDay(ss);
			vo.setSection(rs.getString("SECTION"));
			vo.setRemark(rs.getString("REMARK"));
			vo.setRevenue(rs.getLong("REVENUE"));
			vo.setExpense(rs.getLong("EXPENSE"));
			vo.setTitleid(rs.getString("TITLEID"));
			vo.setUserid(rs.getString("USERID"));
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
	public boolean search(HomeBook t, Connection conn) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}
}

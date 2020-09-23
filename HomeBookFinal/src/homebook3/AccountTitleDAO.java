package homebook3;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AccountTitleDAO implements IDAO<AccountTitle, String> {
	
	@Override
	public boolean isExists(String key, Connection conn) throws Exception {
		String sql = "select count(*) from ACCOUNT_TITLE where titleid=?";
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
	public boolean insert(AccountTitle t, Connection conn) throws Exception {
		String sql = "insert into ACCOUNTTITLE (titleid, title) values (?,?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, t.getTitleid());
		pstmt.setString(2, t.getTitle());
		int res = pstmt.executeUpdate();
		close(conn);
		return res>0;
	}

	@Override
	public boolean delete(String key, Connection conn) throws Exception {
		String sql = "delete from ACCOUNTTITLE where TITLEID=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, key);
		int res = pstmt.executeUpdate();
		close(conn);
		return res>0;
	}

	@Override
	public boolean update(AccountTitle t, Connection conn) throws Exception {
		String sql = "update ACCOUNTTITLE set TITLE=? where TITLEID=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, t.getTitle());
		pstmt.setString(2, t.getTitleid());
		int res = pstmt.executeUpdate();
		close(conn);
		return res==1;
	}

	@Override
	public AccountTitle select(String key, Connection conn) throws Exception {
		String sql = "select * from ACCOUNTTITLE where TITLEID=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, key);
		ResultSet rs = pstmt.executeQuery();
		AccountTitle vo = new AccountTitle();
		while(rs.next()) {
			//안정성을 위해서 컬럼 순서를 지양하고 컬럼 이용을 사용하였음
			vo.setTitleid(rs.getString("titleid"));
			vo.setTitle(rs.getString("title"));
		}
		close(conn);
		return vo;
	}

	@Override
	public List<AccountTitle> selectByCondition(Map<Object, Object> conditionMap, Connection conn) throws Exception {
		String sql = "select * from ACCOUNTTITLE where ";
		String sql2 = "";	//sql의 where 뒷 문장에 사용될 부분
		for(Object x:conditionMap.keySet()) {
			sql2 = sql2 + (String)x + " = " + conditionMap.get((String)x) + " AND ";
		}
		sql2 = sql2.substring(0, sql2.length()-5);
		sql = sql + sql2;
		System.out.println(sql);
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		List<AccountTitle> list = new ArrayList<>();
		while(rs.next()) {
			AccountTitle vo = new AccountTitle();
			vo.setTitleid(rs.getString("titleid"));
			vo.setTitle(rs.getString("title"));
			list.add(vo);
		}
		close(conn);
		return list;
	}

	@Override
	public List<AccountTitle> selectAll(Connection conn) throws Exception {
		String sql = "select * from ACCOUNTTITLE";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		List<AccountTitle> list = new ArrayList<>();
		while(rs.next()) {
			AccountTitle vo = new AccountTitle();
			vo.setTitleid(rs.getString("titleid"));
			vo.setTitle(rs.getString("title"));
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
	public boolean search(AccountTitle t, Connection conn) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}
}

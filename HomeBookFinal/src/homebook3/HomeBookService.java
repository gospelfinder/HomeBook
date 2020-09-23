package homebook3;
import java.util.*;
import java.sql.*;
public class HomeBookService {

	//vo를 이용하지 않고 map을 만들어 이용한 예
	public List<Map<String, Object>> selectAllView(Connection conn) throws Exception {
		String sql = "select * from MANAGEVIEW";
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		List<Map<String, Object>> list = new ArrayList<>();
		while(rs.next()) {
			Map<String, Object> map = new HashMap<>();
			map.put("SERIALNO", rs.getLong("SERIALNO"));
			map.put("DAY", rs.getString("DAY"));
			map.put("SECTION", rs.getString("SECTION"));
			map.put("REMARK", rs.getString("REMARK"));
			map.put("REVENUE", rs.getLong("REVENUE"));
			map.put("EXPENSE", rs.getLong("EXPENSE"));
			map.put("TITLE", rs.getString("TITLE"));
			map.put("USERNAME", rs.getString("USERNAME"));
			map.put("PHONE", rs.getString("PHONE"));
			list.add(map);
		}
		return list;
	}
	public List<Map<String, Object>> conditionByView(Connection conn, String condition) throws Exception {
		String sql = "select * from MANAGEVIEW where "+condition;
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		List<Map<String, Object>> list = new ArrayList<>();
		while(rs.next()) {
			Map<String, Object> map = new HashMap<>();
			map.put("SERIALNO", rs.getLong("SERIALNO"));
			map.put("DAY", rs.getString("DAY"));
			map.put("SECTION", rs.getString("SECTION"));
			map.put("REMARK", rs.getString("REMARK"));
			map.put("REVENUE", rs.getLong("REVENUE"));
			map.put("EXPENSE", rs.getLong("EXPENSE"));
			map.put("TITLE", rs.getString("TITLE"));
			map.put("USERNAME", rs.getString("USERNAME"));
			map.put("PHONE", rs.getString("PHONE"));
			list.add(map);
		}
		return list;
	}
}

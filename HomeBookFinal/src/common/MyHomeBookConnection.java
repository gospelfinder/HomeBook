package common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyHomeBookConnection {
	
	public static Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(
				"jdbc:oracle:thin:@localhost:1521:xe",	//url
				"HOMEBOOK2",  //user
				"homebook2"   //password
			);
		} catch (SQLException e) {
			System.err.println("url, user, password 점검!!");
			System.exit(0);	//시스템 종료
		} catch (ClassNotFoundException e) {
			System.err.println("오라클 드라이버가 없음!");
			System.exit(0);	//시스템 종료
		}
		return conn;
	}//----------------------------------------------------------end of method
	
}

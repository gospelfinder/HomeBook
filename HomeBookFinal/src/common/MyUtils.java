package common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//공통으로 사용하는 툴, 특히 Connection 객체 얻어오기 기능 등
public class MyUtils {

	public static Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(
				"jdbc:oracle:thin:@localhost:1521:xe",	//url
				"madang",  //user
				"madang"   //password
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
	


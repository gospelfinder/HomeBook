package homebook3;

import java.awt.HeadlessException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

import common.MyHomeBookConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class MemberLoginController {

    @FXML
    private Button btnJoin;

    @FXML
    private TextField txtId;

    @FXML
    private Button btnLogin;

    @FXML
    private Label labNotyet;

    @FXML
    private Button btnDelete;

    @FXML
    private PasswordField txtPassword;

    @FXML
    void join(ActionEvent event) {
    	try {
			Stage primaryStage = new Stage();
			Stage stage = (Stage)btnJoin.getScene().getWindow();
				Parent asd = FXMLLoader.load(getClass().getResource("JoinFx.fxml"));
				Scene sc = new Scene(asd);
					primaryStage.setScene(sc);
				primaryStage.setScene(sc);
			primaryStage.show();
				stage.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @FXML
    void delete(ActionEvent event) {
    	txtId.clear();
    	txtPassword.clear();
    }

    @FXML
    void login(ActionEvent event){
    	Connection conn = MyHomeBookConnection.getConnection();
    	String PWfromDB = MemberLoginController.members_load(txtId.getText());
    	Alert loginFail = new Alert(AlertType.ERROR);
    	if(txtId.getText().isEmpty()) {
    		loginFail.setHeaderText("Login Fail");
    		loginFail.setContentText("아이디를 입력하세요");
    		loginFail.showAndWait();
    	}else if(txtPassword.getText().isEmpty()) {
        		loginFail.setHeaderText("Login Fail");
        		loginFail.setContentText("비밀번호를 입력하세요");
        		loginFail.showAndWait();
    	}else if(PWfromDB.equals(txtPassword.getText())) {
    		try {
    			MemberDAO dao = new MemberDAO();
				JOptionPane.showMessageDialog(null, "로그인에 성공했습니다.");
					Stage primaryStage = new Stage();
					Stage stage = (Stage)btnLogin.getScene().getWindow();
						Parent asd = FXMLLoader.load(getClass().getResource("HomeBook3Fx.fxml"));
						Scene sc = new Scene(asd);
						primaryStage.setScene(sc);
					primaryStage.show();
						stage.close();
			} catch (HeadlessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}else{
    		loginFail.setHeaderText("Login Fail");
    		loginFail.setContentText("아이디 또는 비밀번호가 옳지 않습니다.");
    		loginFail.showAndWait();
    	}
    }
    
    public static String members_load(String id) {
    	Connection conn = MyHomeBookConnection.getConnection();
    	Statement stmt = null;
    	ResultSet rs = null;
    	String password = "";
    	try {
    		stmt = conn.createStatement();
    		String sql = "select password from member where userid = "+"'"+id+"'";
    		rs = stmt.executeQuery(sql);
    		while(rs.next()) {
    			password = rs.getString(1);
    		}
    	}catch(Exception e) {
    		e.printStackTrace();
    	}finally {
    		try {
				if(conn != null && !conn.isClosed()) {
					conn.close();
				}
				if(stmt != null && stmt.isClosed()) {
					stmt.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	return password;
    }   
}

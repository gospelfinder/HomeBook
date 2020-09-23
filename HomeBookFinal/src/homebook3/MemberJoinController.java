package homebook3;


import java.awt.HeadlessException;
import java.io.IOException;
import java.sql.Connection;
import javax.swing.JOptionPane;
import common.MyHomeBookConnection;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class MemberJoinController {

    @FXML
    private TextField txtPhone;

    @FXML
    private Button btnJoin;

    @FXML
    private TextField txtUserid;

    @FXML
    private Button btnSameIdSearch;

    @FXML
    private TextField txtUsername;

    @FXML
    private Button btnDelete;

    @FXML
    private Label labId;

    @FXML
    private Label LabPassword;

    @FXML
    private Label labJoin;

    @FXML
    private Label LabPhone;

    @FXML
    private Label labName;

    @FXML
    private PasswordField txtPassword;
    
    @FXML
    void delete(ActionEvent event) {
		try {
			Stage primaryStage = new Stage();
			Stage stage = (Stage)btnSameIdSearch.getScene().getWindow();
				Parent asd = FXMLLoader.load(getClass().getResource("MemberFx.fxml"));
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
    void join(ActionEvent event)  {
	   Connection conn = MyHomeBookConnection.getConnection();
    	try {
			MemberDAO dao = new MemberDAO();
			Member vo = new Member();
			vo.setUserid(txtUserid.getText());
			vo.setUsername(txtUsername.getText());
			vo.setPhone(txtPhone.getText());
			vo.setPassword(txtPassword.getText());
			Alert joinFail = new Alert(AlertType.ERROR);
			if(txtUserid.getText().isEmpty()) {
				joinFail.setHeaderText("회원가입 실패");
				joinFail.setContentText("아이디란이 비어있습니다.");
				joinFail.showAndWait();
				return;
			}if(txtUsername.getText().isEmpty()){
				joinFail.setHeaderText("회원가입 실패");
				joinFail.setContentText("이름란이 비어있습니다.");
				joinFail.showAndWait();
				return;
			}if(txtPhone.getText().isEmpty()) {
				joinFail.setHeaderText("회원가입 실패");
				joinFail.setContentText("핸드폰번호란이 비어있습니다.");
				joinFail.showAndWait();
				return;
			}if(txtPassword.getText().isEmpty()){
				joinFail.setHeaderText("회원가입 실패");
				joinFail.setContentText("비밀번호란이 비어있습니다.");
				joinFail.showAndWait();
				return;
			}
			vo.setUserid(txtUserid.getText());
			boolean res2 = dao.search(vo, conn);
				if(res2) {
					JOptionPane.showMessageDialog(null, vo.getUserid()+" 아이디 중복확인하세요.");
					return;
				}
			boolean res = dao.insert(vo, conn);
				if(res) {
					JOptionPane.showMessageDialog(null, vo.getUserid()+"아이디 등록완료");
				}
				dao.close(conn);
			Stage primaryStage = new Stage();
	    	Stage stage = (Stage)btnSameIdSearch.getScene().getWindow();
	    		Parent asd = FXMLLoader.load(getClass().getResource("MemberFx.fxml"));
	    		Scene sc = new Scene(asd);
	    			primaryStage.setScene(sc);
	    		primaryStage.setScene(sc);
	    	primaryStage.show();
	    		stage.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
   
    @FXML
    void sameIdSearch(ActionEvent event) {
    	Connection conn = MyHomeBookConnection.getConnection();
    	try {
			MemberDAO dao = new MemberDAO();
			Member vo = new Member();
			vo.setUserid(txtUserid.getText());
			boolean res = dao.search(vo, conn);
			if(res) {
				JOptionPane.showMessageDialog(null, vo.getUserid()+" 아이디가 이미 존재합니다.");
				txtUserid.clear();
			}else if (txtUserid.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "아이디를 입력하세요");
			}else {
				JOptionPane.showMessageDialog(null, txtUserid.getText()+" 아이디 사용 가능합니다.");
			}
			dao.close(conn);
		} catch (HeadlessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}



package homebook3;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.awt.HeadlessException;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import common.MyHomeBookConnection;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

public class AccountTitleController implements Initializable {

    @FXML
    private Button btnSearch;

    @FXML
    private Button btnDelete;

    @FXML
    private TextField txtAccounttitle;

    @FXML
    private Label labelAccount;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableView<AccountTitle> accountTable;

    @FXML
    private Button btnRegest;

    @FXML
    private TableColumn<AccountTitle, String> colAccountid;

    @FXML
    private TableColumn<AccountTitle, String> colAccounttitle;

    @FXML
    private TextField txtAccountid;
    
    @FXML
    private Button btnReturnTo;

    @FXML
    void accountDelete(ActionEvent event) {
    	Connection conn = MyHomeBookConnection.getConnection();
    	try {
			AccountTitleDAO dao = new AccountTitleDAO();
			AccountTitle vo = accountTable.getSelectionModel().getSelectedItem();
			int index = accountTable.getSelectionModel().getSelectedIndex();
			String num = vo.getTitleid();
			boolean res = dao.delete(num, conn);
			accountTable.getItems().remove(index);
			dao.close(conn);
			if(res) {
				JOptionPane.showMessageDialog(null, vo.getTitleid()+" 자료삭제완료");
			}
		} catch (HeadlessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @FXML
    void accountSearch(ActionEvent event) {
    	String titleid = JOptionPane.showInputDialog("찾을아이디");
    	ObservableList<AccountTitle> data = accountTable.getItems();
    	int num = 0;
    	for(int i=0; i<accountTable.getItems().size(); i++) {
    		if(data.get(i).getTitleid().equals(titleid)) {
    			num = i;
    			break;
    		}
    	}	
    	accountTable.scrollTo(num);
    	accountTable.getSelectionModel().select(num);
    }

    @FXML
    void selectRow(MouseEvent event) {
    	int index = accountTable.getSelectionModel().getSelectedIndex();
    	AccountTitle vo = accountTable.getSelectionModel().getSelectedItem();
    	txtAccountid.setText(vo.getTitleid());
    	txtAccounttitle.setText(vo.getTitle());
    }

    @FXML
    void accountUpdate(ActionEvent event) {
    	Connection conn = MyHomeBookConnection.getConnection();
    	try {
			int index = accountTable.getSelectionModel().getSelectedIndex();
			AccountTitleDAO dao = new AccountTitleDAO();
			AccountTitle vo = new AccountTitle();
			vo.setTitleid(txtAccountid.getText());
			vo.setTitle(txtAccounttitle.getText());
			if(dao.update(vo, conn)) {
				accountTable.getItems().set(index, vo);
				JOptionPane.showMessageDialog(null, "수정완료");
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

    @FXML
    void accountRegest(ActionEvent event) {
    	Connection conn = MyHomeBookConnection.getConnection();
    	try {
			AccountTitleDAO dao = new AccountTitleDAO();
			AccountTitle vo = new AccountTitle();
			vo.setTitleid(txtAccountid.getText());
			vo.setTitle(txtAccounttitle.getText());
			boolean res = dao.insert(vo, conn);
			accountTable.getItems().add(vo);
				if(res) {
					JOptionPane.showMessageDialog(null, vo.getTitleid()+"계정 등록완료");
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
    
    @FXML
    void returnTo(ActionEvent event) throws IOException {
    	Stage primaryStage = new Stage();
    	Stage stage = (Stage)btnReturnTo.getScene().getWindow();
			Parent asd = FXMLLoader.load(getClass().getResource("HomeBook3Fx.fxml"));
			Scene sc = new Scene(asd);
			primaryStage.setScene(sc);
		primaryStage.show();
		stage.close();
    }
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
    	Connection conn = MyHomeBookConnection.getConnection();
    	
    	colAccountid.setCellValueFactory(new PropertyValueFactory<AccountTitle, String>("titleid"));
    	colAccounttitle.setCellValueFactory(new PropertyValueFactory<AccountTitle, String>("title"));
    	
    	AccountTitleDAO dao = new AccountTitleDAO();
		try {
			List<AccountTitle> data = dao.selectAll(conn);
			accountTable.getItems().addAll(data);	//랜더링 완료
			dao.close(conn);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
    }
}

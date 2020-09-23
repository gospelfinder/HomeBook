package homebook3;


import java.awt.HeadlessException;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import common.MyHomeBookConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class HomeBook3Controller implements Initializable {

	@FXML
    private TextField txtSerialno;
	
    @FXML
    private TextArea txtRemark;

    @FXML
    private TableColumn<HomeBook, String> colDay;

    @FXML
    private Button btnSearch;

    @FXML
    private ComboBox<Member> comboUserid;

    @FXML
    private TableColumn<HomeBook, Long> colRevenue;

    @FXML
    private TextField txtRevenue;

    @FXML
    private Button btnRegist;

    @FXML
    private TableColumn<HomeBook, Long> colExpense;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<HomeBook, Long> colSerialno;

    @FXML
    private TableColumn<HomeBook, String> colRemark;

    @FXML
    private TableColumn<HomeBook, String> colUserid;

    @FXML
    private DatePicker dateDay;

    @FXML
    private ComboBox<AccountTitle> comboTitleid;

    @FXML
    private Button btnDelete;

    @FXML
    private TextField txtExpense;
    
    @FXML
    private Button btnAccount;

    @FXML
    private TableColumn<HomeBook, String> colSection;

    @FXML
    private TableColumn<HomeBook, String> colTitleid;
    
    @FXML
    private ComboBox<String> comboSection;

    @FXML
    private TableView<HomeBook> table;
    
    @FXML
    private Label labelId;
    
    @FXML
    private Button btnLogout;
    
    @FXML
    void update(ActionEvent event) {
    	 Connection conn = MyHomeBookConnection.getConnection();
    	try {
			int index = table.getSelectionModel().getSelectedIndex();
			HomeBook3DAO dao = new HomeBook3DAO();
			HomeBook vo = new HomeBook();
			vo.setSerialno(Long.parseLong(txtSerialno.getText()));
			vo.setDay(dateDay.getValue().toString());
			vo.setSection(comboSection.getValue());
			vo.setRemark(txtRemark.getText());
			vo.setRevenue(Long.parseLong(txtRevenue.getText()));
			vo.setExpense(Long.parseLong(txtExpense.getText()));
			vo.setTitleid(comboTitleid.getValue().getTitleid());
			vo.setUserid(comboUserid.getValue().getUserid());
			if(dao.update(vo, conn)) {
				table.getItems().set(index, vo);
				JOptionPane.showMessageDialog(null, "수정완료");
			}
			dao.close(conn);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HeadlessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @FXML
    void delete(ActionEvent event) {
    	 Connection conn = MyHomeBookConnection.getConnection();
    	try {
			HomeBook3DAO dao = new HomeBook3DAO();
			HomeBook selvo = table.getSelectionModel().getSelectedItem();
			int index = table.getSelectionModel().getSelectedIndex();
			Long num = selvo.getSerialno();
			boolean res = dao.delete(num, conn);
			table.getItems().remove(index);
			dao.close(conn);
			if(res) {
				JOptionPane.showMessageDialog(null, selvo.getUserid()+" 자료삭제완료");
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
    void search(ActionEvent event) {
    	String userid = JOptionPane.showInputDialog("찾을아이디");
    	ObservableList<HomeBook> data = table.getItems();
    	int num = 0;
    	for(int i=0; i<table.getItems().size(); i++) {
    		if(data.get(i).getUserid().equals(userid)) {
    			num = i;
    			break;
    		}
    	}
    	table.scrollTo(num);
    	table.getSelectionModel().select(num);
    }
    
    @FXML
    void selectRow(MouseEvent event) {
    	int index = table.getSelectionModel().getSelectedIndex();
    	HomeBook vo = table.getSelectionModel().getSelectedItem();
    	List<AccountTitle> list = comboTitleid.getItems();
    	int comIndex = 0;
    	for(int i=0;i<list.size();i++) {
    		if(list.get(i).getTitleid().equals(vo.getTitleid())){
    			comIndex = i;
    			break;
    		}
    	}
    	List<Member> mlist = comboUserid.getItems();
    	int mcomIndex = 0;
    	for(int i=0;i<list.size();i++) {
    		if(mlist.get(i).getUserid().equals(vo.getUserid())){
    			mcomIndex = i;
    			break;
    		}
    	}
    	txtSerialno.setText(vo.getSerialno()+"");
    	comboUserid.getSelectionModel().select(mcomIndex);
    	comboSection.setValue(vo.getSection());
    	dateDay.setValue(LocalDate.parse(vo.getDay()));
    	comboTitleid.getSelectionModel().select(comIndex);
		txtRevenue.setText(vo.getRevenue()+"");
		txtExpense.setText(vo.getExpense()+"");
		txtRemark.setText(vo.getRemark());
    }

    @FXML
    void regist(ActionEvent event) {
    	Connection conn = MyHomeBookConnection.getConnection();
    	try {
			HomeBook3DAO dao = new HomeBook3DAO();
			HomeBook vo = new HomeBook();
			vo.setUserid(comboUserid.getValue().getUserid());
			LocalDate localDate = dateDay.getValue();
			vo.setDay(localDate.toString());
			vo.setSection(comboSection.getValue());
			vo.setTitleid(comboTitleid.getValue().getTitleid());
			vo.setRevenue(Long.parseLong(txtRevenue.getText()));
			vo.setExpense(Long.parseLong(txtExpense.getText()));
			vo.setRemark(txtRemark.getText());
			boolean res = dao.insert(vo, conn);
			table.getItems().add(vo);
				if(res) {
					JOptionPane.showMessageDialog(null, vo.getUserid()+"가계부 등록완료");
				}
				dao.close(conn);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    @FXML
    void accountRegist(ActionEvent event) throws IOException {
    	Stage primaryStage = new Stage();
    	Stage stage = (Stage)btnAccount.getScene().getWindow();
			Parent asd = FXMLLoader.load(getClass().getResource("AccountTitleFx.fxml"));
			Scene sc = new Scene(asd);
			primaryStage.setScene(sc);
		primaryStage.show();
		stage.close();
    }
    
    @FXML
    void logout(ActionEvent event) throws IOException {
    	Stage primaryStage = new Stage();
    	Stage stage = (Stage)btnLogout.getScene().getWindow();
			Parent asd = FXMLLoader.load(getClass().getResource("MemberFx.fxml"));
			Scene sc = new Scene(asd);
			primaryStage.setScene(sc);
		primaryStage.show();
		stage.close();
    }
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO 랜더링
		colSerialno.setCellValueFactory(new PropertyValueFactory<HomeBook, Long>("serialno"));
		colDay.setCellValueFactory(new PropertyValueFactory<HomeBook, String>("day"));
		loadData();
		colSection.setCellValueFactory(new PropertyValueFactory<HomeBook, String>("section"));
		colRemark.setCellValueFactory(new PropertyValueFactory<HomeBook, String>("remark"));
		colRevenue.setCellValueFactory(new PropertyValueFactory<HomeBook, Long>("revenue"));
		colExpense.setCellValueFactory(new PropertyValueFactory<HomeBook, Long>("expense"));
		colTitleid.setCellValueFactory(new PropertyValueFactory<HomeBook, String>("titleid"));
		colUserid.setCellValueFactory(new PropertyValueFactory<HomeBook, String>("userid"));
		
		labelId.setText("로그인 중");
		
		HomeBook3DAO dao = new HomeBook3DAO();
			Connection conn = MyHomeBookConnection.getConnection();
			try {
				List<HomeBook> data = dao.selectAll(conn);
				txtSerialno.setDisable(true);
				table.getItems().addAll(data);	//랜더링 완료
				dao.close(conn);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
		AccountTitleDAO adao = new AccountTitleDAO();
		conn = MyHomeBookConnection.getConnection();
			try {
				List<AccountTitle> adata = adao.selectAll(conn);
				comboTitleid.getItems().addAll(adata);
				adao.close(conn);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		MemberDAO mdao = new MemberDAO();
		conn = MyHomeBookConnection.getConnection();
			try {
				List<Member> mdata = mdao.selectAll(conn);
				comboUserid.getItems().addAll(mdata);
				mdao.close(conn);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
    
    private void loadData() {
    	ObservableList<String> list = FXCollections.observableArrayList();
    	list.removeAll(list);
    	String a = "수입";
    	String b = "지출";
    	list.addAll(a, b);
    	comboSection.getItems().addAll(list);
    }
}

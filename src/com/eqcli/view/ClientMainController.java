package com.eqcli.view;



import com.eqcli.application.EqClient;
import com.eqcli.application.LogEvent;
import com.eqcli.simulation.TriggerDetection;
import com.eqcli.simulation.WavefDataCreatorTask;
import com.eqcli.simulation.DataReport;
import com.eqcli.util.Constant;
import com.eqcli.util.EqConfig;
import com.eqcli.util.JDBCHelper;
import com.eqcli.util.ParseUtil;
import com.eqcli.util.SysConfig;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;

public class ClientMainController {
	
	private EqClient mainApp;

	//主页tab
	@FXML
	private Label stdIdLabel;
	@FXML
	private Label longitudeLabel;
	@FXML
	private Label latitudeLabel;
	@FXML
	private Label altitudeLabel;
	@FXML
	private Label sensitivityLabel;
	@FXML
	private Label modeLabel;
	@FXML
	private Label threshHoldLabel;
	@FXML
	private Label stateLabel;
	@FXML
	private Label srvIdLabel;
	@FXML
	private Button connectBtn;
	@FXML
	private Button disconnectBtn;
	@FXML
	private Label dbStateLabel;
	
	//数据库管理tab
	@FXML
	private TextField dbServer;
	@FXML
	private TextField dbUser;
	@FXML
	private PasswordField dbPasswd;
	@FXML
	private Button dbSave;
	
	//连接配置tab
	@FXML
	private TextField ipTf;
	@FXML
	private TextField portTf;
	@FXML
	private TextField srvIdTf;
	@FXML
	private TextField codeTf;
	@FXML
	private Button saveBtn;
	//模拟数据Tab
	@FXML
	private Label creatorStateLabel;
	@FXML
	private Label triggerStateLabel;
	@FXML
	private TableView<DataReport>  dataTable;
	@FXML
	private TableColumn<DataReport, String> dataTime;
	@FXML
	private TableColumn<DataReport, String> dataType;
	@FXML
	private TableColumn<DataReport, String> dataId;
	@FXML
	private Button triggerBtn;
	
	
	//模拟触发开关
	@FXML
	private void handleTrigger(){
		TriggerDetection.toggleTriggerSignal();
		if(TriggerDetection.detect()){
			triggerStateLabel.setText("触发");
		}else{
			triggerStateLabel.setText("未触发");			
		}
	}
	
	//日志
	@FXML
	private TableView<LogEvent> logArea;
	@FXML
	private TableColumn<LogEvent, String> logTime;
	@FXML
	private TableColumn<LogEvent, String> logEvent;
	
	
	
	@FXML
	private void initialize() {
		initMainTab();
		initDbTab();
		initConfigTab();
		//init log tableview
		logTime
		.setCellValueFactory(new PropertyValueFactory<LogEvent, String>(
				"time"));
		logEvent
		.setCellValueFactory(new PropertyValueFactory<LogEvent, String>(
				"event"));

		logArea.setItems(EqClient.logList);
		//init data creator tableviews
		dataTime
		.setCellValueFactory(new PropertyValueFactory<DataReport, String>(
				"time"));
		dataType
		.setCellValueFactory(new PropertyValueFactory<DataReport, String>(
				"type"));
		dataId
		.setCellValueFactory(new PropertyValueFactory<DataReport, String>(
				"id"));

		dataTable.setItems(EqClient.dataList);
	}
	/** 初始化主页 tab */
	private void initMainTab(){
		stdIdLabel.setText(EqConfig.stdId);
		modeLabel.setText(ParseUtil.parseTransMode(EqConfig.transMode));
		threshHoldLabel.setText(String.valueOf(EqConfig.triggerThreshold));
		stateLabel.setTextFill(Color.RED);
		stateLabel.setText("未连接");
		srvIdLabel.setText(SysConfig.getServerId());
		longitudeLabel.setText(String.valueOf(EqConfig.longitude));
		latitudeLabel.setText(String.valueOf(EqConfig.latitude));
		altitudeLabel.setText(String.valueOf(EqConfig.altitude));
		sensitivityLabel.setText(String.valueOf(EqConfig.sensitivity));
		
		if(JDBCHelper.getDbState()){
			dbStateLabel.setTextFill(Color.GREEN);
			dbStateLabel.setText("已连接");
		}else{
			dbStateLabel.setTextFill(Color.RED);
			dbStateLabel.setText("未连接");
		}
		
		creatorStateLabel.setTextFill(Color.RED);
		creatorStateLabel.setText("未启用");
	}
	
	/** 初始化数据库管理Tab */
	private void initDbTab(){
		dbServer.setText(SysConfig.getJdbcServerName());
		dbUser.setText(SysConfig.getJdbcUser());
		dbPasswd.setText(SysConfig.getJdbcPasswd());
	}
	/** 初始化连接配置Tab */
	private void initConfigTab(){
		
		ipTf.setText(SysConfig.getServerIp());
		portTf.setText(String.valueOf(SysConfig.getServerPort()));
		codeTf.setText(SysConfig.getAuthenCode());
		srvIdTf.setText(SysConfig.getServerId());
	}
	
	@FXML
	private void handleConnect(){
		mainApp.connectToHost();
	}
	
	
	@FXML
	private void handleDisconnect(){
		mainApp.disconnect();
	}
	
	/** 保存台网服务器配置 */
	@FXML
	private void handleServerSave(){
		String srvIp = ipTf.getText(); 
		String port = portTf.getText();
		String srvId = srvIdTf.getText();
		String code = codeTf.getText();
		
		//if valid
		SysConfig.saveServerConfig(srvIp, port, srvId, code);
	}
	
	@FXML
	private void handleServerReset(){
		initConfigTab();
	}
	@FXML
	private void handleDbSave(){
		String serverName = dbServer.getText();
		String userName = dbUser.getText();
		String password = dbPasswd.getText();
		
		//if valid
		SysConfig.saveDbConfig(serverName, userName, password);
	}
	@FXML
	private void handleDbReset(){
		initDbTab();
	}
	
	/** 连接数据库 */
	@FXML
	private void handleConnectToDb(){
		
		update(Constant.UICODE_DBSTATE, JDBCHelper.initDB());
	}
	
	@FXML
	private void handleCreatorToggle(){
		mainApp.toggleDataCreator();
	}
	
	public void setMainApp(EqClient client){
		this.mainApp = client;
	}
	
	/** 外部更新UI接口 
	 * 
	 * @param updatecode	更新类型码
	 * @param value			更新值
	 */
	public void update(int updatecode, Object value) {

		if(updatecode == Constant.UICODE_STATE){	//与台网连接状态
			
			if((boolean)value){
				stateLabel.setTextFill(Color.GREEN);
				stateLabel.setText("已连接");
			}else{
				stateLabel.setTextFill(Color.RED);
				stateLabel.setText("未连接");
			}
		}else if(updatecode == Constant.UICODE_MODE){	//传输模式
			
			modeLabel.setText(value.toString());
		}else if(updatecode == Constant.UICODE_THREHOLD){	//触发阈值
			
			threshHoldLabel.setText(String.valueOf((short)value));
		}else if(updatecode ==Constant.UICODE_DBSTATE){	//数据库连接状态
			
			if((boolean)value){
				dbStateLabel.setTextFill(Color.GREEN);
				dbStateLabel.setText("已连接");
			}else{
				dbStateLabel.setTextFill(Color.RED);
				dbStateLabel.setText("未连接");
			}
		}else if(updatecode == Constant.UICODE_DATACREATOR){	//模拟数据产生开关
			
			if((boolean)value){
				creatorStateLabel.setTextFill(Color.GREEN);
				creatorStateLabel.setText("开启");
			}else{
				creatorStateLabel.setTextFill(Color.RED);
				creatorStateLabel.setText("关闭");
			}
		}
	}
	
}

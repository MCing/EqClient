package com.eqcli.view;


import com.eqcli.application.EqClient;
import com.eqcli.simulation.DataCreatorTask;
import com.eqcli.util.Constant;
import com.eqcli.util.EqConfig;
import com.eqcli.util.JDBCHelper;
import com.eqcli.util.ParseUtil;
import com.eqcli.util.SysConfig;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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
	private Label creatorCounter;
	@FXML
	private Button creatorToggle;
	@FXML
	private Button creatorUpdate;
	
	@FXML
	private void initialize() {
		initMainTab();
		initDbTab();
		initConfigTab();
	}
	/** 初始化主页 tab */
	private void initMainTab(){
		stdIdLabel.setText(EqConfig.stdId);
		modeLabel.setText(ParseUtil.parseTransMode(EqConfig.transMode)); 
		threshHoldLabel.setText(String.valueOf(EqConfig.triggerThreshold));
		stateLabel.setTextFill(Color.RED);
		stateLabel.setText("未连接");
		srvIdLabel.setText(EqConfig.defaultSrv);
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
		
		creatorCounter.setText(String.valueOf(DataCreatorTask.getCount()));
		creatorStateLabel.setTextFill(Color.RED);
		creatorStateLabel.setText("未启用");
		
	}
	
	/** 初始化数据库管理Tab */
	private void initDbTab(){
		
	}
	/** 初始化连接配置Tab */
	private void initConfigTab(){
		
		ipTf.setText(SysConfig.getServerIp());
		portTf.setText(String.valueOf(SysConfig.getServerPort()));
		codeTf.setText(EqConfig.authenCode);
		srvIdTf.setText(EqConfig.defaultSrv);
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
	}
	
	@FXML
	private void handleServerReset(){
		
	}
	@FXML
	private void handleDbSave(){
		
	}
	@FXML
	private void handleDbReset(){
		
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
	@FXML 
	private void handleCreatorUpdate(){
		
		creatorCounter.setText(String.valueOf(DataCreatorTask.getCount()));
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

		if(updatecode == Constant.UICODE_STATE){
			
			if((boolean)value){
				stateLabel.setTextFill(Color.GREEN);
				stateLabel.setText("已连接");
			}else{
				stateLabel.setTextFill(Color.RED);
				stateLabel.setText("未连接");
			}
		}else if(updatecode == Constant.UICODE_MODE){
			
			modeLabel.setText(value.toString());
		}else if(updatecode == Constant.UICODE_THREHOLD){
			
			threshHoldLabel.setText(String.valueOf((short)value));
		}else if(updatecode ==Constant.UICODE_DBSTATE){
			
			if((boolean)value){
				dbStateLabel.setTextFill(Color.GREEN);
				dbStateLabel.setText("已连接");
			}else{
				dbStateLabel.setTextFill(Color.RED);
				dbStateLabel.setText("未连接");
			}
		}else if(updatecode == Constant.UICODE_DATACREATOR){
			
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

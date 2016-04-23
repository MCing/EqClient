package com.eqcli.view;

import com.eqcli.application.EqClient;
import com.eqcli.util.Constant;
import com.eqcli.util.EqConfig;
import com.eqcli.util.ParseUtil;
import com.eqcli.util.SysConfig;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

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
	
	//数据库管理tab
	@FXML
	private Label dbnameLabel;
	
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
		stateLabel.setText("未连接");
		srvIdLabel.setText(EqConfig.defaultSrv);
		longitudeLabel.setText(String.valueOf(EqConfig.longitude));
		latitudeLabel.setText(String.valueOf(EqConfig.latitude));
		altitudeLabel.setText(String.valueOf(EqConfig.altitude));
		sensitivityLabel.setText(String.valueOf(EqConfig.sensitivity));
		
	}
	
	/** 初始化数据库管理Tab */
	private void initDbTab(){
		//未完成
		dbnameLabel.setText("");
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
	
	/** 保存修改后的配置,未完成 */
	@FXML
	private void handleSave(){
	}
	
	public void setMainApp(EqClient client){
		this.mainApp = client;
	}
	
	/** 更新连接状态 */
	public void updateConnectState(boolean isConnect, String srvId){
		
		if(isConnect){
			stateLabel.setText("已连接");
		}else{
			stateLabel.setText("未连接");
		}
	}
	
	/** 外部更新UI接口 
	 * 
	 * @param updatecode	更新类型码
	 * @param value			更新值
	 */
	public void update(int updatecode, Object value) {

		if(updatecode == Constant.UICODE_STATE){
			
			if((boolean)value){
				stateLabel.setText("已连接");
			}else{
				stateLabel.setText("未连接");
			}
		}else if(updatecode == Constant.UICODE_MODE){
			modeLabel.setText(value.toString());
		}else if(updatecode == Constant.UICODE_THREHOLD){
			threshHoldLabel.setText(String.valueOf((short)value));
		}
		
	}
}

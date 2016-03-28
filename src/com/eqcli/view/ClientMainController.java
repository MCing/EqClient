package com.eqcli.view;

import com.eqcli.application.ClientApp;
import com.eqcli.util.Constant;
import com.eqcli.util.SysConfig;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ClientMainController {
	
	private ClientApp mainApp;

	//主页tab
	@FXML
	private Label stdIdLabel;
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
	private TextField codeTf;
	@FXML
	private Button saveBtn;
	
	@FXML
	private void initialize() {
		initMainTab();
		initDbTab();
		initConfigTab();
	}
	
	private void initMainTab(){
		stdIdLabel.setText(Constant.stationId);
		modeLabel.setText("");
		threshHoldLabel.setText("");
		stateLabel.setText("未连接");
		srvIdLabel.setText("");
	}
	
	private void initDbTab(){
		dbnameLabel.setText("");
	}
	
	private void initConfigTab(){
		
		ipTf.setText(SysConfig.getServerIp());
		portTf.setText(String.valueOf(SysConfig.getServerPort()));
		//未存入配置文件
		codeTf.setText(Constant.authorcode);
	}
	
	@FXML
	private void handleConnect(){
		mainApp.connectToHost();
	}
	
	
	@FXML
	private void handleDisconnect(){
		mainApp.disconnect();
	}
	
	@FXML
	private void handleSave(){
		
	}
	
	public void setMainApp(ClientApp client){
		this.mainApp = client;
	}
}

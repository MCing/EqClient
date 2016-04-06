package com.eqcli.application;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ConnectTimeoutException;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import org.apache.log4j.Logger;

import com.eqcli.handler.CtrlRespHandler;
import com.eqcli.handler.RegReqHandler;
import com.eqcli.task.DataCreatorTask;
import com.eqcli.util.Constant;
import com.eqcli.util.JDBCHelper;
import com.eqcli.util.LogUtil;
import com.eqcli.util.SysConfig;
import com.eqcli.view.ClientMainController;

public class EqClient extends Application {

	private Logger log = Logger.getLogger(EqClient.class);
	
	private String mainPagePath = "/com/eqcli/view/ClientMainLayout.fxml";
	private String iconPath = "/com/eqcli/view/images/icon.png";

	private ClientMainController controller;
	private boolean isConnected = false; // tcp链路连接标志
	
	private Channel channel;

	// 全局传输模式
	public static volatile short transMode = Constant.MODE_CONTINUOUS;

	// netty 对象
	private Bootstrap bootstrap;
	private EventLoopGroup group;
	private ScheduledExecutorService executor = Executors
			.newScheduledThreadPool(2);

	/**
	 * 程序入口
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}

	/** 初始化netty通信框架参数 */
	private void initNetty() {

		group = new NioEventLoopGroup();
		bootstrap = new Bootstrap();
		bootstrap.group(group).channel(NioSocketChannel.class)
				.option(ChannelOption.TCP_NODELAY, true)
				.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000) // 连接超时
				.handler(new ChannelHandlers());
	}

	/**
	 * 启动界面
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
			globalInit(primaryStage);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** 全局初始化 
	 * @param primaryStage
	  */
	private void globalInit(Stage primaryStage) {

		LogUtil.initLog();
		SysConfig.preConfig();
		initNetty();
		JDBCHelper.initDB();
		initView(primaryStage);
		// 模拟数据发生器
		executor.execute(new DataCreatorTask());
	}

	/** 初始化界面 */
	private void initView(Stage primaryStage) {

		// 操作系统标题栏图标
		primaryStage.getIcons().add(new Image(iconPath));
		primaryStage.setTitle("烈度仪系统");

		Node page = loadMainPage();
		Scene scene = new Scene((Parent) page);
		primaryStage.setResizable(false);
		primaryStage.setScene(scene);

	}

	private Node loadMainPage() {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getFXMLURL(mainPagePath));
		Node page = null;
		try {
			page = loader.load();
			controller = loader.getController();
			controller.setMainApp(EqClient.this);
		} catch (IOException e) {
			log.error("页面加载失败:"+e.getMessage());
		}
		return page;
	}

	/**
	 * 程序结束 回收资源
	 */
	@Override
	public void stop() throws Exception {

		JDBCHelper.closeDB();
		group.shutdownGracefully();
		executor.shutdown();
		log.info("退出软件");
	}

	/**
	 * Pipeline handler 队列初始化
	 *
	 */
	private class ChannelHandlers extends ChannelInitializer<SocketChannel> {

		@Override
		protected void initChannel(SocketChannel ch) throws Exception {

			ChannelPipeline pipeline = ch.pipeline();
			ch.pipeline().addLast(
					new ObjectDecoder(1024, ClassResolvers.cacheDisabled(this
							.getClass().getClassLoader())));
			ch.pipeline().addLast(new ObjectEncoder());
			pipeline.addLast(new RegReqHandler(EqClient.this));
			pipeline.addLast(new CtrlRespHandler());
		}

	}

	/**
	 * 连接到服务器
	 */
	public void connectToHost() {

		// 启动连接线程
		if(channel == null || !channel.isActive()){
			executor.execute(new ConnectTask());
		}
	}
	
	//_test 断开连接接口
	public void disconnect(){
		
		if(channel != null && channel.isActive()){
			channel.close();
			isConnected = false;
		}
		
	}

	/**
	 * 连接任务线程 处理连接超时重连
	 *
	 */
	private class ConnectTask implements Runnable {

		@Override
		public void run() {
			ChannelFuture f = null;
			while (!isConnected && !executor.isShutdown()) {
				try {
					f = bootstrap.connect(SysConfig.getServerIp(),
							SysConfig.getServerPort()).sync();
					if (f.isSuccess()) {
						channel = f.channel();
						isConnected = true;
					}
				} catch (Exception e) {
					if (e instanceof ConnectTimeoutException) {

						// 连接超时,host可解析，port开放但是不接收tcp连接时才会触发这个超时
						// 重连
						log.info("连接超时");

					} else {
						// 其他异常
						// 打印日志,显示必要错误等处理
						log.info("连接失败:" + e.getMessage());
					}
					try {
						TimeUnit.SECONDS.sleep(5); // 连接失败后重连间隔 5s
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					isConnected = false;
				}

			}
		}
	}

	/**
	 * 用于handler端注册失败重新连接的接口
	 */
	public void reconnect() {
		isConnected = false;
		connectToHost();
	}
	
	/** 更新连接状态UI 
	 * @param isConnect  连接状态   true:已连接
	 * @param srvId      台网服务器 id
	 */
	public void updateGUI(final boolean isConnect, final String srvId){
		
		//只能在JavaFX UI线程上修改UI
		Platform.runLater(new Runnable() {
	        @Override
	        public void run() {
				controller.updateConnectState(isConnect, srvId);
	        }
	   });
		
	}
	
	/**
	 * 为了解决打包成jar包后找不到fxml文件的问题
	 * @param path  fxml的绝对路径(这里的绝对路径是相对于工程的)
	 * @return
	 */
	private URL getFXMLURL(String path) {

		URL url = null;
		url = this.getClass().getResource(path);
		try {
			//打包成jar包后不能通过getResource得到类路径
			//所以,jar包中的文件用url表示方法:  jar:url!{entity}
			//如 :     jar:file:/c:/a/b.jar!/com/test/a.txt
			if (url == null) {
				URL jarUrl = this.getClass().getProtectionDomain()
						.getCodeSource().getLocation();
				url = new URL("jar:" + jarUrl + "!" + path);
			}
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}
		System.err.println("url:"+url.toExternalForm());
		return url;
	}

	
}

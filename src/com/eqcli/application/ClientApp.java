package com.eqcli.application;
	

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.eqcli.dao.TrgDataDao;
import com.eqcli.dao.WavefDataDao;
import com.eqcli.handler.ClientHandler;
import com.eqcli.task.DataCreatorTask;
import com.eqcli.util.Constant;
import com.eqcli.util.DataBuilder;
import com.eqcli.util.JDBCHelper;
import com.eqsys.msg.data.TrgData;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ConnectTimeoutException;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class ClientApp extends Application {
	
	private boolean isConnected = false;  //tcp链路连接标志
	private static final String DEFALUT_HOST = "localhost";
	private static final int DEFAULT_PORT = 8080;
	
	//全局传输模式
	public static volatile short transMode = Constant.MODE_CONTINUOUS;
	
	//netty 对象
	private Bootstrap bootstrap;
	private EventLoopGroup group;
	private ScheduledExecutorService executor = Executors
		    .newScheduledThreadPool(2);
	
	
	/**
	 * 程序入口
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
	/**
	 * 应用全局初始化
	 */
	@Override
	public void init() throws Exception {
		
		//初始化netty通信框架参数
		group = new NioEventLoopGroup();
		bootstrap = new Bootstrap();
		bootstrap.group(group)
		.channel(NioSocketChannel.class)
		.option(ChannelOption.TCP_NODELAY, true)
		//.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000) //连接超时
		.handler(new ChannelHandlers());
		
		//初始化数据库连接池
		JDBCHelper.initDB();
		
		//testJDBC();
		
		//连接服务器
		connectToHost();
		
		executor.execute(new DataCreatorTask());
		
	}
	
	/**
	 * 启动界面
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle(""+(int)(100*Math.random()));
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 程序结束
	 * 回收资源
	 */
	@Override
	public void stop() throws Exception {

		System.out.println("client app stop");
		JDBCHelper.closeDB();
		group.shutdownGracefully();
		executor.shutdown();
	}
	
	
	
	
	/**
	 * Pipeline handler 队列初始化
	 *
	 */
	private class ChannelHandlers extends ChannelInitializer<SocketChannel>{

		@Override
		protected void initChannel(SocketChannel ch) throws Exception {

			ch.pipeline().addLast(new ObjectDecoder(1024,ClassResolvers.cacheDisabled(this.getClass().getClassLoader())));
			ch.pipeline().addLast(new ObjectEncoder());
			ch.pipeline().addLast(new ClientHandler(ClientApp.this));
		}
		
	}
	
	/**
	 * 连接到服务器
	 */
	private void connectToHost(){
		
		//启动连接线程
		executor.execute(new ConnectTask());
	}
	
	/**
	 * 连接任务线程
	 * 处理连接超时
	 *
	 */
	private class ConnectTask implements Runnable{

		@Override
		public void run() {
			ChannelFuture f = null;
			while(!isConnected){
				try {
					f = bootstrap.connect(DEFALUT_HOST,DEFAULT_PORT).sync();
					if(f.isSuccess()){
						isConnected = true;
					}
				}catch(Exception e){
					//e.printStackTrace();
					if(e instanceof ConnectTimeoutException){
						
						//连接超时,host可解析，port开放但是不接收tcp连接时才会触发这个超时
						//重连
						System.out.println("连接超时");
						
					}else{
						//其他异常
						//打印日志,显示必要错误等处理
						System.out.println("连接失败");
						//e.printStackTrace();
						//break;
						
					}
					try {
						TimeUnit.SECONDS.sleep(5);
//						Thread.sleep(1000);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
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
	public void reconnect(){
		isConnected = false;
		connectToHost();
	}
	
	/**
	 * 测试jdbc
	 */
	private void testJDBC(){
		
		//test jdbc
		for(int i = 0; i < 1; i++){
//			new TrgDataDao().save(DataBuilder.buildTrgData());
			new WavefDataDao().save(DataBuilder.buildWavefData(i));
		}
		
		
//		group.execute(new Runnable(){
//
//			@Override
//			public void run() {
//				for(int i = 0; i < 1000; i++){
//					new TrgDataDao().save(DataBuilder.buildTrgData());
//				}
//			}
//			
//		});
//		TrgData t = new TrgData();
//		t.setId(129);
//		new TrgDataDao().delete(t);
	}
	
}
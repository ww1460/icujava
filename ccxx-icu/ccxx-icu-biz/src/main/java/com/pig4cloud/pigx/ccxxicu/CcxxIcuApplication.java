package com.pig4cloud.pigx.ccxxicu;

import com.pig4cloud.pigx.ccxxicu.tcp.EchoServer;
import com.pig4cloud.pigx.common.security.annotation.EnablePigxFeignClients;
import com.pig4cloud.pigx.common.security.annotation.EnablePigxResourceServer;
import com.pig4cloud.pigx.common.swagger.annotation.EnablePigxSwagger2;
import io.netty.channel.ChannelFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

// @EnableScheduling  启动检索定时方法

@EnableScheduling
@EnablePigxSwagger2
@SpringCloudApplication
@EnablePigxFeignClients
@EnablePigxResourceServer
public class CcxxIcuApplication implements CommandLineRunner {

	@Value("${netty.port}")
	private int port;

	@Value("${netty.url}")
	private String url;

	@Autowired
	private EchoServer echoServer;

	public static void main(String[] args) {
		try {
			SpringApplication.run(CcxxIcuApplication.class, args);
		}catch (Exception e){
			System.out.print(e);
		}
	}

	@Override
	public void run(String... args) throws Exception {
		ChannelFuture future = echoServer.start(url,port);
		Runtime.getRuntime().addShutdownHook(new Thread(){
			@Override
			public void run() {
				echoServer.destroy();
			}
		});
		//服务端管道关闭的监听器并同步阻塞,直到channel关闭,线程才会往下执行,结束进程
		future.channel().closeFuture().syncUninterruptibly();
	}


}

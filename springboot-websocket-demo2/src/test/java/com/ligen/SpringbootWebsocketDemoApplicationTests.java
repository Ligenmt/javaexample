package com.ligen;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SpringbootWebsocketDemoApplicationTests {

	public static String url = "ws://localhost:9030/ws?token=eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOlsiMyIsInhpeWFuIiwiYWRtaW4iLCIxNzA0NjA4Njk1Njg2Il19.K7kruqjOFhs4gumBNvunKijlhndG4JIIAzICYNaGBn8";

	public static void main(String[] args) {
		OkHttpClient client = new OkHttpClient.Builder()
				.connectTimeout(30, TimeUnit.SECONDS)
				.readTimeout(30, TimeUnit.SECONDS)
				.writeTimeout(30, TimeUnit.SECONDS)
				.build();
		MyListener listener = new MyListener();
//		String url = "ws://localhost:8087/websocket";
		Request request = new Request.Builder().url(url).build();
		WebSocket webSocket = client.newBuilder().build().newWebSocket(request, listener);
	}

	@Data
	public static class MyListener extends WebSocketListener {
		private WebSocket webSocket;

		@Override
		public void onOpen(WebSocket webSocket, Response response) {
			log.info("onOpen");
			//在连接打开的时候直接订阅消息主题
			String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
//			Map<String, Object> topic = this.apiWebSocketClient.createTopic(this.channel, this.symbols, timestamp);
//			try {
				this.webSocket = webSocket;
				JSONObject map = new JSONObject();
				map.put("type", "SUBSCRIBE");
				map.put("channel", "REAL_TIME_DATA");
				map.put("caseId", "1");
				sendMeg(map);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
		}

		public void sendMeg(JSONObject message) {
			try {
				String text = message.toJSONString();
				log.info("send:{}", text);
				this.webSocket.send(text);
			} catch (Exception e) {
				log.error("coinbase ws sendMeg error", e);
			}
		}

		@Override
		public void onClosed(WebSocket webSocket, int code, String reason) {
			super.onClosed(webSocket, code, reason);
			log.error("coinbase ws closed,reason:{}", reason);
		}

		@Override
		public void onClosing(WebSocket webSocket, int code, String reason) {
			super.onClosing(webSocket, code, reason);
			log.error("coinbase ws closing,reason:{}", reason);
			reConnection();
		}

		@Override
		public void onMessage(WebSocket webSocket, String text) {
			try {
				log.info(text);
			} catch (Exception e) {
				log.error("coinbase ws onMessage error", e);
			}
		}

		@Override
		public void onFailure(WebSocket webSocket, Throwable t, Response response) {
			log.error("coinbase ws onFailure", t);
			synchronized (this) {
				while (true) {
					try {
						this.wait(5000);
					} catch (Exception e) {
						e.printStackTrace();
					}
					if (reConnection()) {
						break;
					}
				}
			}

		}
	}

	private static Boolean reConnection() {
//		try {
//			Request request = new Request.Builder().url(url).build();
//			WebSocket webSocket = this.client.newBuilder().build().newWebSocket(request, listener);
//			Thread.sleep(500);
//			return true;
//		} catch (Exception e) {
//			log.error("coinbase ws  reconnection error :", e);
//			return false;
//		}
		return true;
	}

}

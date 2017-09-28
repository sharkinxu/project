package util;

import java.io.IOException;
import java.nio.CharBuffer;

import util.WebSocketAction.MyMessageInbound;

public class CountMessageUtil {
	//循环与服务器建立的客户端socket连接，给每个连接发送num数据
	public static void sendCountMessage(int num) {

		for (MyMessageInbound mmib : WebSocketAction.mmiList) {
			try {
				CharBuffer buffer = CharBuffer.wrap(String.valueOf(num));
				mmib.myoutbound.writeTextMessage(buffer);
				mmib.myoutbound.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

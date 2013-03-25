package com.listmedia.classes;

import java.net.Socket;

public class Conexao {

	public static String ip;
	
	static Socket getConexao() {
		Socket socket = null;
		try {
			socket = new Socket(ip, 7000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return socket;
	}
}

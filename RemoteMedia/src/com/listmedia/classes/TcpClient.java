package com.listmedia.classes;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.List;

public class TcpClient {

	static String ip = null;

//	public Socket conexao() {
//		ip = "10.0.0.100";
//		Socket socket = null;
//		try {
//			socket = new Socket(ip, 7000);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return socket;
//	}

	public List<String> start(String acao) {
		DataOutputStream out;
		DataInputStream in;
		List<String> lista = null;

		try {

			Socket socket = Conexao.getConexao();

			out = new DataOutputStream(socket.getOutputStream());
			out.writeUTF(acao);

			in = new DataInputStream(socket.getInputStream());

			ByteArrayOutputStream buffer = new ByteArrayOutputStream();

			int nRead;
			byte[] data = new byte[16384];

			while ((nRead = in.read(data, 0, data.length)) != -1) {
				buffer.write(data, 0, nRead);
			}

			buffer.flush();

			lista = toList(buffer.toByteArray());

			// out.close();
			// in.close();
			// socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return lista;
	}

	@SuppressWarnings("unchecked")
	private List<String> toList(byte[] b) {
		List<String> o = null;
		ByteArrayInputStream bis = new ByteArrayInputStream(b);
		ObjectInput in = null;
		try {
			in = new ObjectInputStream(bis);
			o = (List<String>) in.readObject();
			bis.close();
			in.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return o;
	}
}

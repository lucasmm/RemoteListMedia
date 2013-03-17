package com.listmedia.classes;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

public class TcpClient {

	private Socket socket;

	public boolean conexao() {

		try {
			socket = new Socket("10.0.0.100", 7000);
			return true;
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	}

	public List<String> start(String acao) {
		DataOutputStream out;
		DataInputStream in;
		List<String> lista = null;

		try {
			
			socket = new Socket("10.0.0.100", 7000);

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

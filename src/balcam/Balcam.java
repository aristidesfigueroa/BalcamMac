/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package balcam;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
//import static oracle.jrockit.jfr.events.Bits.length;

/**
 *
 * @author Figueroa
 */
public class Balcam {

    /**
     * @param args the command line arguments
     * @throws java.net.MalformedURLException
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws MalformedURLException, IOException {
        // TODO code application logic here
        token();
    }
    
    
    public static void token() throws MalformedURLException, IOException{
        String uri = "https://wsbalcampruebas.bch.sf/api/Login/Autenticacion";
        
        URL url = new URL(uri);
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        connection.setRequestMethod("POST");        
        connection.setRequestProperty("username", "WebSerTrabajadores");
        connection.setRequestProperty("content-type", "application/x-www-form-urlencoded");
        
        connection.setDoOutput(true);
        int length = 0;
        connection.setRequestProperty("Content-Length", Integer.toString(length));
        
        connection.getOutputStream().flush();
        
        /*
        POST /api/Login/Autenticacion HTTP/1.1
        Content-Length: 27
        Host: wsbalcampruebas.bch.sf
        Content-Type: application/x-www-form-urlencoded
        username=WebSerTrabajadores
        
        try {

		URL url = new URL("https://wsbalcampruebas.bch.sf/api/Login/Autenticacion");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/json");
                //conn.setRequestProperty("username", "WebSerTrabajadores");

		String input = "username WebSerTrabajadores";

		OutputStream os = conn.getOutputStream();
		os.write(input.getBytes());
		os.flush();

		if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
			throw new RuntimeException("Failed : HTTP error code : "
				+ conn.getResponseCode());
		}

		BufferedReader br = new BufferedReader(new InputStreamReader(
				(conn.getInputStream())));

		String output;
		System.out.println("Output from Server .... \n");
		while ((output = br.readLine()) != null) {
			System.out.println(output);
		}

		conn.disconnect();

	  } catch (MalformedURLException e) {

		e.printStackTrace();

	  } catch (IOException e) {

		e.printStackTrace();

	 }       */
        
    }
    
    
    
    
    public static void registrar(){
        try {

		URL url = new URL("https://wsbalcampruebas.bch.sf/api/Transacciones/Registrar");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1bmlxdWVfbmFtZSI6IldlYlNlclRyYWJhamFkb3JlcyIsIm5iZiI6MTU2MTg3MTkzNywiZXhwIjoxNTYxODczNzM3LCJpYXQiOjE1NjE4NzE5MzcsImlzcyI6Imh0dHA6Ly9sb2NhbGhvc3Q6MTAyNSIsImF1ZCI6Imh0dHA6Ly9sb2NhbGhvc3Q6MTAyNSJ9.QjAulLW0NLXTsPX4SYJjGYcco0zq0syK4ZwyiMp4MeA");

		String input = "{\"TransaccionCodigo\":100,\"TransaccionFecha\":\"07/03/2019\"}";
                
                //String input1 = "{\"TransaccionCodigo\":"100,\"name\":\"iPad 4\"}";

		OutputStream os = conn.getOutputStream();
		os.write(input.getBytes());
		os.flush();

		if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
			throw new RuntimeException("Failed : HTTP error code : "
				+ conn.getResponseCode());
		}

		BufferedReader br = new BufferedReader(new InputStreamReader(
				(conn.getInputStream())));

		String output;
		System.out.println("Output from Server .... \n");
		while ((output = br.readLine()) != null) {
			System.out.println(output);
		}

		conn.disconnect();

	  } catch (MalformedURLException e) {

		e.printStackTrace();

	  } catch (IOException e) {

		e.printStackTrace();

	 }
        
    }
    
    public static void anular(){
        //write here
    }
    
}
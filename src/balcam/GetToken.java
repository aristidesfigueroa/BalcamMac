/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package balcam;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

import java.net.MalformedURLException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.logging.Logger;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.net.ssl.SSLPeerUnverifiedException;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class GetToken {

    private String HTTPS_URL = "";
    //private String HTTPS_URL = "https://wsbalcampruebas.bch.sf/concertificado/api/Login/Autenticacion";
//    private String KEY_STORE_FILE = "C:\\Users\\Administrador\\Desktop\\CertificadosBalcam\\bantrab.pfx";
    private String KEY_STORE_FILE = "/Balcam/bantrab.pfx";
    private String KEY_STORE_PASS = "bantrab";
//    private String TRUST_STORE_FILE = "C:\\Program Files\\Java\\jdk1.8.0_211\\jre\\lib\\security\\cacerts";
    private String TRUST_STORE_FILE = "/Balcam/cacerts";
    private String TRUST_STORE_PASS = "changeit";

    //Documented in security guides (Under "Standard Names") on both Java 7 VMs
    /**
     * Oracle VM valid values - SunX509 IBM VM valid values - IbmX509
     */
    private String KEY_MANAGER_ALGORITHM = "IbmX509";
//    private String KEY_MANAGER_ALGORITHM = "SunX509";
    /**
     * Oracle VM valid values - PKCS12, JKS IBM VM valid values - PKCS12, JKS,
     * JCEKS
     */
    private String KEY_STORE_FORMAT = "PKCS12";
    private String TRUST_STORE_FORMAT = "JCEKS";
    //private String TRUST_STORE_FORMAT = "JKS";

    /**
     * Oracle VM valid values - PKIX IBM VM valid values - PKIX or IbmPKIX or
     * IbmX509
     */
    private String TRUST_MANAGER_ALGORITHM = "IbmPKIX";
    //private String TRUST_MANAGER_ALGORITHM = "PKIX";

    /**
     * Oracle VM valid values - SSL SSLv2 SSLv3 TLS TLSv1 TLSv1.1 TLSv1.2 IBM VM
     * valid values - SSL SSLv3 TLS_SSL TLS TLSv1
     */
    // Oracle VM
    private String SSL_CONTEXT_ALGORITHM = "TLS";

    private final static Logger logger = Logger.getLogger(HTTPSClient.class.getName());

//    public static void main(String[] args) {
//        new HTTPSClient().connect();
//    }
    public String connect(String tipoTransaccion,String TransaccionCodigo, String UrlToken, String usuario ) {
        HTTPS_URL = UrlToken;
        try {
            URL url = new URL(HTTPS_URL);
            String urlParameters = "username="+usuario;
            //String urlParameters = "username=WebSerTrabajadores";
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
            int postDataLength = postData.length;
            conn.setRequestProperty("Content-Length", Integer.toString(postDataLength));
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("POST");
//            conn.setRequestProperty("Content-Type", "application/json");

            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
            conn.setSSLSocketFactory(getFactory(new File(KEY_STORE_FILE), KEY_STORE_PASS, new File(TRUST_STORE_FILE), TRUST_STORE_PASS));
            conn.setDoOutput(true);
            conn.setDoInput(true);
            OutputStream os = conn.getOutputStream();
            os.write(postData);
            os.flush();
            InputStream is = null;
            // read the response
            int statusCode = conn.getResponseCode();
//            System.out.println("statusCode :" + statusCode);
            if (statusCode != 200) {
//                System.out.println("result after Reading JSON Response");
              BufferedWriter writer = new BufferedWriter(new FileWriter("/Balcam/" +tipoTransaccion + TransaccionCodigo + ".txt"));
//                BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\jeta\\ArchivoBALCAM\\" +tipoTransaccion+ TransaccionCodigo + ".txt"));
                writer.write(statusCode+"");
                writer.close();
                System.exit(statusCode);
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;

            while ((line = br.readLine()) != null) {
//                logger.info(line);
                return line;
            }

            br.close();
            return line;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (SSLPeerUnverifiedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private SSLSocketFactory getFactory(File pKeyFile, String pKeyPassword, File pTrustStoreFile, String pTrustStorePassword) {

        SSLSocketFactory socketFactory = null;

        try {

            KeyManagerFactory keyManagerFactory;
            keyManagerFactory = KeyManagerFactory.getInstance(KEY_MANAGER_ALGORITHM);
            KeyStore keyStore;
            keyStore = KeyStore.getInstance(KEY_STORE_FORMAT);
            InputStream keyInput = new FileInputStream(pKeyFile);
            keyStore.load(keyInput, pKeyPassword.toCharArray());
            keyInput.close();
            keyManagerFactory.init(keyStore, pKeyPassword.toCharArray());

            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TRUST_MANAGER_ALGORITHM);
            KeyStore trustStore;
            trustStore = KeyStore.getInstance(TRUST_STORE_FORMAT);
            InputStream trustStoreInput = new FileInputStream(pTrustStoreFile);
            trustStore.load(trustStoreInput, pTrustStorePassword.toCharArray());
            trustStoreInput.close();
            trustManagerFactory.init(trustStore);

            SSLContext context = SSLContext.getInstance(SSL_CONTEXT_ALGORITHM);
            context.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), new SecureRandom());
            socketFactory = context.getSocketFactory();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return socketFactory;
    }

}

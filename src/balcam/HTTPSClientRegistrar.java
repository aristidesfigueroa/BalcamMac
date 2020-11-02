/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package balcam;

import static balcam.BalcamToken.respuestaConfirmar;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
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
//import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
//import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
//import java.io.InputStreamReader;

import javax.net.ssl.SSLPeerUnverifiedException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import javax.json.Json;
import javax.json.stream.JsonParser;
import org.apache.commons.io.IOUtils;

public class HTTPSClientRegistrar {

    private String HTTPS_URL = "https://wsbalcampruebas.bch.sf/concertificado/api/Transacciones/Registrar";
    private String KEY_STORE_FILE = "/Balcam/bantrab.pfx";
//    private String KEY_STORE_FILE = "C:\\Users\\Administrador\\Desktop\\CertificadosBalcam\\bantrab.pfx";
    private String KEY_STORE_PASS = "bantrab";
    private String TRUST_STORE_FILE = "/Balcam/cacerts";
//    private String TRUST_STORE_FILE = "C:\\Program Files\\Java\\jdk1.8.0_211\\jre\\lib\\security\\cacerts";
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
//    private String TRUST_STORE_FORMAT = "JKS";
    private String TRUST_STORE_FORMAT = "JCEKS";

    /**
     * Oracle VM valid values - PKIX IBM VM valid values - PKIX or IbmPKIX or
     * IbmX509
     */
//    private String TRUST_MANAGER_ALGORITHM = "PKIX";
    private String TRUST_MANAGER_ALGORITHM = "IbmPKIX";

    /**
     * Oracle VM valid values - SSL SSLv2 SSLv3 TLS TLSv1 TLSv1.1 TLSv1.2 IBM VM
     * valid values - SSL SSLv3 TLS_SSL TLS TLSv1
     */
    // Oracle VM
    private String SSL_CONTEXT_ALGORITHM = "TLS";

    private final static Logger logger = Logger.getLogger(HTTPSClient.class.getName());

    public static void main(String[] args) {
        String trama = null;
        if ((args.length == 1)) {
            trama = args[0];
        } else {
            System.out.println("Usage: java -jar [path] Balcam.jar [TransaccionCodigo]");
            return;
        }
        new HTTPSClientRegistrar().connect(trama);
        //new HTTPSClientRegistrar().connect("r;1522;27/12/2019;27/12/2019;10:30:20;1;1;01010206;1;HN;PEDRO JUAN LAINEZ MARTINEZ;1;0801198005256;M;N;2000.00;;;USD;24.5878;1;2;18;1; ;");
    }

    private void connect(String trama) {
        BufferedReader br = null;
        String fileParametros = "";
        try {
            br = new BufferedReader(new FileReader("/Balcam/url/parametros.txt"));
            String line = null;
            while ((line = br.readLine()) != null) {
                fileParametros = line;
            }
            br.close();
        } catch (FileNotFoundException e) {
            System.out.println("Archivo parametros no existe = " + e);
            System.exit(0);

        } catch (IOException e) {
            System.out.println("Archivo URL Base de Datos Incompleto o Erroneo = " + e);
            System.exit(0);
        }
        String[] parametros = fileParametros.split("\\;");
        for (int x = 0; x < parametros.length; x++) {
        }
        String UrlToken     = parametros[0];
        String urlRegistra  = parametros[1];
        String urlAnula     = parametros[2];
        String usuario      = parametros[3];
        //</editor-fold>
        /**
         * Substring de Trama *************************
         */
//<editor-fold defaultstate="collapsed" desc="Substring de Trama *************************">
        String tramaIn = trama;
        String Token = null;
        String TransaccionCodigo = "";
        String TransaccionFecha = "";
        String TransaccionFechaContable = "";
        String TransaccionHora = "";
        String TipoTransaccionCodigo = "";
        String OrigenDestinoCodigo = "";
        String RubroCodigo = "";
        String TipoErogacion = "";
        String PaisCodigo = "";
        String ClienteNombre = "";
        String TipoIdCodigo = "";
        String ClienteId = "";
        String GeneroClienteCodigo = "";
        String TipoClienteCodigo = "";
        String TransaccionMonto = "";
        String TransaccionBeneficiario = "";
        String TransaccionConcepto = "";
        String MonedaCodigo = "";
        String TransaccionTasaCambio = "";
        String MedioPagoCodigo = "";
        String CanalAtencionCodigo = "";
        String DepartamentoCodigo = "";
        String MunicipioCodigo = "";
        String DeclaracionNumero = "";
        //String tramaIn = "ASEADI109|2|1|1|999|||";
        String[] result = tramaIn.split("\\;");
        for (int x = 0; x < result.length; x++) {
        }
        String tipoTransaccion = result[0];
        if (tipoTransaccion.equals("r")) {
            HTTPS_URL = urlRegistra ;
            TransaccionCodigo = result[1];
            TransaccionFecha = result[2];
            TransaccionFechaContable = result[3];
            TransaccionHora = result[4];
            TipoTransaccionCodigo = result[5];
            OrigenDestinoCodigo = result[6];
            RubroCodigo = result[7];
            TipoErogacion = result[8];
            PaisCodigo = result[9];
            ClienteNombre = result[10];
            TipoIdCodigo = result[11];
            ClienteId = result[12];
            GeneroClienteCodigo = result[13];
            TipoClienteCodigo = result[14];
            TransaccionMonto = result[15];
            TransaccionBeneficiario = result[16];
            TransaccionConcepto = result[17];
            MonedaCodigo = result[18];
            TransaccionTasaCambio = result[19];
            MedioPagoCodigo = result[20];
            CanalAtencionCodigo = result[21];
            DepartamentoCodigo = result[22];
            MunicipioCodigo = result[23];
            DeclaracionNumero = result[24];

        }
        if (tipoTransaccion.equals("a")) {
            TransaccionCodigo = result[1];
            TransaccionFechaContable = result[2];
            HTTPS_URL = urlAnula ;
            //HTTPS_URL = "https://wsbalcampruebas.bch.sf/concertificado/api/Transacciones/Anular";
        }

        GetToken getToken = new GetToken();
        Token = getToken.connect(tipoTransaccion, TransaccionCodigo, UrlToken, usuario);
//        System.out.println("Token : " + Token);
        try {
            URL url = new URL(HTTPS_URL);

//            String codTrans = "1529";
//            String urlParameters = "username=WebSerTrabajadores";
            String urlParameters = "[\n"
                    + "    {\n"
                    + "    \"TransaccionCodigo\": \"" + TransaccionCodigo + "\",\n"
                    + "    \"TransaccionFecha\": \"" + TransaccionFecha + "\",\n"
                    + "    \"TransaccionFechaContable\": \"" + TransaccionFechaContable + "\",\n"
                    + "    \"TransaccionHora\": \"" + TransaccionHora + "\",\n"
                    + "    \"TipoTransaccionCodigo\": \"" + TipoTransaccionCodigo + "\",\n"
                    + "    \"OrigenDestinoCodigo\": \"" + OrigenDestinoCodigo + "\",\n"
                    + "    \"RubroCodigo\": \"" + RubroCodigo + "\",\n"
                    + "    \"TipoErogacion\": \"" + TipoErogacion + "\",\n"
                    + "    \"PaisCodigo\": \"" + PaisCodigo + "\",\n"
                    + "    \"ClienteNombre\": \"" + ClienteNombre + "\",\n"
                    + "    \"TipoIdCodigo\": \"" + TipoIdCodigo + "\",\n"
                    + "    \"ClienteId\": \"" + ClienteId + "\",\n"
                    + "    \"GeneroClienteCodigo\": \"" + GeneroClienteCodigo + "\",\n"
                    + "    \"TipoClienteCodigo\": \"" + TipoClienteCodigo + "\",\n"
                    + "    \"TransaccionMonto\":\"" + TransaccionMonto + "\",\n"
                    + "    \"TransaccionBeneficiario\": \"" + TransaccionBeneficiario + "\",\n"
                    + "    \"TransaccionConcepto\": \"" + TransaccionConcepto + "\",\n"
                    + "    \"MonedaCodigo\": \"" + MonedaCodigo + "\",\n"
                    + "    \"TransaccionTasaCambio\":\"" + TransaccionTasaCambio + "\",\n"
                    + "    \"MedioPagoCodigo\": \"" + MedioPagoCodigo + "\",\n"
                    + "    \"CanalAtencionCodigo\": \"" + CanalAtencionCodigo + "\",\n"
                    + "    \"DepartamentoCodigo\": \"" + DepartamentoCodigo + "\",\n"
                    + "    \"MunicipioCodigo\": \"" + MunicipioCodigo + "\",\n"
                    + "    \"DeclaracionNumero\": \"" + DeclaracionNumero.trim() + "\",\n"
                    + "  }\n"
                    + "][\n";
            if (tipoTransaccion.equals("a")) {
                urlParameters = "[\n"
                        + "    {\n"
                        + "    \"TransaccionCodigo\": \"" + TransaccionCodigo + "\",\n"
                        + "    \"TransaccionFechaContable\": \"" + TransaccionFechaContable + "\",\n"
                        + "  }\n"
                        + "][\n";

            }
//            String urlParameters = "TransaccionCodigo=000002083&TransaccionFecha=19/12/2019&TransaccionFechaContable=19/12/2019&TransaccionHora=10:20:00&TipoTransaccionCodigo=1&OrigenDestinoCodigo=5&RubroCodigo=01010101&TipoErogacion=1&PaisCodigo=HN&ClienteNombre=ANGELUS S. DE R.L. &TipoIdCodigo=2&ClienteId=17099004457680&GeneroClienteCodigo=&TipoClienteCodigo=J&"
//                    + "TransaccionMonto=25000.12&TransaccionBeneficiario=&TransaccionConcepto=&MonedaCodigo=USD&TransaccionTasaCambio=23.5986&MedioPagoCodigo=1&CanalAtencionCodigo=1&DepartamentoCodigo=15&MunicipioCodigo=1&DeclaracionNumero=17";
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
            int postDataLength = postData.length;

            int longi = Token.length();
//            System.out.println("Longi 1 : " + longi);
            String TokenFinal = Token.substring(1, longi - 1);
            int longi2 = TokenFinal.length();
//            System.out.println("Longi 2 : " + longi2);
            conn.setRequestProperty("Authorization", TokenFinal);
//            conn.setRequestProperty("Authorization", "yj8lS+xmIPRP+MjGOJHCaHQ2i0VKi5Ks1ghqj1uElhoVnkgPtRkqYKWLI0ZlcjB+P+BriMIiT9U5QVtR6nVcEBuCFZg7jrU4VwKJ1e5e7kkHOEkhA7Eaw1CgO+gokfe1n35mu0rrVxfz1MV156un6Squrzz6ffFLKAgilPj/gNJfhHN5AwrffG3FU/iaaicRK3TghFivVPGu1FssSX1KjTLg2eB/YNHostqih5y77GLe4DjerQqJoWFkXydkzVq70Sp+GlGWWiB31Xr/jVdWdVBr5f+Va5Yigay4P4Bcu1zHjwO+GUsMmZAcFoW08TAx5NHWBFwM2lG0NmrV48GsKAJGcEdRoICjiICCJ6E1AewN+stHm59Q5y9AKyZ9OGGXMnppNeGT0fhLNqgO8mAiQmawsnAs6IVQFkFJU4BSQ1IY31ajjJEX3AcWxjkZTUv4avFnQp4x1UUMyZ0oyuyTsAUPqjGdmLYxgwvEmeKHrBlk7T49yT3DnvSxL0fXg57SlF8fIpS52UWk7AJiwXPturMILOXft0760XazZ/rAMrccRFnzCq8oHzJE/o7/JpwQcZxYjDJb9knIq7k9zkYfuO5LBmdaP4QCxmSbkVMX0oe63hlLyiJhlzJsFqyzJDoRnDdue4ryUMwio52NaimwWckrugL+FeWJpcdgBWNVq2I1keMgffF+xzYsUIhTmM76ptI7MDkjGUzCd2Bq9+EzsO6JmyZAoUUV8IowXRQlLTobFG65yqD8LwLmB6jKHzT8PCeK/b54EWGRpSqsnDD61A==");
            conn.setRequestProperty("Content-Length", Integer.toString(postDataLength));
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");

//            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
            conn.setSSLSocketFactory(getFactory(new File(KEY_STORE_FILE), KEY_STORE_PASS, new File(TRUST_STORE_FILE), TRUST_STORE_PASS));
//            conn.connect();
//            Certificate[] certs = conn.getServerCertificates();
//
//            if (conn != null) {
//                for (Certificate cert : certs) {
//                    logger.info("Cert Type: " + cert.getType());
//                    logger.info("Cert Hash Code: " + cert.hashCode());
//                    logger.info("Cert Algorithm: " + cert.getPublicKey().getAlgorithm());
//                    logger.info("Cert Format: " + cert.getPublicKey().getFormat());
//                }
//            }
            conn.setDoOutput(true);
            conn.setDoInput(true);
            OutputStream os = conn.getOutputStream();
            os.write(postData);
            os.flush();
            InputStream is = null;
            // read the response
            int statusCode = conn.getResponseCode();
//            System.out.println("statusCode. :" + statusCode);
            if (statusCode != 200) {
//                System.out.println("result after Reading JSON Response");
                BufferedWriter writer = new BufferedWriter(new FileWriter("/Balcam/" + tipoTransaccion + TransaccionCodigo + ".txt"));
//                BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\jeta\\ArchivoBALCAM\\" + tipoTransaccion + TransaccionCodigo + ".txt"));
                writer.write(statusCode + "");
                writer.close();
                System.exit(statusCode);
            }
            InputStream in = new BufferedInputStream(conn.getInputStream());
            String resulta = IOUtils.toString(in, "UTF-8");
//            System.out.println("Respuesta : " + resulta);
            String eso1 = new java.util.Scanner(resulta).useDelimiter("\\A").next();
            final JsonParser parser1 = Json.createParser(new StringReader(eso1));
            String key1 = null;
            String value1 = null;

//            System.out.println(result);
//            String eso1 = new java.util.Scanner(result).useDelimiter("\\A").next();
//            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//            String line;
////            String result = IOUtils.toString(br, "UTF-8");
//            while ((line = br.readLine()) != null) {
//                logger.info(line);
//            }
//            System.out.println(result);
//            String eso1 = new java.util.Scanner(result).useDelimiter("\\A").next();
//            System.out.println("Resultado : " + line);
//            eso1 = eso1.replace("<br />\n"
//                    + "<b>Warning</b>:  Use of undefined constant status - assumed 'status' (this will throw an Error in a future version of PHP) in <b>C:\\inetpub\\wwwroot\\csfrontend\\api\\functions.php</b> on line <b>15</b><br />", "");
//            System.out.println("result after Reading JSON Response");
            BufferedWriter writer = new BufferedWriter(new FileWriter("/Balcam/" + tipoTransaccion + TransaccionCodigo + ".txt"));
//            BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\jeta\\ArchivoBALCAM\\" + tipoTransaccion + TransaccionCodigo + ".txt"));
            writer.write(statusCode + resulta);
            writer.close();
            System.exit(statusCode);
//            FileOutputStream fos = new FileOutputStream("C:\\jeta\\ArchivoBALCAM\\" + codTrans + ".txt");
//            DataOutputStream outStream = new DataOutputStream(new BufferedOutputStream(fos));
//            outStream.writeUTF(result.toString());
//            outStream.close();
//            System.exit(statusCode);

//            br.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (SSLPeerUnverifiedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

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

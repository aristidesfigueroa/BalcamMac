/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package balcam;

/**
 *
 * @author jeffreehy
 */
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import javax.json.Json;
import javax.json.stream.JsonParser;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.stream.JsonParser.Event;
//import jdk.nashorn.internal.parser.JSONParser;

public class BalcamRegistrar {

    public static int count = 0;
    static Connection con = null;
    static String sql = null;
    static String sql1 = null;
    static String respuestaConfirmar = null;

    public static void main(String[] args) throws IOException, MalformedURLException, ParseException {

        try {
            BalcamRegistrar.Post_JSON();
        } catch (SQLException ex) {
            System.out.println("ERROR --- SQL : COUNT : " + count);
            System.out.println("ERROR --- SQL : " + ex.getMessage());
            System.out.println("ERROR --- SQL = " + sql);
            System.exit(0);

        }

    }

    public static void Post_JSON() throws MalformedURLException, IOException, SQLException, ParseException {

//        String urlParameters = "";TransaccionCodigo
        String urlParameters = "TransaccionCodigo=1208&TransaccionFecha=07/03/2019&TransaccionFechaContable=07/03/2019&TransaccionHora=10:15:00&TipoTransaccionCodigo=1&OrigenDestinoCodigo=5&RubroCodigo=01010101&TipoErogacion=1&PaisCodigo=HN&ClienteNombre=ANGELUS S. DE R.L. &TipoIdCodigo=2&ClienteId=17099004457680&GeneroClienteCodigo=&TipoClienteCodigo=J&"+
                "TransaccionMonto=25000.12&TransaccionBeneficiario=&TransaccionConcepto=&MonedaCodigo=USD&TransaccionTasaCambio=23.5986&MedioPagoCodigo=1&CanalAtencionCodigo=1&DepartamentoCodigo=15&MunicipioCodigo=1&DeclaracionNumero=15";
        byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
        int postDataLength = postData.length;

        try {  //

//            String query_url = "https://wsbalcampruebas.bch.sf/api/Transacciones/Registrar";
             String query_url = "https://wsbalcampruebas.bch.sf/concertificado/api/Login/Autenticacion";
            
            //https://wsbalcampruebas.bch.sf/api/Login/Autenticacion
            URL url = new URL(query_url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Length", Integer.toString(postDataLength));
            conn.setConnectTimeout(5000);
//            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
            conn.setRequestProperty("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1bmlxdWVfbmFtZSI6IldlYlNlclRyYWJhamFkb3JlcyIsIm5iZiI6MTU3NTc0NDY4NywiZXhwIjoxNTc1NzQ2NDg3LCJpYXQiOjE1NzU3NDQ2ODcsImlzcyI6Imh0dHA6Ly9sb2NhbGhvc3Q6MTAyNSIsImF1ZCI6Imh0dHA6Ly9sb2NhbGhvc3Q6MTAyNSJ9.x2XoxSNvyF6b35FJfcZ8ml8DVj98jJtiNEjrCQ7YrHg");
            conn.setDoOutput(true);
//           conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");

            OutputStream os = conn.getOutputStream();
            os.write(postData);
            os.flush();

            // read the response
            InputStream in = new BufferedInputStream(conn.getInputStream());

            String result = IOUtils.toString(in, "UTF-8");
//            System.out.println(result);
            String eso = new java.util.Scanner(result).useDelimiter("\\A").next();
//            eso = eso.replace("[, "");
//            eso = eso.replaceAll("], "");
//            System.out.println(eso);
            System.out.println("Resultado : " + result);
            System.out.println("Resultado : " + eso);
            eso = eso.replace("Error 1049 SQLSTATE[HY000] [1049] Unknown database 'circular_memo'", "");
            System.out.println("result after Reading JSON Response");
            System.exit(0);
//final String result = "{\"name\":\"Falco\,\"age\":3,\"bitable\":false}";
            final JsonParser parser = Json.createParser(new StringReader(eso));
            String key = null;
            String value = null;

//            String string ="";
            int n = 0;
            String[] string = null;
            string = new String[66];
//
//            try {
//                java.sql.DriverManager.registerDriver((Driver) new com.sybase.jdbc3.jdbc.SybDriver());
//            } catch (SQLException ex) {
//                System.out.println("Error Registrando el Driver : " + ex.getMessage());
//                System.exit(0);
//            }
//            String fileUrl = "jdbc:sybase:Tds:10.122.133.13:2638/creditoSolidario";
////            String fileUrl = "jdbc:sybase:Tds:localhost:2638/creditoSolidario";
//            try {
//                con = DriverManager.getConnection(fileUrl, "crivera", "123456");
////                con = DriverManager.getConnection(fileUrl, "dba", "sql");
////            con = DriverManager.getConnection(fileUrl, "jeta", "instinf7");
//                //con = DriverManager.getConnection(fileUrl, "supervisor", "*sisc@10*");
//            } catch (SQLException ex) {
//                System.out.println("Error de Connect : " + ex.getMessage());
//                System.exit(0);
//            }
            System.out.println("Coneccto : ********* ");
            String sw = "1";
            String sw1 = "0";
            int nn = 0;
            while (parser.hasNext()) {
                final Event event = parser.next();
                if (event.equals("VALUE_NULL")) {
                    string[n] = "";
                    n++;
                }
                switch (event) {
                    case KEY_NAME:
                        key = parser.getString();
                        if (key.equals("No")) {
                            sw = "1";
                        }
                        System.out.println(key + "  =========  : " + n);
                        break;
                    case VALUE_NULL:
//                    if (n == 1) {
//                        n = 0;
//                    }

                        string[n] = "";

//                    if (parser.getString().equals(null) ) {
//                        string[n] = "";
//                        System.out.println(key + "  Value NULL  : " + parser.getString() + " --- "+n);
//                    }
                        if (!sw.equals("1")) {
//                        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
//                        Date date = new Date();
//                        System.out.println(string[n] + "  =========  : " + n);
//                        sql = "INSERT INTO DBA.CS_Colocaciones (Departamento,Municipio,Ciudad,Nombre,PrimerNoombre,SegundoNombre,PrimerApellido,SegundoApellido,Identidad,LugarNacimiento,FechaNacimiento,Edad,EstadoCivil,Sexo,NivelEducativo,Profesion,TipodePersona,Dependiantes,DireccionDomicilio,Telefono,GrupoSolidario,SectorEconomico,ActividadEconomica,DireccionNegocio,FechaSolicitud,MontoAutorizado,Plazo,ValordelAhorro,Tasa,FechaAutorizacion,FormadePago,PrestamoNumero,Gestor,Supervisor,Coordinador,TipoCliente,IFI,Ciclo,FechaLog,EstatusPrestamo,Observaciones,id,GrupoSolidarioHash,Documento,Programa,Fondo,NombreRef1,TelefonoRef1,DireccionRef1,ParentescoRef1,NombreRef2,TelefonoRef2,DireccionRef2,ParentescoRef2,NombreRef3,TelefonoRef3,DireccionRef3,ParentescoRef3,NombreRef4,TelefonoRef4,DireccionRef4,ParentescoRef4) values ('" + string[0] + "','" + string[1] + "','" + string[2] + "','" + string[3] + "','" + string[4] + "','" + string[5] + "','" + string[6] + "','" + string[7] + "','" + string[8] + "','" + string[9] + "','" + dateFormat.format(string[10]) + "','" + string[11] + "','" + string[12] + "','" + string[13] + "','" + string[14] + "','" + string[15] + "','" + string[16] + "','" + string[17] + "','" + string[18] + "','" + string[19] + "','" + string[20] + "','" + string[21] + "','" + string[22] + "','" + string[23] + "','" + dateFormat.format(string[24]) + "', '" + string[25] + "','" + string[26] + "','" + string[27] + "','" + string[28] + "','" + dateFormat.format(string[29]) + "', '" + string[30] + "','" + string[31] + "','" + string[32] + "','" + string[33] + "','" + string[34] + "','" + string[35] + "','" + string[36] + "','" + string[37] + "','" + dateFormat.format(string[38]) + "','" + string[39] + "','" + string[40] + "','" + string[41] + "','" + string[42] + "','" + string[43] + "','" + string[44] + "','" + string[45] + "','" + string[46] + "','" + string[47] + "','" + string[48] + "','" + string[49] + "','" + string[50] + "','" + string[51] + "','" + string[52] + "','" + string[53] + "','" + string[54] + "','" + string[55] + "','" + string[56] + "','" + string[57] + "','" + string[58] + "','" + string[59] + "','" + string[60] + "','" + string[61] + "')";
//                        String sql1 = "INSERT INTO DBA.CS_Colocaciones (Departamento,Municipio,Ciudad,Nombre,PrimerNoombre,SegundoNombre,PrimerApellido,SegundoApellido,Identidad,LugarNacimiento,FechaNacimiento,Edad,EstadoCivil,Sexo,NivelEducativo,Profesion,TipodePersona,Dependiantes,DireccionDomicilio,Telefono,GrupoSolidario,SectorEconomico,ActividadEconomica,DireccionNegocio,FechaSolicitud,MontoAutorizado,Plazo,ValordelAhorro,Tasa,FechaAutorizacion,FormadePago,PrestamoNumero,Gestor,Supervisor,Coordinador,TipoCliente,IFI,Ciclo,FechaLog,EstatusPrestamo,Observaciones,id,GrupoSolidarioHash,Documento,Programa,Fondo,NombreRef1,TelefonoRef1,DireccionRef1,ParentescoRef1,NombreRef2,TelefonoRef2,DireccionRef2,ParentescoRef2,NombreRef3,TelefonoRef3,DireccionRef3,ParentescoRef3,NombreRef4,TelefonoRef4,DireccionRef4,ParentescoRef4) VALUES ('"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]"','"+string[n]+"')";
                            System.out.println(key + "  Nulo *****  : " + n);
                            n++;
                            if (n == 66) {
                                DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
//                            DateFormat dateFormat1 = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
                                Date date = new Date();
//                            Date fecha1 = new SimpleDateFormat("yyyy-MM-dd").parse(string[10]);
//                            Date fecha2 = new SimpleDateFormat("dd-MM-yyyy").parse(string[24]);
//                            Date fecha3 = new SimpleDateFormat("dd-MM-yyyy").parse(string[29]);
//                            Date fecha4 = new SimpleDateFormat("dd-MM-yyyy").parse(string[38]);
                                if (string[25].equals("")) {
                                    string[25] = "0";
                                }
                                if (string[27].equals("")) {
                                    string[27] = "0";
                                }
                                if (string[28].equals("")) {
                                    string[28] = "0";
                                }
                                if (string[31].equals("")) {
                                    string[31] = "0";
                                }
                                if (string[36].equals("")) {
                                    string[36] = "0";
                                }
                                if (string[37].equals("")) {
                                    string[37] = "0";
                                }
                                if (string[41].equals("")) {
                                    string[41] = "0";
                                }
                                sql = "INSERT INTO DBA.CS_Colocaciones (Departamento,Municipio,Ciudad,Nombre,PrimerNoombre,SegundoNombre,PrimerApellido,SegundoApellido,Identidad,LugarNacimiento,FechaNacimiento,Edad,EstadoCivil,Sexo,NivelEducativo,Profesion,TipodePersona,Dependiantes,DireccionDomicilio,Telefono,GrupoSolidario,SectorEconomico,ActividadEconomica,DireccionNegocio,FechaSolicitud,MontoAutorizado,Plazo,ValordelAhorro,Tasa,FechaAutorizacion,FormadePago,PrestamoNumero,Gestor,Supervisor,Coordinador,TipoCliente,IFI,Ciclo,FechaLog,EstatusPrestamo,Observaciones,idPrestamo,GrupoSolidarioHash,Documento,Programa,Fondo,NombreRef1,TelefonoRef1,DireccionRef1,ParentescoRef1,NombreRef2,TelefonoRef2,DireccionRef2,ParentescoRef2,NombreRef3,TelefonoRef3,DireccionRef3,ParentescoRef3,NombreRef4,TelefonoRef4,DireccionRef4,ParentescoRef4,NombreAval,TelefonoAval,IdentidadAval,DireccionAval) values ('" + string[0].toString().trim() + "','" + string[1].toString().trim() + "','" + string[2].toString().trim() + "','" + string[3].toString().trim() + "','" + string[4].toString().trim() + "','" + string[5].toString().trim() + "','" + string[6].toString().trim() + "','" + string[7].toString().trim() + "','" + string[8].toString().trim() + "','" + string[9].toString().trim() + "','" + string[10] + "','" + string[11].toString().trim() + "','" + string[12].toString().trim() + "','" + string[13].toString().trim() + "','" + string[14].toString().trim() + "','" + string[15].toString().trim() + "','" + string[16].toString().trim() + "','" + string[17].toString().trim() + "','" + string[18].toString().trim() + "','" + string[19].toString().trim() + "','" + string[20].toString().trim() + "','" + string[21].toString().trim() + "','" + string[22].toString().trim() + "','" + string[23].toString().trim() + "','" + string[24] + "'," + string[25].toString().trim() + ",'" + string[26].toString().trim() + "','" + string[27].toString().trim() + "','" + string[28].toString().trim() + "','" + string[29] + "', '" + string[30].toString().trim() + "','" + string[31].toString().trim() + "','" + string[32].toString().trim() + "','" + string[33].toString().trim() + "','" + string[34].toString().trim() + "','" + string[35].toString().trim() + "','" + string[36].toString().trim() + "','" + string[37].toString().trim() + "','" + string[38] + "','" + string[39].toString().trim() + "','" + string[40].toString().trim() + "','" + string[41].toString().trim() + "','" + string[42].toString().trim() + "','" + string[43].toString().trim() + "','" + string[44].toString().trim() + "','" + string[45].toString().trim() + "','" + string[46].toString().trim() + "','" + string[47].toString().trim() + "','" + string[48].toString().trim() + "','" + string[49].toString().trim() + "','" + string[50].toString().trim() + "','" + string[51].toString().trim() + "','" + string[52].toString().trim() + "','" + string[53].toString().trim() + "','" + string[54].toString().trim() + "','" + string[55].toString().trim() + "','" + string[56].toString().trim() + "','" + string[57].toString().trim() + "','" + string[58].toString().trim() + "','" + string[59].toString().trim() + "','" + string[60].toString().trim() + "','" + string[61].toString().trim() + "','" + string[62].toString().trim() + "','" + string[63].toString().trim() + "','" + string[64].toString().trim() + "','" + string[65].toString().trim() + "')";
                                System.out.println("OJO : SQL  Numero : " + nn + "   = " + sql);
                                Statement stmt = con.createStatement();
                                int rows = stmt.executeUpdate(sql);
                                String respuestaConfirma = sendConfirmarColocacion(string[41]);
                                if (respuestaConfirma.equals("OK")) {
                                    System.out.println("Resultado confirmación de colocación : " + respuestaConfirma);
                                    sql1 = "UPDATE DBA.CS_Colocaciones SET EstatusPrestamo='Colocado' where idPrestamo = " + string[41];
                                    rows = stmt.executeUpdate(sql1);
//                                    System.exit(0);
                                } else {
                                    System.out.println("No confirmo colocación : " + respuestaConfirma);
                                    System.out.println("ID colocación no confirmada  : " + string[41]);
                                }

                                count++;
                                con.commit();
                                stmt.close();
                                n = 0;
                                string[n] = "";
                                nn++;
                                break;
                            }
//                        sw = "0";

                        }
//                    sw = "0";
                        break;
                    case VALUE_STRING:
//                    if (n == 1) {
//                        n = 0;
//                    }
//                    if(sw.equals("1")){
//                        break;
//                    }
                        if (sw.equals("1")) {
                            sw = "0";
                            break;
                        }
                        if (parser.getString() == null) {
                            parser.next();
                        }
                        System.out.println("  X=========X  : " + parser.getString());
                        string[n] = "";
                        string[n] = parser.getString();

                        if (string[n] == null) {
                            string[n] = "";
                        }
                        if (!sw.equals("1")) {
                            System.out.println(string[n] + "  =========  : " + n);
//                        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
//                        Date date = new Date();
//                        sql = "INSERT INTO DBA.CS_Colocaciones (Departamento,Municipio,Ciudad,Nombre,PrimerNoombre,SegundoNombre,PrimerApellido,SegundoApellido,Identidad,LugarNacimiento,FechaNacimiento,Edad,EstadoCivil,Sexo,NivelEducativo,Profesion,TipodePersona,Dependiantes,DireccionDomicilio,Telefono,GrupoSolidario,SectorEconomico,ActividadEconomica,DireccionNegocio,FechaSolicitud,MontoAutorizado,Plazo,ValordelAhorro,Tasa,FechaAutorizacion,FormadePago,PrestamoNumero,Gestor,Supervisor,Coordinador,TipoCliente,IFI,Ciclo,FechaLog,EstatusPrestamo,Observaciones,id,GrupoSolidarioHash,Documento,Programa,Fondo,NombreRef1,TelefonoRef1,DireccionRef1,ParentescoRef1,NombreRef2,TelefonoRef2,DireccionRef2,ParentescoRef2,NombreRef3,TelefonoRef3,DireccionRef3,ParentescoRef3,NombreRef4,TelefonoRef4,DireccionRef4,ParentescoRef4) values ('" + string[0] + "','" + string[1] + "','" + string[2] + "','" + string[3] + "','" + string[4] + "','" + string[5] + "','" + string[6] + "','" + string[7] + "','" + string[8] + "','" + string[9] + "','" + dateFormat.format(string[10]) + "','" + string[11] + "','" + string[12] + "','" + string[13] + "','" + string[14] + "','" + string[15] + "','" + string[16] + "','" + string[17] + "','" + string[18] + "','" + string[19] + "','" + string[20] + "','" + string[21] + "','" + string[22] + "','" + string[23] + "','" + dateFormat.format(string[24]) + "', '" + string[25] + "','" + string[26] + "','" + string[27] + "','" + string[28] + "','" + dateFormat.format(string[29]) + "', '" + string[30] + "','" + string[31] + "','" + string[32] + "','" + string[33] + "','" + string[34] + "','" + string[35] + "','" + string[36] + "','" + string[37] + "','" + dateFormat.format(string[38]) + "','" + string[39] + "','" + string[40] + "','" + string[41] + "','" + string[42] + "','" + string[43] + "','" + string[44] + "','" + string[45] + "','" + string[46] + "','" + string[47] + "','" + string[48] + "','" + string[49] + "','" + string[50] + "','" + string[51] + "','" + string[52] + "','" + string[53] + "','" + string[54] + "','" + string[55] + "','" + string[56] + "','" + string[57] + "','" + string[58] + "','" + string[59] + "','" + string[60] + "','" + string[61] + "')";
//                        String sql1 = "INSERT INTO DBA.CS_Colocaciones (Departamento,Municipio,Ciudad,Nombre,PrimerNoombre,SegundoNombre,PrimerApellido,SegundoApellido,Identidad,LugarNacimiento,FechaNacimiento,Edad,EstadoCivil,Sexo,NivelEducativo,Profesion,TipodePersona,Dependiantes,DireccionDomicilio,Telefono,GrupoSolidario,SectorEconomico,ActividadEconomica,DireccionNegocio,FechaSolicitud,MontoAutorizado,Plazo,ValordelAhorro,Tasa,FechaAutorizacion,FormadePago,PrestamoNumero,Gestor,Supervisor,Coordinador,TipoCliente,IFI,Ciclo,FechaLog,EstatusPrestamo,Observaciones,id,GrupoSolidarioHash,Documento,Programa,Fondo,NombreRef1,TelefonoRef1,DireccionRef1,ParentescoRef1,NombreRef2,TelefonoRef2,DireccionRef2,ParentescoRef2,NombreRef3,TelefonoRef3,DireccionRef3,ParentescoRef3,NombreRef4,TelefonoRef4,DireccionRef4,ParentescoRef4) VALUES ('"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]+"','"+string[n]"','"+string[n]+"')";

//                            con.close();
                            n++;
                            if (n == 66) {
                                DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
//                            DateFormat dateFormat1 = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
                                Date date = new Date();
//                            Date fecha1 = new SimpleDateFormat("yyyy-MM-dd").parse(string[10]);
//                            Date fecha2 = new SimpleDateFormat("dd-MM-yyyy").parse(string[24]);
//                            Date fecha3 = new SimpleDateFormat("dd-MM-yyyy").parse(string[29]);
//                            Date fecha4 = new SimpleDateFormat("dd-MM-yyyy").parse(string[38]);
                                if (string[25].equals("")) {
                                    string[25] = "0";
                                }
                                if (string[27].equals("")) {
                                    string[27] = "0";
                                }
                                if (string[28].equals("")) {
                                    string[28] = "0";
                                }
                                if (string[31].equals("")) {
                                    string[31] = "0";
                                }
                                if (string[36].equals("")) {
                                    string[36] = "0";
                                }
                                if (string[37].equals("")) {
                                    string[37] = "0";
                                }
                                if (string[41].equals("")) {
                                    string[41] = "0";
                                }
                                sql = "INSERT INTO DBA.CS_Colocaciones (Departamento,Municipio,Ciudad,Nombre,PrimerNoombre,SegundoNombre,PrimerApellido,SegundoApellido,Identidad,LugarNacimiento,FechaNacimiento,Edad,EstadoCivil,Sexo,NivelEducativo,Profesion,TipodePersona,Dependiantes,DireccionDomicilio,Telefono,GrupoSolidario,SectorEconomico,ActividadEconomica,DireccionNegocio,FechaSolicitud,MontoAutorizado,Plazo,ValordelAhorro,Tasa,FechaAutorizacion,FormadePago,PrestamoNumero,Gestor,Supervisor,Coordinador,TipoCliente,IFI,Ciclo,FechaLog,EstatusPrestamo,Observaciones,idPrestamo,GrupoSolidarioHash,Documento,Programa,Fondo,NombreRef1,TelefonoRef1,DireccionRef1,ParentescoRef1,NombreRef2,TelefonoRef2,DireccionRef2,ParentescoRef2,NombreRef3,TelefonoRef3,DireccionRef3,ParentescoRef3,NombreRef4,TelefonoRef4,DireccionRef4,ParentescoRef4,NombreAval,TelefonoAval,IdentidadAval,DireccionAval) values ('" + string[0].toString().trim() + "','" + string[1].toString().trim() + "','" + string[2].toString().trim() + "','" + string[3].toString().trim() + "','" + string[4].toString().trim() + "','" + string[5].toString().trim() + "','" + string[6].toString().trim() + "','" + string[7].toString().trim() + "','" + string[8].toString().trim() + "','" + string[9].toString().trim() + "','" + string[10] + "','" + string[11].toString().trim() + "','" + string[12].toString().trim() + "','" + string[13].toString().trim() + "','" + string[14].toString().trim() + "','" + string[15].toString().trim() + "','" + string[16].toString().trim() + "','" + string[17].toString().trim() + "','" + string[18].toString().trim() + "','" + string[19].toString().trim() + "','" + string[20].toString().trim() + "','" + string[21].toString().trim() + "','" + string[22].toString().trim() + "','" + string[23].toString().trim() + "','" + string[24] + "'," + string[25].toString().trim() + ",'" + string[26].toString().trim() + "','" + string[27].toString().trim() + "','" + string[28].toString().trim() + "','" + string[29] + "', '" + string[30].toString().trim() + "','" + string[31].toString().trim() + "','" + string[32].toString().trim() + "','" + string[33].toString().trim() + "','" + string[34].toString().trim() + "','" + string[35].toString().trim() + "','" + string[36].toString().trim() + "','" + string[37].toString().trim() + "','" + string[38] + "','" + string[39].toString().trim() + "','" + string[40].toString().trim() + "','" + string[41].toString().trim() + "','" + string[42].toString().trim() + "','" + string[43].toString().trim() + "','" + string[44].toString().trim() + "','" + string[45].toString().trim() + "','" + string[46].toString().trim() + "','" + string[47].toString().trim() + "','" + string[48].toString().trim() + "','" + string[49].toString().trim() + "','" + string[50].toString().trim() + "','" + string[51].toString().trim() + "','" + string[52].toString().trim() + "','" + string[53].toString().trim() + "','" + string[54].toString().trim() + "','" + string[55].toString().trim() + "','" + string[56].toString().trim() + "','" + string[57].toString().trim() + "','" + string[58].toString().trim() + "','" + string[59].toString().trim() + "','" + string[60].toString().trim() + "','" + string[61].toString().trim() + "','" + string[62].toString().trim() + "','" + string[63].toString().trim() + "','" + string[64].toString().trim() + "','" + string[65].toString().trim() + "')";
                                System.out.println("OJO : SQL  Numero : " + nn + "   = " + sql);
                                Statement stmt = con.createStatement();
                                int rows = stmt.executeUpdate(sql);
                                String respuestaConfirma = sendConfirmarColocacion(string[41]);
                                if (respuestaConfirma.equals("OK")) {
                                    System.out.println("Resultado confirmación de colocación : " + respuestaConfirma);
                                    sql1 = "UPDATE DBA.CS_Colocaciones SET EstatusPrestamo='Colocado' where idPrestamo = " + string[41];
                                    rows = stmt.executeUpdate(sql1);
//                                    System.exit(0);
                                } else {
                                    System.out.println("No confirmo colocación : " + respuestaConfirma);
                                }
                                count++;
                                con.commit();
                                stmt.close();
                                n = 0;
                                string[n] = "";
                                nn++;
                                //                            sw = "1";
                                break;
                            }
                            sw = "0";
                            break;
                        }
                        sw = "0";
                        break;
                    case VALUE_NUMBER:
                        BigDecimal number = parser.getBigDecimal();
                        System.out.println(number);
                        break;
                    case VALUE_TRUE:
                        System.out.println(true);
                        break;
                    case VALUE_FALSE:
                        System.out.println(false);
                        break;
                }
            }

            System.out.println("OJO : " + sql);
            parser.close();
        } catch (Exception e) {
            System.out.println("\nError invocando a Login/Autenticacion REST Service");
            System.out.println(e);
        }
    }

    private static String sendConfirmarColocacion(String idCredito) {

        try {
            String urlParameters = "user=guadalupe&password=kY7aBLHk&id_credito=" + idCredito + "&tipo=confirmar_colocacion";
            byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
            int postDataLength = postData.length;
            String query_url = "http://api.creditosolidario.hn/send.php";
            System.out.println(urlParameters);
            URL url = new URL(query_url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Length", Integer.toString(postDataLength));
            conn.setConnectTimeout(5000);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
            conn.setDoOutput(true);
//           conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");

            OutputStream os = conn.getOutputStream();
            os.write(postData);
            os.flush();

// read the response
            InputStream in = new BufferedInputStream(conn.getInputStream());

            String result = IOUtils.toString(in, "UTF-8");
//            System.out.println(result);
            String eso1 = new java.util.Scanner(result).useDelimiter("\\A").next();
            System.out.println("Resultado : " + result);
            eso1 = eso1.replace("<br />\n" +
"<b>Warning</b>:  Use of undefined constant status - assumed 'status' (this will throw an Error in a future version of PHP) in <b>C:\\inetpub\\wwwroot\\csfrontend\\api\\functions.php</b> on line <b>15</b><br />", "");
            System.out.println("result after Reading JSON Response");
            
//            if (eso1.trim().equals("200")) {
//                return "OK";
//
//            } else {
//                return "NOOK";
//            }
//            eso = eso.replace("[, "");
//            eso = eso.replaceAll("], "");
//            System.out.println(eso);
//            System.out.println("Resultado : " + result);
            eso1 = eso1.replace("Error 1049 SQLSTATE[HY000] [1049] Unknown database 'circular_memo'", "");
            System.out.println("result after Reading JSON Response");

            final JsonParser parser1 = Json.createParser(new StringReader(eso1));
            String key1 = null;
            String value1 = null;

            while (parser1.hasNext()) {
                final Event event = parser1.next();
                switch (event) {
                    case KEY_NAME:
                        key1 = parser1.getString();
                        System.out.println(key1 + " ****** confirmarColocación  ****** ");
                        break;
                    case VALUE_NULL:
                        return "NOOK";
//                        break;
                    case VALUE_NUMBER:
                        System.out.println("  *** confirmarColocación *** Respuesta  : " + parser1.getInt());
                        respuestaConfirmar = String.valueOf(parser1.getInt());
                        if (respuestaConfirmar.equals("200")) {
                            return "OK";

                        } else {
                            return "Respuesta = " + respuestaConfirmar + " - idCredito " + idCredito;
                        }
                    case VALUE_STRING:
                        System.out.println("  *** confirmarColocación *** Respuesta  : " + parser1.getString());
                        respuestaConfirmar = parser1.getString().trim();
                        if (respuestaConfirmar.equals("200")) {
                            return "OK";

                        } else {
                            return "Respuesta = " + respuestaConfirmar + " - idCredito " + idCredito;
                        }

//                        break;
                    case VALUE_TRUE:
                        System.out.println(true);
                        break;
                    case VALUE_FALSE:
                        System.out.println(false);
                        break;
                }
            }
//            con.close();
//            parser1.close();
        } catch (MalformedURLException ex) {
            Logger.getLogger(BalcamRegistrar.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(BalcamRegistrar.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;

    }
}

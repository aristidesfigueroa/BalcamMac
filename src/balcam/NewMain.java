/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package balcam;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Administrador
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        String codTrans = "TestFile";
        String str = "[{\"Errores\":null,\"EstadoRespuesta\":1,\"TransaccionCodigo\":\"1529\",\"TransaccionFechaContable\":\"19/12/2019\",\"MensajeRespuesta\":\"1. Transacción procesada exitosamente.\"}]";
        BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\jeta\\ArchivoBALCAM\\"+codTrans+".txt"));
        writer.write(str);

        writer.close();
    }
}



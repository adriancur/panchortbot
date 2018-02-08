package uy.panchobot.utiles;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ManejadorArchivosGenerico {

    //Logger
    private static final Logger LOG = Logger.getLogger(ManejadorArchivosGenerico.class.getName());

    /**
     * @param nombreCompletoArchivo
     * @param listaLineasArchivo lista con las lineas del archivo
     */
    public static void escribirArchivo(String nombreCompletoArchivo,
            String[] listaLineasArchivo) {
        FileWriter fw;
        try {
            fw = new FileWriter(nombreCompletoArchivo, true);
            BufferedWriter bw = new BufferedWriter(fw);
            for (String lineaActual : listaLineasArchivo) {
                bw.write(lineaActual);
                bw.newLine();
            }
            bw.close();
            fw.close();
        } catch (IOException e) {
            LOG.log(Level.SEVERE, "Error al escribir el archivo {0}", nombreCompletoArchivo);
        }
    }

    public static String[] leerArchivo(String nombreCompletoArchivo) {
        FileReader fr;
        ArrayList<String> listaLineasArchivo = new ArrayList<String>();
        try {
            fr = new FileReader(nombreCompletoArchivo);
            BufferedReader br = new BufferedReader(fr);
            String lineaActual = br.readLine();
            while (lineaActual != null) {
                listaLineasArchivo.add(lineaActual);
                lineaActual = br.readLine();
            }
            br.close();
            fr.close();
        } catch (FileNotFoundException e) {
            LOG.log(Level.SEVERE, "Error al leer el archivo {0}", nombreCompletoArchivo);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, "Error al leer el archivo {0}", nombreCompletoArchivo);
        }
        LOG.log(Level.INFO, "Archivo leido satisfactoriamente");

        return listaLineasArchivo.toArray(new String[0]);
    }

    /**
     * @return the LOG
     */
    public static Logger getLOG() {
        return LOG;
    }
}

package uy.panchobot.utils;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileManager {

    private static FileManager singletonFileManagerInstance = null;
    public static final String FILE_NAME = "meta-inf/rimas.txt";

    //Logger
    private static final Logger LOG = Logger.getLogger(FileManager.class.getName());

    public String[] readFileLines() {
        List<String> listaLineasArchivo = new ArrayList<String>();
        try {
            InputStream is = this.getClass().getClassLoader().getResourceAsStream(FILE_NAME);

            BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            listaLineasArchivo =  br.lines().collect(Collectors.toList());

            br.close();
            is.close();
        } catch (FileNotFoundException e) {
            LOG.log(Level.SEVERE, "Error al leer el archivo {0}", FILE_NAME);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, "Error al leer el archivo {0}", FILE_NAME);
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

    protected FileManager() {}

    public static FileManager getInstance() {
        if(singletonFileManagerInstance == null) {
            singletonFileManagerInstance = new FileManager();
        }
        return singletonFileManagerInstance;
    }
}

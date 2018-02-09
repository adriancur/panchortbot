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
    public static final String FILE_NAME = "rimas.txt";

    //Logger
    private static final Logger LOG = Logger.getLogger(FileManager.class.getName());

    public void writeFile(String message) {
        try {
            //Check if exist modified file in user.dir
            //if not, create a new one and copy the default content.
            File file = new File(System.getProperty("user.dir"), FILE_NAME);
            if(!file.exists()){
                file.createNewFile();
                Writer output =  new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8));
                InputStream is = this.getClass().getClassLoader().getResourceAsStream(FILE_NAME);
                BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
                List<String> originalLines = br.lines().collect(Collectors.toList());
                for (String line: originalLines) {
                    output.append(line);
                    output.append(System.lineSeparator());
                }
                output.append(message);
                output.append(System.lineSeparator());
                output.close();
            }else{
                Writer output = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file,true), StandardCharsets.UTF_8));
                output.append(message);
                output.append(System.lineSeparator());
                output.close();
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE, "Error al escribir el archivo {0}", FILE_NAME);
        }
    }

    public String[] readFileLines() {
        List<String> listaLineasArchivo = new ArrayList<String>();
        InputStream is = null;
        try {
            //Check if exist modified file in user.dir
            //if not, use default file.
            File file = new File(System.getProperty("user.dir"), FILE_NAME);
            if(file.exists() && !file.isDirectory()){
                is = new FileInputStream(file);
            }else{
                is = this.getClass().getClassLoader().getResourceAsStream(FILE_NAME);
            }

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

package com.cfao.app.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ResourceBundle;

/**
 * Created by armel on 24/08/2017.
 */
public class Log {

    private LocalDateTime time;
    private String message = null;
    private String path;
    private FileWriter fos;
    private Exception exception = null;
    private File file;

    public Log(){
        this.time = LocalDateTime.now();
        Path path = Paths.get(ResourceBundle.getBundle("Bundle").getString("log.dir")).toAbsolutePath();
        String chemin = path.toString() + File.separator + "errorLog.txt";
        file = new File(chemin);

        //this.path = getClass().getResource("/errLog.txt").getPath();
    }

    public Log (String message){
        this();
        this.message = message;
    }

    public void write(){

        try {
            this.fos = new FileWriter(this.file, true);
            String str = "**************** " + this.time.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)) +
                    " ********************************************************\n";
            String str2 ="\n" + "-----------------------------------------------------------------------------------------------\n\n\n";

            this.fos.write(str);
            if(message != null) {
                this.fos.write(this.message);
            }
            if(exception != null){
                this.fos.write(exception.getLocalizedMessage());
            }
            this.fos.write(str2);

            this.fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public Log(Exception ex){
        this();
        this.exception = ex;
    }
}
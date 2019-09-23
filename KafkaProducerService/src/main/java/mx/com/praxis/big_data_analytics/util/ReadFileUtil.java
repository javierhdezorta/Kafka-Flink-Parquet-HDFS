package mx.com.praxis.big_data_analytics.util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import com.csvreader.CsvReader;

/***
 * Esta clase se encarga de leer el archivo de texto, y obtenener las
 * columnas necesarios de acuerdo
 * a las necesidades que se requieran y de acuerdo a los campos que se
 * quieran utilizar
 */
public class ReadFileUtil {

    /**
     * Este metodo se encarga de leer el archivo, requiriendo del path del archivo,
     * de vuelve un arraylist con los datos leidos de acuerdo a un formato dado
     * */
    public ArrayList<String> readFile(String path){

        ArrayList<String>rows=new ArrayList<String>();
        String row="";
        String append=Constants.KEYAPPEND;

        try {
            CsvReader reader = new CsvReader(new FileReader(path), Constants.KEYSEPARATOR);

            while (reader.readRecord())
            {
                String id = reader.get(0);
                String movie = reader.get(1);
                String date = reader.get(2);
                String imdb = reader.get(4);

                row=(id + append + movie+ append + date+ append +imdb);
                rows.add(row);
            }

            reader.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rows;
    }
}

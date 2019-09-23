package mx.com.praxis.big_data_analytics;

import mx.com.praxis.big_data_analytics.kafka.KafkaProducerUtil;
import mx.com.praxis.big_data_analytics.util.Constants;
import mx.com.praxis.big_data_analytics.util.ReadFileUtil;

/**
 * Esta clase es en donde se encuentra el metodo main
 */

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        ReadFileUtil file=new ReadFileUtil();// se crea una instancia del la clase Readfile
        KafkaProducerUtil kafkaProducer=	new KafkaProducerUtil();// se crea una instancia de la clase KafkaProducer


        ArrayList<String> rows=file.readFile(Constants.PATHFILE);// se almacena en un arraylist los datos leidos del archivo
        for (String row:rows  ) {
            kafkaProducer.producerPublish(Constants.KAFKATOPIC,	row);// metodo se encarga de producir y publicar los row del arraylist
        }

        //kafkaProducer.closeProducer();
    }
}

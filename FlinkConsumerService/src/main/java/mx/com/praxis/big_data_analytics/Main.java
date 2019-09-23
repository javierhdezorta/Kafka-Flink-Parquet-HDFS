package mx.com.praxis.big_data_analytics;

import mx.com.praxis.big_data_analytics.kafka.FlinkConsumerFromKafkaUtil;
import mx.com.praxis.big_data_analytics.util.Constants;

public class Main {

    /**
     * Clase en donde se encuantra el main
     */
    public static void main(String[] args) {

        FlinkConsumerFromKafkaUtil consume=new FlinkConsumerFromKafkaUtil();//se genera una instancia de la clase FlinkConsumerFromKafkaUtil
        String[] avroTagsNames={Constants.ID,Constants.MOVIE,Constants.DATE,Constants.IMDB};//Un arreglo de los names que es de acuerdo al esquema avro que se utiliza
        try {
            /***
             * Se llama al metodo que le el datastream de kafka, genera los parquet y los almacena
             * en hdfs, en el cual se le pasan algunos parametros constantes
             */
            consume.storeDataStreamToHDFS(Constants.KAFKATOPIC,consume.setProperties(Constants.KAFKAHOSTS,Constants.FLINKCONSUMERGROUPID),avroTagsNames);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

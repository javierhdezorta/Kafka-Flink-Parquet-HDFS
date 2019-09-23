package mx.com.praxis.big_data_analytics.kafka;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
import mx.com.praxis.big_data_analytics.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * Esta clase se encarga de producir (publicar) los datos del archivo
 */

public class KafkaProducerUtil {
    //variable necesaria para crear un producer de kafka
    private	static Producer<String,	String> producer;

    //El constructor se encarga de configurar las properties de acuerdo
    // a nustro kafka daemon
    public KafkaProducerUtil()	{
        Logger logger = LoggerFactory.getLogger(KafkaProducerUtil.class);
        logger.info("praxis.big_data_analytics");

        Properties props	=	new	Properties();
        props.put("metadata.broker.list", Constants.KAFKAHOSTS);// se estable el host de nuestro kafka daemon
        props.put("serializer.class",	Constants.KAFKASERIALIZER);
        props.put("request.required.acks",	Constants.KAFKAREQUEST);

        ProducerConfig config	=	new	ProducerConfig(props);
        producer = new	Producer<String,String>(config);
    }

    /***
     * Este metodo se encarga de publicar, es decir de producir los datos del archivo de texto
     */
    public	void	producerPublish(String	topic,	String message) {
        String	msg	=	message;
        System.out.println(msg);

        /**
         * vartiable que se utiliza para producir,el cual se le define el topico de kafka, y el mensaje a producir
         */
        KeyedMessage<String,	String> data	=new	KeyedMessage<String,	String>(topic,	msg);

        producer.send(data);
    }

    /**
     * Metodo que cierra la conexion del producer de kafka
     */
    public void closeProducer(){
        producer.close();
    }
}

package mx.com.praxis.big_data_analytics.util;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;

import org.apache.hadoop.fs.Path;
import org.apache.parquet.avro.AvroParquetWriter;
import org.apache.parquet.hadoop.ParquetWriter;
import org.apache.parquet.hadoop.metadata.CompressionCodecName;

import java.io.File;
import java.io.IOException;

/**
 *Esta clase se encarga de generar los archivos parquet
 */

public class WriteParquetUtil {

    /**
     * Este metodo se encarga de validar el arreglo que contiene la información
     * de los campos que se utilizan para generar el parquet; como solo requerimos de 4 campos,
     * validamos que el arreglo cumpla con los 4 campos
     */
    public static boolean validateDataArray(String[] data){
        if(data.length==4)
            return true;
        else
            return false;
    }

    /**
     *Este metodo se encarga de escribir los parquet en hdfs utilizando un esquema avro el cual
     * es el esqueleto de los parquets.
     * Se utiliza un arreglo que contiene 4 datos los cuales son los datos que se van a utilizar
     * para los campos de los parquets.
     * Se le incluye la direccion del dir. en hdfs
     */
    public static void  generateParquet(String[] datos,String parquetFileName,int parquetPostFileName,String uriHDFSPath,String avroSchemeLocation,String[] avroTagsNames ) throws IOException {

        if(datos!=null) {
            Schema avroSchema = new Schema.Parser().parse(new File(avroSchemeLocation));
            String parquetFile = uriHDFSPath + "/" + parquetFileName + parquetPostFileName + Constants.PARQUETFORMATSTORE;
            Path path = new Path(parquetFile);
            AvroParquetWriter parquetWriter = new AvroParquetWriter(path, avroSchema, CompressionCodecName.SNAPPY, ParquetWriter.DEFAULT_BLOCK_SIZE, ParquetWriter.DEFAULT_PAGE_SIZE);
            GenericRecord record = new GenericData.Record(avroSchema);

            if (datos.length == avroTagsNames.length) {
                int index = 0;
                for (String tagName : avroTagsNames) {
                    record.put(tagName, datos[index]);
                    index++;
                }
                parquetWriter.write(record);//escribimos el parquet
            }
            parquetWriter.close();// es necesario cerrar para que los datos se escriban de otro modo no lo hace y genera los parquet vacios
        }
    }
}

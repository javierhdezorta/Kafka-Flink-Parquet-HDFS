# POC-Kafka-Flink-Parquet-HDFS -- Prueba de concepto de arquitectura

El proyecto POC-Kafka-Flink-Parquet-HDFS se divide en dos partes.

Son dos proyectos en maven con java, El primer proyecto es el de KafkaProducerService, el cual se encarga de producir los mensajes que vaya leyendo del archivo de texto, el archivo es ```u.item``` ,y se encuentra en el path del proyecto : ```POC-Kafka-Flink-Parquet-HDFS/KafkaProducerService/src/files/```.

El segundo proyecto de FlinkConsumerService, ese proyecto se encarga de consumir el datastream que genere kafka, es decir es un consumer,  en el momento en que este consumiendo del topico de kafka, este va generando archivos parquet y los va  alamacenando en hdfs. 

Los proyectos se deben de importar en el ide IntelliJ IDEA,  para descargar el ide ve a  <a href='https://www.jetbrains.com/idea/download/#section=linux' target='_blank' rel='nofollow'>https://www.jetbrains.com/idea/download/#section=linux/</a> , y pueden descargar la version community, y una vez que lo tengan instalado, para importar un proyecto solo le dan click en ```Import Project```. eligen la ruta del proyecto,  despues en el tipo de proyecto eligen maven, y lo demas lo pueden dejar como esta por default.

Se tienen que importar ambos proyectos el de ```KafkaProducerService```, y el de ```FlinkConsumerService```; además de que flink lo vamos a utilizar como un mini cluster desde el ide; hay dos formas de ejecutar un proyecto de flink: desde un cluster con flink, o desde un ide como es nuestro caso.

En el proyecto ```KafkaProducerService``` es necesario cambiar la ruta del archivo antes de ejecutar la aplicación, para eso hay que cambiar una constante que se encuentra en la clase ```Constants.java```,  se debe de cambiar la ruta del ```PATHFILE="";``` por la nueva ruta del archivo ```u.item``` local, se debe de colocar la ruta completa.

Una vez que se tengan los dos proyectos es necesario utilizar las siguientes herramientas, apache kafka, apache zookeper y apache hadoop, es necesario que se encuentren instalados y con los servicios levantados de cada uno antes de ejecutar los proyectos; para descargarlos :

* Link apache kafka :  <a href='http://www-eu.apache.org/dist/kafka/1.0.1/kafka_2.11-1.0.1.tgz' target='_blank' rel='nofollow'>http://www-eu.apache.org/dist/kafka/1.0.1/kafka_2.11-1.0.1.tgz</a>  


* Link apache zookeper:<a href='http://www-us.apache.org/dist/zookeeper/stable/zookeeper-3.4.10.tar.gz' target='_blank' rel='nofollow'>http://www-us.apache.org/dist/zookeeper/stable/zookeeper-3.4.10.tar.gz</a> 
 

* Link apache hadoop : <a href='http://www-eu.apache.org/dist/hadoop/common/hadoop-3.0.0/hadoop-3.0.0.tar.gz' target='_blank' rel='nofollow'> http://www-eu.apache.org/dist/hadoop/common/hadoop-3.0.0/hadoop-3.0.0.tar.gz</a>
 
  

## Instalación y Configuración de las herramientas

Se van a instalar y configurar las herramientas de una forma local, y con una configuración básica stand alone.
También se debe tener instalado en el sitema java 8 con el jdk 8, para comprobar si se tiene java se ejecuta el siguiente comando en la consola ```java -version```; si acaso no se tiene se debe de instalar.


### Configuración de Java
Es necesario que java este instalado en especial la version de jdk 8 que es la mas estable, también es importante que se tenga la configuración de java 8 en el path, solo se agregan las siguientes líneas :

* Tipeamos ```gedit ~/.bashrc```

Y al final del archivo se agregan las siguientes lineas :
```
JAVA_HOME=<java_path_local>
PATH=$PATH:$HOME/bin:$JAVA_HOME/bin
export JAVA_HOME
export PATH
```

En el ```<java_path_local>``` se coloca la ruta en donde se encuentra el directorio donde esta instalado java 8, ejemplo ```JAVA_HOME=/usr/lib/jvm/java-8-oracle```. 

### Apache Zookeper

En el path donde se encuentra ```zookeeper-3.4.10.tar.gz```, abrimos una consola en ese directorio y tipeamos en la consola ```tar -zxf zookeeper-3.4.10.tar.gz```, con ese comando extraemos el archivo tar.gz; despues entramos en el directorio que se extrajo y en la carpeta ```conf``` renombramos el archivo ```zoo_sample.cfg``` por ```zoo.cfg```.

Ahora configuramos el path: en la consola tipeamos ```gedit ~/.bashrc```, y al final del archivo ponemos las siguientes lineas: 
``` 
export ZOOKEEPER_HOME=<paht dir>
export PATH=$PATH:$ZOOKEEPER_HOME/bin
```

En el ```<paht dir>``` se coloca la ruta en donde se encuentra el directorio que extrajimos, ejemplo ```ZOOKEEPER_HOME=/home/javier/Software/zookeeper-3.4.10```.

Guardamos los cambios del archivo, y cerramos.


### Apache Kafka

En el path donde se encuentra  ```kafka_2.11-1.0.1.tgz```, abrimos una consola en ese directorio y tipeamos en la consola
```tar -zxf kafka_2.11-1.0.1.tgz```, con ese comando extraemos el archivo tgz.

Configuramos el path: en la consola tipeamos ```gedit ~/.bashrc```, y al final del archivo ponemos las siguientes lineas: 
``` 
export KAFKA_HOME="<path dir>/kafka_<version>"
export PATH=$PATH:$KAFKA_HOME/bin
```

En el ```<paht dir>``` se coloca el path completo en donde se encuentra el directorio que extrajimos, ejemplo
 ```KAFKA_HOME=/home/javier/Software/kafka_2.11-1.0.1```.

Guardamos los cambios del archivo, y cerramos.


### Apache Hadoop

Primero ejecutamos los siguientes comandos:
```
javier$ sudo apt-get install ssh -y
javier$ sudo apt-get install pdsh -y
```

Una vez que los tengamos instalados generamos las claves de autenticación con las siguientes instrucciones, y también realizamos algunas configuraciones de permisos:
```
javier$  ssh-keygen -t rsa -P '' -f ~/.ssh/id_rsa
javier$  cat ~/.ssh/id_rsa.pub  >> ~/.ssh/authorized_keys
javier$  chmod 0600 ~/.ssh/authorized_keys
javier$  sudo su
root#    echo "ssh" > /etc/pdsh/rcmd_default
```
  

Ahora en el path donde se encuentra  ```hadoop-3.0.0.tar.gz```, abrimos una consola en ese directorio y tipeamos en la consola :
```tar -zxf hadoop-3.0.0.tar.gz```, con ese comando extraemos el archivo tar.gz.

Configuramos el path: en la consola tipeamos ```gedit ~/.bashrc```, y al final del archivo ponemos las siguientes lineas: 
``` 
export HADOOP_HOME="<path dir>"
export PATH=$PATH:$HADOOP_HOME/bin
export PATH=$PATH:$HADOOP_HOME/sbin
export HADOOP_MAPRED_HOME=${HADOOP_HOME}
export HADOOP_COMMON_HOME=${HADOOP_HOME}
export HADOOP_HDFS_HOME=${HADOOP_HOME}
export YARN_HOME=${HADOOP_HOME}
export HADOOP_CONF_DIR="${HADOOP_HOME}/etc/hadoop"
```

En el ```<paht dir>``` se coloca el path completo en donde se encuentra el directorio que extrajimos de hadoop, ejemplo ```"HADOOP_HOME="/home/javier/Software/hadoop-3.0.0"```.

Guardamos los cambios del archivo, y cerramos.

### Configuración de Hadoop

Para configurar hadoop, son varios archivos los cuales se tienen que editar, y se encuentran el dir ```$HADOOP_HOME/etc/hadoop/```.

* El primer archivo que se va editar es  ```core-site.xml```:

Se le agregan las siguientes lineas :
```
<configuration>
	<property>
		<name>fs.defaultFS</name>
		<value>hdfs://localhost:9000</value>
	</property>
	<property>
		<name>hadoop.tmp.dir</name>
		<value>/tmp/hadoop</value>
	</property>
</configuration>
```

* Configuramos el archivo ```hdfs-site.xml ```:

Se le agregan las siguientes lineas :

```
<configuration>
  <property>
    <name>dfs.replication</name>
    <value>3</value>
  </property>
</configuration>
```


* Configuramos el archivo ```mapred-site.xml```:

Se le agregan las siguientes lineas :

```
<configuration>
    <property>
        <name>mapreduce.framework.name</name>
        <value>yarn</value>
    </property>
</configuration>
```

* Configuramos el archivo ```yarn-site.xml```:

Se le agregan las siguientes lineas :

```
<configuration>
    <property>
        <name>yarn.nodemanager.aux-services</name>
        <value>mapreduce_shuffle</value>
    </property>
    <property>
        <name>yarn.nodemanager.env-whitelist</name>
        <value>JAVA_HOME,HADOOP_HOME,HADOOP_HDFS_HOME,HADOOP_CONF_DIR,HADOOP_YARN_HOME,HADOOP_MAPRED_HOME</value>
    </property>
    <property>
        <name>yarn.nodemanager.aux-services.mapreduce.shuffle.class</name>
        <value>org.apache.hadoop.mapred.ShuffleHandler</value>
    </property>
</configuration>
```

* Configuramos el archivo ```hadoop-env.sh``` :
Le agregamos las siguientes lineas al final del archivo:
```
export JAVA_HOME=<java_home_path_dir>

export HADOOP_HOME=<hadoop_home_path_dir>
```

Se debe de remplazar la linea ```<java_home_path_dir>``` por el path de java local; tambien se debe de remplazar la linea ```<hadoop_home_path_dir>``` por el path de hadoop local.
  
  
  
  
Una vez que ya terminamos todas las configuraciones, ejecutamos el siguiente comando :
```source ~/.bashrc``` , este comando se encarga de refrescar el archivo bashrc que contiene todos los path que configuramos.


## Ejecución del proyecto completo

Una vez que ya instalamos y configuramos todo lo que se necesita, ahora antes de ejecutar los proyectos :```KafkaProducerService```, y el de ```FlinkConsumerService```


### Levantar Servicios 

Es necesario que antes levantemos los servicios:

* Levantamos Hadoop de la siguiente manera, en la consola tipeamos los siguientes comandos:

```
javier$ $HADOOP_HOME/bin/hdfs namenode -format  # solo se realiza este comando una vez
javier$ $HADOOP_HOME/sbin/start-dfs.sh
javier$ $HADOOP_HOME/sbin/start-yarn.sh
```
Para verificar que estan levantados los servicios, ejecutamos el siguiente comando :
```javier$ jps```, y nos tiene que mostrar los nodos en ejecución.


* Levantamos Zookeper de la siguiente manera, en la consola tipeamos el siguiente comando :
```
javier$ sudo  $ZOOKEEPER_HOME/bin/zkServer.sh start
```

  
* Levantamos Kafka de la siguiente manera, en la consola tipeamos el siguiente comando :
```
javier$ sudo $KAFKA_HOME/bin/kafka-server-start.sh -daemon $KAFKA_HOME/config/server.properties
```


### Creamos Kafka Topic

Despues de levantar kafka es necesario que creemos un topic de kafka, este topic es al que referenciamos en ambos proyectos de java, es necesario que se llame igual como lo definimos en los proyectos de java, ya que este es el topic que va hacer utilizado y si acaso no existe entonces al momento de ejecutar los proyectos va a suceder un error ya que no existe el topic de donde se va a producir y consumir la información.

* Creamos el topic de la siguiente manera:
```
javier$ sudo $KAFKA_HOME/bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic dataflow
``` 


  
Una vez que ya creamos el topic, ahora ya podemos ejecutar los proyectos de java.

### Ejecución de Projectos KafkaProducerService y de  FlinkConsumerService

Una vez que tengamos abiertos cada proyectos por separado en IntelliJ IDEA, y que ya tengamos instalados y levantados todos los servicios necesarios, ya podemos ejecutar las aplicaciones de java.

Tambien se pueden cambiar las contantes en ambos proyectos de acuerdo a sus necesidades, estas constantes se encuentran en la clase ```Constants.java``` en ambos proyectos.

#### Pasos para la ejecución

1. Primero se debe de ejecutar el consumidor, ejecutamos PRIMERO el proyecto  de ```FlinkConsumerService```

2. Despues una vez que ejecutamos  ```FlinkConsumerService```, ahora ejecutamos el proyecto de ```KafkaProducerService```


  
NOTA: ES MUY IMPORTANTE QUE PRIMERO SE EJECUTE FlinkConsumerService, Y UNA VEZ QUE ESTE EN EJECUCIÓN FlinkConsumerService SE PROCEDE A EJECUTAR KafkaProducerService.

## Verificación

Una vez que ya se haya terminado de leer el archivo y de que FlinkConsumerService consuma el stream; para verificar que se hayan generado los parquet y alamacenado en hdfs tipeamos el siguiente comando:
```hdfs dfs -ls /dataflowTest```.

Este directorio : ```dataflowTest``` hace referencia al path en donce se va a almacenar los parquet, este path esta definido en el proyecto  ```FlinkConsumerService``` en la clase ```Constants.java```    y la variable es ```HDFSPATH```

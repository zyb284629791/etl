FROM ibmjava:latest

COPY ./kafka_2.11-2.1.0.tgz /opt/kafka/

WORKDIR /opt/kafka

RUN tar -xzf kafka_2.11-2.1.0.tgz && rm kafka_2.11-2.1.0.tgz && cd kafka_2.11-2.1.0 && sed -i 's/zookeeper.connect=localhost:2181/zookeeper.connect=zookeeper:2181/g' ./config/server.properties

WORKDIR /opt/kafka/kafka_2.11-2.1.0


ENTRYPOINT ['bin/kafka-server-start.sh','config/server.properties']

EXPOSE 9092


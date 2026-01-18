package com.healthcare.kafka;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Properties;

public class PatientVitalsProducer {

    public static void main(String[] args) throws Exception {

        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        Producer<String, String> producer = new KafkaProducer<>(props);

        BufferedReader br = new BufferedReader(
                new FileReader("src/main/resources/patient_vitals.csv"));

        String line;
        br.readLine();

        while ((line = br.readLine()) != null) {

            String[] d = line.split(",");

            String json = "{"
                    + "\"patient_id\":\"" + d[0] + "\","
                    + "\"recorded_time\":\"" + d[1] + "\","
                    + "\"heart_rate\":" + d[2] + ","
                    + "\"spo2\":" + d[3] + ","
                    + "\"systolic_bp\":" + d[4] + ","
                    + "\"diastolic_bp\":" + d[5] + ","
                    + "\"body_temperature\":" + d[6] + ","
                    + "\"alert_flag\":\"" + d[7] + "\""
                    + "}";

            ProducerRecord<String, String> record =
                    new ProducerRecord<>("patient_vitals", json);

            producer.send(record);
            System.out.println("Sent → " + json);

            Thread.sleep(1000);
        }

        producer.close();
        br.close();
    }
}

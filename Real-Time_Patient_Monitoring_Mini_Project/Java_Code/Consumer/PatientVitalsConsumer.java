package com.healthcare.kafka;

import java.sql.*;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.serialization.StringDeserializer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PatientVitalsConsumer {

    public static void main(String[] args) throws Exception {

        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "healthcare-group");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList("patient_vitals"));

        String url = "jdbc:postgresql://localhost:5432/HealthcareMonitoring";
        String user = "postgres";
        String password = "postgres";

        Connection conn = DriverManager.getConnection(url, user, password);

        String sql = "INSERT INTO patient_vitals VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);

        ObjectMapper mapper = new ObjectMapper();

        System.out.println("Kafka Consumer Started...");

        while (true) {

            ConsumerRecords<String, String> records =
                    consumer.poll(Duration.ofMillis(1000));

            for (ConsumerRecord<String, String> record : records) {

                JsonNode json = mapper.readTree(record.value());

                ps.setString(1, json.get("patient_id").asText());
                ps.setTimestamp(2,
                        Timestamp.valueOf(json.get("recorded_time").asText()));
                ps.setInt(3, json.get("heart_rate").asInt());
                ps.setInt(4, json.get("spo2").asInt());
                ps.setInt(5, json.get("systolic_bp").asInt());
                ps.setInt(6, json.get("diastolic_bp").asInt());
                ps.setDouble(7, json.get("body_temperature").asDouble());
                ps.setString(8, json.get("alert_flag").asText());

                ps.executeUpdate();

                System.out.println("Inserted patient: "
                        + json.get("patient_id").asText());
            }
        }
    }
}

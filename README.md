# Real-Time Patient Monitoring System 🏥

A comprehensive real-time healthcare monitoring solution that streams, processes, and visualizes patient vital signs using modern big data technologies. This system enables healthcare providers to monitor patient health metrics in real-time and receive instant alerts for critical conditions.

## 📋 Overview

This project implements a real-time patient monitoring system that:
- Streams patient vital signs data through Apache Kafka
- Stores data in PostgreSQL database
- Visualizes real-time analytics through Apache Superset dashboards
- Monitors critical health indicators including heart rate, SpO2, blood pressure, and body temperature
- Provides alert flags (NORMAL, WARNING, CRITICAL) based on patient vitals

## 🏗️ Architecture

The system follows a modern data streaming architecture:

1. **Data Source**: CSV file containing patient vital signs
2. **Producer**: Java application that reads patient data and publishes to Kafka topic
3. **Kafka Cluster**: Message broker with Zookeeper for distributed coordination
4. **Consumer**: Java application that consumes from Kafka and writes to PostgreSQL
5. **PostgreSQL Database**: Persistent storage for patient records
6. **Apache Superset**: Real-time visualization and analytics dashboard

```
CSV Data → Kafka Producer → Kafka Topic → Kafka Consumer → PostgreSQL → Superset Dashboards
```

## ✨ Features

- **Real-time Data Streaming**: Continuous streaming of patient vitals using Apache Kafka
- **Scalable Architecture**: Distributed system capable of handling multiple patients simultaneously
- **Data Persistence**: Reliable storage in PostgreSQL database
- **Alert System**: Automatic categorization of patient conditions (NORMAL/WARNING/CRITICAL)
- **Interactive Dashboards**: Rich visualizations including:
  - Area charts for trend analysis
  - Bar graphs for comparative metrics
  - Scatter plots for correlation analysis
  - Real-time monitoring dashboards
- **Comprehensive Monitoring**: Tracks multiple vital signs:
  - Heart Rate (BPM)
  - Blood Oxygen Level (SpO2 %)
  - Blood Pressure (Systolic/Diastolic)
  - Body Temperature (°C)

## 🛠️ Technologies Used

- **Programming Language**: Java 11
- **Build Tool**: Apache Maven
- **Message Broker**: Apache Kafka 3.6.0
- **Database**: PostgreSQL
- **Data Processing**: Jackson JSON (2.15.2)
- **Visualization**: Apache Superset
- **Coordination Service**: Apache Zookeeper

### Maven Dependencies
- `kafka-clients` (3.6.0)
- `jackson-databind` (2.15.2)
- `postgresql` (42.7.3)
- `slf4j-simple` (1.7.36)

## 📋 Prerequisites

Before running this project, ensure you have the following installed:

- Java Development Kit (JDK) 11 or higher
- Apache Maven
- Apache Kafka (with Zookeeper)
- PostgreSQL Database
- Apache Superset (optional, for visualization)

## 🚀 Installation

### 1. Clone the Repository
```bash
git clone https://github.com/grandmaster-01/Real-Time-Patient-Monitoring.git
cd Real-Time-Patient-Monitoring
```

### 2. Set Up Kafka
```bash
# Start Zookeeper
bin/zookeeper-server-start.sh config/zookeeper.properties

# Start Kafka Server
bin/kafka-server-start.sh config/server.properties

# Create Kafka Topic
bin/kafka-topics.sh --create --topic patient_vitals --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1
```

### 3. Set Up PostgreSQL Database
```sql
-- Create database
CREATE DATABASE HealthcareMonitoring;

-- Connect to database
\c HealthcareMonitoring

-- Create table
CREATE TABLE patient_vitals (
    patient_id VARCHAR(20),
    recorded_time TIMESTAMP,
    heart_rate INTEGER,
    spo2 INTEGER,
    systolic_bp INTEGER,
    diastolic_bp INTEGER,
    body_temperature DECIMAL(4,2),
    alert_flag VARCHAR(20)
);
```

### 4. Build the Java Project
```bash
cd Real-Time_Patient_Monitoring_Mini_Project/Java_Code
mvn clean install
```

## ⚙️ Configuration

Update the following configurations in the Java files if needed:

### Kafka Configuration
- **Bootstrap Server**: `localhost:9092`
- **Topic Name**: `patient_vitals`
- **Consumer Group**: `healthcare-group`

### PostgreSQL Configuration
- **URL**: `jdbc:postgresql://localhost:5432/HealthcareMonitoring`
- **Username**: `postgres`
- **Password**: `postgres`

Update these values in `PatientVitalsConsumer.java` if your setup differs.

## 🎯 Usage

### Step 1: Ensure Prerequisites are Running
```bash
# Verify Zookeeper is running
# Verify Kafka server is running
# Verify PostgreSQL is running
```

### Step 2: Start the Consumer
```bash
cd Real-Time_Patient_Monitoring_Mini_Project/Java_Code
java -cp target/PatientVitalsProject-1.0.jar com.healthcare.kafka.PatientVitalsConsumer
```

### Step 3: Start the Producer
```bash
cd Real-Time_Patient_Monitoring_Mini_Project/Java_Code
java -cp target/PatientVitalsProject-1.0.jar com.healthcare.kafka.PatientVitalsProducer
```

The producer will read patient vitals from the CSV file and stream them to Kafka with a 1-second interval between records.

### Step 4: Set Up Superset (Optional)
1. Install and configure Apache Superset
2. Connect Superset to the PostgreSQL database
3. Create visualizations using the `patient_vitals` table
4. Build real-time monitoring dashboards

## 📁 Project Structure

```
Real-Time-Patient-Monitoring/
├── Real-Time_Patient_Monitoring_Mini_Project/
│   ├── Dataset/
│   │   └── patient_vitals.csv          # Sample patient data
│   ├── Java_Code/
│   │   ├── Producer/
│   │   │   └── PatientVitalsProducer.java  # Kafka producer
│   │   ├── Consumer/
│   │   │   └── PatientVitalsConsumer.java  # Kafka consumer
│   │   └── pom.xml                     # Maven configuration
│   ├── Kafka/
│   │   ├── kafka_topic_creation.png    # Kafka setup screenshots
│   │   └── zookeper.png
│   ├── SQL/
│   │   └── *.png                       # Database screenshots
│   ├── Superset/
│   │   ├── Area_chart.png              # Visualization examples
│   │   ├── Bar_graph.png
│   │   ├── Scatter_plot.png
│   │   └── dashboard.jpg
│   └── Report/
│       └── Real_time_analytics_project.pdf  # Detailed project report
├── README.md
└── LICENSE
```

## 📊 Data Schema

The system processes patient vitals with the following schema:

| Field | Type | Description |
|-------|------|-------------|
| patient_id | VARCHAR(20) | Unique patient identifier |
| recorded_time | TIMESTAMP | Time of vital signs recording |
| heart_rate | INTEGER | Heart rate in BPM |
| spo2 | INTEGER | Blood oxygen saturation (%) |
| systolic_bp | INTEGER | Systolic blood pressure |
| diastolic_bp | INTEGER | Diastolic blood pressure |
| body_temperature | DECIMAL(4,2) | Body temperature in °C |
| alert_flag | VARCHAR(20) | Health status (NORMAL/WARNING/CRITICAL) |

## 🔍 Sample Data

The project includes sample patient vital signs data with various health conditions:
- **NORMAL**: Healthy vital signs within normal ranges
- **WARNING**: Elevated vital signs requiring attention
- **CRITICAL**: Dangerous vital signs requiring immediate medical intervention

## 📈 Visualizations

The project includes pre-built Superset dashboards featuring:
- **Area Charts**: Time-series visualization of vital signs trends
- **Bar Graphs**: Comparative analysis across different patients
- **Scatter Plots**: Correlation analysis between different vital signs
- **Real-time Dashboard**: Consolidated view of all monitored patients

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

Copyright (c) 2026 Umesh Naik

## 👥 Author

**Umesh Naik** (grandmaster-01)

## 🙏 Acknowledgments

- Apache Kafka for reliable message streaming
- PostgreSQL for robust data storage
- Apache Superset for powerful data visualization
- The healthcare technology community

## 📞 Support

For questions or issues, please open an issue in the GitHub repository.

---

**Note**: This is a demonstration project for educational purposes. For production healthcare systems, ensure compliance with relevant regulations (HIPAA, GDPR, etc.) and implement appropriate security measures.
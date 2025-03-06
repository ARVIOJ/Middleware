package com.trainibit.middleware.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void enviarSolicitudReporte(String mensaje) {
        kafkaTemplate.send("reportes", mensaje);
        System.out.println("📩 Reporte enviado a Kafka: " + mensaje);
    }
}

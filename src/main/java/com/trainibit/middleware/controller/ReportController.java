package com.trainibit.middleware.controller;

import com.trainibit.middleware.response.ReportResponse;
import com.trainibit.middleware.service.KafkaProducerService;
import com.trainibit.middleware.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reporte")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping
    public ReportResponse obtenerReporte(@RequestParam String fecha) {
        return reportService.generarReporte(fecha);
    }

    private final KafkaProducerService kafkaProducerService;

    public ReportController(KafkaProducerService kafkaProducerService) {
        this.kafkaProducerService = kafkaProducerService;
    }

    @PostMapping("/generar")
    public String generarReporte(@RequestParam String fecha) {
        kafkaProducerService.enviarSolicitudReporte("Generar reporte para la fecha: " + fecha);
        return "📨 Solicitud de reporte enviada para la fecha: " + fecha;
    }

}

package com.trainibit.middleware.service;

import com.trainibit.middleware.dao.CampaignDao;
import com.trainibit.middleware.vo.ReportVo;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class KafkaConsumerService {

    private final CampaignDao campaignDao;
    private final ExecutorService executorService = Executors.newFixedThreadPool(5); // 5 hilos en paralelo

    public KafkaConsumerService(CampaignDao campaignDao) {
        this.campaignDao = campaignDao;
    }

    @KafkaListener(topics = "reportes", groupId = "report-group", concurrency = "3")
    public void procesarSolicitudReporte(String mensaje) {
        System.out.println("📥 Mensaje recibido de Kafka: " + mensaje);

        // Extraer fecha del mensaje
        var fecha = mensaje.replace("Generar reporte para la fecha: ", "").trim();
        var fechaTimestamp = Timestamp.valueOf(fecha + " 00:00:00");

        // Procesar en un hilo separado
        executorService.submit(() -> generarReportes(fecha, fechaTimestamp));
    }

    private void generarReportes(String fecha, Timestamp fechaTimestamp) {
        System.out.println("🔄 Iniciando generación de reporte para la fecha: " + fecha);

        List<ReportVo> reportes = campaignDao.obtenerReportesPorFecha(fechaTimestamp);
        if (reportes.isEmpty()) {
            System.out.println("⚠ No hay datos para la fecha: " + fecha);
            return;
        }

        generarReporteTXT(fecha, reportes);
    }


//    private void generarReporteTXT(String fecha, List<ReportVo> reportes) {
//        String fileName = "reports/reporte_" + fecha + ".txt";
//        File file = new File(fileName);
//
//        try (FileWriter writer = new FileWriter(file)) {
//            writer.append("=== REPORTE DE CAMPAÑAS (" + fecha + ") ===\n\n");
//
//            for (ReportVo reporte : reportes) {
//                writer.append("ID_CAMPANA: " + reporte.getIdCampana() + "\n");
//                writer.append("NOMBRE: " + reporte.getNombre() + "\n");
//                writer.append("FECHA_CREACION: " + reporte.getFechaCreacion().toString() + "\n");
//                writer.append("ESTADO: " + reporte.getEstado() + "\n");
//                writer.append("------------------------------\n");
//            }
//
//            System.out.println("✅ Reporte TXT generado: " + fileName);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    private void generarReporteTXT(String fecha, List<ReportVo> reportes) {
        String fileName = "reports/reporte_" + fecha + ".txt";
        File file = new File(fileName);

        try (FileWriter writer = new FileWriter(file)) {
            writer.append("=== REPORTE DE CAMPAÑAS (%s) ===\n\n".formatted(fecha));

            for (var reporte : reportes) {
                writer.append("""
                    ----------------------------------------
                    ID_CAMPANA: %d
                    NOMBRE: %s
                    FECHA_CREACION: %s
                    ESTADO: %s
                    ----------------------------------------
                    """.formatted(
                        reporte.getIdCampana(),
                        reporte.getNombre(),
                        reporte.getFechaCreacion().toString(),
                        reporte.getEstado()
                ));

                writer.append("🔹 DETALLES DE LA CAMPAÑA:\n");

                for (var detalle : reporte.getDetalles()) {
                    writer.append("""
                        - ID_DETALLE: %d
                        - TELEFONO: %s
                        - MENSAJE: %s
                        - FECHA_ENVIO: %s
                        ----------------------------------------
                        """.formatted(
                            detalle.getIdDetalle(),
                            detalle.getTelefono(),
                            detalle.getMensaje(),
                            detalle.getFechaEnvio().toString()
                    ));
                }

                writer.append("\n"); 
            }

            System.out.println("✅ Reporte TXT generado con detalles: " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

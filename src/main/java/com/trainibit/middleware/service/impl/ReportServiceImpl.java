package com.trainibit.middleware.service.impl;

import com.trainibit.middleware.dao.CampaignDao;
import com.trainibit.middleware.response.ReportResponse;
import com.trainibit.middleware.service.ReportService;
import com.trainibit.middleware.util.DateUtil;
import com.trainibit.middleware.vo.ReportVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {

    private final CampaignDao reportDao;
    public static final String DATE_FORMAT = "yyyy-MM-dd";

    public ReportServiceImpl(CampaignDao reportDao) {
        this.reportDao = reportDao;
    }

    @Override
    public ReportResponse generarReporte(String fecha) {
        Timestamp fechaTimestamp;
        try {
            fechaTimestamp = DateUtil.convertStringToTimestamp(fecha, DATE_FORMAT);
        } catch (Exception e) {
            throw new RuntimeException("Error al convertir la fecha: " + fecha, e);
        }

        List<ReportVo> listaResultadosVo = reportDao.obtenerReportesPorFecha(fechaTimestamp);

        ReportResponse response = new ReportResponse();
        response.setFecha(fecha);
        response.setTotalCampana(listaResultadosVo.size());
        response.setCampanas(listaResultadosVo);

        return response;
    }
}

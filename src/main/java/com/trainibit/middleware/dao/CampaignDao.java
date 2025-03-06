package com.trainibit.middleware.dao;

import com.trainibit.middleware.vo.ReportVo;

import java.sql.Timestamp;
import java.util.List;

public interface CampaignDao {
    List<ReportVo> obtenerReportesPorFecha(Timestamp fecha);
    List<ReportVo.DetalleVo> obtenerDetallesPorCampana(Long idCampana, int offset, int limit);
}

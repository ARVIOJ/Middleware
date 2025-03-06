package com.trainibit.middleware.mapper;

import com.trainibit.middleware.vo.ReportVo;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CampaignRowMapper implements RowMapper<ReportVo> {
    @Override
    public ReportVo mapRow(ResultSet rs, int rowNum) throws SQLException {
        //Mapear datos de la campaña de la tabla maestro
        ReportVo reporte = new ReportVo();
        reporte.setIdCampana(rs.getLong("ID_CAMPANA"));
        reporte.setNombre(rs.getString("NOMBRE"));
        reporte.setFechaCreacion(rs.getTimestamp("FECHA_CREACION"));
        reporte.setEstado(rs.getString("ESTADO"));
        return reporte;
    }

    }


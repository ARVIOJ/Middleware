package com.trainibit.middleware.mapper;

import com.trainibit.middleware.vo.ReportVo;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DetalleCampaignRowMapper implements RowMapper<ReportVo.DetalleVo> {
    @Override
    public ReportVo.DetalleVo mapRow(ResultSet rs, int rowNum) throws SQLException {
        ReportVo.DetalleVo detalle = new ReportVo.DetalleVo();
        detalle.setIdDetalle(rs.getLong("ID_DETALLE"));
        detalle.setTelefono(rs.getString("TELEFONO"));
        detalle.setMensaje(rs.getString("MENSAJE"));
        detalle.setFechaEnvio(rs.getTimestamp("FECHA_ENVIO"));
        return detalle;
    }
}

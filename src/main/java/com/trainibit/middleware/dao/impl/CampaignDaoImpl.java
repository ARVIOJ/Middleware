package com.trainibit.middleware.dao.impl;

import com.trainibit.middleware.dao.CampaignDao;
import com.trainibit.middleware.mapper.CampaignRowMapper;
import com.trainibit.middleware.mapper.DetalleCampaignRowMapper;
import com.trainibit.middleware.util.DataBaseParams;
import com.trainibit.middleware.vo.ReportVo;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.List;
import java.util.Map;

@Repository
public class CampaignDaoImpl implements CampaignDao {

    private final JdbcTemplate jdbcTemplate;

    private SimpleJdbcCall simpleJdbcCall;

    public CampaignDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName(DataBaseParams.NAME_SP_OBTENER_CAMPANAS)
                .declareParameters(
                        new SqlParameter(DataBaseParams.In.P_FECHA, Types.TIMESTAMP),
                        new SqlParameter(DataBaseParams.Out.P_RESULTADO, Types.REF_CURSOR)
                )
                .returningResultSet(DataBaseParams.Out.P_RESULTADO, new CampaignRowMapper());
    }

    @Override
    public List<ReportVo> obtenerReportesPorFecha(Timestamp fecha) {
        System.out.println("CAMAPAÑAS CON FECHA" + fecha);
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(DataBaseParams.In.P_FECHA, fecha);

        Map<String, Object> result = this.simpleJdbcCall.execute(params);

        List<ReportVo> reportes = (List<ReportVo>) result.get(DataBaseParams.Out.P_RESULTADO);

        // Obtener detalles para cada campaña
        for (ReportVo reporte : reportes) {
            List<ReportVo.DetalleVo> detalles = obtenerDetallesPorCampana(reporte.getIdCampana(),0,10);
            reporte.setDetalles(detalles);
        }

        System.out.println("Resultados obtenidos: " + reportes);

        return reportes;
    }

//    private List<ReportVo.DetalleVo> obtenerDetallesPorCampana(Long idCampana) {
//        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
//                .withProcedureName("SP_GET_DETALLES_CAMPANA")
//                .returningResultSet("p_result", new DetalleCampaignRowMapper());
//
//        MapSqlParameterSource params = new MapSqlParameterSource();
//        params.addValue("p_id_campana", idCampana);
//
//        Map<String, Object> result = jdbcCall.execute(params);
//        return (List<ReportVo.DetalleVo>) result.get("p_result");
//    }

    @Override
    public List<ReportVo.DetalleVo> obtenerDetallesPorCampana(Long idCampana, int offset, int limit) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("SP_GET_DETALLES_CAMPANA")
                .returningResultSet("P_RESULT", new DetalleCampaignRowMapper());  // ✅ Mapea correctamente a DetalleVo

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("P_ID_CAMPANA", idCampana);
        params.addValue("P_OFFSET", offset);
        params.addValue("P_LIMIT", limit);

        Map<String, Object> result = jdbcCall.execute(params);

        return (List<ReportVo.DetalleVo>) result.get("P_RESULT");
    }

}

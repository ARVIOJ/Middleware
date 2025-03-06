package com.trainibit.middleware.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReportVo {
    private Long idCampana;
    private String nombre;
    private Timestamp fechaCreacion;
    private String estado;
    private List<DetalleVo> detalles;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DetalleVo {
        private Long idDetalle;
        private String telefono;
        private String mensaje;
        private Timestamp fechaEnvio;
    }
}

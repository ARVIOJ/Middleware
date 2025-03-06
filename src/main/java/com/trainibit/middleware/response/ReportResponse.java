package com.trainibit.middleware.response;

import com.trainibit.middleware.vo.ReportVo;
import lombok.Data;

import java.util.List;

@Data
public class ReportResponse {
    private String fecha;
    private int totalCampana;
    private List<ReportVo> campanas;
}

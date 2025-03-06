package com.trainibit.middleware.util;

public class DataBaseParams {
    public static final String NAME_SP_OBTENER_CAMPANAS = "SP_GET_CAMPANAS_POR_FECHA";

    public static class In {
        public static final String P_FECHA = "p_fecha"; // En mayúsculas para coincidir con el SP
    }

    public static class Out {
        public static final String P_RESULTADO = "p_result"; // En mayúsculas para coincidir con el SP
    }
}

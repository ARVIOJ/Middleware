-- ========================================
-- CREACIÓN DE TABLAS
-- ========================================

-- Tabla principal de campañas
CREATE TABLE TA_SMS_MAESTRO (
                                ID_CAMPANA NUMBER PRIMARY KEY,
                                NOMBRE VARCHAR2(255) NOT NULL,
                                FECHA_CREACION DATE DEFAULT SYSDATE,
                                ESTADO VARCHAR2(50) NOT NULL
);

-- Tabla de detalles de las campañas
CREATE TABLE TA_SMS_DETALLE (
                                ID_DETALLE NUMBER PRIMARY KEY,
                                ID_CAMPANA NUMBER,
                                TELEFONO VARCHAR2(20) NOT NULL,
                                MENSAJE VARCHAR2(1000) NOT NULL,
                                FECHA_ENVIO DATE DEFAULT SYSDATE,
                                CONSTRAINT FK_CAMPANA FOREIGN KEY (ID_CAMPANA) REFERENCES TA_SMS_MAESTRO(ID_CAMPANA)
);

-- ========================================
-- CREACIÓN DE ÍNDICES PARA MEJORAR RENDIMIENTO
-- ========================================

CREATE INDEX IDX_SMS_MAESTRO_FECHA ON TA_SMS_MAESTRO(FECHA_CREACION);
CREATE INDEX IDX_SMS_DETALLE_CAMPANA ON TA_SMS_DETALLE(ID_CAMPANA);

-- ========================================
-- PROCEDIMIENTOS ALMACENADOS (SPs)
-- ========================================

-- SP para obtener campañas por fecha
CREATE OR REPLACE PROCEDURE SP_GET_CAMPANAS_POR_FECHA(
    p_fecha IN DATE,
    p_result OUT SYS_REFCURSOR
) AS
BEGIN
    OPEN p_result FOR
        SELECT ID_CAMPANA, NOMBRE, FECHA_CREACION, ESTADO
        FROM TA_SMS_MAESTRO
        WHERE TRUNC(FECHA_CREACION) = TRUNC(p_fecha);
END;
/

-- SP para obtener detalles de campaña con paginación
CREATE OR REPLACE PROCEDURE SP_GET_DETALLES_CAMPANA(
    p_id_campana IN NUMBER,
    p_offset IN NUMBER,
    p_limit IN NUMBER,
    p_result OUT SYS_REFCURSOR
) AS
BEGIN
    OPEN p_result FOR
        SELECT ID_DETALLE, TELEFONO, MENSAJE, FECHA_ENVIO
        FROM TA_SMS_DETALLE
        WHERE ID_CAMPANA = p_id_campana
        ORDER BY ID_DETALLE
        OFFSET p_offset ROWS FETCH NEXT p_limit ROWS ONLY;
END;
/

-- ========================================
-- INSERCIÓN DE DATOS DE PRUEBA
-- ========================================

INSERT INTO TA_SMS_MAESTRO (ID_CAMPANA, NOMBRE, FECHA_CREACION, ESTADO)
VALUES (1, 'Campaña Promoción', TO_DATE('2025-03-05', 'YYYY-MM-DD'), 'ACTIVA');

INSERT INTO TA_SMS_DETALLE (ID_DETALLE, ID_CAMPANA, TELEFONO, MENSAJE, FECHA_ENVIO)
VALUES (1, 1, '5551234567', '¡Oferta especial!', SYSDATE);

COMMIT;


INSERT INTO TA_SMS_MAESTRO (ID_CAMPANA, NOMBRE, FECHA_CREACION, ESTADO)
VALUES (2, 'Campaña Verano', TO_DATE('2025-06-15', 'YYYY-MM-DD'), 'ACTIVA');

INSERT INTO TA_SMS_MAESTRO (ID_CAMPANA, NOMBRE, FECHA_CREACION, ESTADO)
VALUES (3, 'Campaña Invierno', TO_DATE('2025-12-01', 'YYYY-MM-DD'), 'INACTIVA');

INSERT INTO TA_SMS_MAESTRO (ID_CAMPANA, NOMBRE, FECHA_CREACION, ESTADO)
VALUES (4, 'Campaña Navidad', TO_DATE('2025-12-25', 'YYYY-MM-DD'), 'ACTIVA');

INSERT INTO TA_SMS_MAESTRO (ID_CAMPANA, NOMBRE, FECHA_CREACION, ESTADO)
VALUES (5, 'Campaña Black Friday', TO_DATE('2025-11-29', 'YYYY-MM-DD'), 'ACTIVA');

INSERT INTO TA_SMS_MAESTRO (ID_CAMPANA, NOMBRE, FECHA_CREACION, ESTADO)
VALUES (6, 'Campaña Año Nuevo', TO_DATE('2026-01-01', 'YYYY-MM-DD'), 'ACTIVA');


-- Detalles para Campaña 1
INSERT INTO TA_SMS_DETALLE (ID_DETALLE, ID_CAMPANA, TELEFONO, MENSAJE, FECHA_ENVIO) VALUES
    (2, 1, '5559876543', '¡Descuento exclusivo para ti!', SYSDATE - 1),
(3, 1, '5552345678', 'Última oportunidad para tu compra', SYSDATE - 2),
(4, 1, '5558765432', '¡Oferta por tiempo limitado!', SYSDATE - 3),
(5, 1, '5553456789', 'Gana premios con tu compra', SYSDATE - 4),
(6, 1, '5554567890', 'Descuento en tu siguiente compra', SYSDATE - 5);

-- Detalles para Campaña 2
INSERT INTO TA_SMS_DETALLE (ID_DETALLE, ID_CAMPANA, TELEFONO, MENSAJE, FECHA_ENVIO) VALUES
    (7, 2, '5555678901', '¡Bienvenido al verano!', SYSDATE - 6),
(8, 2, '5556789012', 'Descuentos en productos de verano', SYSDATE - 7),
(9, 2, '5557890123', 'Compra con 20% de descuento', SYSDATE - 8),
(10, 2, '5558901234', 'Promo especial hasta fin de mes', SYSDATE - 9),
(11, 2, '5559012345', 'Cupón exclusivo para ti', SYSDATE - 10);

-- Detalles para Campaña 3
INSERT INTO TA_SMS_DETALLE (ID_DETALLE, ID_CAMPANA, TELEFONO, MENSAJE, FECHA_ENVIO) VALUES
    (12, 3, '5550123456', 'Prepara tu invierno con estas ofertas', SYSDATE - 11),
(13, 3, '5551234567', 'Ropa de invierno con descuento', SYSDATE - 12),
(14, 3, '5552345678', '¡Gran liquidación de invierno!', SYSDATE - 13),
(15, 3, '5553456789', 'Abrigos y bufandas a precios bajos', SYSDATE - 14),
(16, 3, '5554567890', 'Oferta especial: calefacción al mejor precio', SYSDATE - 15);

-- Detalles para Campaña 4
INSERT INTO TA_SMS_DETALLE (ID_DETALLE, ID_CAMPANA, TELEFONO, MENSAJE, FECHA_ENVIO) VALUES
    (17, 4, '5555678901', '¡Feliz Navidad! Disfruta nuestras promociones', SYSDATE - 16),
(18, 4, '5556789012', 'Regalos perfectos para Navidad', SYSDATE - 17),
(19, 4, '5557890123', 'Santa trae descuentos para ti', SYSDATE - 18),
(20, 4, '5558901234', 'Descuentos en juguetes y más', SYSDATE - 19),
(21, 4, '5559012345', 'Compra tu árbol de Navidad con 30% de descuento', SYSDATE - 20);

-- Detalles para Campaña 5
INSERT INTO TA_SMS_DETALLE (ID_DETALLE, ID_CAMPANA, TELEFONO, MENSAJE, FECHA_ENVIO) VALUES
    (22, 5, '5550123456', '¡Black Friday ha llegado! Grandes descuentos', SYSDATE - 21),
(23, 5, '5551234567', 'Electrónica con hasta 50% de descuento', SYSDATE - 22),
(24, 5, '5552345678', 'Solo por hoy: precios increíbles', SYSDATE - 23),
(25, 5, '5553456789', '¡Compra ahora y ahorra!', SYSDATE - 24),
(26, 5, '5554567890', 'Cupón especial para Black Friday', SYSDATE - 25);

-- Detalles para Campaña 6
INSERT INTO TA_SMS_DETALLE (ID_DETALLE, ID_CAMPANA, TELEFONO, MENSAJE, FECHA_ENVIO) VALUES
    (27, 6, '5555678901', '¡Feliz Año Nuevo! Promociones especiales', SYSDATE - 26),
(28, 6, '5556789012', 'Brinda con nosotros con grandes descuentos', SYSDATE - 27),
(29, 6, '5557890123', 'Oferta de Año Nuevo solo por hoy', SYSDATE - 28),
(30, 6, '5558901234', 'Celebra con estas promociones únicas', SYSDATE - 29),
(31, 6, '5559012345', 'Empieza el año con los mejores precios', SYSDATE - 30);



-----------------------------
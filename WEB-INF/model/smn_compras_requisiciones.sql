DROP TABLE smn_compras.smn_impuest_deducc_detalle;
DROP TABLE smn_compras.smn_rel_requisicion_f_entrega;
DROP TABLE smn_compras.smn_requisicion_detalle;
DROP TABLE smn_compras.smn_requisicion_cabecera;
DROP TABLE smn_compras.smn_rel_ruta_aprobacion;
DROP TABLE smn_compras.smn_rel_proveedor_producto;
DROP TABLE smn_compras.smn_regla;
DROP TABLE smn_compras.smn_rel_usuarios_linea;
DROP TABLE smn_compras.smn_ruta;
DROP TABLE smn_compras.smn_roles;
DROP TABLE smn_compras.smn_rel_linea_afijo;
DROP TABLE smn_compras.smn_rel_linea_servicio;
DROP TABLE smn_compras.smn_servicio;
DROP TABLE smn_compras.smn_rel_linea_item;
DROP TABLE smn_compras.smn_proveedor;
DROP TABLE smn_compras.smn_motivo;
DROP TABLE smn_compras.smn_lineas;
DROP TABLE smn_compras.smn_tipo_linea;
DROP TABLE smn_compras.smn_documentos;
DROP TABLE smn_compras.smn_tipo_documento;

CREATE TABLE smn_compras.smn_tipo_documento(
  smn_tipo_documento_id INTEGER NOT NULL,
  tdc_codigo VARCHAR(16) NOT NULL,
  tdc_nombre VARCHAR(64) NOT NULL,
  tdc_naturaleza CHARACTER(2) NOT NULL,
  tdc_idioma CHARACTER(2) NOT NULL,
  tdc_usuario_id VARCHAR(10) NOT NULL,
  tdc_fecha_registro DATE NOT NULL,
  tdc_hora CHARACTER(8) NOT NULL
);

CREATE SEQUENCE smn_compras.seq_smn_tipo_documento;


CREATE TABLE smn_compras.smn_documentos(
  smn_documentos_id INTEGER NOT NULL,
  smn_tipo_documento_id INTEGER NOT NULL,
  dcc_codigo VARCHAR(16) NOT NULL,
  dcc_nombre VARCHAR(64) NOT NULL,
  dcc_transaccion_id INTEGER,
  dcc_recurrente CHARACTER(2),
  dcc_fecha_inicio DATE,
  dcc_fecha_final DATE,
  dcc_modalidad CHARACTER(2),
  dcc_escotizacion CHARACTER(1) NOT NULL,
  dcc_cant_cotizaciones INTEGER,
  dcc_esoferta CHARACTER(1),
  dcc_cant_ofertas INTEGER,
  dcc_idioma CHARACTER(2) NOT NULL,
  dcc_usuario_id VARCHAR(10) NOT NULL,
  dcc_fecha_registro DATE NOT NULL,
  dcc_hora CHARACTER(8) NOT NULL
);

CREATE SEQUENCE smn_compras.seq_smn_documentos;


CREATE TABLE smn_compras.smn_tipo_linea(
  smn_tipo_linea_id INTEGER NOT NULL,
  tlc_codigo VARCHAR(16) NOT NULL,
  tlc_nombre VARCHAR(64) NOT NULL,
  tlc_naturaleza CHARACTER(2) NOT NULL,
  tlc_idioma CHARACTER(2) NOT NULL,
  tlc_usuario_id VARCHAR(10) NOT NULL,
  tlc_fecha_registro DATE NOT NULL,
  tlc_hora CHARACTER(8) NOT NULL
);

CREATE SEQUENCE smn_compras.seq_smn_tipo_linea;


CREATE TABLE smn_compras.smn_lineas(
  smn_lineas_id INTEGER NOT NULL,
  smn_tipo_linea_id INTEGER NOT NULL,
  lin_codigo VARCHAR(16) NOT NULL,
  lin_nombre VARCHAR(64) NOT NULL,
  lin_idioma CHARACTER(2) NOT NULL,
  lin_usuario_id VARCHAR(10) NOT NULL,
  lin_fecha_registro DATE NOT NULL,
  lin_hora CHARACTER(8) NOT NULL
);

CREATE SEQUENCE smn_compras.seq_smn_lineas;


CREATE TABLE smn_compras.smn_motivo(
  smn_motivos_id INTEGER NOT NULL,
  mtv_tipo CHARACTER(2) NOT NULL,
  mtv_codigo VARCHAR(16) NOT NULL,
  mtv_nombre VARCHAR(64) NOT NULL,
  mtv_idioma CHARACTER(2) NOT NULL,
  mtv_usuario_id VARCHAR(10) NOT NULL,
  mtv_fecha_registro DATE NOT NULL,
  mtv_hora CHARACTER(8) NOT NULL
);

CREATE SEQUENCE smn_compras.seq_smn_motivo;


CREATE TABLE smn_compras.smn_proveedor(
  smn_proveedor_id INTEGER NOT NULL,
  smn_clase_auxiliar_id INTEGER NOT NULL,
  prv_auxiliar_categoria_id INTEGER NOT NULL,
  prv_idioma CHARACTER(2) NOT NULL,
  prv_usuario_id VARCHAR(10) NOT NULL,
  prv_fecha_registro DATE NOT NULL,
  prv_hora CHARACTER(8) NOT NULL
);

CREATE SEQUENCE smn_compras.seq_smn_proveedor;


CREATE TABLE smn_compras.smn_rel_linea_item(
  smn_rel_linea_item_id INTEGER NOT NULL,
  smn_lineas_id INTEGER NOT NULL,
  smn_item_id INTEGER NOT NULL
);

CREATE SEQUENCE smn_compras.seq_smn_rel_linea_item;


CREATE TABLE smn_compras.smn_servicio(
  smn_servicio_id INTEGER NOT NULL,
  sco_codigo VARCHAR(16) NOT NULL,
  sco_nombre VARCHAR(64) NOT NULL,
  sco_maneja_contrato CHARACTER(2),
  sco_proveedor_exclusivo CHARACTER(2),
  smn_area_servicio_id INTEGER,
  smn_unidades_servicios_id INTEGER NOT NULL,
  sco_idioma CHARACTER(2) NOT NULL,
  sco_usuario_id VARCHAR(10) NOT NULL,
  sco_fecha_registro DATE NOT NULL,
  sco_hora CHARACTER(8) NOT NULL
);

CREATE SEQUENCE smn_compras.seq_smn_servicio;


CREATE TABLE smn_compras.smn_rel_linea_servicio(
  smn_rel_linea_servicio_id INTEGER NOT NULL,
  smn_lineas_id INTEGER NOT NULL,
  smn_servicio_id INTEGER NOT NULL
);

CREATE SEQUENCE smn_compras.seq_smn_rel_linea_servicio;


CREATE TABLE smn_compras.smn_rel_linea_afijo(
  smn_rel_linea_afijo_id INTEGER NOT NULL,
  smn_lineas_id INTEGER NOT NULL,
  smn_afijo_id INTEGER NOT NULL
);

CREATE SEQUENCE smn_compras.seq_smn_rel_linea_afijo;


CREATE TABLE smn_compras.smn_roles(
  smn_roles_id INTEGER NOT NULL,
  smn_usuarios_id INTEGER NOT NULL,
  rol_tipo CHARACTER(2) NOT NULL,
  smn_corporaciones_id INTEGER,
  smn_entidades_id INTEGER,
  smn_sucursales_id INTEGER,
  smn_areas_servicios_id INTEGER,
  smn_unidades_servicios_id INTEGER,
  smn_estructura_organizacional_id INTEGER,
  rol_idioma CHARACTER(2) NOT NULL,
  rol_usuario_id VARCHAR(10) NOT NULL,
  rol_fecha_registro DATE NOT NULL,
  rol_hora CHARACTER(8) NOT NULL
);

CREATE SEQUENCE smn_compras.seq_smn_roles;


CREATE TABLE smn_compras.smn_ruta(
  smn_ruta_id INTEGER NOT NULL,
  rut_codigo VARCHAR(16) NOT NULL,
  rut_nombre VARCHAR(48) NOT NULL,
  rut_cantidad_aprobaciones NUMERIC(10) NOT NULL,
  rut_idioma CHARACTER(2) NOT NULL,
  rut_usuario_id VARCHAR(10) NOT NULL,
  rut_fecha_registro DATE NOT NULL,
  rut_hora CHARACTER(8) NOT NULL
);

CREATE SEQUENCE smn_compras.seq_smn_ruta;


CREATE TABLE smn_compras.smn_rel_usuarios_linea(
  smn_rel_usuario_linea_id INTEGER NOT NULL,
  smn_usuarios_id INTEGER NOT NULL,
  smn_lineas_id INTEGER NOT NULL
);

CREATE SEQUENCE smn_compras.seq_smn_rel_usuarios_linea;


CREATE TABLE smn_compras.smn_regla(
  smn_regla_id INTEGER NOT NULL,
  rul_codigo VARCHAR(16) NOT NULL,
  rul_nombre VARCHAR(48) NOT NULL,
  rul_cantidad_desde INTEGER NOT NULL,
  rul_cantidad_hasta INTEGER NOT NULL,
  rul_var_cantidad INTEGER NOT NULL,
  smn_monedas_id INTEGER NOT NULL,
  rul_monto_desde DOUBLE PRECISION NOT NULL,
  rul_monto_hasta DOUBLE PRECISION NOT NULL,
  rul_var_monto DOUBLE PRECISION NOT NULL,
  rul_idioma CHARACTER(2) NOT NULL,
  rul_usuario_id VARCHAR(10) NOT NULL,
  rul_fecha_registro DATE NOT NULL,
  rul_hora CHARACTER(8) NOT NULL
);

CREATE SEQUENCE smn_compras.seq_smn_regla;


CREATE TABLE smn_compras.smn_rel_proveedor_producto(
  smn_rel_proveedor_producto_id INTEGER NOT NULL,
  smn_proveedor_id INTEGER NOT NULL,
  smn_item_id INTEGER NOT NULL,
  smn_servicio_id INTEGER NOT NULL,
  smn_afijo_id INTEGER NOT NULL,
  rpp_item_alterno VARCHAR(16) NOT NULL,
  rpp_afijo_alterno VARCHAR(16) NOT NULL
);

CREATE SEQUENCE smn_compras.seq_smn_rel_proveedor_producto;


CREATE TABLE smn_compras.smn_rel_ruta_aprobacion(
  smn_ruta_aprobacion_id INTEGER NOT NULL,
  smn_ruta_id INTEGER NOT NULL,
  rra_aprobacion VARCHAR(2) NOT NULL,
  smn_lineas_id INTEGER NOT NULL,
  smn_roles_id INTEGER NOT NULL,
  smn_regla_id INTEGER NOT NULL,
  rra_idioma CHARACTER(2) NOT NULL,
  rra_usuario_id VARCHAR(10) NOT NULL,
  rra_fecha_registro DATE NOT NULL,
  rra_hora CHARACTER(8) NOT NULL
);

CREATE SEQUENCE smn_compras.seq_smn_rel_ruta_aprobacion;


CREATE TABLE smn_compras.smn_requisicion_cabecera(
  smn_requisicion_cabecera_id INTEGER NOT NULL,
  smn_cabecera_version_id INTEGER NOT NULL,
  req_requisicion_numero SERIAL,
  req_estatus CHARACTER(2) NOT NULL,
  smn_documento_id INTEGER NOT NULL,
  req_descripcion VARCHAR(64) NOT NULL,
  req_fecha_requerido DATE,
  req_estatus_ruta CHARACTER(2),
  smn_entidad_id INTEGER,
  smn_sucursal_id INTEGER,
  smn_area_servicio_id INTEGER,
  smn_unidades_servicio_id INTEGER,
  smn_almacen_id INTEGER,
  smn_centro_costo_id INTEGER,
  req_idioma CHARACTER(2) NOT NULL,
  req_usuario VARCHAR(10) NOT NULL,
  req_fecha_registro DATE NOT NULL,
  req_hora CHARACTER(8) NOT NULL
);

CREATE TABLE smn_compras.smn_requisicion_detalle(
  smn_requisicion_detalle_id INTEGER NOT NULL,
  smn_requisicion_cabecera_id INTEGER NOT NULL,
  smn_cabecera_version_id INTEGER NOT NULL,
  smn_naturaleza_id CHARACTER(2) NOT NULL,
  smn_servicio_id INTEGER,
  smn_item_id INTEGER,
  smn_afijo_id INTEGER,
  smn_proveedor_id INTEGER,
  smn_moneda_id INTEGER,
  rrs_producto_encontrado CHARACTER(2) NOT NULL,
  rrs_porcentaje DOUBLE PRECISION,
  rss_cantidad INTEGER NOT NULL,
  rrs_precio DOUBLE PRECISION NOT NULL,
  rrs_monto DOUBLE PRECISION NOT NULL,
  rrs_precio_moneda_alterna DOUBLE PRECISION,
  rrs_monto_moneda_alterna DOUBLE PRECISION,
  rrs_especificaciones_del_requerimiento VARCHAR(256),
  rrs_fecha_de_requerido DATE NOT NULL,
  rrs_observaciones VARCHAR(256)
);

CREATE SEQUENCE smn_compras.seq_smn_requisicion_detalle;


CREATE TABLE smn_compras.smn_rel_requisicion_f_entrega(
  smn_rel_requisicion_f_entrega_id INTEGER NOT NULL,
  smn_requisicion_detalle_id INTEGER NOT NULL,
  cfe_consecutivo INTEGER NOT NULL,
  cfe_cantidad INTEGER NOT NULL,
  cfe_fecha_de_entrega DATE NOT NULL
);

CREATE SEQUENCE smn_compras.seq_smn_rel_requisicion_f_entrega;


CREATE TABLE smn_compras.smn_impuest_deducc_detalle(
  smn_impuest_deducc_detalle_id INTEGER NOT NULL,
  smn_requisicion_detalle_id INTEGER NOT NULL,
  smn_cod_impuesto_deducc_id INTEGER,
  rld_monto_impuesto DOUBLE PRECISION,
  smn_moneda_id INTEGER,
  rld_monto_imp_moneda_alterna DOUBLE PRECISION,
  rld_cod_descuento CHARACTER(2),
  rld_porcentaje_descuento DOUBLE PRECISION,
  rld_monto_descuento DOUBLE PRECISION,
  rld_monto_desc_moneda_alterna DOUBLE PRECISION
);

CREATE SEQUENCE smn_compras.seq_smn_impuest_deducc_detalle;



ALTER TABLE smn_compras.smn_tipo_documento ADD PRIMARY KEY (smn_tipo_documento_id);

ALTER TABLE smn_compras.smn_documentos ADD PRIMARY KEY (smn_documentos_id);
ALTER TABLE smn_compras.smn_documentos ADD CONSTRAINT FK_smn_documentos_0 FOREIGN KEY (smn_tipo_documento_id) REFERENCES smn_compras.smn_tipo_documento (smn_tipo_documento_id) ON DELETE NO ACTION;

ALTER TABLE smn_compras.smn_tipo_linea ADD PRIMARY KEY (smn_tipo_linea_id);

ALTER TABLE smn_compras.smn_lineas ADD PRIMARY KEY (smn_lineas_id);
ALTER TABLE smn_compras.smn_lineas ADD CONSTRAINT FK_smn_lineas_0 FOREIGN KEY (smn_tipo_linea_id) REFERENCES smn_compras.smn_tipo_linea (smn_tipo_linea_id) ON DELETE NO ACTION;

ALTER TABLE smn_compras.smn_motivo ADD PRIMARY KEY (smn_motivos_id);

ALTER TABLE smn_compras.smn_proveedor ADD PRIMARY KEY (smn_proveedor_id);

ALTER TABLE smn_compras.smn_rel_linea_item ADD PRIMARY KEY (smn_rel_linea_item_id);
ALTER TABLE smn_compras.smn_rel_linea_item ADD CONSTRAINT FK_smn_rel_linea_item_0 FOREIGN KEY (smn_lineas_id) REFERENCES smn_compras.smn_lineas (smn_lineas_id) ON DELETE NO ACTION;

ALTER TABLE smn_compras.smn_servicio ADD PRIMARY KEY (smn_servicio_id);

ALTER TABLE smn_compras.smn_rel_linea_servicio ADD PRIMARY KEY (smn_rel_linea_servicio_id);
ALTER TABLE smn_compras.smn_rel_linea_servicio ADD CONSTRAINT FK_smn_rel_linea_servicio_0 FOREIGN KEY (smn_lineas_id) REFERENCES smn_compras.smn_lineas (smn_lineas_id) ON DELETE NO ACTION;
ALTER TABLE smn_compras.smn_rel_linea_servicio ADD CONSTRAINT FK_smn_rel_linea_servicio_1 FOREIGN KEY (smn_servicio_id) REFERENCES smn_compras.smn_servicio (smn_servicio_id) ON DELETE NO ACTION;

ALTER TABLE smn_compras.smn_rel_linea_afijo ADD PRIMARY KEY (smn_rel_linea_afijo_id);
ALTER TABLE smn_compras.smn_rel_linea_afijo ADD CONSTRAINT FK_smn_rel_linea_afijo_0 FOREIGN KEY (smn_lineas_id) REFERENCES smn_compras.smn_lineas (smn_lineas_id) ON DELETE NO ACTION;

ALTER TABLE smn_compras.smn_roles ADD PRIMARY KEY (smn_roles_id);

ALTER TABLE smn_compras.smn_ruta ADD PRIMARY KEY (smn_ruta_id);

ALTER TABLE smn_compras.smn_rel_usuarios_linea ADD PRIMARY KEY (smn_rel_usuario_linea_id);
ALTER TABLE smn_compras.smn_rel_usuarios_linea ADD CONSTRAINT FK_smn_rel_usuarios_linea_0 FOREIGN KEY (smn_lineas_id) REFERENCES smn_compras.smn_lineas (smn_lineas_id) ON DELETE NO ACTION;

ALTER TABLE smn_compras.smn_regla ADD PRIMARY KEY (smn_regla_id);

ALTER TABLE smn_compras.smn_rel_proveedor_producto ADD PRIMARY KEY (smn_rel_proveedor_producto_id);
ALTER TABLE smn_compras.smn_rel_proveedor_producto ADD CONSTRAINT FK_smn_rel_proveedor_producto_0 FOREIGN KEY (smn_proveedor_id) REFERENCES smn_compras.smn_proveedor (smn_proveedor_id) ON DELETE NO ACTION;
ALTER TABLE smn_compras.smn_rel_proveedor_producto ADD CONSTRAINT FK_smn_rel_proveedor_producto_1 FOREIGN KEY (smn_servicio_id) REFERENCES smn_compras.smn_servicio (smn_servicio_id) ON DELETE NO ACTION;

ALTER TABLE smn_compras.smn_rel_ruta_aprobacion ADD PRIMARY KEY (smn_ruta_aprobacion_id);
ALTER TABLE smn_compras.smn_rel_ruta_aprobacion ADD CONSTRAINT FK_smn_rel_ruta_aprobacion_0 FOREIGN KEY (smn_lineas_id) REFERENCES smn_compras.smn_lineas (smn_lineas_id) ON DELETE NO ACTION;
ALTER TABLE smn_compras.smn_rel_ruta_aprobacion ADD CONSTRAINT FK_smn_rel_ruta_aprobacion_1 FOREIGN KEY (smn_ruta_id) REFERENCES smn_compras.smn_ruta (smn_ruta_id) ON DELETE NO ACTION;
ALTER TABLE smn_compras.smn_rel_ruta_aprobacion ADD CONSTRAINT FK_smn_rel_ruta_aprobacion_2 FOREIGN KEY (smn_regla_id) REFERENCES smn_compras.smn_regla (smn_regla_id) ON DELETE NO ACTION;
ALTER TABLE smn_compras.smn_rel_ruta_aprobacion ADD CONSTRAINT FK_smn_rel_ruta_aprobacion_3 FOREIGN KEY (smn_roles_id) REFERENCES smn_compras.smn_roles (smn_roles_id) ON DELETE NO ACTION;

ALTER TABLE smn_compras.smn_requisicion_cabecera ADD PRIMARY KEY (smn_requisicion_cabecera_id);

ALTER TABLE smn_compras.smn_requisicion_detalle ADD PRIMARY KEY (smn_requisicion_detalle_id);
ALTER TABLE smn_compras.smn_requisicion_detalle ADD CONSTRAINT FK_smn_requisicion_detalle_0 FOREIGN KEY (smn_requisicion_cabecera_id) REFERENCES smn_compras.smn_requisicion_cabecera (smn_requisicion_cabecera_id) ON DELETE NO ACTION;

ALTER TABLE smn_compras.smn_rel_requisicion_f_entrega ADD PRIMARY KEY (smn_rel_requisicion_f_entrega_id);
ALTER TABLE smn_compras.smn_rel_requisicion_f_entrega ADD CONSTRAINT FK_smn_rel_requisicion_f_entrega_0 FOREIGN KEY (smn_requisicion_detalle_id) REFERENCES smn_compras.smn_requisicion_detalle (smn_requisicion_detalle_id) ON DELETE NO ACTION;

ALTER TABLE smn_compras.smn_impuest_deducc_detalle ADD PRIMARY KEY (smn_impuest_deducc_detalle_id);
ALTER TABLE smn_compras.smn_impuest_deducc_detalle ADD CONSTRAINT FK_smn_impuest_deducc_detalle_0 FOREIGN KEY (smn_requisicion_detalle_id) REFERENCES smn_compras.smn_requisicion_detalle (smn_requisicion_detalle_id) ON DELETE NO ACTION;


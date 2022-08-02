DROP TABLE smn_compras.smn_servicios_compras;
DROP TABLE smn_compras.smn_tipos_lineas_compras;
DROP TABLE smn_compras.smn_item;
DROP TABLE smn_compras.smn_lineas_compra;
DROP TABLE smn_compras.smn_estatus;
DROP TABLE smn_compras.smn_reglas_negocio;
DROP TABLE smn_compras.smn_roles;
DROP TABLE smn_compras.smn_documento;
DROP TABLE smn_compras.smn_tipo_documento;
DROP TABLE smn_compras.smn_motivo;
DROP TABLE smn_compras.smn_activo_fijo;
DROP TABLE smn_compras.smn_contrato;
DROP TABLE smn_compras.smn_proveedor;
DROP TABLE smn_compras.smn_requisiciones;
DROP TABLE smn_compras.smn_item_requisiciones;
DROP TABLE smn_compras.smn_servicios;
DROP TABLE smn_compras.smn_servicios_requisicion;
DROP TABLE smn_compras.smn_activos_fijos_requisicion;
DROP TABLE smn_compras.smn_niveles;
DROP TABLE smn_compras.smn_rel_lcompra_items;
DROP TABLE smn_compras.smn_rel_lcompra_afijos;
DROP TABLE smn_compras.smn_rel_lcompras_servicios;

CREATE TABLE smn_compras.smn_rel_lcompras_servicios(
  smn_rel_lcompra_servicios_id INTEGER NOT NULL,
  smn_lineas_compra_id INTEGER NOT NULL,
  smn_servicios_id VARCHAR(16) NOT NULL
);

CREATE SEQUENCE smn_compras.seq_smn_rel_lcompras_servicios;


CREATE TABLE smn_compras.smn_rel_lcompra_afijos(
  smn_rel_lcompra_afijos_id INTEGER NOT NULL,
  smn_lineas_compra_id INTEGER NOT NULL,
  smn_activo_fijo_id INTEGER NOT NULL
);

CREATE SEQUENCE smn_compras.seq_smn_rel_lcompra_afijos;


CREATE TABLE smn_compras.smn_rel_lcompra_items(
  smn_rel_lcompra_items_id INTEGER NOT NULL,
  smn_lineas_compra_id INTEGER NOT NULL,
  smn_item_id INTEGER NOT NULL
);

CREATE SEQUENCE smn_compras.seq_smn_rel_lcompra_items;


CREATE TABLE smn_compras.smn_niveles(
  smn_niveles_id INTEGER NOT NULL,
  niv_codigo VARCHAR(16) NOT NULL,
  niv_nombre VARCHAR(64) NOT NULL,
  niv_consecutivo INTEGER NOT NULL,
  niv_idioma CHARACTER(2) NOT NULL,
  niv_usuario VARCHAR(10) NOT NULL,
  niv_fecha_registro DATE NOT NULL,
  niv_hora CHARACTER(8) NOT NULL
);

CREATE SEQUENCE smn_compras.seq_smn_niveles;


CREATE TABLE smn_compras.smn_activos_fijos_requisicion(
  smn_activos_fijos_requisiciones_id INTEGER NOT NULL,
  smn_requisiciones_id INTEGER NOT NULL,
  smn_activo_fijo_id INTEGER NOT NULL
);

CREATE SEQUENCE smn_compras.seq_smn_activos_fijos_requisicion;


CREATE TABLE smn_compras.smn_servicios_requisicion(
  smn_servicio_requisicion_id INTEGER NOT NULL,
  smn_requisiciones_id INTEGER NOT NULL,
  smn_servicios_id INTEGER NOT NULL
);

CREATE SEQUENCE smn_compras.seq_smn_servicios_requisicion;


CREATE TABLE smn_compras.smn_servicios(
  smn_servicios_id INTEGER NOT NULL,
  ser_codigo VARCHAR(16) NOT NULL,
  ser_nombre VARCHAR(48) NOT NULL,
  ser_descripcion VARCHAR(64) NOT NULL,
  ser_idioma CHARACTER(2) NOT NULL,
  ser_usuario VARCHAR(10) NOT NULL,
  ser_fecha_registro DATE NOT NULL,
  ser_hora CHARACTER(8) NOT NULL,
  smn_servicio_requisicion_id INTEGER
);

CREATE SEQUENCE smn_compras.seq_smn_servicios;


CREATE TABLE smn_compras.smn_item_requisiciones(
  smn_item_requisiciones_id INTEGER NOT NULL,
  smn_requisiciones_id INTEGER NOT NULL,
  smn_item_id INTEGER NOT NULL
);

CREATE SEQUENCE smn_compras.seq_smn_item_requisiciones;


CREATE TABLE smn_compras.smn_requisiciones(
  smn_requisiciones_id INTEGER NOT NULL,
  smn_servicio_requisicion_id INTEGER NOT NULL,
  smn_documento_id INTEGER NOT NULL,
  smn_contrato_id INTEGER NOT NULL,
  smn_proveedor_id INTEGER NOT NULL,
  smn_estatus_id INTEGER NOT NULL,
  smn_reglas_negocio_id INTEGER NOT NULL,
  smn_motivo_id INTEGER NOT NULL,
  req_variacion_porcentaje DOUBLE PRECISION NOT NULL,
  req_variacion_cantidad DOUBLE PRECISION NOT NULL,
  req_variacion_precio DOUBLE PRECISION NOT NULL,
  req_fecha_tope DATE NOT NULL,
  req_url_archivo VARCHAR(80) NOT NULL,
  req_motivo_cambio VARCHAR(10) NOT NULL,
  req_precio DOUBLE PRECISION NOT NULL,
  req_cantidad DOUBLE PRECISION NOT NULL,
  req_justificacion_compra VARCHAR(80) NOT NULL,
  req_observacion VARCHAR(256) NOT NULL,
  req_pasos INTEGER NOT NULL,
  req_fecha_actual DATE NOT NULL,
  req_hora_actual TIME NOT NULL,
  req_idioma CHARACTER(02) NOT NULL,
  req_usuario VARCHAR(10) NOT NULL,
  req_fecha_registro DATE NOT NULL,
  req_hora CHARACTER(08) NOT NULL,
  smn_activos_fijos_requisiciones_id INTEGER,
  smn_item_requisiciones_id INTEGER,
  smn_roles_id INTEGER NOT NULL
);

CREATE SEQUENCE smn_compras.seq_smn_requisiciones;


CREATE TABLE smn_compras.smn_proveedor(
  smn_proveedor_id INTEGER NOT NULL,
  pro_codigo VARCHAR(16) NOT NULL,
  pro_nombre VARCHAR(48) NOT NULL,
  pro_idioma CHARACTER(2) NOT NULL,
  pro_usuario VARCHAR(10) NOT NULL,
  pro_fecha_registro DATE NOT NULL,
  pro_hora CHARACTER(8) NOT NULL,
  smn_requisiciones_id INTEGER
);

CREATE SEQUENCE smn_compras.seq_smn_proveedor;


CREATE TABLE smn_compras.smn_contrato(
  smn_contrato_id INTEGER NOT NULL,
  con_codigo VARCHAR(16) NOT NULL,
  con_nombre VARCHAR(48) NOT NULL,
  con_url_archivo VARCHAR(60) NOT NULL,
  con_variacion_precio DOUBLE PRECISION NOT NULL,
  con_variacion_cantidad DOUBLE PRECISION NOT NULL,
  con_variacion_porcentaje DOUBLE PRECISION NOT NULL,
  con_fecha_tope DATE NOT NULL,
  con_idioma CHARACTER(2) NOT NULL,
  con_usuario VARCHAR(10) NOT NULL,
  con_fecha_registro DATE NOT NULL,
  con_hora CHARACTER(8) NOT NULL,
  smn_requisiciones_id INTEGER
);

CREATE SEQUENCE smn_compras.seq_smn_contrato;


CREATE TABLE smn_compras.smn_activo_fijo(
  smn_activo_fijo_id INTEGER NOT NULL,
  smn_proveedor_id INTEGER NOT NULL,
  act_codigo VARCHAR(16) NOT NULL,
  act_nombre VARCHAR(48) NOT NULL,
  act_descripcion VARCHAR(64) NOT NULL,
  act_idioma CHARACTER(2) NOT NULL,
  act_usuario VARCHAR(10) NOT NULL,
  act_fecha_registro DATE NOT NULL,
  act_hora CHARACTER(8) NOT NULL,
  smn_activos_fijos_requisiciones_id INTEGER
);

CREATE SEQUENCE smn_compras.seq_smn_activo_fijo;


CREATE TABLE smn_compras.smn_motivo(
  smn_motivo_id INTEGER NOT NULL,
  mot_codigo VARCHAR(16) NOT NULL,
  mot_nombre VARCHAR(48) NOT NULL,
  mot_descripcion VARCHAR(64) NOT NULL,
  mot_idioma CHARACTER(2) NOT NULL,
  mot_usuario VARCHAR(10) NOT NULL,
  mot_fecha_registro DATE NOT NULL,
  mot_hora CHARACTER(08) NOT NULL,
  smn_requisiciones_id INTEGER
);

CREATE SEQUENCE smn_compras.seq_smn_motivo;


CREATE TABLE smn_compras.smn_tipo_documento(
  smn_tipo_documento_id INTEGER NOT NULL,
  tdoc_nombre CHARACTER(48) NOT NULL,
  tdoc_descripcion VARCHAR(64) NOT NULL,
  tdoc_idioma CHARACTER(2) NOT NULL,
  tdoc_usuario VARCHAR(10) NOT NULL,
  tdoc_fecha_registro DATE NOT NULL,
  tdoc_hora CHARACTER(8) NOT NULL
);

CREATE SEQUENCE smn_compras.seq_smn_tipo_documento;


CREATE TABLE smn_compras.smn_documento(
  smn_documento_id INTEGER NOT NULL,
  smn_tipo_documento_id INTEGER NOT NULL,
  smn_reglas_negocio_id INTEGER NOT NULL,
  doc_idioma CHARACTER(2) NOT NULL,
  doc_usuario VARCHAR(10) NOT NULL,
  doc_fecha_registro DATE NOT NULL,
  doc_hora CHARACTER(8) NOT NULL,
  smn_requisiciones_id INTEGER
);

CREATE SEQUENCE smn_compras.seq_smn_documento;


CREATE TABLE smn_compras.smn_roles(
  smn_roles_id INTEGER NOT NULL,
  rol_codigo VARCHAR(16) NOT NULL,
  rol_nombre VARCHAR(48) NOT NULL,
  rol_descripcion VARCHAR(64) NOT NULL,
  rol_idioma CHARACTER(2) NOT NULL,
  rol_usuario VARCHAR(10) NOT NULL,
  rol_fecha_registro DATE NOT NULL,
  rol_hora CHARACTER(8) NOT NULL
);

CREATE SEQUENCE smn_compras.seq_smn_roles;


CREATE TABLE smn_compras.smn_reglas_negocio(
  smn_reglas_negocio_id INTEGER NOT NULL,
  smn_roles_id INTEGER NOT NULL,
  rul_codigo VARCHAR(16) NOT NULL,
  rul_nombre VARCHAR(48) NOT NULL,
  rul_cantidad_desde DOUBLE PRECISION NOT NULL,
  rul_cantidad_hasta DOUBLE PRECISION NOT NULL,
  rul_por_var_cantidad DOUBLE PRECISION NOT NULL,
  rul_moneda DOUBLE PRECISION NOT NULL,
  rul_monto_desde DOUBLE PRECISION NOT NULL,
  rul_monto_hasta DOUBLE PRECISION NOT NULL,
  rul_por_var_monto DOUBLE PRECISION NOT NULL,
  rul_idioma CHARACTER(2) NOT NULL,
  rul_usuario VARCHAR(10) NOT NULL,
  rul_fecha_registro DATE NOT NULL,
  rul_hora CHARACTER(8) NOT NULL,
  smn_requisiciones_id INTEGER
);

CREATE SEQUENCE smn_compras.seq_smn_reglas_negocio;


CREATE TABLE smn_compras.smn_estatus(
  smn_estatus_id INTEGER NOT NULL,
  est_codigo VARCHAR(16) NOT NULL,
  est_nombre VARCHAR(48) NOT NULL,
  est_nivel VARCHAR(64) NOT NULL,
  est_idioma CHARACTER(2) NOT NULL,
  est_usuario VARCHAR(10) NOT NULL,
  est_fecha_registro DATE NOT NULL,
  est_hora CHARACTER(8) NOT NULL,
  smn_requisiciones_id INTEGER
);

CREATE SEQUENCE smn_compras.seq_smn_estatus;


CREATE TABLE smn_compras.smn_lineas_compra(
  smn_lineas_compra_id INTEGER NOT NULL,
  lco_codigo VARCHAR(16) NOT NULL,
  lco_nombre VARCHAR(64) NOT NULL,
  lco_tipo_linea INTEGER NOT NULL,
  lco_idioma CHARACTER(2) NOT NULL,
  lco_usuario VARCHAR(10) NOT NULL,
  lco_fecha_registro DATE NOT NULL,
  lco_hora CHARACTER(8) NOT NULL
);

CREATE SEQUENCE smn_compras.seq_smn_lineas_compra;


CREATE TABLE smn_compras.smn_item(
  smn_item_id INTEGER NOT NULL,
  smn_item_requisiciones_id INTEGER NOT NULL,
  ite_codigo VARCHAR(16) NOT NULL,
  ite_nombre VARCHAR(48) NOT NULL,
  ite_descripcion VARCHAR(64) NOT NULL,
  ite_idioma CHARACTER(2) NOT NULL,
  ite_usuario VARCHAR(10) NOT NULL,
  ite_fecha_registro DATE NOT NULL,
  ite_hora CHARACTER(8) NOT NULL
);

CREATE SEQUENCE smn_compras.seq_smn_item;


CREATE TABLE smn_compras.smn_tipos_lineas_compras(
  smn_tipos_lineas_compras_id INTEGER NOT NULL,
  tlc_codigo VARCHAR(16) NOT NULL,
  tlc_nombre VARCHAR(64) NOT NULL,
  tlc_naturaleza CHARACTER(2) NOT NULL,
  tlc_idioma CHARACTER(2) NOT NULL,
  tlc_usuario VARCHAR(10) NOT NULL,
  tlc_fecha_registro DATE NOT NULL,
  tlc_hora CHARACTER(8) NOT NULL
);

CREATE SEQUENCE smn_compras.seq_smn_tipos_lineas_compras;


CREATE TABLE smn_compras.smn_servicios_compras(
  smn_servicios_compras_id INTEGER NOT NULL,
  sco_codigo VARCHAR(16) NOT NULL,
  sco_nombre VARCHAR(64) NOT NULL,
  sco_idioma CHARACTER(2) NOT NULL,
  sco_usuario VARCHAR(10) NOT NULL,
  sco_fecha_registro DATE NOT NULL,
  sco_hora CHARACTER(8) NOT NULL
);

CREATE SEQUENCE smn_compras.seq_smn_servicios_compras;



ALTER TABLE smn_compras.smn_rel_lcompras_servicios ADD PRIMARY KEY (smn_rel_lcompra_servicios_id);

ALTER TABLE smn_compras.smn_rel_lcompra_afijos ADD PRIMARY KEY (smn_rel_lcompra_afijos_id);

ALTER TABLE smn_compras.smn_rel_lcompra_items ADD PRIMARY KEY (smn_rel_lcompra_items_id);

ALTER TABLE smn_compras.smn_niveles ADD PRIMARY KEY (smn_niveles_id);

ALTER TABLE smn_compras.smn_activos_fijos_requisicion ADD PRIMARY KEY (smn_activos_fijos_requisiciones_id);

ALTER TABLE smn_compras.smn_servicios_requisicion ADD PRIMARY KEY (smn_servicio_requisicion_id);

ALTER TABLE smn_compras.smn_servicios ADD PRIMARY KEY (smn_servicios_id);
ALTER TABLE smn_compras.smn_servicios ADD CONSTRAINT FK_smn_servicios_0 FOREIGN KEY (smn_servicio_requisicion_id) REFERENCES smn_compras.smn_servicios_requisicion (smn_servicio_requisicion_id) ON DELETE NO ACTION;

ALTER TABLE smn_compras.smn_item_requisiciones ADD PRIMARY KEY (smn_item_requisiciones_id);

ALTER TABLE smn_compras.smn_requisiciones ADD PRIMARY KEY (smn_requisiciones_id);
ALTER TABLE smn_compras.smn_requisiciones ADD CONSTRAINT FK_smn_requisiciones_0 FOREIGN KEY (smn_activos_fijos_requisiciones_id) REFERENCES smn_compras.smn_activos_fijos_requisicion (smn_activos_fijos_requisiciones_id) ON DELETE NO ACTION;
ALTER TABLE smn_compras.smn_requisiciones ADD CONSTRAINT FK_smn_requisiciones_1 FOREIGN KEY (smn_servicio_requisicion_id) REFERENCES smn_compras.smn_servicios_requisicion (smn_servicio_requisicion_id) ON DELETE NO ACTION;
ALTER TABLE smn_compras.smn_requisiciones ADD CONSTRAINT FK_smn_requisiciones_2 FOREIGN KEY (smn_item_requisiciones_id) REFERENCES smn_compras.smn_item_requisiciones (smn_item_requisiciones_id) ON DELETE NO ACTION;

ALTER TABLE smn_compras.smn_proveedor ADD PRIMARY KEY (smn_proveedor_id);
ALTER TABLE smn_compras.smn_proveedor ADD CONSTRAINT FK_smn_proveedor_0 FOREIGN KEY (smn_requisiciones_id) REFERENCES smn_compras.smn_requisiciones (smn_requisiciones_id) ON DELETE NO ACTION;

ALTER TABLE smn_compras.smn_contrato ADD PRIMARY KEY (smn_contrato_id);
ALTER TABLE smn_compras.smn_contrato ADD CONSTRAINT FK_smn_contrato_0 FOREIGN KEY (smn_requisiciones_id) REFERENCES smn_compras.smn_requisiciones (smn_requisiciones_id) ON DELETE NO ACTION;

ALTER TABLE smn_compras.smn_activo_fijo ADD PRIMARY KEY (smn_activo_fijo_id);
ALTER TABLE smn_compras.smn_activo_fijo ADD CONSTRAINT FK_smn_activo_fijo_0 FOREIGN KEY (smn_activos_fijos_requisiciones_id) REFERENCES smn_compras.smn_activos_fijos_requisicion (smn_activos_fijos_requisiciones_id) ON DELETE NO ACTION;

ALTER TABLE smn_compras.smn_motivo ADD PRIMARY KEY (smn_motivo_id);
ALTER TABLE smn_compras.smn_motivo ADD CONSTRAINT FK_smn_motivo_0 FOREIGN KEY (smn_requisiciones_id) REFERENCES smn_compras.smn_requisiciones (smn_requisiciones_id) ON DELETE NO ACTION;

ALTER TABLE smn_compras.smn_tipo_documento ADD PRIMARY KEY (smn_tipo_documento_id);

ALTER TABLE smn_compras.smn_documento ADD PRIMARY KEY (smn_documento_id);
ALTER TABLE smn_compras.smn_documento ADD CONSTRAINT FK_smn_documento_0 FOREIGN KEY (smn_tipo_documento_id) REFERENCES smn_compras.smn_tipo_documento (smn_tipo_documento_id) ON DELETE NO ACTION;
ALTER TABLE smn_compras.smn_documento ADD CONSTRAINT FK_smn_documento_1 FOREIGN KEY (smn_requisiciones_id) REFERENCES smn_compras.smn_requisiciones (smn_requisiciones_id) ON DELETE NO ACTION;

ALTER TABLE smn_compras.smn_roles ADD PRIMARY KEY (smn_roles_id);

ALTER TABLE smn_compras.smn_reglas_negocio ADD PRIMARY KEY (smn_reglas_negocio_id);
ALTER TABLE smn_compras.smn_reglas_negocio ADD CONSTRAINT FK_smn_reglas_negocio_0 FOREIGN KEY (smn_roles_id) REFERENCES smn_compras.smn_roles (smn_roles_id) ON DELETE NO ACTION;
ALTER TABLE smn_compras.smn_reglas_negocio ADD CONSTRAINT FK_smn_reglas_negocio_1 FOREIGN KEY (smn_requisiciones_id) REFERENCES smn_compras.smn_requisiciones (smn_requisiciones_id) ON DELETE NO ACTION;

ALTER TABLE smn_compras.smn_estatus ADD PRIMARY KEY (smn_estatus_id);
ALTER TABLE smn_compras.smn_estatus ADD CONSTRAINT FK_smn_estatus_0 FOREIGN KEY (smn_requisiciones_id) REFERENCES smn_compras.smn_requisiciones (smn_requisiciones_id) ON DELETE NO ACTION;

ALTER TABLE smn_compras.smn_lineas_compra ADD PRIMARY KEY (smn_lineas_compra_id);

ALTER TABLE smn_compras.smn_item ADD PRIMARY KEY (smn_item_id);
ALTER TABLE smn_compras.smn_item ADD CONSTRAINT FK_smn_item_0 FOREIGN KEY (smn_item_requisiciones_id) REFERENCES smn_compras.smn_item_requisiciones (smn_item_requisiciones_id) ON DELETE NO ACTION;

ALTER TABLE smn_compras.smn_tipos_lineas_compras ADD PRIMARY KEY (smn_tipos_lineas_compras_id);

ALTER TABLE smn_compras.smn_servicios_compras ADD PRIMARY KEY (smn_servicios_compras_id);


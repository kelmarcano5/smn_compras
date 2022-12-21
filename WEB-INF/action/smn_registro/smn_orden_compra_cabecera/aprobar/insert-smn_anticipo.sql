INSERT INTO smn_pagos.smn_anticipo
(
	smn_anticipo_id,
  	smn_orden_compra_rf,
  	smn_entidad_rf,
  	smn_sucursal_rf,
  	smn_proveedor_rf,
  	smn_documento_id,
  	ant_numero_documento,
  	ant_porcentaje,
  	ant_monto_ml,
  	smn_moneda_rf,
  	smn_tasa_rf,
  	ant_monto_ma,
  	ant_estatus,
  	ant_idioma,
  	ant_usuario,
  	ant_fecha_registro,
  	ant_hora
)
VALUES
(
	nextval('smn_pagos.seq_smn_anticipo'), --smn_anticipo_id
  	${fld:smn_orden_compra_cabecera_id}, --smn_orden_compra_rf
  	${fld:smn_entidad_rf}, --smn_entidad_rf
  	${fld:smn_sucursal_rf}, --smn_sucursal_rf
  	${fld:smn_proveedor_id}, --smn_proveedor_rf
  	${fld:smn_documento_id}, --smn_documento_id
  	${fld:secuencia}, --ant_numero_documento
  	${fld:ofe_porcentaje_anticipo}, --ant_porcentaje
  	${fld:ofe_monto_anticipo_ml}, --ant_monto_ml
  	${fld:ofe_moneda_id}, --smn_moneda_rf
  	${fld:ofe_tasa}, --smn_tasa_rf
  	${fld:ofe_monto_anticipo_ma}, --ant_monto_ma
  	'GE', --ant_estatus
  	'${def:locale}', --ant_idioma
  	'${def:user}', --ant_usuario
  	{d '${def:date}'}, --ant_fecha_registro
  	'${def:time}' --ant_hora
);

UPDATE smn_pagos.smn_documento SET
	doc_secuencia = ${fld:secuencia},
	doc_idioma = '${def:locale}',
  	doc_usuario = '${def:user}',
  	doc_fecha_registro = {d '${def:date}'},
  	doc_hora = '${def:time}'
WHERE
	smn_documento_id = ${fld:smn_documento_id_pago};
INSERT INTO smn_pagos.smn_nota_recepcion_servicio
(
	smn_nota_recepcion_servicio_id,
	smn_orden_compra_rf,
	smn_centro_costo_rf,
	smn_tipo_documento_rf,
	smn_documento_rf,
	smn_numero_documento_rf,
	smn_documento_id,
	nrs_numero,
	smn_clase_rf,
	smn_proveedor_id,
	smn_usuario_rf,
	nrs_monto_saldo_ml,
	nrs_monto_saldo_ma,
	nrs_monto_ml,
	nrs_monto_ma,
	smn_moneda_rf,
	nrs_monto_usado_ml,
	nrs_monto_usado_ma,
	nrs_estatus,
	nrs_idioma,
	nrs_usuario,
	nrs_fecha_registro,
	nrs_hora,
	smn_entidad,
	smn_sucursal
)
VALUES
(
	nextval('smn_pagos.seq_smn_nota_recepcion_servicio'), --smn_nota_recepcion_servicio_id
	${fld:smn_orden_compra_cabecera_id}, --smn_orden_compra_rf
	${fld:smn_centro_costo_rf}, --smn_centro_costo_rf
	${fld:smn_tipo_documento_id}, --smn_tipo_documento_rf
	${fld:smn_documento_id}, --smn_documento_rf
	${fld:occ_orden_compra_numero}, --smn_numero_documento_rf
	${fld:smn_documento_id_pago}, --smn_documento_id
	${fld:secuencia}, --nrs_numero
	${fld:smn_clase_rf}, --smn_clase_rf
	${fld:smn_proveedor_id}, --smn_proveedor_id
	${fld:smn_usuarios_id}, --smn_usuario_rf
	${fld:nrs_monto_ml}, --nrs_monto_saldo_ml
	${fld:nrs_monto_ma}, --nrs_monto_saldo_ma
	${fld:nrs_monto_ml}, --nrs_monto_ml
	${fld:nrs_monto_ma}, --nrs_monto_ma
	${fld:smn_moneda_rf}, --smn_moneda_rf
	0.0, --nrs_monto_usado_ml
	0.0, --nrs_monto_usado_ma
	'PE', --nrs_estatus
	'${def:locale}', --nrs_idioma 
	'${def:user}', --nrs_usuario
	{d '${def:date}'}, --nrs_fecha_registro
  	'${def:time}', --nrs_hora
  	${fld:smn_entidad_rf},
  	${fld:smn_sucursal_rf}
)

RETURNING smn_nota_recepcion_servicio_id;
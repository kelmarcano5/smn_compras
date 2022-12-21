INSERT INTO smn_compras.smn_cotizacion
(
	smn_cotizacion_id,
	smn_requisicion_detalle_id,
	cot_secuencia,
	smn_documento_id,
	cot_numero_documento,
	smn_proveedor_id,
	cot_fecha_envio,
	cot_fecha_requerido,
	smn_item_id,
	cot_estatus,
	cot_fecha_registro
)
VALUES
(
	${seq:nextval@smn_compras.seq_smn_cotizacion},
	?,
	?,
	?,
	?,
	?,
	?,
	?,
	?,
	?,
	{d '${def:date}'}
)

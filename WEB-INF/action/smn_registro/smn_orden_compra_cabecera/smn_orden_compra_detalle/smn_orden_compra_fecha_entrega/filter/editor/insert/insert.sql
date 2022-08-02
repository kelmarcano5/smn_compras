INSERT INTO smn_compras.smn_orden_compra_fecha_entrega
(
	smn_orden_compra_fecha_entrega_id,
	smn_orden_compra_detalle_id,
	ocf_consecutivo,
	ocf_cantidad,
	ocf_fecha_entrega,
	ocf_estatus,
	ocf_idioma,
	ocf_usuario,
	ocf_fecha_registro,
	ocf_hora
)
VALUES
(
	${seq:currval@smn_compras.seq_smn_orden_compra_fecha_entrega},
	${fld:smn_orden_compra_detalle_id},
	${fld:ocf_consecutivo},
	${fld:ocf_cantidad},
	${fld:ocf_fecha_entrega},
	${fld:ocf_estatus},
	'${def:locale}',
	'${def:user}',
	{d '${def:date}'},
	'${def:time}'
)

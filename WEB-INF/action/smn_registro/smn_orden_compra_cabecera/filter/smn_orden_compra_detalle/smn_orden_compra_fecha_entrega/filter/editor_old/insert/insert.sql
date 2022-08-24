INSERT INTO smn_compras.smn_orden_compra_fecha_entrega
(
	smn_orden_compra_fecha_entrega_id,
	smn_orden_compra_detalle_id,
	ofe_consecutivo,
	ofe_cantidad,
	ofe_fecha_entrega,
	estatus,
	ofe_idioma,
	ofe_usuario,
	ofe_fecha_registro,
	ofe_hora
)
VALUES
(
	${seq:currval@smn_compras.seq_smn_orden_compra_fecha_entrega},
	${fld:smn_orden_compra_detalle_id},
	${fld:ofe_consecutivo},
	${fld:ofe_cantidad},
	${fld:ofe_fecha_entrega},
	${fld:estatus},
	'${def:locale
}',
	'${def:user}',
	{d '${def:date}'},
	'${def:time}'
)

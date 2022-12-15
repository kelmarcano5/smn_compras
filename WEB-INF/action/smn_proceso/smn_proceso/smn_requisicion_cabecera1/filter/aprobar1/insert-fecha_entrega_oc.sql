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
	${fld:smn_rel_requisicion_f_entrega_id},
	${fld:smn_orden_compra_detalle_id},
	${fld:cfe_consecutivo},
	${fld:cfe_cantidad},
	${fld:cfe_fecha_de_entrega},
	'RE',
	'${def:locale}',
    '${def:user}',
    {d '${def:date}'},
    '${def:time}'
)
RETURNING smn_orden_compra_fecha_entrega_id;
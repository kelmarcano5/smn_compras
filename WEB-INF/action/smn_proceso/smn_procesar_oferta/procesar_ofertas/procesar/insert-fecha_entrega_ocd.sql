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
	nextval('smn_compras.seq_smn_orden_compra_fecha_entrega'), --smn_orden_compra_fecha_entrega_id
	${fld:smn_orden_compra_detalle_id}, --smn_orden_compra_detalle_id
	${fld:ofe_consecutivo}, --ofe_consecutivo
	${fld:ofe_cantidad}, --ofe_cantidad
	${fld:ofe_fecha_entrega}, --ofe_fecha_entrega
	${fld:ofe_estatus}, --estatus
	'${def:locale}', --ofe_idioma
	'${def:user}', --ofe_usuario
	{d '${def:date}'}, --ofe_fecha_registro
	'${def:time}'--ofe_hora
)
RETURNING smn_orden_compra_fecha_entrega_id;
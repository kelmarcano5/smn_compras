SELECT
	cfe_cantidad,
	cfe_fecha_de_entrega,
	cfe_consecutivo
FROM
	smn_compras.smn_rel_requisicion_f_entrega
WHERE
	smn_requisicion_detalle_id = ${fld:smn_requisicion_detalle_id}
	
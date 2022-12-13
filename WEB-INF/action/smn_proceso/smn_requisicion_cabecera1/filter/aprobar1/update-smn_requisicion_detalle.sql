UPDATE smn_compras.smn_requisicion_detalle SET
	rrs_estatus_existencia = ${fld:rss_estatus_existencia},
	rrs_idioma = '${def:locale}',
	rrs_usuario = '${def:user}',
	rrs_fecha_registro = {d '${def:date}'},
	rrs_hora = '${def:time}'
WHERE
	smn_requisicion_detalle_id = ${fld:smn_requisicion_detalle_id}
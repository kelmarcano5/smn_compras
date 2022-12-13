UPDATE smn_compras.smn_requisicion_cabecera SET
	req_estatus='RZ',
	smn_motivo_id='${fld:smn_motivo_id}',
	req_idioma='${def:locale}',
	req_fecha_registro='${def:date}',
	req_hora='${def:time}'
WHERE
	smn_requisicion_cabecera_id=${fld:id}
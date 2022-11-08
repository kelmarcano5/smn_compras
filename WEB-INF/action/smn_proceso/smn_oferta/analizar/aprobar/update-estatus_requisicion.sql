UPDATE smn_compras.smn_requisicion_cabecera SET
	req_estatus = 'OC'
WHERE
	smn_requisicion_cabecera_id = ${fld:smn_requisicion_cabecera_id}
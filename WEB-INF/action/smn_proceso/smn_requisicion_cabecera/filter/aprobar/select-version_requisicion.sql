SELECT
	req_cabecera_version,
	req_estatus
FROM
	smn_compras.smn_requisicion_cabecera
WHERE
	smn_requisicion_cabecera_id = ${fld:smn_requisicion_cabecera_id}

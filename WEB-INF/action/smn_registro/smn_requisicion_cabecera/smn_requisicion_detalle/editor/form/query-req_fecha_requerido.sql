SELECT
	req_fecha_requerido,
	req_fecha_registro
FROM
	smn_compras.smn_requisicion_cabecera
WHERE
	smn_requisicion_cabecera_id = ${fld:id2}
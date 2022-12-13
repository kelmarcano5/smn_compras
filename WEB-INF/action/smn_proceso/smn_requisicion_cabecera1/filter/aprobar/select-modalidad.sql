SELECT
	smn_compras.smn_documentos.dcc_modalidad
FROM
	smn_compras.smn_documentos
INNER JOIN
	smn_compras.smn_requisicion_cabecera
ON
	smn_compras.smn_documentos.smn_documentos_id = smn_compras.smn_requisicion_cabecera.smn_documento_id
WHERE
	smn_compras.smn_requisicion_cabecera.smn_requisicion_cabecera_id = ${fld:smn_requisicion_cabecera_id}
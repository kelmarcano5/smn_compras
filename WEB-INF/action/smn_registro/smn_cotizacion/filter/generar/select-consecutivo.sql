SELECT
	(dcc_secuencia+1) AS secuencia
FROM
	smn_compras.smn_documentos
WHERE
	smn_documentos_id = ${fld:smn_documento_id}
	
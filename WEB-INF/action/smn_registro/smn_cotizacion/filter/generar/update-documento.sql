UPDATE
	smn_compras.smn_documentos
SET
	dcc_secuencia = ${fld:dcc_secuencia}
WHERE
	smn_documentos_id = ${fld:smn_documento_id}
RETURNING dcc_secuencia;
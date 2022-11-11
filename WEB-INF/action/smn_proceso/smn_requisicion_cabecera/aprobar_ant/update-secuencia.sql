UPDATE 
	smn_compras.smn_documentos
SET
	dcc_secuencia = ${fld:secuencia}
WHERE
	smn_documentos_id = ${fld:smn_documento_id}
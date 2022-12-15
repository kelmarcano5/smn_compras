UPDATE 
	smn_compras.smn_documentos
SET
	dcc_secuencia = ${fld:cot_numero_documento}
WHERE
	smn_documentos_id = ${fld:smn_documento_id}
	
RETURNING smn_documentos_id;
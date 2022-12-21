SELECT --mca_numero
	doc_secuencia+1 AS secuencia
FROM
	smn_inventario.smn_documento
WHERE
	smn_documento_id =  ${fld:smn_documentos_generales_id}
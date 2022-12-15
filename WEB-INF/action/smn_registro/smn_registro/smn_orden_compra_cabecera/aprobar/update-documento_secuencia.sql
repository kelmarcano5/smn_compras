UPDATE smn_inventario.smn_documento SET
	doc_secuencia = ${fld:secuencia}
WHERE
	smn_documento_id = ${fld:smn_documentos_generales_id}

RETURNING smn_documento_id;
SELECT
	smn_documento_id
FROM
	smn_inventario.smn_documento
INNER JOIN
	smn_compras.smn_documentos
ON
	smn_compras.smn_documentos.dcc_transaccion_id = smn_inventario.smn_documento.smn_documento_general_rf
inner join
	smn_inventario.smn_tipo_documento 
ON 
	smn_inventario.smn_tipo_documento.smn_tipo_documento_id=smn_inventario.smn_documento.smn_tipo_documento_id
WHERE
	smn_compras.smn_documentos.smn_documentos_id = ${fld:smn_documento_id}
AND
	smn_inventario.smn_documento.doc_estatus = 'AC'
AND
	smn_inventario.smn_tipo_documento.tdc_naturaleza='EN'
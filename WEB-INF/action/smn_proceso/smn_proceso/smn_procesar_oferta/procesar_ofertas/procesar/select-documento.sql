SELECT
	smn_documentos_id
FROM
	smn_compras.smn_documentos
INNER JOIN
	smn_compras.smn_tipo_documento
ON
	smn_compras.smn_documentos.smn_tipo_documento_id = smn_compras.smn_tipo_documento.smn_tipo_documento_id
WHERE
	smn_compras.smn_tipo_documento.tdc_naturaleza = 'OC'
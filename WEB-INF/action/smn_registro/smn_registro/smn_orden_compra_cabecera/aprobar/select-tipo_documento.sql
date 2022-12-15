SELECT
	smn_tipo_documento_id
FROM
	smn_inventario.smn_tipo_documento
WHERE
	tdc_naturaleza = 'NE'
AND	
	tdc_estatus = 'AC'
SELECT 
	tlc_naturaleza
FROM
	smn_compras.smn_tipo_linea
INNER JOIN
	smn_compras.smn_lineas ON smn_compras.smn_lineas.smn_tipo_linea_id = smn_compras.smn_tipo_linea.smn_tipo_linea_id
WHERE 
	smn_compras.smn_lineas.smn_lineas_id = ${fld:id}
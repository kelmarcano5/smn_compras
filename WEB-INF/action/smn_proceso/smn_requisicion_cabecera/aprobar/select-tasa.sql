SELECT
	smn_tasas_de_cambio_id
FROM
	smn_base.smn_tasas_de_cambio
WHERE
	tca_fecha_vigencia<=current_date
AND
	tca_estatus='AC'
ORDER BY tca_fecha_vigencia DESC 
LIMIT 1
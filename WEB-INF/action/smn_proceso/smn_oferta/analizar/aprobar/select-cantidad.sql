SELECT
	SUM(dde_cantidad_solicitada) AS cantidad_solicitada
FROM
	smn_inventario.smn_despacho_detalle
WHERE
	dde_estatus_transaccion = 'ER'
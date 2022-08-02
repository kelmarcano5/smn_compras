SELECT --smn_almacen_rf
	smn_almacen_rf
FROM
	smn_inventario.smn_caracteristica_almacen
INNER JOIN
	smn_base.smn_almacen
ON
	smn_inventario.smn_caracteristica_almacen.smn_almacen_rf = smn_base.smn_almacen.smn_almacen_id
WHERE
	smn_base.smn_almacen.alm_empresa = ${fld:smn_entidad_rf}
AND
	smn_inventario.smn_caracteristica_almacen.cal_tipo_almacen = ${fld:cal_tipo_almacen}
AND
	smn_inventario.smn_caracteristica_almacen.cal_estatus = 'AC'
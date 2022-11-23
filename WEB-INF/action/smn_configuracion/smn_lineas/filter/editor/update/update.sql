UPDATE smn_compras.smn_lineas SET
	smn_tipo_linea_id=${fld:smn_tipo_linea_id},
	lin_codigo=${fld:lin_codigo},
	lin_nombre=${fld:lin_nombre},
	lin_idioma='${def:locale}',
	lin_usuario_id='${def:user}',
	lin_fecha_registro='${def:date}',
	lin_hora='${def:time}',
	smn_almacen_consumo_rf=${fld:smn_almacen_consumo_rf}
WHERE
	smn_lineas_id=${fld:id}


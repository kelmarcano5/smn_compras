UPDATE smn_compras.smn_servicio SET
	sco_codigo=${fld:sco_codigo},
	sco_nombre=${fld:sco_nombre},
	sco_maneja_contrato=${fld:sco_maneja_contrato},
	sco_proveedor_exclusivo=${fld:sco_proveedor_exclusivo},
	smn_area_servicio_id=${fld:smn_area_servicio_id},
	smn_unidades_servicios_id=${fld:smn_unidades_servicios_id},
	sco_idioma='${def:locale}',
	sco_usuario_id='${def:user}',
	sco_fecha_registro='${def:date}',
	sco_hora='${def:time}'

WHERE
	smn_servicio_id=${fld:id}


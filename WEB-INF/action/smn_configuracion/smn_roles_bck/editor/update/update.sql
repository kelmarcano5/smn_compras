UPDATE smn_compras.smn_roles SET
	smn_usuarios_id=${fld:smn_usuarios_id},
	rol_tipo=${fld:rol_tipo},
	smn_corporaciones_id=${fld:smn_corporaciones_id},
	smn_entidades_id=${fld:smn_entidades_id},
	smn_sucursales_id=${fld:smn_sucursales_id},
	smn_areas_servicios_id=${fld:smn_areas_servicios_id},
	smn_unidades_servicios_id=${fld:smn_unidades_servicios_id},
	smn_estructura_organizacional_id=${fld:smn_estructura_organizacional_id},
	rol_idioma='${def:locale}',
	rol_usuario_id='${def:user}',
	rol_fecha_registro={d '${def:date}'},
	rol_hora='${def:time}'

WHERE
	smn_roles_id=${fld:id}


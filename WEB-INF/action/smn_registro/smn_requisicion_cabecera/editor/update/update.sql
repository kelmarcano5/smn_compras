UPDATE smn_compras.smn_requisicion_cabecera SET
	smn_cabecera_version_id=${fld:smn_cabecera_version_id},
	req_numero=${fld:req_numero},
	req_estatus=${fld:req_estatus},
	smn_tipo_documento_id=${fld:smn_tipo_documento_id},
	smn_documento_id=${fld:smn_documento_id},
	req_descripcion=${fld:req_descripcion},
	req_fecha_requerido=${fld:req_fecha_requerido},
	req_estatus_ruta=${fld:req_estatus_ruta},
	smn_entidad_id=${fld:smn_entidad_id},
	smn_sucursal_id=${fld:smn_sucursal_id},
	req_idioma='${def:locale}',
	req_usuario='${def:user}',
	req_fecha_registro={d '${def:date}'},
	req_hora='${def:time}',
	smn_almacen_solicitante_rf=${fld:smn_almacen_solicitante_rf}

WHERE
	smn_requisicion_cabecera_id=${fld:id}


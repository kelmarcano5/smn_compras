UPDATE smn_compras.smn_motivo SET
	mtv_tipo=${fld:mtv_tipo},
	mtv_codigo=${fld:mtv_codigo},
	mtv_nombre=${fld:mtv_nombre},
	mtv_idioma='${def:locale}',
	mtv_usuario_id='${def:user}',
	mtv_fecha_registro={d '${def:date}'},
	mtv_hora='${def:time}'

WHERE
	smn_motivos_id=${fld:id}


UPDATE smn_compras.smn_tipo_linea SET
	tlc_codigo=${fld:tlc_codigo},
	tlc_nombre=${fld:tlc_nombre},
	tlc_naturaleza=${fld:tlc_naturaleza},
	tlc_idioma='${def:locale}',
	tlc_usuario_id='${def:user}',
	tlc_fecha_registro='${def:date}',
	tlc_hora='${def:time}'

WHERE
	smn_tipo_linea_id=${fld:id}


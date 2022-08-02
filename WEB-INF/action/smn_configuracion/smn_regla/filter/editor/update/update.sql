UPDATE smn_compras.smn_regla SET
	rul_codigo=${fld:rul_codigo},
	rul_nombre=${fld:rul_nombre},
	rul_cantidad_desde=${fld:rul_cantidad_desde},
	rul_cantidad_hasta=${fld:rul_cantidad_hasta},
	rul_var_cantidad=${fld:rul_var_cantidad},
	smn_monedas_id=${fld:smn_monedas_id},
	rul_monto_desde=${fld:rul_monto_desde},
	rul_monto_hasta=${fld:rul_monto_hasta},
	rul_var_monto=${fld:rul_var_monto},
	rul_idioma='${def:locale}',
	rul_usuario_id='${def:user}',
	rul_fecha_registro='${def:date}',
	rul_hora='${def:time}'

WHERE
	smn_regla_id=${fld:id}


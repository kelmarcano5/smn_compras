UPDATE smn_inventario.smn_control_item SET
	coi_cantidad_reservada = ${fld:coi_cantidad_reserva},
	coi_idioma = '${def:locale}',
	coi_usuario = '${def:user}',
	coi_fecha_registro = {d '${def:date}'},
	coi_hora = '${def:time}'
WHERE
	smn_control_item_id = ${fld:smn_control_item_id}
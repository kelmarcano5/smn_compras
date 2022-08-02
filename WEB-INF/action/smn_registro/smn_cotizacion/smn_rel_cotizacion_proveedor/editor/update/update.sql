UPDATE smn_compras.smn_rel_cotizacion_proveedor SET
	smn_cotizacion_id=${fld:smn_cotizacion_id},
	smn_proveedor_id=${fld:smn_proveedor_id}

WHERE
	smn_rel_cotizacion_proveedor_id=${fld:id}


UPDATE smn_compras.smn_orden_compra_cabecera SET
	occ_estatus = 'GE'
WHERE
	smn_orden_compra_cabecera_id = ${fld:smn_orden_compra_cabecera_id}
	
RETURNING smn_orden_compra_cabecera_id;

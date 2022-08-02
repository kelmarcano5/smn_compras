select	
	ocd.*,
	occ.occ_orden_compra_numero,
	req.req_cabecera_version
from 
	smn_compras.smn_orden_compra_detalle AS ocd
	INNER JOIN
	smn_compras.smn_orden_compra_cabecera AS occ ON ocd.smn_orden_compra_cabecera_id = occ.smn_orden_compra_cabecera_id
	INNER JOIN
	smn_compras.smn_requisicion_cabecera AS req ON occ.smn_requisicion_cabecera_id = req.smn_requisicion_cabecera_id
where 
	smn_orden_compra_detalle_id = ${fld:id}



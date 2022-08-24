select	
	smn_compras.smn_orden_compra_detalle.smn_orden_compra_detalle_id, 
	smn_compras.smn_orden_compra_detalle.ocd_descripcion as ocd_descripcion_pl0,
	smn_compras.smn_orden_compra_fecha_entrega.*
from
	smn_compras.smn_orden_compra_detalle,
	smn_compras.smn_orden_compra_fecha_entrega 
where
	smn_compras.smn_orden_compra_detalle.smn_orden_compra_detalle_id=smn_compras.smn_orden_compra_fecha_entrega.smn_orden_compra_detalle_id and
	smn_orden_compra_fecha_entrega_id = ${fld:id} 
	



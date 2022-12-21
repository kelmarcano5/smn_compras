select
		smn_compras.smn_orden_compra_fecha_entrega.smn_orden_compra_fecha_entrega_id,
	case
	when smn_compras.smn_orden_compra_fecha_entrega.estatus=RE then '${lbl:b_registrada}'
	when smn_compras.smn_orden_compra_fecha_entrega.estatus=GE then '${lbl:b_generada}'
	end as estatus_combo,
	smn_compras.smn_orden_compra_fecha_entrega.ocf_consecutivo,
	smn_compras.smn_orden_compra_fecha_entrega.ocf_cantidad,
	smn_compras.smn_orden_compra_fecha_entrega.ocf_fecha_entrega,
	smn_compras.smn_orden_compra_fecha_entrega.estatus,
	smn_compras.smn_orden_compra_fecha_entrega.ocf_fecha_registro
	
from
	smn_compras.smn_orden_compra_fecha_entrega

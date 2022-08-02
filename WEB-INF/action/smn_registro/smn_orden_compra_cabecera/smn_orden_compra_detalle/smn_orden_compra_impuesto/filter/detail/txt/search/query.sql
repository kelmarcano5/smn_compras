select
	smn_compras.smn_orden_compra_detalle.smn_orden_compra_detalle_id,
	smn_compras.smn_orden_compra_detalle.ocd_descripcion as ocd_descripcion_pl0,
select
select
select
select
	smn_compras.smn_orden_compra_impuesto.*
from
	smn_compras.smn_orden_compra_detalle,
	smn_compras.smn_orden_compra_impuesto
where
	smn_compras.smn_orden_compra_detalle.smn_orden_compra_detalle_id=smn_compras.smn_orden_compra_impuesto.smn_orden_compra_detalle_id
	and
	smn_impuesto_oc_id = ${fld:id}

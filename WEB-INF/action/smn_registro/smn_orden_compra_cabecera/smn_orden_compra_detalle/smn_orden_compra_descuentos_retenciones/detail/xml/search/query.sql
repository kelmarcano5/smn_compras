select
	smn_compras.smn_orden_compra_detalle.smn_orden_compra_detalle_id,
	smn_compras.smn_orden_compra_detalle.ocd_descripcion as ocd_descripcion_pl0,
select
select
select
	smn_compras.smn_orden_compra_descuentos_retenciones.*
from
	smn_compras.smn_orden_compra_detalle,
	smn_compras.smn_orden_compra_descuentos_retenciones
where
	smn_compras.smn_orden_compra_detalle.smn_orden_compra_detalle_id=smn_compras.smn_orden_compra_descuentos_retenciones.smn_orden_compra_detalle_id
	and
	smn_descuento_retencion_oc_id = ${fld:id}
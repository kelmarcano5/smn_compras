WITH ordenCompra AS ( 
select
	smn_compras.smn_orden_compra_detalle.smn_orden_compra_cabecera_id,
	smn_compras.smn_orden_compra_detalle.smn_orden_compra_detalle_id,
	smn_compras.smn_orden_compra_detalle.ocd_costo_ml,
	smn_compras.smn_orden_compra_detalle.ocd_costo_ma,
	smn_compras.smn_orden_compra_detalle.ocd_monto_bruto_ml,
	smn_compras.smn_orden_compra_detalle.ocd_monto_impuesto_ml,
	smn_compras.smn_orden_compra_detalle.ocd_monto_desc_reten_ml,
	smn_compras.smn_orden_compra_detalle.ocd_monto_bruto_ma,
	smn_compras.smn_orden_compra_detalle.ocd_monto_impuesto_ma,
	smn_compras.smn_orden_compra_detalle.ocd_monto_desc_reten_ma,
	smn_compras.smn_orden_compra_detalle.ocd_monto_neto_ml,
	smn_compras.smn_orden_compra_detalle.ocd_monto_neto_ma
from
	smn_compras.smn_orden_compra_detalle 
where
	smn_compras.smn_orden_compra_detalle.smn_orden_compra_cabecera_id = ${fld:id}
)
select sum(ordenCompra.ocd_monto_bruto_ml) as total_monto_bruto_ml, sum(ordenCompra.ocd_monto_impuesto_ml) as total_monto_impuesto_ml,
		sum(ordenCompra.ocd_monto_desc_reten_ml) as total_monto_desc_reten_ml, sum(ordenCompra.ocd_monto_desc_reten_ma) as total_monto_desc_reten_ma,
		sum(ordenCompra.ocd_monto_bruto_ma) as total_monto_bruto_ma, sum(ordenCompra.ocd_monto_impuesto_ma) as total_monto_impuesto_ma,
		sum(ordenCompra.ocd_monto_neto_ml) as total_monto_neto_ml, sum(ordenCompra.ocd_monto_neto_ma) as total_monto_neto_ma

 FROM ordenCompra
WHERE
	ordenCompra.smn_orden_compra_cabecera_id = ${fld:id}
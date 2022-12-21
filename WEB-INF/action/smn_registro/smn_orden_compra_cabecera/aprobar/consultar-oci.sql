SELECT
   *
FROM
   smn_compras.smn_orden_compra_impuesto
WHERE
   smn_compras.smn_orden_compra_impuesto.smn_orden_compra_detalle_id = ${fld:smn_orden_compra_detalle_id}
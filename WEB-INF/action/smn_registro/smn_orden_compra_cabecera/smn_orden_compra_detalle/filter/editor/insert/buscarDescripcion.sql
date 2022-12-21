select
		smn_compras.smn_orden_compra_detalle.ocd_descripcion
from
		smn_compras.smn_orden_compra_detalle
where
		upper(smn_compras.smn_orden_compra_detalle.ocd_descripcion) = upper(${fld:ocd_descripcion})

select
		smn_compras.smn_orden_compra_cabecera.occ_descripcion
from
		smn_compras.smn_orden_compra_cabecera
where
		upper(smn_compras.smn_orden_compra_cabecera.occ_descripcion) = upper(${fld:occ_descripcion})

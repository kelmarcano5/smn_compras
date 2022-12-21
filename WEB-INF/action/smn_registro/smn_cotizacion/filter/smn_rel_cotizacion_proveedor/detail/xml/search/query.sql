select
	smn_compras.smn_proveedor.smn_proveedor_id,
	smn_compras.smn_proveedor.pro_codigo as pro_codigo_pl0,
select
	smn_compras.smn_rel_cotizacion_proveedor.*
from
	smn_compras.smn_proveedor,
	smn_compras.smn_rel_cotizacion_proveedor
where
	smn_compras.smn_proveedor.smn_proveedor_id=smn_compras.smn_rel_cotizacion_proveedor.smn_proveedor_id
	and
	smn_rel_cotizacion_proveedor_id = ${fld:id}

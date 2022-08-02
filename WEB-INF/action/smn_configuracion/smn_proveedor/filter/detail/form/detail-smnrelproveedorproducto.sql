select
	smn_compras.smn_servicio.smn_servicio_id,
	smn_compras.smn_servicio.sco_codigo as sco_codigo_pl0,
	smn_compras.smn_rel_proveedor_producto.*
from
	smn_compras.smn_proveedor ,
	smn_compras.smn_servicio,
	smn_compras.smn_rel_proveedor_producto
where
		smn_compras.smn_rel_proveedor_producto.smn_proveedor_id=smn_compras.smn_proveedor.smn_proveedor_id and
		smn_compras.smn_proveedor.smn_proveedor_id=${fld:id}  and
	smn_compras.smn_servicio.smn_servicio_id=smn_compras.smn_rel_proveedor_producto.smn_servicio_id
	

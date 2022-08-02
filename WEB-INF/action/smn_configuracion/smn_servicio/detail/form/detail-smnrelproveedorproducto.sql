select	
	smn_compras.smn_proveedor.smn_proveedor_id, 
	smn_compras.smn_proveedor.prv_idioma as prv_idioma_pl0,
	(select smn_compras.smn_proveedor.prv_auxiliar_categoria_id|| ' - ' || smn_compras.smn_proveedor.smn_clase_auxiliar_id 
from  smn_compras.smn_proveedor where smn_compras.smn_proveedor.smn_proveedor_id is not null  
and smn_compras.smn_proveedor.smn_proveedor_id=smn_compras.smn_servicio.smn_servicio_id) as smn_proveedor_id,
(select smn_base.smn_item.itm_codigo|| ' - ' || smn_base.smn_item.itm_nombre 
from  smn_base.smn_item where smn_base.smn_item.smn_item_id is not null  
and smn_base.smn_item.smn_item_id=smn_compras.smn_servicio.smn_servicio_id) as smn_item_id,
(select smn_base.smn_activo_fijo.acf_codigo|| ' - ' || smn_base.smn_activo_fijo.acf_nombre 
from  smn_base.smn_activo_fijo where smn_base.smn_activo_fijo.smn_afijo_id is not null  
and smn_base.smn_activo_fijo.smn_afijo_id=smn_compras.smn_servicio.smn_servicio_id) as smn_afijo_id,
smn_compras.smn_rel_proveedor_producto.*
from
	smn_compras.smn_servicio,
	smn_compras.smn_proveedor,
	smn_compras.smn_rel_proveedor_producto
where
	smn_compras.smn_rel_proveedor_producto.smn_servicio_id=smn_compras.smn_servicio.smn_servicio_id and 
	smn_compras.smn_servicio.smn_servicio_id=${fld:id}  and 
	smn_compras.smn_proveedor.smn_proveedor_id=smn_compras.smn_rel_proveedor_producto.smn_proveedor_id 
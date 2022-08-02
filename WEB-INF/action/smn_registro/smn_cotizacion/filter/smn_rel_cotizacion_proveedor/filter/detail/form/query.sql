select
	smn_compras.smn_rel_cotizacion_proveedor.smn_rel_cotizacion_proveedor_id,
	smn_compras.smn_rel_cotizacion_proveedor.smn_cotizacion_id,
	smn_base.smn_auxiliar.aux_codigo ||' - '|| smn_base.smn_auxiliar.aux_descripcion as smn_proveedor_id,
	smn_compras.smn_cotizacion.cot_numero_documento as numero_cotizacion
	
from
	smn_compras.smn_rel_cotizacion_proveedor
	inner join smn_compras.smn_proveedor on smn_compras.smn_proveedor.smn_proveedor_id = smn_compras.smn_rel_cotizacion_proveedor.smn_proveedor_id
	inner join smn_base.smn_auxiliar on smn_base.smn_auxiliar.smn_auxiliar_id = smn_compras.smn_proveedor.smn_auxiliar_rf
		inner join smn_compras.smn_cotizacion on smn_compras.smn_cotizacion.smn_cotizacion_id = smn_compras.smn_rel_cotizacion_proveedor.smn_cotizacion_id
where
	smn_compras.smn_rel_cotizacion_proveedor.smn_rel_cotizacion_proveedor_id = ${fld:id}

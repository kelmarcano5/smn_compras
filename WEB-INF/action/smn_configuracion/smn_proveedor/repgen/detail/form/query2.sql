select
		smn_compras.smn_proveedor.smn_auxiliar_rf,
	smn_compras.smn_proveedor.smn_clasificacion_abc_rf,
	smn_compras.smn_proveedor.prv_fecha_registro
from
	smn_compras.smn_proveedor 
where
	smn_compras.smn_proveedor.smn_proveedor_id = ${fld:id}

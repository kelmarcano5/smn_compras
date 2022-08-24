select
		smn_compras.smn_proveedor.smn_auxiliar_rf
from
		smn_compras.smn_proveedor
where
		smn_compras.smn_proveedor.smn_auxiliar_rf = ${fld:smn_auxiliar_rf}

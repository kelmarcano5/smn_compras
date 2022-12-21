select	
	smn_compras.smn_orden_compra_impuesto.*
from
	smn_compras.smn_orden_compra_impuesto 
where
	smn_compras.smn_orden_compra_impuesto.smn_impuesto_oc_id = ${fld:id} 
	



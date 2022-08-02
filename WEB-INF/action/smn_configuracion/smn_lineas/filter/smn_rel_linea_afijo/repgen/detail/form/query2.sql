select
		smn_compras.smn_rel_linea_afijo.smn_lineas_id,
	smn_compras.smn_rel_linea_afijo.smn_afijo_id
from
	smn_compras.smn_rel_linea_afijo 
where
	smn_compras.smn_rel_linea_afijo.smn_rel_linea_afijo_id = ${fld:id}

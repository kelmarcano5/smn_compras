select
		smn_compras.smn_rel_linea_afijo.smn_rel_linea_afijo_id,
	${field}
from
	smn_compras.smn_rel_linea_afijo
where
		smn_compras.smn_rel_linea_afijo.smn_rel_linea_afijo_id is not null
	${filter}
	
	

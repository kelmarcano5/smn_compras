select
	smn_compras.smn_rel_linea_afijo.*
from
	smn_compras.smn_rel_linea_afijo,
	smn_compras.smn_lineas
where
		smn_compras.smn_rel_linea_afijo.smn_lineas_id=smn_compras.smn_lineas.smn_lineas_id and
		smn_compras.smn_lineas.smn_lineas_id=${fld:id}

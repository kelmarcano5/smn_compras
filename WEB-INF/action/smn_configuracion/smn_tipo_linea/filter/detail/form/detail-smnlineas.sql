select
	smn_compras.smn_lineas.*
from
	smn_compras.smn_lineas,
	smn_compras.smn_tipo_linea
where
		smn_compras.smn_lineas.smn_tipo_linea_id=smn_compras.smn_tipo_linea.smn_tipo_linea_id and
		smn_compras.smn_tipo_linea.smn_tipo_linea_id=${fld:id}

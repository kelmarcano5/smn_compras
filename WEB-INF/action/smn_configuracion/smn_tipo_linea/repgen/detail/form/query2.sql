select
		smn_compras.smn_tipo_linea.tlc_codigo,
	smn_compras.smn_tipo_linea.tlc_nombre,
	smn_compras.smn_tipo_linea.tlc_naturaleza,
	smn_compras.smn_tipo_linea.tlc_fecha_registro
from
	smn_compras.smn_tipo_linea 
where
	smn_compras.smn_tipo_linea.smn_tipo_linea_id = ${fld:id}

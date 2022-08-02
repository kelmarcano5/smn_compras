select
		smn_compras.smn_lineas.smn_lineas_id,
			smn_compras.smn_tipo_linea.tlc_codigo||'-'||smn_compras.smn_tipo_linea.tlc_nombre as smn_tipo_linea_id,
	smn_compras.smn_tipo_linea.tlc_codigo as tlc_codigo_pl0,
	smn_compras.smn_lineas.smn_tipo_linea_id,
	smn_compras.smn_lineas.lin_codigo,
	smn_compras.smn_lineas.lin_nombre,
	smn_compras.smn_lineas.lin_fecha_registro
	
from
	smn_compras.smn_tipo_linea,
	smn_compras.smn_lineas
where
	smn_compras.smn_tipo_linea.smn_tipo_linea_id=smn_compras.smn_lineas.smn_tipo_linea_id

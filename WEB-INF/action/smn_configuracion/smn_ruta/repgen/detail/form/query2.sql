select
		smn_compras.smn_ruta.rut_codigo,
	smn_compras.smn_ruta.rut_nombre,
	smn_compras.smn_ruta.rut_cantidad_aprobaciones,
	smn_compras.smn_ruta.rut_fecha_registro
from
	smn_compras.smn_ruta 
where
	smn_compras.smn_ruta.smn_ruta_id = ${fld:id}

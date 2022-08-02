select
		smn_compras.smn_rel_ruta_aprobacion.smn_ruta_aprobacion_id,
	smn_compras.smn_rel_ruta_aprobacion.smn_ruta_id,
	smn_compras.smn_rel_ruta_aprobacion.rra_aprobacion,
	smn_compras.smn_rel_ruta_aprobacion.smn_lineas_id,
	smn_compras.smn_rel_ruta_aprobacion.smn_roles_id,
	smn_compras.smn_rel_ruta_aprobacion.smn_regla_id,
	smn_compras.smn_rel_ruta_aprobacion.rra_fecha_registro
from
	smn_compras.smn_rel_ruta_aprobacion 
where
	smn_compras.smn_rel_ruta_aprobacion.smn_rel_ruta_aprobacion_id = ${fld:id}

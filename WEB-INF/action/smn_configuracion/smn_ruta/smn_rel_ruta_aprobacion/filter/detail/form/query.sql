select
  smn_compras.smn_rel_ruta_aprobacion.smn_ruta_aprobacion_id,
	smn_compras.smn_ruta.rut_codigo||'-'||smn_compras.smn_ruta.rut_nombre as smn_ruta_id,
	smn_compras.smn_lineas.lin_codigo||'-'||smn_compras.smn_lineas.lin_nombre as smn_lineas_id,
	smn_compras.smn_roles.smn_roles_id||'-'||smn_compras.smn_roles.rol_tipo as smn_roles_id,
  smn_compras.smn_regla.rul_codigo||'-'||smn_compras.smn_regla.rul_nombre as smn_regla_id,
	case
	when smn_compras.smn_rel_ruta_aprobacion.rra_aprobacion='SI' then '${lbl:b_yes}'
	when smn_compras.smn_rel_ruta_aprobacion.rra_aprobacion='NO' then '${lbl:b_not}'
	end as rra_aprobacion,
	smn_compras.smn_rel_ruta_aprobacion.rra_aprobacion,
	smn_compras.smn_rel_ruta_aprobacion.rra_fecha_registro
	
from
	smn_compras.smn_ruta,
	smn_compras.smn_lineas,
	smn_compras.smn_roles,
	smn_compras.smn_regla,
	smn_compras.smn_rel_ruta_aprobacion
where
	smn_compras.smn_ruta.smn_ruta_id=smn_compras.smn_rel_ruta_aprobacion.smn_ruta_id and
	smn_compras.smn_lineas.smn_lineas_id=smn_compras.smn_rel_ruta_aprobacion.smn_lineas_id and
	smn_compras.smn_roles.smn_roles_id=smn_compras.smn_rel_ruta_aprobacion.smn_roles_id and
	smn_compras.smn_regla.smn_regla_id=smn_compras.smn_rel_ruta_aprobacion.smn_regla_id

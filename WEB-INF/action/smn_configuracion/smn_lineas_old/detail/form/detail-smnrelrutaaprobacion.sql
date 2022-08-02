select	
	smn_compras.smn_ruta.smn_ruta_id, 
	smn_compras.smn_ruta.rut_codigo as rut_codigo_pl0,
	smn_compras.smn_roles.smn_roles_id, 
	smn_compras.smn_roles.rol_tipo as rol_tipo_pl1,
	smn_compras.smn_regla.smn_regla_id, 
	smn_compras.smn_regla.rul_codigo as rul_codigo_pl2,
	(select smn_compras.smn_ruta.rut_codigo|| ' - ' || smn_compras.smn_ruta.rut_nombre from  smn_compras.smn_ruta where smn_compras.smn_ruta.smn_ruta_id is not null  and smn_compras.smn_ruta.smn_ruta_id=smn_compras.smn_lineas.smn_lineas_id) as smn_ruta_id,
	case
		when smn_compras.smn_rel_ruta_aprobacion.rra_aprobacion='SI' then '${lbl:b_yes}'
		when smn_compras.smn_rel_ruta_aprobacion.rra_aprobacion='NO' then '${lbl:b_not}'
	end as rra_aprobacion,
	(select smn_compras.smn_roles.rol_tipo from  smn_compras.smn_roles where smn_compras.smn_roles.smn_roles_id is not null  and smn_compras.smn_roles.smn_roles_id=smn_compras.smn_lineas.smn_lineas_id) as smn_roles_id,
	(select smn_compras.smn_regla.rul_codigo|| ' - ' || smn_compras.smn_regla.rul_nombre from  smn_compras.smn_regla where smn_compras.smn_regla.smn_regla_id is not null  and smn_compras.smn_regla.smn_regla_id=smn_compras.smn_lineas.smn_lineas_id) as smn_regla_id,
	smn_compras.smn_rel_ruta_aprobacion.*
from
	smn_compras.smn_lineas ,
	smn_compras.smn_ruta,
	smn_compras.smn_roles,
	smn_compras.smn_regla,
	smn_compras.smn_rel_ruta_aprobacion
where
	smn_compras.smn_rel_ruta_aprobacion.smn_lineas_id=smn_compras.smn_lineas.smn_lineas_id and 
	smn_compras.smn_lineas.smn_lineas_id=${fld:id}  and 
	smn_compras.smn_ruta.smn_ruta_id=smn_compras.smn_rel_ruta_aprobacion.smn_ruta_id and
	smn_compras.smn_roles.smn_roles_id=smn_compras.smn_rel_ruta_aprobacion.smn_roles_id and
	smn_compras.smn_regla.smn_regla_id=smn_compras.smn_rel_ruta_aprobacion.smn_regla_id 


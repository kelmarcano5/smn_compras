select	
  smn_compras.smn_lineas.lin_codigo|| ' - ' || smn_compras.smn_lineas.lin_nombre as smn_lineas_id,
	smn_base.smn_v_usuarios.usr_nombres|| ' - ' || smn_base.smn_v_usuarios.usr_nombres as smn_usuarios_id,
		smn_compras.smn_rel_usuarios_linea.smn_rel_usuario_linea_id
from  smn_compras.smn_rel_usuarios_linea
LEFT OUTER JOIN smn_base.smn_v_usuarios on smn_base.smn_v_usuarios.smn_usuarios_id=smn_compras.smn_rel_usuarios_linea.smn_usuarios_id
LEFT OUTER JOIN smn_compras.smn_lineas on smn_compras.smn_lineas.smn_lineas_id=smn_compras.smn_rel_usuarios_linea.smn_lineas_id

where
	smn_rel_usuario_linea_id is not null and smn_compras.smn_lineas.smn_lineas_id=smn_compras.smn_rel_usuarios_linea.smn_lineas_id 

	${filter}
order by
		smn_rel_usuario_linea_id

	 	${filter}
order by 
	smn_rel_usuario_linea_id
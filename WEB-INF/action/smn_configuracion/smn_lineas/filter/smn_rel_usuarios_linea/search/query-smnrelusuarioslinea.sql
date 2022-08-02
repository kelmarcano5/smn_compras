select	
  smn_compras.smn_lineas.lin_codigo|| ' - ' || smn_compras.smn_lineas.lin_nombre as smn_lineas_id,
	(select smn_base.smn_v_usuarios.usr_nombres|| ' - ' || smn_base.smn_v_usuarios.usr_apellidos 
from  smn_base.smn_v_usuarios where smn_base.smn_v_usuarios.smn_usuarios_id is not null  
and smn_base.smn_v_usuarios.smn_usuarios_id=smn_compras.smn_rel_usuarios_linea.smn_usuarios_id) as smn_usuarios_id,
	smn_compras.smn_rel_usuarios_linea.smn_rel_usuario_linea_id
from
	smn_compras.smn_lineas,
	smn_compras.smn_rel_usuarios_linea
where
	smn_rel_usuario_linea_id is not null	
and 	smn_compras.smn_lineas.smn_lineas_id=smn_compras.smn_rel_usuarios_linea.smn_lineas_id and
	smn_compras.smn_lineas.smn_lineas_id=${fld:id2}

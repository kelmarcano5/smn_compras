SELECT distinct smn_compras.smn_lineas.smn_lineas_id as id, 
smn_compras.smn_lineas.lin_codigo||' - '|| smn_compras.smn_lineas.lin_nombre as item from smn_compras.smn_lineas
left outer join smn_compras.smn_rel_usuarios_linea on smn_compras.smn_rel_usuarios_linea.smn_lineas_id = smn_compras.smn_lineas.smn_lineas_id
left outer join smn_base.smn_usuarios on smn_base.smn_usuarios.smn_usuarios_id = smn_compras.smn_rel_usuarios_linea.smn_usuarios_id
left outer join smn_seguridad.s_user on smn_seguridad.s_user.user_id = smn_base.smn_usuarios.smn_user_rf
--where 
--smn_seguridad.s_user.userlogin='${def:user}'
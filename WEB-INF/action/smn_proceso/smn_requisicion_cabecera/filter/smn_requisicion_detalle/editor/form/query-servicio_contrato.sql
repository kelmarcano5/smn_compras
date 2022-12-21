select smn_compras.smn_servicio.sco_maneja_contrato as contrato from smn_compras.smn_servicio
left outer join smn_compras.smn_rel_linea_servicio on smn_compras.smn_rel_linea_servicio.smn_servicio_id = smn_compras.smn_servicio.smn_servicio_id
left outer join smn_compras.smn_rel_usuarios_linea on smn_compras.smn_rel_usuarios_linea.smn_lineas_id = smn_compras.smn_rel_linea_servicio.smn_lineas_id
left outer join smn_base.smn_usuarios on smn_base.smn_usuarios.smn_usuarios_id = smn_compras.smn_rel_usuarios_linea.smn_usuarios_id
left outer join smn_seguridad.s_user on smn_seguridad.s_user.user_id = smn_base.smn_usuarios.smn_user_rf

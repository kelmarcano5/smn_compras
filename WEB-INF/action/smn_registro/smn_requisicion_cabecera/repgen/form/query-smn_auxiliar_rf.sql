select smn_base.smn_auxiliar.smn_auxiliar_id as id, smn_base.smn_auxiliar.aux_descripcion as item 
from smn_compras.smn_roles
inner join smn_base.smn_usuarios on smn_base.smn_usuarios.smn_usuarios_id = smn_compras.smn_roles.smn_usuarios_id
inner join smn_base.smn_auxiliar on smn_base.smn_auxiliar.smn_auxiliar_id = smn_base.smn_usuarios.smn_auxiliar_rf
inner join smn_seguridad.s_user on smn_seguridad.s_user.user_id = smn_base.smn_usuarios.smn_user_rf
where smn_compras.smn_roles.rol_tipo = 'SO' and smn_seguridad.s_user.userlogin='${def:user}'
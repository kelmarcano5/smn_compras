SELECT
	smn_compras.smn_roles.rol_tipo
FROM
	smn_compras.smn_roles
INNER JOIN
	smn_base.smn_usuarios
ON
	smn_base.smn_usuarios.smn_usuarios_id = smn_compras.smn_roles.smn_usuarios_id
INNER JOIN
	smn_seguridad.s_user
ON
	smn_seguridad.s_user.user_id = smn_base.smn_usuarios.smn_user_rf
WHERE
	smn_seguridad.s_user.userlogin = '${def:user}'
AND
<<<<<<<< HEAD:WEB-INF/action/smn_proceso/smn_requisicion_cabecera/aprobar_ant/select-tipo_rol.sql
	smn_compras.smn_roles.rol_tipo = 'AP'
========
	smn_compras.smn_roles.rol_tipo = 'CO'
>>>>>>>> a57efee07d661d845df2ffc06524651f62dfc69e:WEB-INF/action/smn_proceso/smn_oferta/filter/generar/select-rol.sql

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
	smn_compras.smn_roles.rol_tipo = 'CO'
AND
	smn_compras.smn_roles.smn_entidades_id = (SELECT smn_entidad_rf FROM smn_compras.smn_orden_compra_cabecera WHERE smn_orden_compra_cabecera_id = ${fld:id})

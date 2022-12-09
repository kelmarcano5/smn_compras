SELECT
	smn_base.smn_usuarios.smn_auxiliar_rf
FROM
	smn_seguridad.s_user
INNER JOIN
	smn_base.smn_usuarios ON smn_seguridad.s_user.user_id = smn_base.smn_usuarios.smn_user_rf
WHERE
	smn_seguridad.s_user.userlogin = ${fld:req_usuario}
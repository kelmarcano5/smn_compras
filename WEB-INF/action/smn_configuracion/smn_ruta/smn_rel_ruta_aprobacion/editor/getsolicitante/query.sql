select DISTINCT smn_compras.smn_roles.smn_roles_id as id,
case
when smn_compras.smn_roles.rol_tipo = 'SO' then '${lbl:b_solicitante}' ||' - '|| smn_base.smn_auxiliar.aux_descripcion
when smn_compras.smn_roles.rol_tipo = 'AP' then 'Aprobador' ||' - '|| smn_base.smn_auxiliar.aux_descripcion
when smn_compras.smn_roles.rol_tipo = 'CO' then 'Comprador' ||' - '|| smn_base.smn_auxiliar.aux_descripcion
end as item from smn_compras.smn_roles
inner join smn_base.smn_usuarios on smn_base.smn_usuarios.smn_usuarios_id = smn_compras.smn_roles.smn_usuarios_id
inner join smn_base.smn_auxiliar on smn_base.smn_auxiliar.smn_auxiliar_id = smn_base.smn_usuarios.smn_auxiliar_rf
where smn_compras.smn_roles.rol_tipo is not null
and smn_compras.smn_roles.rol_tipo IN ('SO', 'CO')
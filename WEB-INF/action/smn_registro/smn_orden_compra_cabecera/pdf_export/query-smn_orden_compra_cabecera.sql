
select
	case
	when smn_compras.smn_orden_compra_cabecera.occ_estatus='RE' then '${lbl:b_registrada}'
	when smn_compras.smn_orden_compra_cabecera.occ_estatus='GE' then '${lbl:b_generada}'
	when smn_compras.smn_orden_compra_cabecera.occ_estatus='PC' then '${lbl:b_parcialmente_recibida}'
	when smn_compras.smn_orden_compra_cabecera.occ_estatus='RC' then '${lbl:b_recibida}'
	end as occ_estatus_combo,
	smn_base.smn_entidades.ent_descripcion_corta as smn_entidad_rf_combo,
	enti_direcc.dir_descripcion as direccionempresa,
	smn_base.smn_entidades.ent_nro_id_fiscal as rif,
	(select smn_base.smn_sucursales.suc_empresa|| ' - ' || smn_base.smn_sucursales.suc_nombre from  smn_base.smn_sucursales where smn_base.smn_sucursales.smn_sucursales_id is not null and smn_base.smn_sucursales.smn_sucursales_id=smn_compras.smn_orden_compra_cabecera.smn_sucursal_rf) as smn_sucursal_rf_combo,
	(select smn_base.smn_auxiliar.aux_codigo|| ' - ' || smn_base.smn_auxiliar.aux_descripcion from smn_base.smn_auxiliar where smn_base.smn_auxiliar.smn_auxiliar_id=smn_compras.smn_orden_compra_cabecera.smn_auxiliar_rf) as smn_auxiliar_rf_combo,
	smn_compras.smn_orden_compra_cabecera.*,
	AP.aux_razon_social as proveedor,
	AP.aux_rif as rifproveedor,
	AP_direcc.dir_descripcion as direccionprovee,
	aprob_aux.aux_descripcion as aprobador,
	smn_base.smn_contactos.cnt_email as email_provee,
	smn_base.smn_contactos.cnt_telefono_contacto as tlf_provee
from
	smn_compras.smn_orden_compra_cabecera 
	INNER JOIN smn_compras.smn_proveedor ON  smn_compras.smn_proveedor.smn_proveedor_id is not null and smn_compras.smn_proveedor.smn_proveedor_id=smn_compras.smn_orden_compra_cabecera.smn_proveedor_id
	iNNER JOIN smn_base.smn_auxiliar AP ON AP.smn_auxiliar_id=smn_compras.smn_proveedor.smn_auxiliar_rf
	left join smn_base.smn_direccion AP_direcc ON AP_direcc.smn_direccion_id = AP.aux_direccion_rf
	INNER JOIN smn_base.smn_entidades ON smn_compras.smn_orden_compra_cabecera.smn_entidad_rf=smn_base.smn_entidades.smn_entidades_id
	left join smn_base.smn_direccion enti_direcc ON enti_direcc.smn_direccion_id = smn_base.smn_entidades.ent_direccion
	iNNER JOIN smn_base.smn_auxiliar ent_aux ON ent_aux.smn_auxiliar_id=smn_base.smn_entidades.smn_entidades_id
	INNER JOIN smn_seguridad.s_user ON smn_compras.smn_orden_compra_cabecera.occ_usuario_aprobador=smn_seguridad.s_user.userlogin 
	INNER JOIN smn_base.smn_usuarios ON smn_base.smn_usuarios.smn_user_rf=smn_seguridad.s_user.user_id
	INNER JOIN smn_base.smn_auxiliar aprob_aux ON aprob_aux.smn_auxiliar_id=smn_base.smn_usuarios.smn_auxiliar_rf
	LEFT OUTER JOIN smn_base.smn_contactos ON smn_base.smn_contactos.smn_auxiliar_id=smn_compras.smn_proveedor.smn_auxiliar_rf
where
	smn_orden_compra_cabecera_id = ${fld:id}

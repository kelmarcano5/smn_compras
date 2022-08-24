INSERT INTO smn_compras.smn_requisicion_cabecera (
	smn_requisicion_cabecera_id,
	smn_cabecera_version_id,
  	req_numero,
  	req_estatus,
  	smn_tipo_documento_id,
  	smn_documento_id,
 	req_descripcion,
  	req_fecha_requerido,
  	req_estatus_ruta,
  	smn_entidad_id,
  	smn_sucursal_id,
  	req_idioma,
  	req_usuario,
  	req_fecha_registro,
 	req_hora,
  	req_cabecera_version,
  	smn_modulo_rf
)VALUES(
	nextval('smn_compras.seq_smn_requisicion_cabecera'),
	${fld:smn_requisicion_cabecera_id},
	(SELECT
     	req_numero
     FROM
    	smn_compras.smn_requisicion_cabecera
     WHERE
     	smn_requisicion_cabecera_id = ${fld:smn_requisicion_cabecera_id}
    ),
	'GE',
	${fld:smn_tipo_documento_id},
	${fld:smn_documento_id},
	${fld:req_descripcion},
	${fld:req_fecha_requerido},
	${fld:req_estatus_ruta},
	${fld:smn_entidad_id},
	${fld:smn_sucursal_id},
	'${def:locale}',
    '${def:user}',
    {d '${def:date}'},
    '${def:time}',
    ${fld:req_cabecera_version},
    (SELECT 
    	smn_modulos_id
     FROM
     	smn_base.smn_modulos
     WHERE
     	mod_codigo = 'COM'
    )
)

RETURNING smn_requisicion_cabecera_id as id_cabecera;
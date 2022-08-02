SELECT 
	COALESCE( 
		(SELECT 
			smn_compras.smn_proveedor.smn_proveedor_id  
		FROM   
			smn_compras.smn_proveedor
		INNER JOIN
			smn_base.smn_auxiliar ON smn_compras.smn_proveedor.smn_auxiliar_rf = smn_base.smn_auxiliar.smn_auxiliar_id
		WHERE  
			UPPER(TRIM(smn_base.smn_auxiliar.aux_descripcion)) = UPPER(TRIM(${fld:smn_proveedor_desc}))
	)) AS smn_proveedor_ref	
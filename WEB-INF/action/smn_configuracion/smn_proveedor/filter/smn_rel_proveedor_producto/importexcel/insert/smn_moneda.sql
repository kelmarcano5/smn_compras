SELECT 
	COALESCE( 
		(SELECT 
			smn_monedas_id  
		FROM   
			smn_base.smn_monedas
		WHERE  
			UPPER(TRIM(smn_base.smn_monedas.mon_codigo)) = UPPER(TRIM(${fld:smn_moneda_desc}))
		),
		(SELECT 
			smn_monedas_id  
		FROM   
			smn_base.smn_monedas
		WHERE  
			UPPER(TRIM(smn_base.smn_monedas.mon_nombre)) = UPPER(TRIM(${fld:smn_moneda_desc}))
		) 
	)
	AS smn_moneda_ref	
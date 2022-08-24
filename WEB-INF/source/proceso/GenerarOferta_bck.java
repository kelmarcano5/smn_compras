package proceso;

import dinamica.*;
import java.sql.*;
import javax.sql.DataSource;

public class GenerarOferta extends GenericTransaction
{
	
	public int service(Recordset inputParams) throws Throwable
	{
		int rc = 0;	//variable a retornar.
		//String mensaje = ""; //mensaje a retornar
		
		String jndiName = (String)getContext().getAttribute("dinamica.security.datasource");
		
		if (jndiName==null)
			throw new Throwable("Context attribute [dinamica.security.datasource] is null, check your security filter configuration.");
		
		//establecer la conexion con la base de datos.
		
			DataSource ds = Jndi.getDataSource(jndiName); 
			Connection conn = ds.getConnection();
			this.setConnection(conn);
		
		//**
			
		conn.setAutoCommit(false);
	
		try
		{
			Db db = getDb(); //objeto de conexion.
			
			String sqlRol   = getSQL(getResource("select-rol.sql"),inputParams);
			Recordset rsRol = db.get(sqlRol); //valida si el rol es COMPRADOR
			
			if(rsRol.getRecordCount()<1)
			{
				rc = 1;
				inputParams.setValue("mensaje", "*El usuario debe ser comprador*");
				System.out.println(inputParams.getValue("mensaje"));
				return rc;
			}
			
			String sqlValidar   = getSQL(getResource("select-validarCampos.sql"),inputParams);
			Recordset rsValidar = db.get(sqlValidar); //valida que los campos precios y cond. financiera no esten vacios.
			
			if(rsValidar.getRecordCount()>0)
			{
				rsValidar.first();
						
				String precio_ml = rsValidar.getString("ofe_precio_ml");
						
				if(precio_ml == null)
				{
					rc = 1;
					inputParams.setValue("mensaje", "*el precio en moneda local no debe estar vacio*");
					System.out.println(inputParams.getValue("mensaje"));
					return rc;
				}
			}
			else
			{
				rc = 1;
				inputParams.setValue("mensaje", "*La oferta no esta aprobada*");
				System.out.println(inputParams.getValue("mensaje"));
				return rc;
			}
			
			String sqlOferta   = getSQL(getResource("select-oferta.sql"),inputParams);
			Recordset rsOferta = db.get(sqlOferta);
						
			if(rsOferta.getRecordCount()>0)
			{
				rsOferta.first();
							
				//INYECCION DE VALORES AL VALIDATOR 
				
				if(rsOferta.getString("smn_item_compras_id") != null)
					inputParams.setValue("smn_item_compras_id", rsOferta.getInt("smn_item_compras_id"));
				else
					inputParams.setValue("smn_item_compras_id", 0);
				
				if(rsOferta.getString("smn_servicios_compras_id") != null)
					inputParams.setValue("smn_servicios_compras_id", rsOferta.getInt("smn_servicios_compras_id"));
				else
					inputParams.setValue("smn_servicios_compras_id", 0);
				
				if(rsOferta.getString("smn_activo_fijo_compras_id") != null)
					inputParams.setValue("smn_activo_fijo_compras_id", rsOferta.getInt("smn_activo_fijo_compras_id"));
				else
					inputParams.setValue("smn_activo_fijo_compras_id", 0);
				
				if(rsOferta.getString("smn_proveedor_id") != null)
				{
					inputParams.setValue("smn_proveedor_id", rsOferta.getInt("smn_proveedor_id"));
				}
				else
				{
					rc = 1;
					inputParams.setValue("mensaje","*no existe un proveedor relacionado con la oferta*");
					System.out.println(inputParams.getValue("mensaje"));
					return rc;
				}
				
				String sql = getSQL(getResource("select-cond_pago.sql"),inputParams);
				Recordset rsCond_pago = db.get(sql);
				
				if(rsCond_pago.getRecordCount() > 0)
				{
					rsCond_pago.first();
					if(rsCond_pago.getString("aux_cond_pago_rf")!=null)
						inputParams.setValue("smn_condicion_financiera_rf", rsCond_pago.getInt("aux_cond_pago_rf"));
				}
				else
				{
					rc = 1;
					inputParams.setValue("mensaje","No se encontro la condicion de pago asociado al proveedor = "+inputParams.getValue("smn_proveedor_id"));
					System.out.println(inputParams.getValue("mensaje"));
					return rc;
				}			
				
				inputParams.setValue("ofe_aplica_anticipo", rsOferta.getString("ofe_aplica_anticipo"));
									
				if(rsOferta.getString("ofe_porcentaje_anticipo") != null)
					inputParams.setValue("ofe_porcentaje_anticipo", rsOferta.getDouble("ofe_porcentaje_anticipo"));
				else
					inputParams.setValue("ofe_porcentaje_anticipo", 0.0);
									
				if(rsOferta.getString("ofe_monto_anticipo") != null)
					inputParams.setValue("ofe_monto_anticipo", rsOferta.getDouble("ofe_monto_anticipo"));
				else
					inputParams.setValue("ofe_monto_anticipo", 0.0);
					
				String sqlRequisicion   = getSQL(getResource("select-requisicion.sql"),inputParams);
				Recordset rsRequisicion = db.get(sqlRequisicion);
				
				if(rsRequisicion.getRecordCount()>0)
				{
					rsRequisicion.first();
					inputParams.setValue("smn_requisicion_cabecera_id", rsRequisicion.getInt("smn_requisicion_cabecera_id"));
					inputParams.setValue("occ_descripcion", "Orden de compra proveniente de la requisicion "+rsRequisicion.getInt("req_numero"));
				}
				else
				{
					rc = 1;
					inputParams.setValue("mensaje","*no se encontro la requisicion relacionada a la oferta*");
					System.out.println(inputParams.getValue("mensaje"));
					return rc;
				}
				
				String sqlEntidad   = getSQL(getResource("select-entidad.sql"),inputParams);
				Recordset rsEntidad = db.get(sqlEntidad);
				
				if(rsEntidad.getRecordCount()>0)
				{
					rsEntidad.first();
					inputParams.setValue("smn_entidad_id", rsEntidad.getInt("smn_entidad_id"));
				}
				else
				{
					rc = 1;
					inputParams.setValue("mensaje","*no se encontro la entidad*");
					System.out.println(inputParams.getValue("mensaje"));
					return rc;
				}
				
				String sqlSucursal   = getSQL(getResource("select-sucursal.sql"),inputParams);
				Recordset rsSucursal = db.get(sqlSucursal);
				
				if(rsSucursal.getRecordCount()>0)
				{
					rsSucursal.first();
					inputParams.setValue("smn_sucursal_id", rsSucursal.getInt("smn_sucursal_id"));
				}
				else
				{
					rc = 1;
					inputParams.setValue("mensaje","*no se encontro la sucursal*");
					System.out.println(inputParams.getValue("mensaje"));
					return rc;
				}
				
				String sqlDocumento   = getSQL(getResource("select-documento.sql"),inputParams);
				Recordset rsDocumento = db.get(sqlDocumento);
				
				if(rsDocumento.getRecordCount()>0) 		
				{
					rsDocumento.first();
					inputParams.setValue("smn_documento_id", rsDocumento.getInt("smn_documentos_id"));
				}
				else
				{
					rc = 1;
					inputParams.setValue("mensaje","*no se encontro el documento*");
					System.out.println(inputParams.getValue("mensaje"));
					return rc;
				}
				
				String sqlAuxiliar   = getSQL(getResource("select-auxiliar.sql"),inputParams);
				Recordset rsAuxiliar = db.get(sqlAuxiliar);
				
				if(rsAuxiliar.getRecordCount()>0)
				{
					rsAuxiliar.first();
					inputParams.setValue("smn_auxiliar_rf", rsAuxiliar.getInt("smn_auxiliar_rf"));
				}
				else
				{
					rc = 1;
					inputParams.setValue("mensaje","*no se encontro el auxiliar*");
					System.out.println(inputParams.getValue("mensaje"));
					return rc;
				}
				
				String sqlForma_pago   = getSQL(getResource("select-forma_pago.sql"),inputParams);
				Recordset rsForma_pago = db.get(sqlForma_pago);
				
				if(rsForma_pago.getRecordCount()>0)
				{
					rsForma_pago.first();
					inputParams.setValue("smn_forma_pago_rf", rsForma_pago.getInt("smn_forma_pago_rf"));
				}
				else
				{
					rc = 1;
					inputParams.setValue("mensaje","*no se encontro la forma de pago*");
					System.out.println(inputParams.getValue("mensaje"));
					return rc;
				}
				
				//----------------------------------------------------------------------
				
				String sqlCabecera_oc    = getSQL(getResource("insert-orden_compra_cabecera.sql"),inputParams);
				Recordset rsCabecera_oc  = db.get(sqlCabecera_oc);
				
				if(rsCabecera_oc.getRecordCount()>0)
				{
					rsCabecera_oc.first();
					
					inputParams.setValue("smn_orden_compra_cabecera_id", rsCabecera_oc.getInt("smn_orden_compra_cabecera_id"));
					
					String sqlLinea   = getSQL(getResource("select-linea.sql"),inputParams);
					Recordset rsLinea  = db.get(sqlLinea);
					
					if(rsLinea.getRecordCount()>0)
					{
						rsLinea.first();
						inputParams.setValue("smn_linea_id", rsLinea.getInt("smn_lineas_id"));
					}
					else
					{
						rc = 1;
						inputParams.setValue("mensaje","*no se encontro la linea*");
						System.out.println(inputParams.getValue("mensaje"));
						return rc;
					}
					
					String sqlCantidadPedida   = getSQL(getResource("select-cantidad_pedida.sql"),inputParams);
					Recordset rsCantidadPedida  = db.get(sqlCantidadPedida);
					
					if(rsCantidadPedida.getRecordCount()>0)
					{
						rsCantidadPedida.first();
						inputParams.setValue("ocd_cantidad_pedida", rsCantidadPedida.getDouble("rss_cantidad"));
					}
					else
					{
						rc = 1;
						inputParams.setValue("mensaje","*no se encontro la cantidad pedida*");
						System.out.println(inputParams.getValue("mensaje"));
						return rc;
					}
					
					if(rsOferta.getString("ofe_cantidad") != null)
						inputParams.setValue("ofe_cantidad", rsOferta.getDouble("ofe_cantidad"));
					else
						inputParams.setValue("ofe_cantidad", 0.0);
					
					String sqlUnidad_medida   = getSQL(getResource("select-unidad_medida.sql"),inputParams);
					Recordset rsUnidad_medida  = db.get(sqlUnidad_medida);
					
					if(rsUnidad_medida.getRecordCount()>0)
					{
						rsUnidad_medida.first();
						inputParams.setValue("smn_unidad_medida_rf", rsUnidad_medida.getInt("smn_unidad_medida_compra_rf"));
					}
					else
					{
						rc = 1;
						inputParams.setValue("mensaje","*no se encontro la unidad de medida*");
						System.out.println(inputParams.getValue("mensaje"));
						return rc;
					}
					
					if(rsOferta.getString("ofe_precio_ml") != null)
					{
						inputParams.setValue("ofe_precio_ml", rsOferta.getDouble("ofe_precio_ml"));
					}
					else
					{
						rc = 1;
						inputParams.setValue("mensaje","*no se encontro el precio de la oferta*");
						System.out.println(inputParams.getValue("mensaje"));
						return rc;
					}
					
					if(rsOferta.getString("ofe_monto_ml") != null)
					{
						inputParams.setValue("ofe_monto_ml", rsOferta.getDouble("ofe_monto_ml"));
					}
					else
					{
						rc = 1;
						inputParams.setValue("mensaje","*no se encontro el monto de la oferta*");
						System.out.println(inputParams.getValue("mensaje"));
						return rc;
					}
					
					if(rsOferta.getString("ofe_moneda_id") != null)
						inputParams.setValue("ofe_moneda_id", rsOferta.getInt("ofe_moneda_id"));
					else
						inputParams.setValue("ofe_moneda_id", 0);
					
					if(rsOferta.getString("ofe_tasa") != null)
						inputParams.setValue("ofe_tasa", rsOferta.getInt("ofe_tasa"));
					else
						inputParams.setValue("ofe_tasa", 0);
					
					if(rsOferta.getString("ofe_precio_ma") != null)
						inputParams.setValue("ofe_precio_ma", rsOferta.getDouble("ofe_precio_ma"));
					else
						inputParams.setValue("ofe_precio_ma", 0.0);
					
					if(rsOferta.getString("ofe_monto_ma") != null)
						inputParams.setValue("ofe_monto_ma", rsOferta.getDouble("ofe_monto_ma"));
					else
						inputParams.setValue("ofe_monto_ma", 0.0);
					
					String sql_ocd    = getSQL(getResource("insert-orden_compra_detalle.sql"),inputParams);
					Recordset rs_ocd  = db.get(sql_ocd); //registrar orden de compra detalle
					
					if(rs_ocd.getRecordCount()>0)
					{
						rs_ocd.first();
						
						inputParams.setValue("smn_orden_compra_detalle_id", rs_ocd.getInt("smn_orden_compra_detalle_id"));
						
						String sqlOferta_fecha    = getSQL(getResource("select-oferta_fecha_entrega.sql"),inputParams);
						Recordset rsOferta_fecha  = db.get(sqlOferta_fecha); //consulta la fecha de entrega de la oferta
						
						if(rsOferta_fecha.getRecordCount()>0)
						{
							rsOferta_fecha.first();
							//INYECCION DE VALORES AL VALIDATOR PROVENIENTES DE LA TABLA smn_compras.smn_oferta_f_entrega
							
							if(rsOferta_fecha.getString("ofe_consecutivo") != null)
							{
								inputParams.setValue("ofe_consecutivo", rsOferta_fecha.getInt("ofe_consecutivo"));
							}
							else
							{
								rc = 1;
								inputParams.setValue("mensaje","*el consecutivo de la fecha_entrega no se encontro*");
								System.out.println(inputParams.getValue("mensaje"));
								return rc;
							}
							
							if(rsOferta_fecha.getString("ofe_cantidad") != null)
							{
								inputParams.setValue("ofe_cantidad", rsOferta_fecha.getDouble("ofe_cantidad"));
							}
							else
							{
								rc = 1;
								inputParams.setValue("mensaje","*la cantidad de la fecha_entrega no se encontro*");
								System.out.println(inputParams.getValue("mensaje"));
								return rc;
							}
							
							if(rsOferta_fecha.getString("ofe_fecha_entrega") != null)
							{
								inputParams.setValue("ofe_fecha_entrega", rsOferta_fecha.getDate("ofe_fecha_entrega"));
							}
							else
							{
								rc = 1;
								inputParams.setValue("mensaje","*la fecha_entrega no se encontro*");
								System.out.println(inputParams.getValue("mensaje"));
								return rc;
							}
							
							if(rsOferta_fecha.getString("ofe_status") != null)
							{
								inputParams.setValue("ofe_estatus", rsOferta_fecha.getString("ofe_status"));
							}
							else
							{
								rc = 1;
								inputParams.setValue("mensaje","*el estatus de la fecha_entrega no se encontro*");
								System.out.println(inputParams.getValue("mensaje"));
								return rc;
							}
							
							//---------------------------------------------------------------------------------------
							
							String sqlFecha_ocd    = getSQL(getResource("insert-fecha_entrega_ocd.sql"),inputParams);
							Recordset rsFecha_ocd  = db.get(sqlFecha_ocd); //registra la fecha de entrega de la orden de compra detalle
							 
							if(rsFecha_ocd.getRecordCount()>0)
							{
								rc = 0;
							}
							else
							{
								rc = 1;
								inputParams.setValue("mensaje","*no se registro la fecha de entrega*");
							}
						}
							
						sql_ocd  = getSQL(getResource("select-ocd.sql"),inputParams);
						rs_ocd   = db.get(sql_ocd); //consulta el detalle de la orden de compra.
								
						double monto_base = 0.0;
								
						if(rs_ocd.getRecordCount()>0)
						{
							rs_ocd.first();
									
							if(rs_ocd.getString("ocd_monto_bruto_ml") != null)
							{
								inputParams.setValue("oci_monto_base_ml", rs_ocd.getDouble("ocd_monto_bruto_ml"));
								monto_base = rs_ocd.getDouble("ocd_monto_bruto_ml");
							}
							else
							{
								rc = 1;
								inputParams.setValue("mensaje","*el monto no se encontro*");
								System.out.println(inputParams.getValue("mensaje"));
								return rc;
							}
							
							if(rs_ocd.getString("smn_moneda_rf") != null)
								inputParams.setValue("smn_moneda", rs_ocd.getInt("smn_moneda_rf"));
							else
								inputParams.setValue("smn_moneda", 0);
							
							if(rs_ocd.getString("smn_tasa_rf") != null)
								inputParams.setValue("smn_tasa", rs_ocd.getInt("smn_tasa_rf"));
							else
								inputParams.setValue("smn_tasa", 0);
						}
						else
						{
							rc = 1;
							inputParams.setValue("mensaje","*ocurrio un error, no se encontro el detalle creado*");
							System.out.println(inputParams.getValue("mensaje"));
							return rc;
						}			
								
						String sqlCodImpuesto    = getSQL(getResource("select-cod_impuesto.sql"),inputParams);
						Recordset rsCodImpuesto  = db.get(sqlCodImpuesto); //consulta el impuesto deduccion oferta.
						
						if(rsCodImpuesto.getRecordCount()>0)
						{	
							double porcentaje_base = 0.0;
							
							while(rsCodImpuesto.next() != false)
							{
								if(rsCodImpuesto.getString("smn_codigo_impuesto_rf") != null)
								{
									inputParams.setValue("smn_cod_impuesto_deduc_rf", rsCodImpuesto.getInt("smn_codigo_impuesto_rf"));
								}
								else
								{
									rc = 1;
									inputParams.setValue("mensaje","*el codigo de impuesto deduccion esta vacio*");
									System.out.println(inputParams.getValue("mensaje"));
									return rc;
								}
								
								String sqlCodigo_impuesto   = getSQL(getResource("select-codigo_impuesto.sql"),inputParams);
								Recordset rsCodigo_impuesto = db.get(sqlCodigo_impuesto); //consulta el codigo impuesto.
								
								if(rsCodigo_impuesto.getRecordCount()>0)
								{
									rsCodigo_impuesto.first();
									if(rsCodigo_impuesto.getString("imp_porcentaje_base") != null)
									{
										inputParams.setValue("imp_porcentaje_base", rsCodigo_impuesto.getDouble("imp_porcentaje_base"));
										porcentaje_base = rsCodigo_impuesto.getDouble("imp_porcentaje_base");
									}
									else
									{
										rc = 1;
										inputParams.setValue("mensaje","*el porcentaje base no se encontro*");
										System.out.println(inputParams.getValue("mensaje"));
										return rc;
									}
									
									if(rsCodigo_impuesto.getString("imp_ui_sustraendo") != null)
										inputParams.setValue("imp_ui_sustraendo", rsCodigo_impuesto.getDouble("imp_ui_sustraendo"));
									else
										inputParams.setValue("imp_ui_sustraendo", 0.0);
									
									if(rsCodigo_impuesto.getString("imp_tipo_codigo") != null)
									{
										inputParams.setValue("imp_tipo_codigo", rsCodigo_impuesto.getString("imp_tipo_codigo"));
									}
									else
									{
										rc = 1;
										inputParams.setValue("mensaje","*El tipo de codigo no se encontro*");
										System.out.println(inputParams.getValue("mensaje"));
										return rc;
									}
								}
								else
								{
									rc = 1;
									inputParams.setValue("mensaje","*ocurrio un error, no se encontro el codigo de impuesto*");
									System.out.println(inputParams.getValue("mensaje"));
									return rc;
								}
								
								String sqlTasa   = getSQL(getResource("select-tasa.sql"),inputParams);
								Recordset rsTasa = db.get(sqlTasa); //consulta el impuesto deduccion oferta.
								
								double tasa;
								
								if(rsTasa.getRecordCount()>0)
								{
									rsTasa.first();
									tasa = rsTasa.getDouble("tca_tasa_cambio");
									inputParams.setValue("oci_monto_impuesto_ma",(monto_base*porcentaje_base)/tasa);
								}
								else
								{
									rc = 1;
									inputParams.setValue("mensaje","*ocurrio un error, no se encontro la tasa de cambio*");
									System.out.println(inputParams.getValue("mensaje"));
									return rc;
								}
								
								inputParams.setValue("oci_monto_impuesto_ml",monto_base*porcentaje_base);
								
								String sqlImpuesto   = getSQL(getResource("insert-orden_compra_impuesto.sql"),inputParams);
								Recordset rsImpuesto = db.get(sqlImpuesto);
								
								if(rsImpuesto.getRecordCount()<1)
								{
									rc = 1;
									inputParams.setValue("mensaje","*ocurrio un error, al registrar los impuestos de la orden de compra*");
									System.out.println(inputParams.getValue("mensaje"));
									return rc;
								}
							} //endwhile
						}		
								
						String sqlDescuento_oferta   = getSQL(getResource("select-descuento_oferta.sql"),inputParams);
						Recordset rsDescuento_oferta = db.get(sqlDescuento_oferta); //consulta los descuentos de la oferta
						
						if(rsDescuento_oferta.getRecordCount()>0)
						{
							while(rsDescuento_oferta.next() != false)
							{
								if(rsDescuento_oferta.getString("rio_cod_descuento") != null)
								{
									inputParams.setValue("smn_codigo_descuento_rf", rsDescuento_oferta.getInt("rio_cod_descuento"));
								}
								else
								{
									rc = 1;
									inputParams.setValue("mensaje","*El codigo del descuento no se encontro*");
									System.out.println(inputParams.getValue("mensaje"));
									return rc;
								}
								
								if(rsDescuento_oferta.getString("rio_monto_base_ml") != null)
								{
									inputParams.setValue("ocd_monto_base", rsDescuento_oferta.getDouble("rio_monto_base_ml"));
								}
								else
								{
									rc = 1;
									inputParams.setValue("mensaje","*El monto base no se encontro*");
									System.out.println(inputParams.getValue("mensaje"));
									return rc;
								}
								
								if(rsDescuento_oferta.getString("rio_porcentaje_descuento") != null)
								{
									inputParams.setValue("ocd_porcentaje", rsDescuento_oferta.getDouble("rio_porcentaje_descuento"));
								}
								else
								{
									rc = 1;
									inputParams.setValue("mensaje","*El porcentaje del descuento no se encontro*");
									System.out.println(inputParams.getValue("mensaje"));
									return rc;
								}
								
								if(rsDescuento_oferta.getString("rio_monto_descuento_ml") != null)
								{
									inputParams.setValue("ocd_monto_descuento", rsDescuento_oferta.getDouble("rio_monto_descuento_ml"));
								}
								else
								{
									rc = 1;
									inputParams.setValue("mensaje","*El monto del descuento no se encontro*");
									System.out.println(inputParams.getValue("mensaje"));
									return rc;
								}
								
								if(rsDescuento_oferta.getString("rio_moneda_rf") != null)
									inputParams.setValue("rio_moneda_rf", rsDescuento_oferta.getInt("rio_moneda_rf"));
								else
									inputParams.setValue("rio_moneda_rf", 0);
								
								if(rsDescuento_oferta.getString("rio_tasa_rf") != null)
									inputParams.setValue("rio_tasa_rf", rsDescuento_oferta.getInt("rio_tasa_rf"));
								else
									inputParams.setValue("rio_tasa_rf", 0);
								
								if(rsDescuento_oferta.getString("rio_monto_base_ma") != null)
									inputParams.setValue("ocd_monto_base_ma", rsDescuento_oferta.getDouble("rio_monto_base_ma"));
								else
									inputParams.setValue("ocd_monto_base_ma", 0.0);
								
								if(rsDescuento_oferta.getString("rio_monto_descuento_ma") != null)
									inputParams.setValue("ocd_monto_descuento_ma", rsDescuento_oferta.getDouble("rio_monto_descuento_ma"));
								else
									inputParams.setValue("ocd_monto_descuento_ma", 0.0);
								
								String sqlDescuento_oc   = getSQL(getResource("insert-descuento_oc.sql"),inputParams);
								Recordset rsDescuento_oc = db.get(sqlDescuento_oc); //consulta los descuentos de la oferta
								
								if(rsDescuento_oc.getRecordCount()<1)
								{
									rc = 1;
									inputParams.setValue("mensaje","*Error al registrar el descuento de la orden de compra*");
									System.out.println(inputParams.getValue("mensaje"));
									return rc;
								}
							}//end while
						}		
								
						String sqlImpuestos   = getSQL(getResource("select-impuestos.sql"),inputParams);
						Recordset rsImpuestos = db.get(sqlImpuestos); //consulta los impuestos
						
						if(rsImpuestos.getRecordCount()>0)
						{
							rsImpuestos.first();
							
							if(rsImpuestos.getString("monto_impuesto_ml") != null)
								inputParams.setValue("oci_monto_impuesto_ml", rsImpuestos.getDouble("monto_impuesto_ml"));
							else
								inputParams.setValue("oci_monto_impuesto_ml", 0.0);
							
							if(rsImpuestos.getString("monto_impuesto_ma") != null)
								inputParams.setValue("oci_monto_impuesto_ma", rsImpuestos.getDouble("monto_impuesto_ma"));
							else
								inputParams.setValue("oci_monto_impuesto_ma", 0.0);
						}
						
						String sqlDescuentos   = getSQL(getResource("select-descuentos.sql"),inputParams);
						Recordset rsDescuentos = db.get(sqlDescuentos); //consulta los descuentos
						
						if(rsDescuentos.getRecordCount()>0)
						{
							rsDescuentos.first();
							
							if(rsDescuentos.getString("monto_descuento_ml") != null)
								inputParams.setValue("ocd_monto_descuento_ml", rsDescuentos.getDouble("monto_descuento_ml"));
							else
								inputParams.setValue("ocd_monto_descuento_ml", 0.0);
							
							if(rsDescuentos.getString("monto_descuento_ma") != null)
								inputParams.setValue("ocd_monto_descuento_ma", rsDescuentos.getDouble("monto_descuento_ma"));
							else
								inputParams.setValue("ocd_monto_descuento_ma", 0.0);
						}		
								
						double monto_neto_ml = inputParams.getDouble("ofe_monto_ml")+inputParams.getDouble("oci_monto_impuesto_ml")-inputParams.getDouble("ocd_monto_descuento_ml");
						double monto_neto_ma = inputParams.getDouble("ofe_monto_ma")+inputParams.getDouble("oci_monto_impuesto_ma")-inputParams.getDouble("ocd_monto_descuento_ma");
						
						inputParams.setValue("ocd_monto_neto_ml", monto_neto_ml);
						inputParams.setValue("ocd_monto_neto_ma", monto_neto_ma);
						
						String sqlActualizar_ocd   = getSQL(getResource("update-actualizar_ocd.sql"),inputParams);
						Recordset rsActualizar_ocd = db.get(sqlActualizar_ocd); //Actualiza los campos calculados del detalle de la orden de compra
						
						if(rsActualizar_ocd.getRecordCount()>0)
						{
							String sqlActualizar_occ   = getSQL(getResource("update-actualizar_occ.sql"),inputParams);
							Recordset rsActualizar_occ = db.get(sqlActualizar_occ); //Actualiza los campos calculados de la orden de compra
							
							if(rsActualizar_occ.getRecordCount()>0)
							{
								String sqlUpdate = getSQL(getResource("update-oferta.sql"),inputParams);
								Recordset rsUpdate = db.get(sqlUpdate); //actualiza el estatus de la oferta a generada.
								
								if(rsUpdate.getRecordCount()>0)
								{
									rc = 0;
									inputParams.setValue("mensaje","*Oferta generada exitosamente*");
								}
								else
								{
									rc = 1;
									inputParams.setValue("mensaje","*ocurrio un error al generar la oferta*");
								}	
							}
							else
							{
								rc = 1;
								inputParams.setValue("mensaje","*ocurrio un error al actualizar los campos calculados de la orden de compra*");
							}
						}
						else
						{
							rc = 1;
							inputParams.setValue("mensaje","*ocurrio un error al actualizar los campos calculados de la orden de compra detalle*");
						}		
					}
					else
					{
						rc = 1;
						inputParams.setValue("mensaje","*no se registro el detalle de la orden de compra*");
					}
				}
				else
				{
					rc = 1;
					inputParams.setValue("mensaje","*no se registro la orden de compra*");
				}
			}
			else
			{
				rc = 1;
				inputParams.setValue("mensaje","*no se encontro la oferta*");
			}	
		}
		catch(Throwable e)
		{
			conn.rollback();
			throw e;
		}
		
		finally
		{		
			if(rc==0)
				conn.commit();
			else
				conn.rollback();
			
			if(conn!=null)
				conn.close();
		}
		
		if(rc>0)
			System.out.print("** ERROR **: " + inputParams.getValue("mensaje"));
		
		return rc;
	}
}

package proceso;
import dinamica.*;
import java.sql.*;
import javax.sql.DataSource;

public class AprobarOrdenCompra extends GenericTransaction 
{
	public int service(Recordset inputParams) throws Throwable
	{
		int rc = 0;	//variable a retornar.
		String mensaje = "";	
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
			
			String sqlOrdenCompra   = getSQL(getResource("select-ordenCompra.sql"),inputParams);
			Recordset rsOrdenCompra = db.get(sqlOrdenCompra); //consulta la orden de compra cabecera con el id proveniente del front.
				
			if(rsOrdenCompra.getRecordCount()>0)
			{
				rsOrdenCompra.first();
				
				if(rsOrdenCompra.getString("smn_entidad_rf") != null)
					inputParams.setValue("smn_entidad_rf", rsOrdenCompra.getInt("smn_entidad_rf"));
				else
					inputParams.setValue("smn_entidad_rf", 0);
					
				String sqlRol   = getSQL(getResource("select-rol.sql"),inputParams);
				Recordset rsRol = db.get(sqlRol); //consulta la orden de compra cabecera con el id proveniente del front.
				
				if(rsRol.getRecordCount()>0)
				{
					//**INYECCION DE VALORES EN EL VALIDATOR PROVENIENTES DE LA TABLA: smn_orden_compra_cabecera
					
						if(rsOrdenCompra.getString("smn_documento_id") != null)
							inputParams.setValue("smn_documento_id", rsOrdenCompra.getInt("smn_documento_id"));
						else
							inputParams.setValue("smn_documento_id", 0);
						
						if(rsOrdenCompra.getString("occ_orden_compra_numero") != null)
							inputParams.setValue("occ_orden_compra_numero", rsOrdenCompra.getInt("occ_orden_compra_numero"));
						else
							inputParams.setValue("occ_orden_compra_numero", 0);
						
						if(rsOrdenCompra.getString("smn_proveedor_id") != null)
							inputParams.setValue("smn_proveedor_id", rsOrdenCompra.getInt("smn_proveedor_id"));
						else
							inputParams.setValue("smn_proveedor_id", 0);
						
						if(rsOrdenCompra.getString("smn_sucursal_rf") != null)
							inputParams.setValue("smn_sucursal_rf", rsOrdenCompra.getInt("smn_sucursal_rf"));
						else
							inputParams.setValue("smn_sucursal_rf", 0);
						
						if(rsOrdenCompra.getString("occ_monto_ml") != null)
							inputParams.setValue("occ_monto_ml", rsOrdenCompra.getDouble("occ_monto_ml"));
						else
							inputParams.setValue("occ_monto_ml", 0.0);
						
						if(inputParams.getDouble("occ_monto_ml") <= 0.0)
						{
							mensaje = "**error**, La orden de compra no tiene monto";
							System.out.println(mensaje);
							return 1;
						}
						
						if(rsOrdenCompra.getString("occ_monto_ma") != null)
							inputParams.setValue("occ_monto_ma", rsOrdenCompra.getDouble("occ_monto_ma"));
						else
							inputParams.setValue("occ_monto_ma", 0.0);
						
						if(rsOrdenCompra.getString("occ_monto_desc_rete_ml") != null)
							inputParams.setValue("occ_monto_desc_rete_ml", rsOrdenCompra.getDouble("occ_monto_desc_rete_ml"));
						else
							inputParams.setValue("occ_monto_desc_rete_ml", 0.0);
						
						if(rsOrdenCompra.getString("occ_monto_impuesto_ml") != null)
							inputParams.setValue("occ_monto_impuesto_ml", rsOrdenCompra.getDouble("occ_monto_impuesto_ml"));
						else
							inputParams.setValue("occ_monto_impuesto_ml", 0.0);
						
						if(rsOrdenCompra.getString("occ_monto_impuesto_ma") != null)
							inputParams.setValue("occ_monto_impuesto_ma", rsOrdenCompra.getDouble("occ_monto_impuesto_ma"));
						else
							inputParams.setValue("occ_monto_impuesto_ma", 0.0);
						
						if(rsOrdenCompra.getString("occ_monto_neto_ml") != null)
							inputParams.setValue("occ_monto_neto_ml", rsOrdenCompra.getDouble("occ_monto_neto_ml"));
						else
							inputParams.setValue("occ_monto_neto_ml", 0.0);
						
						if(rsOrdenCompra.getString("smn_moneda_rf") != null)
							inputParams.setValue("smn_moneda_rf", rsOrdenCompra.getInt("smn_moneda_rf"));
						else
							inputParams.setValue("smn_moneda_rf", 0);
						
						if(rsOrdenCompra.getString("smn_tasa_rf") != null)
							inputParams.setValue("smn_tasa_rf", rsOrdenCompra.getInt("smn_tasa_rf"));
						else
							inputParams.setValue("smn_tasa_rf", 0);
						
						if(rsOrdenCompra.getString("occ_fecha_orde_compra") != null)
							inputParams.setValue("occ_fecha_orde_compra", rsOrdenCompra.getDate("occ_fecha_orde_compra"));
						else
							inputParams.setValue("occ_fecha_orde_compra", 0);
						
						String sqlModulo   = getSQL(getResource("select-modulo.sql"),inputParams);
						Recordset rsModulo = db.get(sqlModulo); //consulta el modulo.
						
						if(rsModulo.getRecordCount()>0)
						{
							rsModulo.first();
							inputParams.setValue("smn_modulos_id", rsModulo.getInt("smn_modulos_id"));
						}
						else
						{
							mensaje = "**error**, la orden de compra no contiene smn_modulo_id";
							System.out.println(mensaje);
							return 1; //valor null.
						}
						
						String sqlDoc   = getSQL(getResource("select-documento.sql"),inputParams);
						Recordset rsDoc = db.get(sqlDoc); //consulta el documento.
						
						if(rsDoc.getRecordCount()>0)
						{
							rsDoc.first();
							inputParams.setValue("smn_documentos_generales_id", rsDoc.getInt("smn_documento_id"));
						}
						else
						{
							mensaje = "**error**, la orden de compra no contiene smn_documentos_generales_id";
							System.out.println(mensaje);
							return 1; //valor null.
						}
						
						String sqlSec  = getSQL(getResource("select-secuencia.sql"),inputParams);
						Recordset rsSec = db.get(sqlSec); //consulta la secuencia.
						
						if(rsSec.getRecordCount()>0)
						{
							rsSec.first();
							if(rsSec.getString("secuencia") != null)
								inputParams.setValue("secuencia", rsSec.getInt("secuencia"));
							else
								inputParams.setValue("secuencia", 1);
						}
						else
							inputParams.setValue("secuencia", 1);
						
						
						String sqlAlmacen  = getSQL(getResource("select-almacen.sql"),inputParams);
						Recordset rsAlmacen = db.get(sqlAlmacen); //consulta el almacen.
						
						if(rsAlmacen.getRecordCount()>0)
						{
							rsAlmacen.first();
							inputParams.setValue("smn_almacen_rf", rsAlmacen.getInt("smn_almacen_rf"));
						}
						else
						{
							mensaje = "**error**, la orden de compra no contiene smn_almacen_rf";
							System.out.println(mensaje);
							return 1; //valor null.
						}
						
						String sqlTipo_doc  = getSQL(getResource("select-tipo_documento.sql"),inputParams);
						Recordset rsTipo_doc = db.get(sqlTipo_doc); //consulta el tipo de documento.
						
						if(rsTipo_doc.getRecordCount()>0)
						{
							rsTipo_doc.first();
							inputParams.setValue("smn_tipo_documento_id", rsTipo_doc.getInt("smn_tipo_documento_id"));
						}
						
					//*********************	
					
					int mca = registrarMovimientoCabecera(conn,inputParams); //registra movimiento cabecera.
						
					if(mca == 0)
					{
						String sqlUpdateSec   = getSQL(getResource("update-documento_secuencia.sql"),inputParams); 
						db.get(sqlUpdateSec); //actualiza la secuencia el documento.
						
						String sqlOrdenCompraDetalle = getSQL(getResource("select-orden_compra_detalle.sql"),inputParams);
						Recordset rsOrdenCompraDetalle = db.get(sqlOrdenCompraDetalle); //consulta los detalles de la orden de compra.
							
						if(rsOrdenCompraDetalle.getRecordCount()>0)
						{
							while(rsOrdenCompraDetalle.next() != false)
							{
								//** INYECCION DE VALORES AL VALIDATOR PROVENIENTES DE: smn_orden_compra_detalle.
										
									inputParams.setValue("smn_orden_compra_detalle_id", rsOrdenCompraDetalle.getInt("smn_orden_compra_detalle_id"));
									
									if(rsOrdenCompraDetalle.getString("smn_item_rf") != null)
										inputParams.setValue("smn_item_id", rsOrdenCompraDetalle.getInt("smn_item_rf"));
									else
										inputParams.setValue("smn_item_id", 0);
									
									String sqlitem = getSQL(getResource("select-item.sql"),inputParams);
									Recordset rsitem = db.get(sqlitem); //consulta que el item este activo y disponible.
									
									if(rsitem.getRecordCount()<1)
									{
										mensaje = "**error**, no se encontro el item o esta inactivo";
										System.out.println(mensaje);
										return 1; //no se encontro el item o esta inactivo. 
									}
									
									if(rsOrdenCompraDetalle.getString("ocd_cantidad_pedida") != null)
										inputParams.setValue("ocd_cantidad_pedida", rsOrdenCompraDetalle.getDouble("ocd_cantidad_pedida"));
									else
										inputParams.setValue("ocd_cantidad_pedida", 0.0);
									
									inputParams.setValue("ocd_descripcion", rsOrdenCompraDetalle.getString("ocd_descripcion"));
										
									if(rsOrdenCompraDetalle.getString("smn_unidad_medida_rf") != null)
										inputParams.setValue("smn_unidad_medida_rf", rsOrdenCompraDetalle.getInt("smn_unidad_medida_rf"));
									else
										inputParams.setValue("smn_unidad_medida_rf", 0);
										
									if(rsOrdenCompraDetalle.getString("ocd_costo_ml") != null)
										inputParams.setValue("ocd_costo_ml", rsOrdenCompraDetalle.getDouble("ocd_costo_ml"));
									else
										inputParams.setValue("ocd_costo_ml", 0.0);
									
									if(rsOrdenCompraDetalle.getString("smn_tasa_rf") != null)
										inputParams.setValue("smn_tasa_rf", rsOrdenCompraDetalle.getInt("smn_tasa_rf"));
									else
										inputParams.setValue("smn_tasa_rf", 0);
									
									if(rsOrdenCompraDetalle.getString("smn_moneda_rf") != null)
										inputParams.setValue("smn_moneda_rf", rsOrdenCompraDetalle.getInt("smn_moneda_rf"));
									else
										inputParams.setValue("smn_moneda_rf", 0);
									
									if(rsOrdenCompraDetalle.getString("ocd_costo_ma") != null)
										inputParams.setValue("ocd_costo_ma", rsOrdenCompraDetalle.getDouble("ocd_costo_ma"));
									else
										inputParams.setValue("ocd_costo_ma", 0.0);
									
									if(rsOrdenCompraDetalle.getString("ocd_monto_bruto_ml") != null)
										inputParams.setValue("ocd_monto_bruto_ml", rsOrdenCompraDetalle.getDouble("ocd_monto_bruto_ml"));
									else
										inputParams.setValue("ocd_monto_bruto_ml", 0.0);
										
									if(rsOrdenCompraDetalle.getString("ocd_monto_bruto_ma") != null)
										inputParams.setValue("ocd_monto_bruto_ma", rsOrdenCompraDetalle.getDouble("ocd_monto_bruto_ma"));
									else
										inputParams.setValue("ocd_monto_bruto_ma", 0.0);
									
									if(rsOrdenCompraDetalle.getString("ocd_monto_desc_reten_ml") != null)
										inputParams.setValue("ocd_monto_desc_reten_ml", rsOrdenCompraDetalle.getDouble("ocd_monto_desc_reten_ml"));
									else
										inputParams.setValue("ocd_monto_desc_reten_ml", 0.0);
										
									if(rsOrdenCompraDetalle.getString("ocd_monto_desc_reten_ma") != null)
										inputParams.setValue("ocd_monto_desc_reten_ma", rsOrdenCompraDetalle.getDouble("ocd_monto_desc_reten_ma"));
									else
										inputParams.setValue("ocd_monto_desc_reten_ma", 0.0);
										
									if(rsOrdenCompraDetalle.getString("ocd_monto_impuesto_ml") != null)
										inputParams.setValue("ocd_monto_impuesto_ml", rsOrdenCompraDetalle.getDouble("ocd_monto_impuesto_ml"));
									else
										inputParams.setValue("ocd_monto_impuesto_ml", 0.0);
										
									if(rsOrdenCompraDetalle.getString("ocd_monto_impuesto_ma") != null)
										inputParams.setValue("ocd_monto_impuesto_ma", rsOrdenCompraDetalle.getDouble("ocd_monto_impuesto_ma"));
									else
										inputParams.setValue("ocd_monto_impuesto_ma", 0.0);
									
									if(rsOrdenCompraDetalle.getString("ocd_monto_neto_ml") != null)
										inputParams.setValue("ocd_monto_neto_ml", rsOrdenCompraDetalle.getDouble("ocd_monto_neto_ml"));
									else
										inputParams.setValue("ocd_monto_neto_ml", 0.0);
									
									if(rsOrdenCompraDetalle.getString("ocd_monto_neto_ma") != null)
										inputParams.setValue("ocd_monto_neto_ma", rsOrdenCompraDetalle.getDouble("ocd_monto_neto_ma"));
									else
										inputParams.setValue("ocd_monto_neto_ma", 0.0);
										
								//***************
										
								int mde = registrarMovimientoDetalle(conn,inputParams); //registra movimento detalle.
								
								if(mde == 0)
								{
									//** proceso de registrar movimiento detalle impuesto **
									
									String sqlOrdenCompraImpuesto   = getSQL(getResource("consultar-oci.sql"), inputParams);
									Recordset rsOrdenCompraImpuesto = db.get(sqlOrdenCompraImpuesto);
									
									if(rsOrdenCompraImpuesto.getRecordCount()>0)
									{
										rsOrdenCompraImpuesto.first();
										
										//** INYECCION DE VALORES AL VALIDATOR PROVENIENTES DE: smn_orden_compra_impuesto.
											
											if(rsOrdenCompraImpuesto.getString("smn_cod_impuesto_deduc_rf") != null)
												inputParams.setValue("smn_cod_impuesto_deduc_rf", rsOrdenCompraImpuesto.getInt("smn_cod_impuesto_deduc_rf"));
											else
												inputParams.setValue("smn_cod_impuesto_deduc_rf", 0);
												
											if(rsOrdenCompraImpuesto.getString("oci_monto_base_ml") != null)
												inputParams.setValue("oci_monto_base_ml", rsOrdenCompraImpuesto.getDouble("oci_monto_base_ml"));
											else
												inputParams.setValue("oci_monto_base_ml", 0.0);
												
											if(rsOrdenCompraImpuesto.getString("oci_porcentaje_impuesto") != null)
												inputParams.setValue("smn_porcentaje_impuesto_rf", rsOrdenCompraImpuesto.getInt("oci_porcentaje_impuesto"));
											else
												inputParams.setValue("smn_porcentaje_impuesto_rf", 0);
												
											if(rsOrdenCompraImpuesto.getString("oci_sustraendo_ml") != null)
												inputParams.setValue("oci_sustraendo_ml", rsOrdenCompraImpuesto.getDouble("oci_sustraendo_ml"));
											else
												inputParams.setValue("oci_sustraendo_ml", 0.0);
												
											if(rsOrdenCompraImpuesto.getString("smn_tipo_impuesto_rf") != null)
												inputParams.setValue("oci_tipo_impuesto_rf", rsOrdenCompraImpuesto.getString("smn_tipo_impuesto_rf"));
											else
												inputParams.setValue("oci_tipo_impuesto_rf", null);
												
											if(rsOrdenCompraImpuesto.getString("oci_monto_impuesto_ml") != null)
												inputParams.setValue("oci_monto_impuesto_ml", rsOrdenCompraImpuesto.getDouble("oci_monto_impuesto_ml"));
											else
												inputParams.setValue("oci_monto_impuesto_ml", 0.0);
											
											if(rsOrdenCompraImpuesto.getString("smn_moneda") != null)
												inputParams.setValue("smn_moneda", rsOrdenCompraImpuesto.getInt("smn_moneda"));
											else
												inputParams.setValue("smn_moneda", 0);
											
											if(rsOrdenCompraImpuesto.getString("smn_tasa") != null)
												inputParams.setValue("smn_tasa", rsOrdenCompraImpuesto.getInt("smn_tasa"));
											else
												inputParams.setValue("smn_tasa", 0);
												
											if(rsOrdenCompraImpuesto.getString("oci_monto_impuesto_ma") != null)
												inputParams.setValue("oci_monto_impuesto_ma", rsOrdenCompraImpuesto.getDouble("oci_monto_impuesto_ma"));
											else
												inputParams.setValue("oci_monto_impuesto_ma", 0.0);
												
										//****************
												
										rc = registrarMovimientoDetalleImpuesto(conn, inputParams); //registra movimiento detalle impuesto
									}
									
									//** proceso de registrar movimiento detalle descuentos y deducciones **
									
									String sqlOrdenCompraDesc_retenc   = getSQL(getResource("select-orden_compra_desc_retenc.sql"), inputParams);
									Recordset rsOrdenCompraDesc_retenc= db.get(sqlOrdenCompraDesc_retenc);
									
									if(rsOrdenCompraDesc_retenc.getRecordCount()>0)
									{
										rsOrdenCompraDesc_retenc.first();
										
										//** INYECCION DE VALORES AL VALIDATOR PROVENIENTES DE: smn_orden_compra_descuentos_deducciones.
											
											if(rsOrdenCompraDesc_retenc.getString("smn_codigo_descuento_rf") != null)
												inputParams.setValue("smn_codigo_descuento_rf", rsOrdenCompraDesc_retenc.getInt("smn_codigo_descuento_rf"));
											else
												inputParams.setValue("smn_codigo_descuento_rf", 0);
												
											if(rsOrdenCompraDesc_retenc.getString("ocd_monto_base") != null)
												inputParams.setValue("mdd_monto_base_ml", rsOrdenCompraDesc_retenc.getDouble("ocd_monto_base"));
											else
												inputParams.setValue("mdd_monto_base_ml", 0.0);
											
											if(rsOrdenCompraDesc_retenc.getString("ocd_porcentaje") != null)
												inputParams.setValue("smn_porcentaje_rf", rsOrdenCompraDesc_retenc.getDouble("ocd_porcentaje"));
											else
												inputParams.setValue("smn_porcentaje_rf", 0.0);
											
											if(rsOrdenCompraDesc_retenc.getString("ocd_monto_descuento") != null)
												inputParams.setValue("mdd_monto_descuento_ml", rsOrdenCompraDesc_retenc.getDouble("ocd_monto_descuento"));
											else
												inputParams.setValue("mdd_monto_descuento_ml", 0.0);
											
											if(rsOrdenCompraDesc_retenc.getString("smn_moneda_rf") != null)
												inputParams.setValue("smn_moneda_rf", rsOrdenCompraDesc_retenc.getInt("smn_moneda_rf"));
											else
												inputParams.setValue("smn_moneda_rf", 0);
											
											if(rsOrdenCompraDesc_retenc.getString("smn_tasa_rf") != null)
												inputParams.setValue("smn_tasa_rf", rsOrdenCompraDesc_retenc.getInt("smn_tasa_rf"));
											else
												inputParams.setValue("smn_tasa_rf", 0);
											
											if(rsOrdenCompraDesc_retenc.getString("ocd_monto_base_ma") != null)
												inputParams.setValue("mdd_monto_base_ma", rsOrdenCompraDesc_retenc.getDouble("ocd_monto_base_ma"));
											else
												inputParams.setValue("mdd_monto_base_ma", 0.0);
											
											if(rsOrdenCompraDesc_retenc.getString("ocd_monto_descuento_ma") != null)
												inputParams.setValue("mdd_monto_descuento_ma", rsOrdenCompraDesc_retenc.getDouble("ocd_monto_descuento_ma"));
											else
												inputParams.setValue("mdd_monto_descuento_ma", 0.0);
												
										//****************
												
										rc = registrarMovimientoDetalleDesc_retenc(conn, inputParams); //registra movimiento detalle descuentos retenciones.
									}
								}
								else
								{
									mensaje = "no se registro el detalle del movimiento";
									rc = mde; //no se registro el detalle del movimiento.
								}
				
							} //END WHILE
							
							rc = registrarPago(conn,inputParams);
							
							if(rc == 0)
							{
								String sqlEstatus_occ   = getSQL(getResource("update-estatus_occ.sql"), inputParams);
							    db.get(sqlEstatus_occ); //actualiza el estatus de la orden de compra.
							}
							else
							{
								mensaje = "no se registro el pago de la orden de compra";
								rc = 1; //no se registro el pago de la orden de compra.
							}
						}
						else
						{
							mensaje = "no se encontraron detalles de la orden de compra";
							rc = 1; //no se encontraron detalles de la orden de compra.
						}
					}
					else
					{
						mensaje = "no se registro el movimiento en inventario";
						rc = mca; //no se registro el movimiento cabecera.
					}
				}
				else
				{
					mensaje = "El usuario debe ser comprador";
					rc = 1; //no se encontro un usuario CO.
				}	
			}
			else
			{
				mensaje = "la orden de compra no esta registrada";
				rc = 1; //la orden de compra no esta registrada.
			}
				
			inputParams.setValue("mensaje", mensaje);
		}
		catch(Throwable e)
		{
			conn.rollback();
			throw e;
		}
		
		finally
		{
			if(rc == 0)
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
	
	public int registrarMovimientoCabecera(Connection conn, Recordset inputParams) throws Throwable
	{
		int rc = 0;	//variable a retornar.
			
		String jndiName = (String)getContext().getAttribute("dinamica.security.datasource");
			
		if (jndiName==null)
			throw new Throwable("Context attribute [dinamica.security.datasource] is null, check your security filter configuration.");
			
		//establecer la conexion con la base de datos.
			
			this.setConnection(conn);
			
		//**
			
		try
		{
			Db db = getDb(); //objeto de conexion.
			
			String sqlMovimientoCabecera = getSQL(getResource("insert-movimiento_cabecera.sql"),inputParams);
			Recordset rsMovimientoCabecera = db.get(sqlMovimientoCabecera); //registra movimiento cabecera.
			
			if(rsMovimientoCabecera.getRecordCount()>0)
			{
				rsMovimientoCabecera.first();
				inputParams.setValue("smn_movimiento_cabecera_id", rsMovimientoCabecera.getInt("smn_movimiento_cabecera_id"));
				
				rc = 0; // ejecucion del subproceso exitosamente.
			}
			else
			{
				rc = 1; //no se registro movimiento cabecera.
			}
		}
		catch(Throwable e)
		{
			throw e;
		}
		
		return rc;
	}
	
	public int registrarMovimientoDetalle(Connection conn, Recordset inputParams) throws Throwable
	{
		int rc = 0;	//variable a retornar.
			
		String jndiName = (String)getContext().getAttribute("dinamica.security.datasource");
			
		if (jndiName==null)
			throw new Throwable("Context attribute [dinamica.security.datasource] is null, check your security filter configuration.");
			
		//establecer la conexion con la base de datos.
			
			this.setConnection(conn);
			
		//**
			
		try
		{
			Db db = getDb(); //objeto de conexion.
			
			String sqlMovimientoDetalle = getSQL(getResource("insert-movimiento_detalle.sql"),inputParams);
			Recordset rsMovimientoDetalle = db.get(sqlMovimientoDetalle); //registra movimiento detalle.
			
			if(rsMovimientoDetalle.getRecordCount()>0)
			{
				rsMovimientoDetalle.first();
				inputParams.setValue("smn_movimiento_detalle_id", rsMovimientoDetalle.getInt("smn_movimiento_detalle_id"));
				
				rc = 0; // ejecucion del subproceso exitosamente.
			}
			else
			{
				rc = 1; //no se registro movimiento cabecera.
			}
		}
		catch(Throwable e)
		{
			throw e;
		}
		
		return rc;
	}
	
	public int registrarMovimientoDetalleImpuesto(Connection conn, Recordset inputParams) throws Throwable
	{
		int rc = 0;	//variable a retornar.
			
		String jndiName = (String)getContext().getAttribute("dinamica.security.datasource");
			
		if (jndiName==null)
			throw new Throwable("Context attribute [dinamica.security.datasource] is null, check your security filter configuration.");
			
		//establecer la conexion con la base de datos.
		
			this.setConnection(conn);
			
		//**
			
		try
		{
			Db db = getDb(); //objeto de conexion.
			
			String sqlMovimientoDetalleImpuesto   = getSQL(getResource("insert-movimiento_detalle_impuesto.sql"),inputParams);
			Recordset rsMovimientoDetalleImpuesto = db.get(sqlMovimientoDetalleImpuesto); //registra movimiento detalle.
			
			if(rsMovimientoDetalleImpuesto.getRecordCount()>0)
			{
				rsMovimientoDetalleImpuesto.first();
				inputParams.setValue("smn_movimiento_detalle_impuesto_id", rsMovimientoDetalleImpuesto.getInt("smn_mov_det_impuesto_id"));
				
				rc = 0; // ejecucion del subproceso exitosamente.
			}
			else
			{
				rc = 1; //no se registro movimiento cabecera.
			}
		}
		catch(Throwable e)
		{
			throw e;
		}

		return rc;
	}
	
	public int registrarMovimientoDetalleDesc_retenc(Connection conn, Recordset inputParams) throws Throwable
	{
		int rc = 0;	//variable a retornar.
			
		String jndiName = (String)getContext().getAttribute("dinamica.security.datasource");
			
		if (jndiName==null)
			throw new Throwable("Context attribute [dinamica.security.datasource] is null, check your security filter configuration.");
			
		//establecer la conexion con la base de datos.
			
			this.setConnection(conn);
			
		//**
			
		try
		{
			Db db = getDb(); //objeto de conexion.
			
			String sqlMovimientoDetalleDesc_retenc   = getSQL(getResource("insert-movimiento_detalle_desc_retenc.sql"),inputParams);
			Recordset rsMovimientoDetalleDesc_retenc = db.get(sqlMovimientoDetalleDesc_retenc); //registra movimiento detalle.
			
			if(rsMovimientoDetalleDesc_retenc.getRecordCount()>0)
			{
				rsMovimientoDetalleDesc_retenc.first();
				inputParams.setValue("smn_movimiento_detalle_desc_ret_id", rsMovimientoDetalleDesc_retenc.getInt("smn_descuento_retencion_id"));
				
				rc = 0; // ejecucion del subproceso exitosamente.
			}
			else
			{
				rc = 1; //no se registro movimiento cabecera.
			}
		}
		catch(Throwable e)
		{
			throw e;
		}
		
		return rc;
	}
	
	public int registrarPago(Connection conn, Recordset inputParams) throws Throwable
	{
		int rc = 0;	//variable a retornar.
		String mensaje="";	
		String jndiName = (String)getContext().getAttribute("dinamica.security.datasource");
			
		if (jndiName==null)
			throw new Throwable("Context attribute [dinamica.security.datasource] is null, check your security filter configuration.");
			
		//establecer la conexion con la base de datos.
		
			this.setConnection(conn);
			
		//**
			
		try
		{
			Db db = getDb(); //objeto de conexion.
			
			String sqlOrdenCompra   = getSQL(getResource("select-ordenCompra.sql"),inputParams);
			Recordset rsOrdenCompra = db.get(sqlOrdenCompra); //consulta la orden de compra cabecera.
			
			if(rsOrdenCompra.getRecordCount()>0)
			{
				//**INYECCION DE VALORES EN EL VALIDATOR PROVENIENTES DE LA TABLA: smn_orden_compra_cabecera
				
				rsOrdenCompra.first();
				
				if(rsOrdenCompra.getString("smn_documento_id") != null)
					inputParams.setValue("smn_documento_id", rsOrdenCompra.getInt("smn_documento_id"));
				else
					inputParams.setValue("smn_documento_id", 0);
				
				if(rsOrdenCompra.getString("occ_orden_compra_numero") != null)
					inputParams.setValue("occ_orden_compra_numero", rsOrdenCompra.getInt("occ_orden_compra_numero"));
				else
					inputParams.setValue("occ_orden_compra_numero", 0);
				
				if(rsOrdenCompra.getString("smn_proveedor_id") != null)
					inputParams.setValue("smn_proveedor_id", rsOrdenCompra.getInt("smn_proveedor_id"));
				else
					inputParams.setValue("smn_proveedor_id", 0);
				
				if(rsOrdenCompra.getString("smn_sucursal_rf") != null)
					inputParams.setValue("smn_sucursal_rf", rsOrdenCompra.getInt("smn_sucursal_rf"));
				else
					inputParams.setValue("smn_sucursal_rf", 0);
				
				if(rsOrdenCompra.getString("smn_entidad_rf") != null)
					inputParams.setValue("smn_entidad_rf", rsOrdenCompra.getInt("smn_entidad_rf"));
				else
					inputParams.setValue("smn_entidad_rf", 0);
				
				if(rsOrdenCompra.getString("occ_monto_ml") != null)
					inputParams.setValue("occ_monto_ml", rsOrdenCompra.getDouble("occ_monto_ml"));
				else
					inputParams.setValue("occ_monto_ml", 0.0);
				
				if(rsOrdenCompra.getString("occ_monto_ma") != null)
					inputParams.setValue("occ_monto_ma", rsOrdenCompra.getDouble("occ_monto_ma"));
				else
					inputParams.setValue("occ_monto_ma", 0.0);
				
				if(rsOrdenCompra.getString("occ_monto_desc_rete_ml") != null)
					inputParams.setValue("occ_monto_desc_rete_ml", rsOrdenCompra.getDouble("occ_monto_desc_rete_ml"));
				else
					inputParams.setValue("occ_monto_desc_rete_ml", 0.0);
				
				if(rsOrdenCompra.getString("occ_monto_impuesto_ml") != null)
					inputParams.setValue("occ_monto_impuesto_ml", rsOrdenCompra.getDouble("occ_monto_impuesto_ml"));
				else
					inputParams.setValue("occ_monto_impuesto_ml", 0.0);
				
				if(rsOrdenCompra.getString("occ_monto_impuesto_ma") != null)
					inputParams.setValue("occ_monto_impuesto_ma", rsOrdenCompra.getDouble("occ_monto_impuesto_ma"));
				else
					inputParams.setValue("occ_monto_impuesto_ma", 0.0);
				
				if(rsOrdenCompra.getString("occ_monto_neto_ml") != null)
					inputParams.setValue("occ_monto_neto_ml", rsOrdenCompra.getDouble("occ_monto_neto_ml"));
				else
					inputParams.setValue("occ_monto_neto_ml", 0.0);
				
				if(rsOrdenCompra.getString("occ_monto_neto_ma") != null)
					inputParams.setValue("occ_monto_neto_ma", rsOrdenCompra.getDouble("occ_monto_neto_ma"));
				else
					inputParams.setValue("occ_monto_neto_ma", 0.0);
				
				if(rsOrdenCompra.getString("smn_moneda_rf") != null)
					inputParams.setValue("smn_moneda_rf", rsOrdenCompra.getInt("smn_moneda_rf"));
				else
					inputParams.setValue("smn_moneda_rf", 0);
				
				if(rsOrdenCompra.getString("smn_tasa_rf") != null)
					inputParams.setValue("smn_tasa_rf", rsOrdenCompra.getInt("smn_tasa_rf"));
				else
					inputParams.setValue("smn_tasa_rf", 0);
				
				if(rsOrdenCompra.getString("occ_fecha_orde_compra") != null)
					inputParams.setValue("occ_fecha_orde_compra", rsOrdenCompra.getDate("occ_fecha_orde_compra"));
				else
					inputParams.setValue("occ_fecha_orde_compra", 0);
				
				String sqlModulo   = getSQL(getResource("select-modulo.sql"),inputParams);
				Recordset rsModulo = db.get(sqlModulo); //consulta el modulo.
				
				if(rsModulo.getRecordCount()>0)
				{
					rsModulo.first();
					inputParams.setValue("smn_modulos_id", rsModulo.getInt("smn_modulos_id"));
				}
				else
				{
					mensaje = "**error**, la orden de compra no contiene smn_modulo_id";
					System.out.println(mensaje);
					return 1; //valor null.
				}
				
				String sqlDoc   = getSQL(getResource("select-documento.sql"),inputParams);
				Recordset rsDoc = db.get(sqlDoc); //consulta el documento.
				
				if(rsDoc.getRecordCount()>0)
				{
					rsDoc.first();
					inputParams.setValue("smn_documentos_generales_id", rsDoc.getInt("smn_documento_id"));
				}
				else
				{
					mensaje = "**error**, la orden de compra no contiene smn_documentos_generales_id";
					System.out.println(mensaje);
					return 1; //valor null.
				}
				
				String sqlSec  = getSQL(getResource("select-secuencia.sql"),inputParams);
				Recordset rsSec = db.get(sqlSec); //consulta la secuencia.
				
				if(rsSec.getRecordCount()>0)
				{
					rsSec.first();
					if(rsSec.getString("secuencia") != null)
						inputParams.setValue("secuencia", rsSec.getInt("secuencia"));
					else
						inputParams.setValue("secuencia", 1);
				}
				else
					inputParams.setValue("secuencia", 1);
				
				String sqlAlmacen  = getSQL(getResource("select-almacen.sql"),inputParams);
				Recordset rsAlmacen = db.get(sqlAlmacen); //consulta el almacen.
				
				if(rsAlmacen.getRecordCount()>0)
				{
					rsAlmacen.first();
					inputParams.setValue("smn_almacen_rf", rsAlmacen.getInt("smn_almacen_rf"));
				}
				else
				{
					mensaje = "**error**, la orden de compra no contiene smn_almacen_rf";
					System.out.println(mensaje);
					return 1; //valor null.
				}
				
			//*********************	
				
				String sqlPago   = getSQL(getResource("insert-pago.sql"),inputParams);
				Recordset rsPago = db.get(sqlPago); //registra pago.
				
				if(rsPago.getRecordCount()>0)
				{
					rc = 0; // se registro el pago exitosamente.
					mensaje = "se registro el pago exitosamente";
				}
				else
				{
					rc = 1; //no se registro el pago.
					mensaje = "**error**, no se registro el pago";
					System.out.println(mensaje);
				}
			}
			else
			{
				rc = 1; //no se encontro la orden de compra.
				mensaje = "**error**, no se encontro la orden de compra";
				System.out.println(mensaje);
			}
		}
		catch(Throwable e)
		{
			throw e;
		}
		
		return rc;
	}
}

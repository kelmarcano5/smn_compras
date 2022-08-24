package proceso;

import dinamica.*;
import java.sql.*;
import javax.sql.DataSource;

public class Aprobar extends GenericTransaction
{
	
	public int service(Recordset inputParams) throws Throwable
	{
		int rc = 0;	//variable a retornar.
		
		String jndiName = (String)getContext().getAttribute("dinamica.security.datasource");
		
		if (jndiName==null)
			throw new Throwable("Context attribute [dinamica.security.datasource] is null, check your security filter configuration.");
		
		//establecer la conexion con la base de datos.
		
			DataSource ds = Jndi.getDataSource(jndiName); 
			Connection conn = ds.getConnection();
			//this.setConnection(conn);
		
		//**

		try
		{
			Db db = getDb(); //objeto de conexion.
			
			String mensajeError = ""; //varible que almacena el error para mostrarlo en el front.
			
			String sqlRol_usuario  = getSQL(getResource("select-tipo_rol.sql"),inputParams); 
			Recordset rsRol_usuario = db.get(sqlRol_usuario); //determina si el usuario es aprobador 'AP'.
			
			if(rsRol_usuario.getRecordCount()>0)
			{
				String sqlVersion   = getSQL(getResource("select-version_requisicion.sql"),inputParams); 
				Recordset rsVersion = db.get(sqlVersion); //determina la version y el estatus de la requisicion.
				
				if(rsVersion.getRecordCount()>0)
				{
					rsVersion.first();
					
					if(rsVersion.getString("req_estatus").equals("GE"))
					{
						if(rsVersion.getString("req_cabecera_version") != null)
						{
							String sqlModalidad   = getSQL(getResource("select-modalidad.sql"),inputParams); 
							Recordset rsModalidad = db.get(sqlModalidad); //determina la modalidad.
							
							int interruptor = 0; //variable interruptor que permite determinar si se hizo el despacho o no.
							
							if(rsModalidad.getRecordCount()>0)
							{
								rsModalidad.first();
								
								if(rsModalidad.getString("dcc_modalidad").equals("MA"))
								{
									mensajeError = requisicionInterna(inputParams,conn);									
									interruptor = 1;
								}
							}
								
							switch(rsVersion.getString("req_cabecera_version"))
							{
								case "A":
									
									mensajeError = ordenCompra(inputParams,conn);
									
									break;
									
								case "B":
									if(interruptor == 0)
									{
										mensajeError = requisicionInterna(inputParams,conn);
										
										String sqlAprobarRequisicion   = getSQL(getResource("update-aprobar_requisicion.sql"),inputParams);
										Recordset rsAprobarRequisicion = db.get(sqlAprobarRequisicion);
										
										if(rsAprobarRequisicion.getRecordCount()>0)
										{
											mensajeError = "requisicion aprobada exitosamente";
											rc = 0;
										}
										else
										{
											mensajeError = "la requisicion no cambio su estatus a aprobada";
											rc = 16; // la requisicion no cambio su estatus a aprobada.
										}
									}
									
									break;
									
								case "C":
									
									mensajeError = "*version C en desarrollo*"; 
									System.out.println("* version C en desarrollo *");
									
									break;
									
								case "D":

									mensajeError = cotizar(inputParams,conn);
									
									break;
									
								default:
									rc = 5; //La version es incorrecta.
									mensajeError = "* La version no es correcta *";
							}
						}
						else
						{
							rc = 4; // La version no se encontro.
							mensajeError = "* La requisicion no tiene version *";
						}
					}
					else
					{	
						rc = 3; // la requisicion no esta generada 'GE'.
						mensajeError = "* La requisicion no esta GENERADA *";
					}	
				}
				else
				{
					rc = 2; //la version no se encontro.
					mensajeError = "* La version no se encontro*";
				}
			}
			else
			{
				rc = 1; //no se encontro el registro ya que el usuario no es aprobador.
				mensajeError = "* El usuario debe ser comprador *";
			}
			
			if(rc == 0)
				mensajeError = "el proceso se ejecuto exitosamente";
			
			inputParams.setValue("mensaje", mensajeError);
	
		}
		catch(Throwable e)
		{
			throw e;
		}
		
		finally
		{		
			if(conn!=null)
				conn.close();
		}
		
		if(rc>0)
			System.out.print("** ERROR **: " + rc);
		
		return rc;
	}
	
//***********************************subprogramas*******************************************
	
	//requisicion para cotizar.
	
	public String cotizar(Recordset inputParams,Connection conn) throws Throwable
	{
		int rc = 0;	//variable a retornar.
		String mensaje = "";
		String jndiName = (String)getContext().getAttribute("dinamica.security.datasource");
		
		if (jndiName==null)
			throw new Throwable("Context attribute [dinamica.security.datasource] is null, check your security filter configuration.");
		
		//establecer la conexion con la base de datos.
		
			//DataSource ds = Jndi.getDataSource(jndiName); 
			//Connection conn = ds.getConnection();
			//this.setConnection(conn);
		
		//**
		//conn.setAutoCommit(false);
		try
		{
			Db db = getDb(); //objeto de conexion.
			
			String sqlProducto   = getSQL(getResource("select-producto_requisicion_detalle.sql"),inputParams);
			Recordset rsProducto = db.get(sqlProducto); //CONSULTA EL PRODUCTO BIEN SEA IT,SE,AF
			
			if(rsProducto.getRecordCount()>0)
			{
				rsProducto.first();
				
				if(rsProducto.getString("producto") != null)
					inputParams.setValue("rpp_id_producto", rsProducto.getInt("producto"));
				else
					inputParams.setValue("rpp_id_producto", 0);
				
				if(rsProducto.getString("smn_item_id") != null)
					inputParams.setValue("smn_item_id", rsProducto.getInt("smn_item_id"));
				else
					inputParams.setValue("smn_item_id", 0);
				
				if(rsProducto.getString("smn_servicio_id") != null)
					inputParams.setValue("smn_servicio_id", rsProducto.getInt("smn_servicio_id"));
				else
					inputParams.setValue("smn_servicio_id", 0);
				
				if(rsProducto.getString("smn_afijo_id") != null)
					inputParams.setValue("smn_afijo_id", rsProducto.getInt("smn_afijo_id"));
				else
					inputParams.setValue("smn_afijo_id", 0);
				
				if(rsProducto.getString("smn_item_id") != null)
					inputParams.setValue("smn_item_id", rsProducto.getInt("smn_item_id"));
				else
					inputParams.setValue("smn_item_id", 0);
				
				if(rsProducto.getString("smn_requisicion_detalle_id") != null)
					inputParams.setValue("smn_requisicion_detalle_id", rsProducto.getInt("smn_requisicion_detalle_id"));
				else
					inputParams.setValue("smn_requisicion_detalle_id", 0);
				
				if(rsProducto.getString("rrs_fecha_de_requerido") != null)
					inputParams.setValue("rrs_fecha_de_requerido", rsProducto.getDate("rrs_fecha_de_requerido"));
				else
					inputParams.setValue("rrs_fecha_de_requerido", "0000-00-00");
				
				String sqlProveedores   = getSQL(getResource("select-proveedor_producto.sql"),inputParams);
				Recordset rsProveedores = db.get(sqlProveedores); //CONSULTA LOS POSIBLES PROVEEDORES DEL PRODUCTO.
				
				if(rsProveedores.getRecordCount()>0)
				{
					String sqlCotizacion   = getSQL(getResource("insert-cotizacion.sql"), inputParams);
					Recordset rsCotizacion = db.get(sqlCotizacion);
					
					if(rsCotizacion.getRecordCount()>0)
					{
						rsCotizacion.first();
						
						inputParams.setValue("smn_cotizacion_id", rsCotizacion.getInt("smn_cotizacion_id"));
						
						String sqlSelect_cot  = getSQL(getResource("select-cotizacion.sql"), inputParams);
						Recordset rsSelect_cot = db.get(sqlSelect_cot); 
						
						rsSelect_cot.first();
						
						inputParams.setValue("smn_documento_id", rsSelect_cot.getInt("smn_documento_id"));
						inputParams.setValue("cot_numero_documento", rsSelect_cot.getInt("cot_numero_documento"));
						
						String sqlUpdateDoc   = getSQL(getResource("update-documento_sec.sql"), inputParams);
						Recordset rsUpdateDoc = db.get(sqlUpdateDoc);
							
						if(rsUpdateDoc.getRecordCount()>0)
						{
							while(rsProveedores.next() != false)
							{
								if(rsProveedores.getString("smn_proveedor_id") != null)
									inputParams.setValue("smn_proveedor_id", rsProveedores.getInt("smn_proveedor_id"));
								else
									inputParams.setValue("smn_proveedor_id", 0);
								
								String sql_rel_cot_prov   = getSQL(getResource("insert-rel_cotizacion_proveedor.sql"), inputParams);
								Recordset rs_rel_cot_prov = db.get(sql_rel_cot_prov);
									
								if(rs_rel_cot_prov.getRecordCount()>0)
									rc = 0; //se relaciono el proveedor exitosamente con la cotizacion
								else
									rc = 10; //no se relaciono el proveedor exitosamente con la cotizacion			
							} //end while
						}
						else
						{
							mensaje = "no se actualizo el campo de la secuencia del documento";
							rc = 9; //no se actualizo el campo de la secuencia del documento.
						}
					}
					else
					{
						mensaje = "no se registro la cotizacion";
						rc = 8; //no se registro la cotizacion. 
					}
				}
				else
				{
					mensaje = "no se encontro posibles proveedores de ese producto";
					rc = 7; //no se encontro posibles proveedores de ese producto consultado.
				}
			}
			else
			{
				mensaje = "no se encontro el producto";
				rc = 6; //no se encontro el producto en la tabla smn_compras.smn_requisicion_detalle
			}
			
			if(rc == 0)
			{
				String sqlAprobarRequisicion   = getSQL(getResource("update-aprobar_requisicion.sql"),inputParams);
				Recordset rsAprobarRequisicion = db.get(sqlAprobarRequisicion);
								
				if(rsAprobarRequisicion.getRecordCount()>0)
				{
					mensaje = "requisicion aprobada exitosamente";
					rc = 0;
				}
				else
				{
					mensaje = "error, la requisicion no cambio estatus a aprobada";
					rc = 10; // la requisicion no cambio su estatus a aprobada.
				}
			}
		}
		catch(Throwable e)
		{
			//conn.rollback();
			throw e;
		}
		
		/*finally
		{
			/*if(rc == 0)
				conn.commit();
			else
				conn.rollback();
			
			if(conn!=null)
				conn.close();
		}*/
		
		if(rc>0)
			System.out.print("** ERROR **: " + rc);
		
		return mensaje;
	}
	
	//*****************************************requisicion interna.****************************************************
	
	public String requisicionInterna(Recordset inputParams, Connection conn) throws Throwable 
	{
		int rc = 0;	//variable a retornar.
		String mensaje = "";
		String jndiName = (String)getContext().getAttribute("dinamica.security.datasource");
		
		if (jndiName==null)
			throw new Throwable("Context attribute [dinamica.security.datasource] is null, check your security filter configuration.");
		
		//establecer la conexion con la base de datos.
		
			/*DataSource ds = Jndi.getDataSource(jndiName); 
			Connection conn = ds.getConnection();*/
			this.setConnection(conn);
		
		//**
		//conn.setAutoCommit(false);
		try
		{
			Db db = getDb(); //objeto de conexion.

			String sqlRequisicion_cabecera   = getSQL(getResource("select-requisicion_cabecera.sql"),inputParams);
			Recordset rsRequisicion_cabecera = db.get(sqlRequisicion_cabecera);
			
			if(rsRequisicion_cabecera.getRecordCount()>0)
			{
				rsRequisicion_cabecera.first();
				
				if(rsRequisicion_cabecera.getString("smn_documento_id") != null)
					inputParams.setValue("smn_documento_id", rsRequisicion_cabecera.getInt("smn_documento_id"));
				else
					inputParams.setValue("smn_documento_id", 0);
				
				if(rsRequisicion_cabecera.getString("req_usuario") != null)
					inputParams.setValue("req_usuario", rsRequisicion_cabecera.getString("req_usuario"));
				else
					inputParams.setValue("req_usuario", "");
				
				if(rsRequisicion_cabecera.getString("req_numero") != null)
					inputParams.setValue("req_numero", rsRequisicion_cabecera.getInt("req_numero"));
				else
					inputParams.setValue("req_numero", 0);
				
				if(rsRequisicion_cabecera.getString("req_descripcion") != null)
					inputParams.setValue("req_descripcion", rsRequisicion_cabecera.getString("req_descripcion"));
				else
					inputParams.setValue("req_descripcion", "");
				
				if(rsRequisicion_cabecera.getString("smn_entidad_id") != null)
					inputParams.setValue("smn_entidad_id", rsRequisicion_cabecera.getInt("smn_entidad_id"));
				else
					inputParams.setValue("smn_entidad_id", 0);
				
				if(rsRequisicion_cabecera.getString("smn_sucursal_id") != null)
					inputParams.setValue("smn_sucursal_id", rsRequisicion_cabecera.getInt("smn_sucursal_id"));
				else
					inputParams.setValue("smn_sucursal_id", 0);
				
				if(rsRequisicion_cabecera.getString("smn_modulo_rf") != null)
					inputParams.setValue("smn_modulo_rf", rsRequisicion_cabecera.getInt("smn_modulo_rf"));
				else
					inputParams.setValue("smn_modulo_rf", 0);
				
				if(rsRequisicion_cabecera.getString("req_fecha_requerido") != null)
					inputParams.setValue("req_fecha_requerido", rsRequisicion_cabecera.getDate("req_fecha_requerido"));
				else
					inputParams.setValue("req_fecha_requerido", 0);
				
				String sqlDespacho   = getSQL(getResource("insert-despacho.sql"),inputParams);
				Recordset rsDespacho = db.get(sqlDespacho);
				
				if(rsDespacho.getRecordCount()>0)
				{
					rsDespacho.first();
					
					if(rsDespacho.getString("smn_despacho_id") != null)
						inputParams.setValue("smn_despacho_id", rsDespacho.getInt("smn_despacho_id"));
					else
						inputParams.setValue("smn_despacho_id", 0);
					
					String sqlRequisicion_detalle   = getSQL(getResource("select-req_detalle.sql"),inputParams);
					Recordset rsRequisicion_detalle = db.get(sqlRequisicion_detalle);
					
					if(rsRequisicion_detalle.getRecordCount()>0)
					{
						double costo_ml = 0.0;
						while(rsRequisicion_detalle.next() != false)
						{
							if(rsRequisicion_detalle.getString("smn_item_id") != null)
								inputParams.setValue("smn_item_id", rsRequisicion_detalle.getInt("smn_item_id"));
							else
								inputParams.setValue("smn_item_id", 0);
							
							if(rsRequisicion_detalle.getString("rss_cantidad") != null)
								inputParams.setValue("rss_cantidad", rsRequisicion_detalle.getInt("rss_cantidad"));
							else
								inputParams.setValue("rss_cantidad", 0);
							
							if(rsRequisicion_detalle.getString("rrs_monto") != null)
								inputParams.setValue("rrs_monto", rsRequisicion_detalle.getDouble("rrs_monto"));
							else
								inputParams.setValue("rrs_monto", 0.0);
							
							if(rsRequisicion_detalle.getString("rrs_monto_moneda_alterna") != null)
								inputParams.setValue("rrs_monto_moneda_alterna", rsRequisicion_detalle.getDouble("rrs_monto_moneda_alterna"));
							else
								inputParams.setValue("rrs_monto_moneda_alterna", 0.0);
							
							String sqlDespacho_detalle    = getSQL(getResource("insert-despacho_detalle.sql"),inputParams);  
							Recordset rsDespacho_detalle = db.get(sqlDespacho_detalle);
							
							if(rsDespacho_detalle.getRecordCount()>0)
							{
								rsDespacho_detalle.first();
								
								inputParams.setValue("smn_despacho_detalle_id", rsDespacho_detalle.getInt("smn_despacho_detalle_id"));
								
								String sqlSelect_despacho_detalle    = getSQL(getResource("select-despacho_detalle.sql"),inputParams);  
								Recordset rsSelect_despacho_detalle  = db.get(sqlSelect_despacho_detalle);
								
								rsSelect_despacho_detalle.first();
								
								costo_ml = costo_ml + rsSelect_despacho_detalle.getDouble("dde_costo_ml");
							}
							else
							{
								mensaje = "no se registro el detalle del despacho";
								rc = 5; // no se registro el detalle del despacho.
								break;
							}
						} //End While		
						
						String sqlSelect_despacho   = getSQL(getResource("select-despacho.sql"),inputParams);
						Recordset rsSelect_despacho = db.get(sqlSelect_despacho);
						
						if(rsSelect_despacho.getRecordCount()>0)
						{
							rsSelect_despacho.first();
							
							double monto_impuesto_ml;
							double monto_descuento_ml;
							
							inputParams.setValue("des_monto_pedido_ml", costo_ml);
							
							if(rsSelect_despacho.getString("des_monto_impuesto_ml") != null)
								monto_impuesto_ml = rsSelect_despacho.getDouble("des_monto_impuesto_ml");
							else
								monto_impuesto_ml = 0.0;
							
							if(rsSelect_despacho.getString("des_monto_descuento_ml") != null)
								monto_descuento_ml = rsSelect_despacho.getDouble("des_monto_descuento_ml");
							else
								monto_descuento_ml = 0.0;
							
							inputParams.setValue("des_monto_neto_ml", (costo_ml + monto_impuesto_ml) - monto_descuento_ml);
							
							String sqlUpdate_despacho   = getSQL(getResource("update-despacho.sql"),inputParams);
							Recordset rsUpdate_despacho = db.get(sqlUpdate_despacho);
							
							if(rsUpdate_despacho.getRecordCount()>0)
							{
								
							}
							else
							{
								mensaje = "no se actualizo el costo_ml del despacho.";
								rc = 15; // no se actualizo el costo_ml del despacho.
							}
						}
						else
						{
							rc = 14;
						}
					}
					else
					{
						mensaje = "no se encontro el detalle de la requisicion.";
						rc = 13; // no se encontro el detalle de la requisicion.
					}
				}
				else
				{
					mensaje = "no se registro el despacho.";
					rc = 12; //no se registro el despacho.
				}
			}
			else
			{
				mensaje = "no se encontro la requisicion original.";
				rc = 11; //no se encontro la requisicion_cabecera.
			}
		}
		catch(Throwable e)
		{
			//conn.rollback();
			throw e;
		}
		
		/*finally
		{
			/*if(rc == 0)
				conn.commit();
			else
				conn.rollback();
			
			if(conn!=null)
				conn.close();
		}*/
		
		if(rc>0)
			System.out.print("** ERROR **: " + rc);
		
		return mensaje;
	}
	
	// ** ORDEN COMPRA **
	
	public String ordenCompra(Recordset inputParams, Connection conn) throws Throwable 
	{
		int rc = 0;	//variable a retornar.
		String mensaje = "";
		String jndiName = (String)getContext().getAttribute("dinamica.security.datasource");
		
		if (jndiName==null)
			throw new Throwable("Context attribute [dinamica.security.datasource] is null, check your security filter configuration.");
		
		//establecer la conexion con la base de datos.
		
			/*DataSource ds = Jndi.getDataSource(jndiName); 
			Connection conn = ds.getConnection();*/
			this.setConnection(conn);
		
		//**
		//conn.setAutoCommit(false);
		try
		{
			Db db = getDb(); //objeto de conexion.
			
			String sqlReq_detalle   = getSQL(getResource("select-req_detalle.sql"),inputParams); 
			Recordset rsReq_detalle = db.get(sqlReq_detalle); //consultar las req_detalle.
			
			if(rsReq_detalle.getRecordCount()>0)
			{
				double ac_monto_ml          = 0.0;
				double ac_monto_impuesto_ml = 0.0;
				double ac_monto_desc_ret_ml = 0.0;
				double ac_monto_neto_ml     = 0.0;
				double ac_monto_ma          = 0.0;
				double ac_monto_impuesto_ma = 0.0;
				//double ac_monto_desc_ret_ma = 0.0;
				double ac_monto_neto_ma     = 0.0;
				
				while(rsReq_detalle.next() != false) //recorre todos los detalles de esa requisicion
				{
					//*inyeccion de valores del detalle_requisicion en el validator.
					
					if(rsReq_detalle.getString("smn_requisicion_detalle_id") != null)
						inputParams.setValue("smn_requisicion_detalle_id", rsReq_detalle.getInt("smn_requisicion_detalle_id"));
					else
						inputParams.setValue("smn_requisicion_detalle_id", 0);
					
					if(rsReq_detalle.getString("smn_linea_id") != null)
						inputParams.setValue("smn_linea_id", rsReq_detalle.getInt("smn_linea_id"));
					else
						inputParams.setValue("smn_linea_id", 0);
					
					if(rsReq_detalle.getString("smn_servicio_id") != null)
						inputParams.setValue("smn_servicio_id", rsReq_detalle.getInt("smn_servicio_id"));
					else
						inputParams.setValue("smn_servicio_id", 0);
					
					if(rsReq_detalle.getString("smn_item_id") != null)
						inputParams.setValue("smn_item_id", rsReq_detalle.getInt("smn_item_id"));
					else
						inputParams.setValue("smn_item_id", 0);
					
					if(rsReq_detalle.getString("smn_afijo_id") != null)
						inputParams.setValue("smn_afijo_id", rsReq_detalle.getInt("smn_afijo_id"));
					else
						inputParams.setValue("smn_afijo_id", 0);
					
					if(rsReq_detalle.getString("smn_contrato_id") != null)
						inputParams.setValue("smn_contrato_id", rsReq_detalle.getInt("smn_contrato_id"));
					else
						inputParams.setValue("smn_contrato_id", 0);
					
					if(rsReq_detalle.getString("rss_cantidad") != null)
						inputParams.setValue("rss_cantidad", rsReq_detalle.getInt("rss_cantidad"));
					else
						inputParams.setValue("rss_cantidad", 0);
					 
					if(rsReq_detalle.getString("rrs_precio") != null)
						inputParams.setValue("rrs_precio", rsReq_detalle.getDouble("rrs_precio"));
					else
						inputParams.setValue("rrs_precio", 0.0);
					 
					if(rsReq_detalle.getString("rrs_monto") != null)
						inputParams.setValue("rrs_monto", rsReq_detalle.getDouble("rrs_monto"));
					else
						inputParams.setValue("rrs_monto", 0.0);
					
					if(rsReq_detalle.getString("smn_moneda_id") != null)
						inputParams.setValue("smn_moneda_id", rsReq_detalle.getInt("smn_moneda_id"));
					else
						inputParams.setValue("smn_moneda_id", 0);
					
					if(rsReq_detalle.getString("rrs_precio_moneda_alterna") != null)
						inputParams.setValue("rrs_precio_moneda_alterna", rsReq_detalle.getDouble("rrs_precio_moneda_alterna"));
					else
						inputParams.setValue("rrs_precio_moneda_alterna", 0.0);
					
					if(rsReq_detalle.getString("rrs_monto_moneda_alterna") != null)
						inputParams.setValue("rrs_monto_moneda_alterna", rsReq_detalle.getDouble("rrs_monto_moneda_alterna"));
					else
						inputParams.setValue("rrs_monto_moneda_alterna", 0.0);
		
					if(rsReq_detalle.getString("smn_proveedor_id") != null)
						inputParams.setValue("smn_proveedor_id", rsReq_detalle.getInt("smn_proveedor_id"));
					else
						inputParams.setValue("smn_proveedor_id", 0);
					
					String sqlImpuestos_retenciones    = getSQL(getResource("select-imp_retenc.sql"),inputParams); 
					Recordset rsImpuestos_retenciones  = db.get(sqlImpuestos_retenciones); //requisicion: impuestos y retenciones. 
					
					rsImpuestos_retenciones.first();
					
					if(rsImpuestos_retenciones.getString("rim_monto_impuesto")!=null)
						inputParams.setValue("rim_monto_impuesto", rsImpuestos_retenciones.getDouble("rim_monto_impuesto"));
					else
						inputParams.setValue("rim_monto_impuesto", 0.0);
					
					if(rsImpuestos_retenciones.getString("drc_monto_descuento")!=null)
						inputParams.setValue("drc_monto_descuento", rsImpuestos_retenciones.getDouble("drc_monto_descuento"));
					else
						inputParams.setValue("drc_monto_descuento", 0.0);
					
				//**
					
					String sqlexistProv_occ   = getSQL(getResource("select-prov_occ.sql"),inputParams); 
					Recordset rsexistProv_occ  = db.get(sqlexistProv_occ); //consultar si existe una orden de compra cabecera con ese proveedor del detalle.
						
					if(rsexistProv_occ.getRecordCount()>0) 
					{
						//instrucciones si existe la occ (orden de compra cabecera) con ese proveedor.
						
						rsexistProv_occ.first();
						
						//inyeccion del id encontrado de occ en el validator.
						inputParams.setValue("smn_orden_compra_cabecera_id", rsexistProv_occ.getInt("smn_orden_compra_cabecera_id"));
					
						String sqlOrden_compra_detalle   = getSQL(getResource("insert-orden_compra_detalle.sql"),inputParams);
						Recordset rsOrden_compra_detalle = db.get(sqlOrden_compra_detalle); //registra orden compra detalle.
						
						if(rsOrden_compra_detalle.getRecordCount()>0)
						{
							rsOrden_compra_detalle.first();
							
							//inyeccion del id del detalle de orden de compra en el validator.
							inputParams.setValue("smn_orden_compra_detalle_id", rsOrden_compra_detalle.getInt("smn_orden_compra_detalle_id"));
							
							String sqlConsultarDetalle_oc = getSQL(getResource("select-campos_calculados_ocd.sql"),inputParams);
							Recordset rsConsultarDetalle_oc = db.get(sqlConsultarDetalle_oc);
							
							if(rsConsultarDetalle_oc.getRecordCount()>0)
							{
								rsConsultarDetalle_oc.first();
								
								double monto_ml;
								double monto_impuesto_ml;
								double monto_desc_ret_ml;
								double monto_ma;
								double monto_impuesto_ma;
								double monto_desc_ret_ma;
								
								//********** inyeccion de campos calculados en el validator*******
								
									if(rsConsultarDetalle_oc.getString("ocd_monto_bruto_ml") != null)
										monto_ml = rsConsultarDetalle_oc.getDouble("ocd_monto_bruto_ml");
									else
										monto_ml = 0.0;
									
									if(rsConsultarDetalle_oc.getString("ocd_monto_impuesto_ml") != null)
										monto_impuesto_ml = rsConsultarDetalle_oc.getDouble("ocd_monto_impuesto_ml");
									else
										monto_impuesto_ml = 0.0;
									
									if(rsConsultarDetalle_oc.getString("ocd_monto_desc_reten_ml") != null)
										monto_desc_ret_ml = rsConsultarDetalle_oc.getDouble("ocd_monto_desc_reten_ml");
									else
										monto_desc_ret_ml = 0.0;
									
									if(rsConsultarDetalle_oc.getString("ocd_monto_bruto_ma") != null)
										monto_ma = rsConsultarDetalle_oc.getDouble("ocd_monto_bruto_ma");
									else
										monto_ma = 0.0;
									
									if(rsConsultarDetalle_oc.getString("ocd_monto_impuesto_ma") != null)
										monto_impuesto_ma = rsConsultarDetalle_oc.getDouble("ocd_monto_impuesto_ma");
									else
										monto_impuesto_ma = 0.0;
									
									if(rsConsultarDetalle_oc.getString("ocd_monto_desc_reten_ma") != null)
										monto_desc_ret_ma = rsConsultarDetalle_oc.getDouble("ocd_monto_desc_reten_ma");
									else
										monto_desc_ret_ma = 0.0;
									
									inputParams.setValue("ocd_monto_neto_ml",(monto_ml + monto_impuesto_ml) - monto_desc_ret_ml);
									inputParams.setValue("ocd_monto_neto_ma",(monto_ma + monto_impuesto_ma) - monto_desc_ret_ma);
								
								//***

								String sqlActualizarDetalle_oc   = getSQL(getResource("update-orden_compra_detalle.sql"),inputParams);
								Recordset rsActualizarDetalle_oc = db.get(sqlActualizarDetalle_oc); //actualiza el monto_neto_ml y el monto_neto_ma de la tabla orden_compra_detalle.
									
								if(rsActualizarDetalle_oc.getRecordCount()>0)
								{
									registrarImpuesto(inputParams,conn); // registra orden compra impuestos
									registrarDescuentos_retenciones(inputParams,conn); //registra descuentos y retenciones
									registrarFecha_entrega(inputParams,conn); //registra orden compra fecha entrega.
											
									String sqlDetalle_oc   = getSQL(getResource("select-orden_compra_detalle.sql"),inputParams);
									Recordset rsDetalle_oc = db.get(sqlDetalle_oc); //busca el detalle de la orden de compra.
												
									if(rsDetalle_oc.getRecordCount()>0)
									{
										rsDetalle_oc.first();
													
										if(rsDetalle_oc.getString("ocd_monto_bruto_ml") != null)
											ac_monto_ml = ac_monto_ml + rsDetalle_oc.getDouble("ocd_monto_bruto_ml");
												
										if(rsDetalle_oc.getString("ocd_monto_impuesto_ml") != null)
											ac_monto_impuesto_ml = ac_monto_impuesto_ml + rsDetalle_oc.getDouble("ocd_monto_impuesto_ml");
													
										if(rsDetalle_oc.getString("ocd_monto_desc_reten_ml") != null)
											ac_monto_desc_ret_ml = ac_monto_desc_ret_ml + rsDetalle_oc.getDouble("ocd_monto_desc_reten_ml");
													
										if(rsDetalle_oc.getString("ocd_monto_neto_ml") != null)
											ac_monto_neto_ml = ac_monto_neto_ml + rsDetalle_oc.getDouble("ocd_monto_neto_ml");
												
										if(rsDetalle_oc.getString("smn_moneda_rf") != null)
											inputParams.setValue("smn_moneda_rf", rsDetalle_oc.getInt("smn_moneda_rf"));
										else
											inputParams.setValue("smn_moneda_rf", 0);
													
										if(rsDetalle_oc.getString("smn_tasa_rf") != null)
											inputParams.setValue("smn_tasa_rf", rsDetalle_oc.getInt("smn_tasa_rf"));
										else
											inputParams.setValue("smn_tasa_rf", 0);
													
										if(rsDetalle_oc.getString("ocd_monto_bruto_ma") != null)
											ac_monto_ma = ac_monto_ma + rsDetalle_oc.getDouble("ocd_monto_bruto_ma");
													
										if(rsDetalle_oc.getString("ocd_monto_impuesto_ma") != null)
											ac_monto_impuesto_ma = ac_monto_impuesto_ma + rsDetalle_oc.getDouble("ocd_monto_impuesto_ma");
													
										if(rsDetalle_oc.getString("ocd_monto_neto_ma") != null)
											ac_monto_neto_ma = ac_monto_neto_ma + rsDetalle_oc.getDouble("ocd_monto_neto_ma");
												
										//*inyeccion de valores al validator de los campos calculados de los detalles.
												
											inputParams.setValue("ocd_monto_bruto_ml", ac_monto_ml);
											inputParams.setValue("ocd_monto_impuesto_ml", ac_monto_impuesto_ml);
											inputParams.setValue("ocd_monto_desc_reten_ml", ac_monto_desc_ret_ml);
											inputParams.setValue("ocd_monto_neto_ml", ac_monto_neto_ml);
											inputParams.setValue("ocd_monto_bruto_ma", ac_monto_ma);
											inputParams.setValue("ocd_monto_impuesto_ma", ac_monto_impuesto_ma);
											inputParams.setValue("ocd_monto_neto_ma", ac_monto_neto_ma);
												
										//*	
										rc = 0;
									}
									else
									{
										mensaje = "no se encontro el detalle de orden de compra";
										rc = 32; //no se encontro el detalle de orden de compra.
										break;
									}
								}
								else
								{
									mensaje = "no se actualizaron los campos calculados del detalle de la orden de compra";
									rc=31; // no se actualizaron los campos calculados de ocd.
									break;
								}
							}
							else
							{
								mensaje = "no se encontraron campos calculados del detalle de la orden de compra";
								rc=30; //no se encontraron campos calculados de ocd(orden de compra detalle).
								break;
							}
						}
						else
						{
							mensaje = "no se registro el detalle de la orden de compra";
							rc = 29; //no se registro el detalle de la orden de compra.
							break;
						}
					}
					else
					{
						//inicializacion de los campos calculados de occ.
							 ac_monto_ml          = 0.0;
							 ac_monto_impuesto_ml = 0.0;
							 ac_monto_desc_ret_ml = 0.0;
							 ac_monto_neto_ml     = 0.0;
							 ac_monto_ma          = 0.0;
							 ac_monto_impuesto_ma = 0.0;
						    //ac_monto_desc_ret_ma = 0.0;
							 ac_monto_neto_ma     = 0.0;
					    //**
						 
						String sqlRequsicion_cabecera    = getSQL(getResource("select-requisicion_cabecera.sql"),inputParams);
						Recordset rsRequisicion_cabecera = db.get(sqlRequsicion_cabecera); //buscar la requisicion cabecera.
						
						if(rsRequisicion_cabecera.getRecordCount()>0)
						{
							rsRequisicion_cabecera.first();
							
							//* inyeccion de valores al validator.
								
								if(rsRequisicion_cabecera.getString("smn_entidad_id") != null)
									inputParams.setValue("smn_entidad_id", rsRequisicion_cabecera.getInt("smn_entidad_id"));
								else
									inputParams.setValue("smn_entidad_id", 0);
								
								if(rsRequisicion_cabecera.getString("smn_sucursal_id") != null)
									inputParams.setValue("smn_sucursal_id", rsRequisicion_cabecera.getInt("smn_sucursal_id"));
								else
									inputParams.setValue("smn_sucursal_id", 0);
								
								if(rsRequisicion_cabecera.getString("req_descripcion") != null)
									inputParams.setValue("req_descripcion", rsRequisicion_cabecera.getString("req_descripcion"));
								else
									inputParams.setValue("req_descripcion", null);		
							
								
								java.util.Date utilDate = new java.util.Date(); //fecha actual completa
								long lnMilisegundos = utilDate.getTime();
								
								Date fechaActual = new java.sql.Date(lnMilisegundos); //fecha actual (aaaa-mm-dd)
								
								inputParams.setValue("fecha_actual", fechaActual);
								
							//*
								
							String sqlOrden_compra   = getSQL(getResource("insert-orden_compra.sql"), inputParams);
							Recordset rsOrden_compra = db.get(sqlOrden_compra); //registra la orden de compra.
							
							if(rsOrden_compra.getRecordCount()>0)
							{
								rsOrden_compra.first();
								//conn.commit(); //ejecuta la sentencia correctamente
								inputParams.setValue("smn_orden_compra_cabecera_id", rsOrden_compra.getInt("orden_compra_id"));
								
								String sqlOrden_compra_detalle   = getSQL(getResource("insert-orden_compra_detalle.sql"),inputParams);
								Recordset rsOrden_compra_detalle = db.get(sqlOrden_compra_detalle); //registra orden compra detalle.
								
								if(rsOrden_compra_detalle.getRecordCount()>0)
								{
									rsOrden_compra_detalle.first();
									//conn.commit(); //ejecuta la sentencia correctamente
									//inyeccion del id del detalle de orden de compra en el validator.
									inputParams.setValue("smn_orden_compra_detalle_id", rsOrden_compra_detalle.getInt("smn_orden_compra_detalle_id"));
									
									String sqlConsultarDetalle_oc = getSQL(getResource("select-campos_calculados_ocd.sql"),inputParams);
									Recordset rsConsultarDetalle_oc = db.get(sqlConsultarDetalle_oc);
									
									if(rsConsultarDetalle_oc.getRecordCount()>0)
									{
										rsConsultarDetalle_oc.first();
										
										double monto_ml;
										double monto_impuesto_ml;
										double monto_desc_ret_ml;
										double monto_ma;
										double monto_impuesto_ma;
										double monto_desc_ret_ma;
										
										//********** inyeccion de campos calculados en el validator*******
										
											if(rsConsultarDetalle_oc.getString("ocd_monto_bruto_ml") != null)
												monto_ml = rsConsultarDetalle_oc.getDouble("ocd_monto_bruto_ml");
											else
												monto_ml = 0.0;
											
											if(rsConsultarDetalle_oc.getString("ocd_monto_impuesto_ml") != null)
												monto_impuesto_ml = rsConsultarDetalle_oc.getDouble("ocd_monto_impuesto_ml");
											else
												monto_impuesto_ml = 0.0;
											
											if(rsConsultarDetalle_oc.getString("ocd_monto_desc_reten_ml") != null)
												monto_desc_ret_ml = rsConsultarDetalle_oc.getDouble("ocd_monto_desc_reten_ml");
											else
												monto_desc_ret_ml = 0.0;
											
											if(rsConsultarDetalle_oc.getString("ocd_monto_bruto_ma") != null)
												monto_ma = rsConsultarDetalle_oc.getDouble("ocd_monto_bruto_ma");
											else
												monto_ma = 0.0;
											
											if(rsConsultarDetalle_oc.getString("ocd_monto_impuesto_ma") != null)
												monto_impuesto_ma = rsConsultarDetalle_oc.getDouble("ocd_monto_impuesto_ma");
											else
												monto_impuesto_ma = 0.0;
											
											if(rsConsultarDetalle_oc.getString("ocd_monto_desc_reten_ma") != null)
												monto_desc_ret_ma = rsConsultarDetalle_oc.getDouble("ocd_monto_desc_reten_ma");
											else
												monto_desc_ret_ma = 0.0;
											
											inputParams.setValue("ocd_monto_neto_ml",(monto_ml + monto_impuesto_ml) - monto_desc_ret_ml);
											inputParams.setValue("ocd_monto_neto_ma",(monto_ma + monto_impuesto_ma) - monto_desc_ret_ma);
										
										//***

										String sqlActualizarDetalle_oc   = getSQL(getResource("update-orden_compra_detalle.sql"),inputParams);
										Recordset rsActualizarDetalle_oc = db.get(sqlActualizarDetalle_oc); //actualiza el monto_neto_ml y el monto_neto_ma de la tabla orden_compra_detalle.
											
										if(rsActualizarDetalle_oc.getRecordCount()>0)
										{
											 registrarImpuesto(inputParams,conn); //orden compra impuestos
											 registrarDescuentos_retenciones(inputParams,conn); //descuentos y retenciones
											 registrarFecha_entrega(inputParams,conn); //orden compra fecha entrega.
											
											 String sqlDetalle_oc   = getSQL(getResource("select-orden_compra_detalle.sql"),inputParams);
											 Recordset rsDetalle_oc = db.get(sqlDetalle_oc); //busca el detalle de la orden de compra.
														
											if(rsDetalle_oc.getRecordCount()>0)
											{
												rsDetalle_oc.first();
															
												if(rsDetalle_oc.getString("ocd_monto_bruto_ml") != null)
													ac_monto_ml = ac_monto_ml + rsDetalle_oc.getDouble("ocd_monto_bruto_ml");
															
												if(rsDetalle_oc.getString("ocd_monto_impuesto_ml") != null)
													ac_monto_impuesto_ml = ac_monto_impuesto_ml + rsDetalle_oc.getDouble("ocd_monto_impuesto_ml");
															
												if(rsDetalle_oc.getString("ocd_monto_desc_reten_ml") != null)
													ac_monto_desc_ret_ml = ac_monto_desc_ret_ml + rsDetalle_oc.getDouble("ocd_monto_desc_reten_ml");
															
												if(rsDetalle_oc.getString("ocd_monto_neto_ml") != null)
													ac_monto_neto_ml = ac_monto_neto_ml + rsDetalle_oc.getDouble("ocd_monto_neto_ml");
															
												if(rsDetalle_oc.getString("smn_moneda_rf") != null)
													inputParams.setValue("smn_moneda_rf", rsDetalle_oc.getInt("smn_moneda_rf"));
												else
													inputParams.setValue("smn_moneda_rf", 0);
															
												if(rsDetalle_oc.getString("smn_tasa_rf") != null)
													inputParams.setValue("smn_tasa_rf", rsDetalle_oc.getInt("smn_tasa_rf"));
												else
													inputParams.setValue("smn_tasa_rf", 0);
															
												if(rsDetalle_oc.getString("ocd_monto_bruto_ma") != null)
													ac_monto_ma = ac_monto_ma + rsDetalle_oc.getDouble("ocd_monto_bruto_ma");
															
												if(rsDetalle_oc.getString("ocd_monto_impuesto_ma") != null)
													ac_monto_impuesto_ma = ac_monto_impuesto_ma + rsDetalle_oc.getDouble("ocd_monto_impuesto_ma");
															
												if(rsDetalle_oc.getString("ocd_monto_neto_ma") != null)
													ac_monto_neto_ma = ac_monto_neto_ma + rsDetalle_oc.getDouble("ocd_monto_neto_ma");
															
												//*inyeccion de valores al validator de los campos calculados de los detalles.
															
													inputParams.setValue("ocd_monto_bruto_ml", ac_monto_ml);
													inputParams.setValue("ocd_monto_impuesto_ml", ac_monto_impuesto_ml);
													inputParams.setValue("ocd_monto_desc_reten_ml", ac_monto_desc_ret_ml);
													inputParams.setValue("ocd_monto_neto_ml", ac_monto_neto_ml);
													inputParams.setValue("ocd_monto_bruto_ma", ac_monto_ma);
													inputParams.setValue("ocd_monto_impuesto_ma", ac_monto_impuesto_ma);
													inputParams.setValue("ocd_monto_neto_ma", ac_monto_neto_ma);
														
												//*	
												rc = 0;
											}
											else
											{
												mensaje = "no se encontro el detalle de orden de compra";
												rc = 27; //no se encontro el detalle de orden de compra.
												break;
											}
										}
										else
										{
											mensaje = "no se actualizaron los campos calculados del detalle de orden de compra"; 
											rc = 26; // no se actualizaron los campos calculados de ocd.
											break;
										}
									}
									else
									{
										mensaje = "no se encontraron campos calculados del detalle de orden de compra"; 
										rc = 25; //no se encontraron campos calculados de ocd(orden de compra detalle).
										break;
									}
								}
								else
								{
									mensaje = "no se registro el detalle de la orden de compra";
									rc = 24; //no se registro el detalle de la orden de compra.
									break;
								}
							}
							else
							{
								mensaje = "no se registro la orden de compra";
								rc = 23; // no se registro la occ
								break;
							}
						}
						else
						{
							mensaje = "no se encontro la requisicion";
							rc = 22; //no se encontro la requisicion_cabecera.
							break;
						}
					}
					
					if(rc == 0)
					{
						String sqlCabecera_oc  = getSQL(getResource("update-orden_compra_cabecera.sql"),inputParams);
						Recordset rsCabecera_oc = db.get(sqlCabecera_oc); //actualiza los campos calculados de la tabla smn_orden_compra_cabecera.
						
						if(rsCabecera_oc.getRecordCount()>0)
						{
							mensaje = "se actualizaron los campos calculados de orden de compra exitosamente";
							rc = 0; //actualizacion de los campos calculados de orden compra cabecera exitoso.
							break;
						}
						else
						{
							mensaje = "ocurrio un error al actualizar los campos calculado de la orden de compra";
							rc = 20; //ocurrio un error al actualizar los campos calculado de la tabla smn_orden_compra_cabecera.
							break;
						}
					}
					else
					{
						mensaje = "no se actualizaron los campos calculados de la orden de compra";
						rc = 19; //no se actualizaron los campos calculados de la occ.
						break;
					}
					
				}//END WHILE
				
				if(rc == 0)
				{
					String sqlAprobarRequisicion   = getSQL(getResource("update-aprobar_requisicion.sql"),inputParams);
					Recordset rsAprobarRequisicion = db.get(sqlAprobarRequisicion);
					
					if(rsAprobarRequisicion.getRecordCount()>0)
					{
						mensaje = "requisicion aprobada exitosamente";
						rc = 0;
					}
					else
					{
						mensaje = "la requisicion no cambio su estatus a aprobada";
						rc = 18; // la requisicion no cambio su estatus a aprobada.
					}	
				}
			}
			else
			{
				mensaje = "no se encontro el detalle de la requisicion";
				rc = 17; // no se encontro el detalle de la requisicion.
			}
		}
		catch(Throwable e)
		{
			//conn.rollback();
			throw e;
		}
		
		/*finally
		{
			/*if(rc == 0)
				conn.commit();
			else
				conn.rollback();
			
			if(conn!=null)
				conn.close();
		}*/
		
		if(rc>0)
			System.out.print("** ERROR **: " + rc);
		
		return mensaje;
	}
	
	public int registrarImpuesto(Recordset inputParams, Connection conn) throws Throwable 
	{
		int rc = 0;	//variable a retornar.
		
		String jndiName = (String)getContext().getAttribute("dinamica.security.datasource");
		
		if (jndiName==null)
			throw new Throwable("Context attribute [dinamica.security.datasource] is null, check your security filter configuration.");
		
		//establecer la conexion con la base de datos.
		
			/*DataSource ds = Jndi.getDataSource(jndiName); 
			Connection conn = ds.getConnection();*/
			this.setConnection(conn);
		
		//**
			//conn.setAutoCommit(false);
		try
		{
			Db db = getDb(); //objeto de conexion.
			String sqlDetalle_impuesto    = getSQL(getResource("select-req_detalle_impuesto.sql"),inputParams);
			Recordset rsDetalle_impuesto  = db.get(sqlDetalle_impuesto); //consulta el detalle de impuesto.

			if(rsDetalle_impuesto.getRecordCount()>0)
			{
				while(rsDetalle_impuesto.next()!=false)
				{
				
					//*inyeccion de valores al validator desde la tabla smn_req_detalle_impuesto
					
					if(rsDetalle_impuesto.getString("smn_cod_impuesto_deduc_rf") != null)
						inputParams.setValue("smn_cod_impuesto_deduc_rf", rsDetalle_impuesto.getInt("smn_cod_impuesto_deduc_rf"));
					else
						inputParams.setValue("smn_cod_impuesto_deduc_rf", 0);
					
					if(rsDetalle_impuesto.getString("rim_monto_base") != null)
						inputParams.setValue("rim_monto_base", rsDetalle_impuesto.getDouble("rim_monto_base"));
					else
						inputParams.setValue("rim_monto_base", 0.0);
					
					if(rsDetalle_impuesto.getString("smn_porcentaje_impuesto") != null)
						inputParams.setValue("smn_porcentaje_impuesto", rsDetalle_impuesto.getDouble("smn_porcentaje_impuesto"));
					else
						inputParams.setValue("smn_porcentaje_impuesto", 0.0);
					
					if(rsDetalle_impuesto.getString("smn_sustraendo") != null)
						inputParams.setValue("smn_sustraendo", rsDetalle_impuesto.getDouble("smn_sustraendo"));
					else
						inputParams.setValue("smn_sustraendo", 0.0);
					
					if(rsDetalle_impuesto.getString("rim_monto_impuesto") != null)
						inputParams.setValue("rim_monto_impuesto", rsDetalle_impuesto.getDouble("rim_monto_impuesto"));
					else
						inputParams.setValue("rim_monto_impuesto", 0.0);
					
					if(rsDetalle_impuesto.getString("smn_moneda_rf") != null)
						inputParams.setValue("smn_moneda_rf", rsDetalle_impuesto.getInt("smn_moneda_rf"));
					else
						inputParams.setValue("smn_moneda_rf", 0);
					
					if(rsDetalle_impuesto.getString("smn_tasa_rf") != null)
						inputParams.setValue("smn_tasa_rf", rsDetalle_impuesto.getInt("smn_tasa_rf"));
					else
						inputParams.setValue("smn_tasa_rf", 0);
					
					if(rsDetalle_impuesto.getString("rim_monto_imp_moneda_alterna") != null)
						inputParams.setValue("rim_monto_imp_moneda_alterna", rsDetalle_impuesto.getDouble("rim_monto_imp_moneda_alterna"));
					else
						inputParams.setValue("rim_monto_imp_moneda_alterna", 0.0);
					
					//*
					
					String sqlOrdenCompraImp   = getSQL(getResource("insert-orden_compra_impuesto.sql"),inputParams);
					Recordset rsOrdenCompraImp = db.get(sqlOrdenCompraImp); //inserta impuesto de orden de compra 
					
					if(rsOrdenCompraImp.getRecordCount()>0)
					{
						rc=0; //registro de impuesto exitoso.
					}
					else
					{
						rc=34; //error de impuesto.
					}
				}//end while
			}
			else
			{
				rc=33; //no se encontro el detalle impuesto
			}
		}
		catch(Throwable e)
		{
			//conn.rollback();
			throw e;
		}
		/*finally
		{
			if(rc == 0)
				conn.commit();
			else
				conn.rollback();
		}*/
		return rc;
	}
	
	public int registrarDescuentos_retenciones(Recordset inputParams, Connection conn) throws Throwable 
	{
		int rc = 0;	//variable a retornar.
		
		String jndiName = (String)getContext().getAttribute("dinamica.security.datasource");
		
		if (jndiName==null)
			throw new Throwable("Context attribute [dinamica.security.datasource] is null, check your security filter configuration.");
		
		//establecer la conexion con la base de datos.
		
			/*DataSource ds = Jndi.getDataSource(jndiName); 
			Connection conn = ds.getConnection();*/
			this.setConnection(conn);
		
		//**
			//conn.setAutoCommit(false);
		try
		{
			Db db = getDb(); //objeto de conexion.
			
			String sqlDescuentosRetenciones_req   = getSQL(getResource("select-descuento_retenciones_req.sql"),inputParams);
			Recordset rsDescuentosRetenciones_req = db.get(sqlDescuentosRetenciones_req); //consulta los descuentos y retenciones de la requisicion.
			
			if(rsDescuentosRetenciones_req.getRecordCount()>0)
			{
				while(rsDescuentosRetenciones_req.next()!=false)
				{
					//*inyeccion de valores al validator desde la tabla smn_req_detalle_dcto_retenc
					
					if(rsDescuentosRetenciones_req.getString("smn_req_detalle_dcto_retenc_id") != null)
						inputParams.setValue("smn_req_detalle_dcto_retenc_id", rsDescuentosRetenciones_req.getInt("smn_req_detalle_dcto_retenc_id"));
					else
						inputParams.setValue("smn_req_detalle_dcto_retenc_id", 0);
					
					if(rsDescuentosRetenciones_req.getString("smn_codigo_descuento_rf") != null)
						inputParams.setValue("smn_codigo_descuento_rf", rsDescuentosRetenciones_req.getInt("smn_codigo_descuento_rf"));
					else
						inputParams.setValue("smn_codigo_descuento_rf", 0);
					
					if(rsDescuentosRetenciones_req.getString("drc_monto_base") != null)
						inputParams.setValue("drc_monto_base", rsDescuentosRetenciones_req.getDouble("drc_monto_base"));
					else
						inputParams.setValue("drc_monto_base", 0.0);
					
					if(rsDescuentosRetenciones_req.getString("drc_porcentaje") != null)
						inputParams.setValue("drc_porcentaje", rsDescuentosRetenciones_req.getDouble("drc_porcentaje"));
					else
						inputParams.setValue("drc_porcentaje", 0.0);
					
					if(rsDescuentosRetenciones_req.getString("drc_monto_descuento") != null)
						inputParams.setValue("drc_monto_descuento", rsDescuentosRetenciones_req.getDouble("drc_monto_descuento"));
					else
						inputParams.setValue("drc_monto_descuento", 0.0);
					
					//*
					
					String sqlDescuentosRetenciones_oc   = getSQL(getResource("insert-descuentos_retenciones_oc.sql"),inputParams);
					Recordset rsDescuentosRetenciones_oc = db.get(sqlDescuentosRetenciones_oc); //registra descuentos y retenciones de orden de compra.
					
					if(rsDescuentosRetenciones_oc.getRecordCount()>0)
					{
						rc=0; //registro descuento y retenciones exitoso.
					}
					else
					{
						rc=36; //no se registraron los descuentos y retenciones.
					}
				}//end while
			}
			else
			{
				rc=35; //no se encontraron los descuentos y retenciones de requisicion.
			}
		}
		catch(Throwable e)
		{
			//conn.rollback();
			throw e;
		}
		/*finally
		{
			if(rc == 0)
				conn.commit();
			else
				conn.rollback();
		}*/
		
		return rc;
	}
	
	public int registrarFecha_entrega(Recordset inputParams, Connection conn) throws Throwable 
	{
		int rc = 0;	//variable a retornar.
		
		String jndiName = (String)getContext().getAttribute("dinamica.security.datasource");
		
		if (jndiName==null)
			throw new Throwable("Context attribute [dinamica.security.datasource] is null, check your security filter configuration.");
		
		//establecer la conexion con la base de datos.
		
			/*DataSource ds = Jndi.getDataSource(jndiName); 
			Connection conn = ds.getConnection();*/
			this.setConnection(conn);
		
		//**
		//conn.setAutoCommit(false);
		try
		{
			Db db = getDb(); //objeto de conexion.
			
			String sqlFechaEntrega_req   = getSQL(getResource("select-fecha_entrega_req.sql"),inputParams);
			Recordset rsFechaEntrega_req = db.get(sqlFechaEntrega_req); //consulta la fecha de entrega de la requisicion.
			
			if(rsFechaEntrega_req.getRecordCount()>0)
			{
				while(rsFechaEntrega_req.next()!=false)
				{
					//*inyeccion de valores al validator desde la tabla smn_rel_requisicion_f_entrega.
					
					if(rsFechaEntrega_req.getString("smn_rel_requisicion_f_entrega_id") != null)
						inputParams.setValue("smn_rel_requisicion_f_entrega_id", rsFechaEntrega_req.getInt("smn_rel_requisicion_f_entrega_id"));
					else
						inputParams.setValue("smn_rel_requisicion_f_entrega_id", 0);
					
					if(rsFechaEntrega_req.getString("cfe_consecutivo") != null)
						inputParams.setValue("cfe_consecutivo", rsFechaEntrega_req.getInt("cfe_consecutivo"));
					else
						inputParams.setValue("cfe_consecutivo", 0);
					
					if(rsFechaEntrega_req.getString("cfe_cantidad") != null)
						inputParams.setValue("cfe_cantidad", rsFechaEntrega_req.getInt("cfe_cantidad"));
					else
						inputParams.setValue("cfe_cantidad", 0);
					
					if(rsFechaEntrega_req.getString("cfe_fecha_de_entrega") != null)
						inputParams.setValue("cfe_fecha_de_entrega", rsFechaEntrega_req.getDate("cfe_fecha_de_entrega"));
					else
						inputParams.setValue("cfe_fecha_de_entrega", 0);
					
					//*
					
					String sqlFechaEntrega_oc   = getSQL(getResource("insert-fecha_entrega_oc.sql"),inputParams);
					Recordset rsFechaEntrega_oc = db.get(sqlFechaEntrega_oc); //registra la fecha de entrega de la orden de compra.
					
					if(rsFechaEntrega_oc.getRecordCount()>0)
					{
						rc = 0; // registro exitoso de fecha de entrega.
					}
					else
					{
						rc = 38; //no se registro la fecha entrega.
					}
				}//end while
			}
			else
			{
				rc = 37; //no se encontro la fecha de entrega de requisicion.
			}
		}
		catch(Throwable e)
		{
			//conn.rollback();
			throw e;
		}
		/*finally
		{
			if(rc == 0)
				conn.commit();
			else
				conn.rollback();
		}*/
		return rc;
	}
}
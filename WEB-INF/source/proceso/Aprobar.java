package proceso;

import dinamica.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.sql.DataSource;

public class Aprobar extends GenericTransaction
{
	
	public int service(Recordset inputParams) throws Throwable
	{
		int rc = 0;	//variable a retornar.
		
		String jndiName = (String)getContext().getAttribute("dinamica.security.datasource");
		
		if (jndiName==null)
			throw new Throwable("1Context attribute [dinamica.security.datasource] is null, check your security filter configuration.");
		
		//establecer la conexion con la base de datos.
		
			DataSource ds = Jndi.getDataSource(jndiName); 
			Connection conn = ds.getConnection();
			this.setConnection(conn);
			conn.setAutoCommit(false);
		//**
			
			String fechaActual= new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			String file = "../logAprobarRequisicion_"+fechaActual+".txt";
			File newLogFile = new File(file);
			FileWriter fw;
			String str="";
			
			if(!newLogFile.exists())
				fw = new FileWriter(newLogFile);
			else
				fw = new FileWriter(newLogFile,true);
			
			BufferedWriter bw=new BufferedWriter(fw);
			
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			
		try
		{
			str = "----------"+timestamp+"----------";	
			bw.write(str);
			bw.flush();
			bw.newLine();
			bw.newLine();
			
			Db db = getDb(); //objeto de conexion.
			
			String mensajeError = ""; //varible que almacena el error para mostrarlo en el front.
			
			String sqlRol_usuario  = getSQL(getResource("select-tipo_rol.sql"),inputParams); 
			Recordset rsRol_usuario = db.get(sqlRol_usuario); //determina si el usuario es aprobador 'AP'.
			
			str = "*Validando rol...";	
			bw.write(str);
			bw.flush();
			bw.newLine();
			
			if(rsRol_usuario.getRecordCount()>0)
			{
				str = "*Rol aprobado";	
				bw.write(str);
				bw.flush();
				bw.newLine();
				
				String sqlVersion   = getSQL(getResource("select-version_requisicion.sql"),inputParams); 
				Recordset rsVersion = db.get(sqlVersion); //determina la version y el estatus de la requisicion.
				
				str = "*Determinando version y estatus de la requisicion";	
				bw.write(str);
				bw.flush();
				bw.newLine();
				
				if(rsVersion.getRecordCount()>0)
				{
					rsVersion.first();
					
					if(rsVersion.getString("req_estatus").equals("GE"))
					{
						if(rsVersion.getString("req_cabecera_version") != null)
						{
							str = "*Version = "+rsVersion.getString("req_cabecera_version");	
							bw.write(str);
							bw.flush();
							bw.newLine();
							
							String sqlModalidad   = getSQL(getResource("select-modalidad.sql"),inputParams); 
							Recordset rsModalidad = db.get(sqlModalidad); //determina la modalidad.
							
							str = "*Consultando modalidad...";	
							bw.write(str);
							bw.flush();
							bw.newLine();
							
							if(rsModalidad.getRecordCount()>0)
							{
								rsModalidad.first();
								
								if(rsModalidad.getString("dcc_modalidad").equals("MA") && !rsVersion.getString("req_cabecera_version").equals("B"))
									rc = despacho(inputParams,conn,str,bw);									
							}
								
							switch(rsVersion.getString("req_cabecera_version"))
							{
								case "A":
									
									rc = ordenCompra(inputParams,conn,str,bw);
									
									break;
									
								case "B":
									
									rc = requisicionInterna(inputParams,conn,str,bw);
							
									break;
									
								case "C":
									
									str = "***VERSION 'C' NO DISPONIBLE***";	
									bw.write(str);
									bw.flush();
									bw.newLine();
									
									break;
									
								case "D":

									rc = cotizar(inputParams,conn,str,bw);
									
									break;
									
								default:
									rc = 1; //La version es incorrecta.
									mensajeError = "* La version no es correcta *";
									str = mensajeError;	
									bw.write(str);
									bw.flush();
									bw.newLine();
							}
						}
						else
						{
							rc = 1; // La version no se encontro.
							mensajeError = "* La requisicion no tiene version *";
							str = mensajeError;	
							bw.write(str);
							bw.flush();
							bw.newLine();
						}
					}
					else
					{	
						rc = 1; // la requisicion no esta generada 'GE'.
						mensajeError = "* La requisicion no esta GENERADA *";
						str = mensajeError;	
						bw.write(str);
						bw.flush();
						bw.newLine();
					}	
				}
				else
				{
					rc = 1; //la version no se encontro.
					mensajeError = "* La version no se encontro*";
					str = mensajeError;	
					bw.write(str);
					bw.flush();
					bw.newLine();
				}
			}
			else
			{
				rc = 1; //no se encontro el registro ya que el usuario no es aprobador.
				mensajeError = "* El usuario debe ser comprador *";
				str = mensajeError;	
				bw.write(str);
				bw.flush();
				bw.newLine();
			}
			
			if(rc == 0)
				mensajeError = "*El proceso se ejecuto exitosamente*";
			
			inputParams.setValue("mensaje", mensajeError);
			
			str = "FIN DEL PROCESO";	
			bw.write(str);
			bw.flush();
			bw.newLine();
			bw.newLine();
	        bw.close();
		}
		catch(Throwable e)
		{
			conn.rollback();
			throw e;
		}
		
		finally
		{		
			bw.close();
			
			if(rc == 0)
				conn.commit();
			else
				conn.rollback();
			
			if(conn!=null)
				conn.close();
		}
		
		return rc;
	}
	
//***********************************subprogramas*******************************************
	
	//requisicion para cotizar.
	
	public int cotizar(Recordset inputParams,Connection conn, String str, BufferedWriter bw) throws Throwable
	{
		int rc = 0;	//variable a retornar.
		String mensaje = "";
		String jndiName = (String)getContext().getAttribute("dinamica.security.datasource");
		
		if (jndiName==null)
			throw new Throwable("Context attribute [dinamica.security.datasource] is null, check your security filter configuration.");
		
		try
		{
			str = "*Preparando para cotizaciones";	
			bw.write(str);
			bw.flush();
			bw.newLine();
			
			Db db = getDb(); //objeto de conexion.
			
			String sqlProducto   = getSQL(getResource("select-producto_requisicion_detalle.sql"),inputParams);
			Recordset rsProducto = db.get(sqlProducto); //CONSULTA EL PRODUCTO BIEN SEA IT,SE,AF
			
			str = "*Consultando producto";	
			bw.write(str);
			bw.flush();
			bw.newLine();
			
			if(rsProducto.getRecordCount()>0)
			{
				str = "-Total productos encontrados = "+rsProducto.getRecordCount();	
				bw.write(str);
				bw.flush();
				bw.newLine();
				
				while(rsProducto.next() != false)
				{	
					str = "-procesando requisicion_detalle id = "+rsProducto.getString("smn_requisicion_detalle_id");	
					bw.write(str);
					bw.flush();
					bw.newLine();
					
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
					
					str = "-Buscando posibles proveedores del producto id = "+inputParams.getValue("rpp_id_producto")+"...";	
					bw.write(str);
					bw.flush();
					bw.newLine();
					
					if(rsProveedores.getRecordCount()>0)
					{
						str = "-Se encontraron "+rsProveedores.getRecordCount()+" posibles proveedores";	
						bw.write(str);
						bw.flush();
						bw.newLine();
						
						String sqlCotizacion   = getSQL(getResource("insert-cotizacion.sql"), inputParams);
						Recordset rsCotizacion = db.get(sqlCotizacion);
						
						str = "-Registrando cotizacion...";	
						bw.write(str);
						bw.flush();
						bw.newLine();
						
						if(rsCotizacion.getRecordCount()>0)
						{
							str = "-cotizacion registrada correctamente";	
							bw.write(str);
							bw.flush();
							bw.newLine();
							
							rsCotizacion.first();
								
							inputParams.setValue("smn_cotizacion_id", rsCotizacion.getInt("smn_cotizacion_id"));
							
							String sqlSelect_cot  = getSQL(getResource("select-cotizacion.sql"), inputParams);
							Recordset rsSelect_cot = db.get(sqlSelect_cot); 
								
							rsSelect_cot.first();
								
							inputParams.setValue("smn_documento_id", rsSelect_cot.getInt("smn_documento_id"));
							inputParams.setValue("cot_numero_documento", rsSelect_cot.getInt("cot_numero_documento"));
							
							String sqlUpdateDoc   = getSQL(getResource("update-documento_sec.sql"), inputParams);
							Recordset rsUpdateDoc = db.get(sqlUpdateDoc);
							
							str = "-Actualizando secuencia del documento con id = "+inputParams.getValue("smn_documento_id");	
							bw.write(str);
							bw.flush();
							bw.newLine();
							
							if(rsUpdateDoc.getRecordCount()>0)
							{
								registrarFecha_entrega(inputParams,conn,"D",str,bw); //registra fecha entrega.
								
								while(rsProveedores.next() != false)
								{
									if(rsProveedores.getString("smn_proveedor_id") != null)
										inputParams.setValue("smn_proveedor_id", rsProveedores.getInt("smn_proveedor_id"));
									else
										inputParams.setValue("smn_proveedor_id", 0);
									
									String sql_rel_cot_prov   = getSQL(getResource("insert-rel_cotizacion_proveedor.sql"), inputParams);
									Recordset rs_rel_cot_prov = db.get(sql_rel_cot_prov);
									
									str = "-Registrando relacion cotizacion-proveedor...";	
									bw.write(str);
									bw.flush();
									bw.newLine();
									
									if(rs_rel_cot_prov.getRecordCount()>0)
									{
										rc = 0; //se relaciono el proveedor exitosamente con la cotizacion
										str = "-Relacion cotizacion-proveedor registrada correctamente.";	
										bw.write(str);
										bw.flush();
										bw.newLine();
									}
									else
									{
										rc = 1; //no se relaciono el proveedor exitosamente con la cotizacion
										str = "*No se relaciono el proveedor correctamente con la cotizacion*";	
										bw.write(str);
										bw.flush();
										bw.newLine();
									}
								} //end while
							}
							else
							{
								mensaje = "*No se actualizo el campo de la secuencia del documento*";
								rc = 1; //no se actualizo el campo de la secuencia del documento.
								str = mensaje;	
								bw.write(str);
								bw.flush();
								bw.newLine();
							}
						}
						else
						{
							mensaje = "*No se registro la cotizacion*";
							rc = 1; //no se registro la cotizacion. 
							str = mensaje;	
							bw.write(str);
							bw.flush();
							bw.newLine();
						}	
					}
					else
					{
						str = "-No se encontraron posibles proveedores";	
						bw.write(str);
						bw.flush();
						bw.newLine();
						
						String sqlCotizacion   = getSQL(getResource("insert-cotizacion.sql"), inputParams);
						Recordset rsCotizacion = db.get(sqlCotizacion);
						
						str = "-Registrando cotizacion...";	
						bw.write(str);
						bw.flush();
						bw.newLine();
						
						if(rsCotizacion.getRecordCount()>0)
						{
							str = "-Cotizacion registrada correctamente...";	
							bw.write(str);
							bw.flush();
							bw.newLine();
							
							rsCotizacion.first();
								
							inputParams.setValue("smn_cotizacion_id", rsCotizacion.getInt("smn_cotizacion_id"));
								
							String sqlSelect_cot  = getSQL(getResource("select-cotizacion.sql"), inputParams);
							Recordset rsSelect_cot = db.get(sqlSelect_cot); 
								
							rsSelect_cot.first();
							
							inputParams.setValue("smn_documento_id", rsSelect_cot.getInt("smn_documento_id"));
							inputParams.setValue("cot_numero_documento", rsSelect_cot.getInt("cot_numero_documento"));
							
							String sqlUpdateDoc   = getSQL(getResource("update-documento_sec.sql"), inputParams);
							Recordset rsUpdateDoc = db.get(sqlUpdateDoc);
								
							str = "-Actualizando secuencia del documento con id = "+inputParams.getValue("smn_documento_id");	
							bw.write(str);
							bw.flush();
							bw.newLine();
							
							if(rsUpdateDoc.getRecordCount()>0)
							{
								registrarFecha_entrega(inputParams,conn,"D",str,bw); //registra fecha entrega.
							}
							else
							{
								mensaje = "*No se actualizo el campo de la secuencia del documento*";
								rc = 1; //no se actualizo el campo de la secuencia del documento.
								str = mensaje;	
								bw.write(str);
								bw.flush();
								bw.newLine();
							}
						}
						else
						{
							mensaje = "*No se registro la cotizacion*";
							rc = 1; //no se registro la cotizacion. 
							str = mensaje;	
							bw.write(str);
							bw.flush();
							bw.newLine();
						}	
					}
				}//end while
			}
			else
			{
				mensaje = "*No se encontro el producto*";
				rc = 1; //no se encontro el producto en la tabla smn_compras.smn_requisicion_detalle
				str = mensaje;	
				bw.write(str);
				bw.flush();
				bw.newLine();
			}
			
			if(rc == 0)
			{
				String sqlAprobarRequisicion   = getSQL(getResource("update-aprobar_requisicion.sql"),inputParams);
                db.exec(sqlAprobarRequisicion);
				/* Recordset rsAprobarRequisicion = db.get(sqlAprobarRequisicion); */
					
				str = "-Cambiando estatus de la requisicion...";	
				bw.write(str);
				bw.flush();
				bw.newLine();

                mensaje = "-*Requisicion aprobada exitosamente*";
                rc = 0;
                str = mensaje;	
                bw.write(str);
                bw.flush();
                bw.newLine();
				
				/* if(rsAprobarRequisicion.getRecordCount()>0)
				{
					mensaje = "-*Requisicion aprobada exitosamente*";
					rc = 0;
					str = mensaje;	
					bw.write(str);
					bw.flush();
					bw.newLine();
				}
				else
				{
					mensaje = "*Error, la requisicion no cambio estatus a aprobada*";
					rc = 1;
					str = mensaje;	
					bw.write(str);
					bw.flush();
					bw.newLine();
				} */
			}
		}
		catch(Throwable e)
		{
			throw e;
		}
		finally{
			inputParams.setValue("mensaje", mensaje);
		}
		
		return rc;
	}
	
	//*****************************************requisicion interna.****************************************************
	
	public int requisicionInterna(Recordset inputParams, Connection conn, String str, BufferedWriter bw) throws Throwable 
	{
		int rc = 0;	//variable a retornar.
		String mensaje = "";
		String jndiName = (String)getContext().getAttribute("dinamica.security.datasource");
		
		if (jndiName==null)
			throw new Throwable("Context attribute [dinamica.security.datasource] is null, check your security filter configuration.");
		
		//establecer la conexion con la base de datos.
		
			this.setConnection(conn);
		
		//**
		try
		{
			str = "*Preparando para requisicion interna";	
			bw.write(str);
			bw.flush();
			bw.newLine();
			
			Db db = getDb(); //objeto de conexion.

			String sqlRequisicion_cabecera   = getSQL(getResource("select-requisicion_cabecera.sql"),inputParams);
			Recordset rsRequisicion_cabecera = db.get(sqlRequisicion_cabecera);
			
			str = "*Consultando requisicion_cabecera...";	
			bw.write(str);
			bw.flush();
			bw.newLine();
			
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
				
				str = "*Registrando despacho...";	
				bw.write(str);
				bw.flush();
				bw.newLine();
				
				if(rsDespacho.getRecordCount()>0)
				{
					str = "*Despacho registrado con exito*";	
					bw.write(str);
					bw.flush();
					bw.newLine();
					
					rsDespacho.first();
					
					if(rsDespacho.getString("smn_despacho_id") != null)
						inputParams.setValue("smn_despacho_id", rsDespacho.getInt("smn_despacho_id"));
					else
						inputParams.setValue("smn_despacho_id", 0);
					
					String sqlRequisicion_detalle   = getSQL(getResource("select-req_detalle.sql"),inputParams);
					Recordset rsRequisicion_detalle = db.get(sqlRequisicion_detalle);
					
					str = "*Consultando detalles de la requisicion...";	
					bw.write(str);
					bw.flush();
					bw.newLine();
					
					if(rsRequisicion_detalle.getRecordCount()>0)
					{
						str = "-Total detalles encontrados = "+rsRequisicion_detalle.getRecordCount();	
						bw.write(str);
						bw.flush();
						bw.newLine();
						
						double costo_ml = 0.0;
						while(rsRequisicion_detalle.next() != false)
						{
							str = "-Procesando requisicion_detalle con id = "+rsRequisicion_detalle.getInt("smn_requisicion_detalle_id");	
							bw.write(str);
							bw.flush();
							bw.newLine();
							
							if(rsRequisicion_detalle.getString("smn_item_id") != null)
								inputParams.setValue("smn_item_id", rsRequisicion_detalle.getInt("smn_item_id"));
							else
								inputParams.setValue("smn_item_id", 0);
							
							if(rsRequisicion_detalle.getString("rss_cantidad") != null)
								inputParams.setValue("rss_cantidad", rsRequisicion_detalle.getDouble("rss_cantidad"));
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
							
							String sqlCaracteristica_item    = getSQL(getResource("select-caracteristica_item.sql"),inputParams);  
							Recordset rsCaracteristica_item  = db.get(sqlCaracteristica_item);
							
							str = "-Consultando caracteristica_item...";	
							bw.write(str);
							bw.flush();
							bw.newLine();
							
							if(rsCaracteristica_item.getRecordCount()>0)
							{
								rsCaracteristica_item.first();
								
								inputParams.setValue("smn_caracteristica_item_id", rsCaracteristica_item.getInt("smn_caracteristica_item_id"));
							}
							else
							{
								mensaje = "No se encontro la caracteristica del item con el item_id= "+inputParams.getValue("smn_item_id");
								str = mensaje;	
								bw.write(str);
								bw.flush();
								bw.newLine();
								return 1;
							}
							
							String sqlDespacho_detalle    = getSQL(getResource("insert-despacho_detalle.sql"),inputParams);  
							Recordset rsDespacho_detalle = db.get(sqlDespacho_detalle);
							
							str = "Registrando despacho_detalle...";	
							bw.write(str);
							bw.flush();
							bw.newLine();
							
							if(rsDespacho_detalle.getRecordCount()>0)
							{
								str = "Se registro el despacho_detalle correctamente";	
								bw.write(str);
								bw.flush();
								bw.newLine();
								
								rsDespacho_detalle.first();
								
								inputParams.setValue("smn_despacho_detalle_id", rsDespacho_detalle.getInt("smn_despacho_detalle_id"));
								
								String sqlSelect_despacho_detalle    = getSQL(getResource("select-despacho_detalle.sql"),inputParams);  
								Recordset rsSelect_despacho_detalle  = db.get(sqlSelect_despacho_detalle);
								
								rsSelect_despacho_detalle.first();
								
								costo_ml = costo_ml + rsSelect_despacho_detalle.getDouble("dde_costo_ml");
							}
							else
							{
								mensaje = "-*No se registro el detalle del despacho*";
								rc = 1; // no se registro el detalle del despacho.
								str = mensaje;	
								bw.write(str);
								bw.flush();
								bw.newLine();
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
							
							str = "-Actualizando despacho...";	
							bw.write(str);
							bw.flush();
							bw.newLine();
							
							if(rsUpdate_despacho.getRecordCount()>0)
							{
								str = "-*Se actualizo el despacho correctamente*";	
								bw.write(str);
								bw.flush();
								bw.newLine();
								
								String sqlAprobarRequisicion   = getSQL(getResource("update-aprobar_requisicion.sql"),inputParams);
                                db.exec(sqlAprobarRequisicion);
								/* Recordset rsAprobarRequisicion = db.get(sqlAprobarRequisicion); */

                                mensaje = "*Requisicion aprobada exitosamente*";
                                rc = 0;
                                str = mensaje;	
                                bw.write(str);
                                bw.flush();
                                bw.newLine();
								
								/* if(rsAprobarRequisicion.getRecordCount()>0)
								{
									mensaje = "*Requisicion aprobada exitosamente*";
									rc = 0;
									str = mensaje;	
									bw.write(str);
									bw.flush();
									bw.newLine();
								}
								else
								{
									mensaje = "*La requisicion no cambio su estatus a aprobada*";
									rc = 1; // la requisicion no cambio su estatus a aprobada.
									str = mensaje;	
									bw.write(str);
									bw.flush();
									bw.newLine();
								} */
							}
							else
							{
								mensaje = "-*No se actualizo el costo_ml del despacho*";
								rc = 1; // no se actualizo el costo_ml del despacho.
								str = mensaje;	
								bw.write(str);
								bw.flush();
								bw.newLine();
							}
						}
						else
						{
							rc = 1;
							mensaje = "-*No se encontro el despacho*";
							str = mensaje;	
							bw.write(str);
							bw.flush();
							bw.newLine();
						}
					}
					else
					{
						mensaje = "-*No se encontro el detalle de la requisicion*";
						rc = 1; // no se encontro el detalle de la requisicion.
						str = mensaje;	
						bw.write(str);
						bw.flush();
						bw.newLine();
					}
				}
				else
				{
					mensaje = "-*No se registro el despacho*";
					rc = 1; //no se registro el despacho.
					str = mensaje;	
					bw.write(str);
					bw.flush();
					bw.newLine();
				}
			}
			else
			{
				mensaje = "-*No se encontro la requisicion original*";
				rc = 1; //no se encontro la requisicion_cabecera.
				str = mensaje;	
				bw.write(str);
				bw.flush();
				bw.newLine();
			}
		}
		catch(Throwable e)
		{
			throw e;
		}
		
		finally
		{
			inputParams.setValue("mensaje", mensaje);
		}
		
		return rc;
	}
	
	public int despacho(Recordset inputParams, Connection conn,String str, BufferedWriter bw) throws Throwable 
	{
		int rc = 0;	//variable a retornar.
		String mensaje = "";
		String jndiName = (String)getContext().getAttribute("dinamica.security.datasource");
		
		if (jndiName==null)
			throw new Throwable("Context attribute [dinamica.security.datasource] is null, check your security filter configuration.");
		
		//establecer la conexion con la base de datos.
		
			this.setConnection(conn);
		
		//**
			
		try
		{
			str = "*Preparando para despacho";	
			bw.write(str);
			bw.flush();
			bw.newLine();
			
			Db db = getDb(); //objeto de conexion.

			String sqlRequisicion_cabecera   = getSQL(getResource("select-requisicion_cabecera.sql"),inputParams);
			Recordset rsRequisicion_cabecera = db.get(sqlRequisicion_cabecera);
			
			str = "*Consultando requisicion_cabecera...";	
			bw.write(str);
			bw.flush();
			bw.newLine();
			
			if(rsRequisicion_cabecera.getRecordCount()>0)
			{
				str = "*Procesando requisicion_cabecera...";	
				bw.write(str);
				bw.flush();
				bw.newLine();
				
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
				
				str = "*Registrando despacho...";	
				bw.write(str);
				bw.flush();
				bw.newLine();
				
				if(rsDespacho.getRecordCount()>0)
				{
					str = "*Despacho registrado correctamente...";	
					bw.write(str);
					bw.flush();
					bw.newLine();
					
					rsDespacho.first();
					
					if(rsDespacho.getString("smn_despacho_id") != null)
						inputParams.setValue("smn_despacho_id", rsDespacho.getInt("smn_despacho_id"));
					else
						inputParams.setValue("smn_despacho_id", 0);
					
					String sqlRequisicion_detalle   = getSQL(getResource("select-req_detalle.sql"),inputParams);
					Recordset rsRequisicion_detalle = db.get(sqlRequisicion_detalle);
					
					str = "*Consultando requisicion_detalle...";	
					bw.write(str);
					bw.flush();
					bw.newLine();
					
					if(rsRequisicion_detalle.getRecordCount()>0)
					{
						str = "*Total requisicion_detalles encontrados = "+rsRequisicion_detalle.getRecordCount();	
						bw.write(str);
						bw.flush();
						bw.newLine();
						
						double costo_ml = 0.0;
						while(rsRequisicion_detalle.next() != false)
						{
							str = "-Procesando detalle con id = "+rsRequisicion_detalle.getInt("smn_requisicion_detalle_id");	
							bw.write(str);
							bw.flush();
							bw.newLine();
							
							if(rsRequisicion_detalle.getString("smn_item_id") != null)
								inputParams.setValue("smn_item_id", rsRequisicion_detalle.getInt("smn_item_id"));
							else
								inputParams.setValue("smn_item_id", 0);
							
							if(rsRequisicion_detalle.getString("rss_cantidad") != null)
								inputParams.setValue("rss_cantidad", rsRequisicion_detalle.getDouble("rss_cantidad"));
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
							
							String sqlCaracteristica_item    = getSQL(getResource("select-caracteristica_item.sql"),inputParams);  
							Recordset rsCaracteristica_item  = db.get(sqlCaracteristica_item);
							
							str = "-Consultando caracteristica item...";	
							bw.write(str);
							bw.flush();
							bw.newLine();
							
							if(rsCaracteristica_item.getRecordCount()>0)
							{
								rsCaracteristica_item.first();
								
								inputParams.setValue("smn_caracteristica_item_id", rsCaracteristica_item.getInt("smn_caracteristica_item_id"));
							}
							else
							{
								mensaje = "-*No se encontro la caracteristica del item con el item_id = "+inputParams.getValue("smn_item_id")+" *";
								str = mensaje;	
								bw.write(str);
								bw.flush();
								bw.newLine();
								return 1;
							}
							
							String sqlDespacho_detalle   = getSQL(getResource("insert-despacho_detalle.sql"),inputParams);  
							Recordset rsDespacho_detalle = db.get(sqlDespacho_detalle);
							
							str = "-Registrando despacho_detalle...";	
							bw.write(str);
							bw.flush();
							bw.newLine();
							
							if(rsDespacho_detalle.getRecordCount()>0)
							{
								str = "-Despacho_detalle registrado correctamente";	
								bw.write(str);
								bw.flush();
								bw.newLine();
								
								rsDespacho_detalle.first();
								
								inputParams.setValue("smn_despacho_detalle_id", rsDespacho_detalle.getInt("smn_despacho_detalle_id"));
								
								String sqlSelect_despacho_detalle    = getSQL(getResource("select-despacho_detalle.sql"),inputParams);  
								Recordset rsSelect_despacho_detalle  = db.get(sqlSelect_despacho_detalle);
								
								rsSelect_despacho_detalle.first();
								
								costo_ml = costo_ml + rsSelect_despacho_detalle.getDouble("dde_costo_ml");
							}
							else
							{
								mensaje = "-*No se registro el detalle del despacho*";
								rc = 1; // no se registro el detalle del despacho.
								str = mensaje;	
								bw.write(str);
								bw.flush();
								bw.newLine();
								break;
							}
						} //End While		
						
						String sqlSelect_despacho   = getSQL(getResource("select-despacho.sql"),inputParams);
						Recordset rsSelect_despacho = db.get(sqlSelect_despacho);
						
						str = "*Consultando despacho...";	
						bw.write(str);
						bw.flush();
						bw.newLine();
						
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
							
							str = "*Actualizando despacho...";	
							bw.write(str);
							bw.flush();
							bw.newLine();
							
							if(rsUpdate_despacho.getRecordCount()>0)
							{
								mensaje = "*Despacho registrado exitosamente*";
								rc = 0; // no se actualizo el costo_ml del despacho.
								str = mensaje;	
								bw.write(str);
								bw.flush();
								bw.newLine();
							}
							else
							{
								mensaje = "*No se actualizo el costo_ml del despacho*";
								rc = 1; // no se actualizo el costo_ml del despacho.
								str = mensaje;	
								bw.write(str);
								bw.flush();
								bw.newLine();
							}
						}
						else
						{
							rc = 1;
							str = "*No se encontro el despacho*";	
							bw.write(str);
							bw.flush();
							bw.newLine();
						}
					}
					else
					{
						mensaje = "*No se encontro el detalle de la requisicion*";
						rc = 1; // no se encontro el detalle de la requisicion.
						str = mensaje;	
						bw.write(str);
						bw.flush();
						bw.newLine();
					}
				}
				else
				{
					mensaje = "*No se registro el despacho*";
					rc = 1; //no se registro el despacho.
					str = mensaje;	
					bw.write(str);
					bw.flush();
					bw.newLine();
				}
			}
			else
			{
				mensaje = "*No se encontro la requisicion original*";
				rc = 1; //no se encontro la requisicion_cabecera.
				str = mensaje;	
				bw.write(str);
				bw.flush();
				bw.newLine();
			}
		}
		catch(Throwable e)
		{
			throw e;
		}
		
		finally
		{
			inputParams.setValue("mensaje", mensaje);
		}
		
		return rc;
	}
	
	// ** ORDEN COMPRA **
	
	public int ordenCompra(Recordset inputParams, Connection conn, String str, BufferedWriter bw) throws Throwable 
	{
		int rc = 0;	//variable a retornar.
		String mensaje = "";
		String jndiName = (String)getContext().getAttribute("dinamica.security.datasource");
		
		if (jndiName==null)
			throw new Throwable("Context attribute [dinamica.security.datasource] is null, check your security filter configuration.");
		
		//establecer la conexion con la base de datos.
		
			this.setConnection(conn);
		
		//**
		//conn.setAutoCommit(false);
		try
		{
			str = "*Preparando para orden de compra...";	
			bw.write(str);
			bw.flush();
			bw.newLine();
			
			Db db = getDb(); //objeto de conexion.
			
			String sqlReq_detalle   = getSQL(getResource("select-req_detalle.sql"),inputParams); 
			Recordset rsReq_detalle = db.get(sqlReq_detalle); //consultar las req_detalle.
			
			str = "*Consultando requisicion_detalle...";	
			bw.write(str);
			bw.flush();
			bw.newLine();
			
			if(rsReq_detalle.getRecordCount()>0)
			{
				str = "*Total de requisiciones_detalles encontrados = "+rsReq_detalle.getRecordCount();	
				bw.write(str);
				bw.flush();
				bw.newLine();
				
				double ac_monto_ml          = 0.0;
				double ac_monto_impuesto_ml = 0.0;
				double ac_monto_desc_ret_ml = 0.0;
				double ac_monto_neto_ml     = 0.0;
				double ac_monto_ma          = 0.0;
				double ac_monto_impuesto_ma = 0.0;
				double ac_monto_neto_ma     = 0.0;
				
				while(rsReq_detalle.next() != false) //recorre todos los detalles de esa requisicion
				{
					str = "-Procesando detalle con id = "+rsReq_detalle.getInt("smn_requisicion_detalle_id");	
					bw.write(str);
					bw.flush();
					bw.newLine();
					
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
						inputParams.setValue("rss_cantidad", rsReq_detalle.getDouble("rss_cantidad"));
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
					
					str = "-Consultando impuestos y retenciones...";	
					bw.write(str);
					bw.flush();
					bw.newLine();
					
					rsImpuestos_retenciones.first();
					
					if(rsImpuestos_retenciones.getString("rim_monto_impuesto")!=null)
						inputParams.setValue("rim_monto_impuesto", rsImpuestos_retenciones.getDouble("rim_monto_impuesto"));
					else
						inputParams.setValue("rim_monto_impuesto", 0.0);
					
					if(rsImpuestos_retenciones.getString("drc_monto_descuento")!=null)
						inputParams.setValue("drc_monto_descuento", rsImpuestos_retenciones.getDouble("drc_monto_descuento"));
					else
						inputParams.setValue("drc_monto_descuento", 0.0);
					
					String sql = getSQL(getResource("select-descripcion.sql"),inputParams); 
					Recordset rsDescripcion  = db.get(sql); //consultar descripcion de item,servicio y activo fijo.
					
					str = "-Buscando descripcion del producto...";	
					bw.write(str);
					bw.flush();
					bw.newLine();
					
					if(rsDescripcion.getRecordCount()>0)
					{
						rsDescripcion.first();
						inputParams.setValue("ocd_descripcion", rsDescripcion.getString("descripcion"));
					}
					else
					{
						mensaje = "*ERROR, no se encontro descripcion del producto*";
						str = mensaje;	
						bw.write(str);
						bw.flush();
						bw.newLine();
						return 1;
					}
				//**
					
					String sqlexistProv_occ   = getSQL(getResource("select-prov_occ.sql"),inputParams); 
					Recordset rsexistProv_occ  = db.get(sqlexistProv_occ); //consultar si existe una orden de compra cabecera con ese proveedor del detalle.
					
					str = "-Consultando si existe una orden de compra registrada con el proveedor con id = "+inputParams.getValue("smn_proveedor_id");	
					bw.write(str);
					bw.flush();
					bw.newLine();
					
					if(rsexistProv_occ.getRecordCount()>0) 
					{
						//instrucciones si existe la occ (orden de compra cabecera) con ese proveedor.
						
						//inyeccion del id encontrado de occ en el validator.
						inputParams.setValue("smn_orden_compra_cabecera_id", rsexistProv_occ.getInt("smn_orden_compra_cabecera_id"));
					
						String sqlOrden_compra_detalle   = getSQL(getResource("insert-orden_compra_detalle.sql"),inputParams);
						Recordset rsOrden_compra_detalle = db.get(sqlOrden_compra_detalle); //registra orden compra detalle.
						
						str = "-Registrando orden_compra_detalle...";
						bw.write(str);
						bw.flush();
						bw.newLine();
						
						rsexistProv_occ.first();
						
						if(rsOrden_compra_detalle.getRecordCount()>0)
						{
							str = "-Orden_compra_detalle registrada correctamente";
							bw.write(str);
							bw.flush();
							bw.newLine();
							
							rsOrden_compra_detalle.first();
							
							//inyeccion del id del detalle de orden de compra en el validator.
							inputParams.setValue("smn_orden_compra_detalle_id", rsOrden_compra_detalle.getInt("smn_orden_compra_detalle_id"));
							
							String sqlConsultarDetalle_oc = getSQL(getResource("select-campos_calculados_ocd.sql"),inputParams);
							Recordset rsConsultarDetalle_oc = db.get(sqlConsultarDetalle_oc);
							
							str = "-Buscando campos calculados de la orden_compra_detalle";
							bw.write(str);
							bw.flush();
							bw.newLine();
							
							if(rsConsultarDetalle_oc.getRecordCount()>0)
							{
								str = "-Calculando montos de la orden_compra";
								bw.write(str);
								bw.flush();
								bw.newLine();
								
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
									
								str = "-Actualizando montos en la orden_compra_detalle...";
								bw.write(str);
								bw.flush();
								bw.newLine();
								
								if(rsActualizarDetalle_oc.getRecordCount()>0)
								{
									str = "-Montos de la orden_compra_detalle actualizados correctamente";
									bw.write(str);
									bw.flush();
									bw.newLine();
									
									registrarImpuesto(inputParams,conn,str,bw); // registra orden compra impuestos
									registrarDescuentos_retenciones(inputParams,conn,str,bw); //registra descuentos y retenciones
									registrarFecha_entrega(inputParams,conn,"A",str,bw); //registra orden compra fecha entrega.
											
									String sqlDetalle_oc   = getSQL(getResource("select-orden_compra_detalle.sql"),inputParams);
									Recordset rsDetalle_oc = db.get(sqlDetalle_oc); //busca el detalle de la orden de compra.
											
									str = "-Consultando orden_compra_detalle...";
									bw.write(str);
									bw.flush();
									bw.newLine();
									
									if(rsDetalle_oc.getRecordCount()>0)
									{
										str = "-Total detalles encontrados = "+rsDetalle_oc.getRecordCount();
										bw.write(str);
										bw.flush();
										bw.newLine();
										
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
										mensaje = "*No se encontro el detalle de orden de compra*";
										rc = 1; //no se encontro el detalle de orden de compra.
										str = mensaje;
										bw.write(str);
										bw.flush();
										bw.newLine();
										break;
									}
								}
								else
								{
									mensaje = "*No se actualizaron los campos calculados del detalle de la orden de compra*";
									rc=1; // no se actualizaron los campos calculados de ocd.
									str = mensaje;
									bw.write(str);
									bw.flush();
									bw.newLine();
									break;
								}
							}
							else
							{
								mensaje = "*No se encontraron campos calculados del detalle de la orden de compra*";
								rc=1; //no se encontraron campos calculados de ocd(orden de compra detalle).
								str = mensaje;
								bw.write(str);
								bw.flush();
								bw.newLine();
								break;
							}
						}
						else
						{
							mensaje = "*No se registro el detalle de la orden de compra*";
							rc = 1; //no se registro el detalle de la orden de compra.
							str = mensaje;
							bw.write(str);
							bw.flush();
							bw.newLine();
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
							 ac_monto_neto_ma     = 0.0;
					    //**
						 
						String sqlRequsicion_cabecera    = getSQL(getResource("select-requisicion_cabecera.sql"),inputParams);
						Recordset rsRequisicion_cabecera = db.get(sqlRequsicion_cabecera); //buscar la requisicion cabecera.
						
						str = "*Consultando requisicion_cabecera...";
						bw.write(str);
						bw.flush();
						bw.newLine();
						
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
								
								sql = getSQL(getResource("select-smn_documento.sql"), inputParams);
								Recordset rsDoc = db.get(sql);
								
								str = "*Consultando documento...";
								bw.write(str);
								bw.flush();
								bw.newLine();
								
								if(rsDoc.getRecordCount()>0)
								{
									rsDoc.first();
									
									if(rsDoc.getString("smn_documentos_id") != null)
										inputParams.setValue("smn_documento_id", rsDoc.getInt("smn_documentos_id"));
									else
									{
										mensaje = "*El documento con naturaleza orden de compra esta vacio*";
										rc = 1;
										str = mensaje;
										bw.write(str);
										bw.flush();
										bw.newLine();
										return rc;
									}
								}
								else
								{
									mensaje = "*El documento con naturaleza orden de compra no existe*";
									rc = 1;
									str = mensaje;
									bw.write(str);
									bw.flush();
									bw.newLine();
									return rc;
								}
								
								sql = getSQL(getResource("select-smn_proveedor.sql"), inputParams);
								Recordset rsProveedor = db.get(sql);
								
								str = "*Consultando proveedor...";
								bw.write(str);
								bw.flush();
								bw.newLine();
								
								if(rsProveedor.getRecordCount()>0)
								{
									str = "*Total proveedores encontrados = "+rsProveedor.getRecordCount();
									bw.write(str);
									bw.flush();
									bw.newLine();
									
									rsProveedor.first();
									
									if(rsProveedor.getString("smn_proveedor_rf") != null)
										inputParams.setValue("smn_proveedor_rf", rsProveedor.getInt("smn_proveedor_rf"));
									else
									{
										mensaje = "*El proveedor relacionado al producto esta vacio*";
										rc = 1;
										str = mensaje;
										bw.write(str);
										bw.flush();
										bw.newLine();
										return rc;
									}
								}
								else
								{
									mensaje = "*El proveedor relacionado al producto no existe*";
									rc = 1;
									str = mensaje;
									bw.write(str);
									bw.flush();
									bw.newLine();
									return rc;
								}
								
								sql = getSQL(getResource("select-smn_forma_pago.sql"), inputParams);
								Recordset rsForma_pago = db.get(sql);
								
								str = "*Consultando formas de pago...";
								bw.write(str);
								bw.flush();
								bw.newLine();
								
								if(rsForma_pago.getRecordCount()>0)
								{
									rsForma_pago.first();
									
									if(rsForma_pago.getString("smn_forma_pago_rf") != null)
										inputParams.setValue("smn_forma_pago_rf", rsForma_pago.getInt("smn_forma_pago_rf"));
									else
									{
										mensaje = "*La forma de pago relacionada al proveedor esta vacia*";
										rc = 1;
										str = mensaje;
										bw.write(str);
										bw.flush();
										bw.newLine();
										return rc;
									}
								}
								else
								{
									mensaje = "*La forma de pago relacionada al proveedor no existe*";
									rc = 1;
									str = mensaje;
									bw.write(str);
									bw.flush();
									bw.newLine();
									return rc;
								}
								
								sql = getSQL(getResource("select-smn_cond_pago.sql"), inputParams);
								Recordset rsCond_pago = db.get(sql);
								
								str = "*Consultando condicion de pago...";
								bw.write(str);
								bw.flush();
								bw.newLine();
								
								if(rsCond_pago.getRecordCount()>0)
								{
									rsCond_pago.first();
									
									if(rsCond_pago.getString("aux_cond_pago_rf") != null)
										inputParams.setValue("aux_cond_pago_rf", rsCond_pago.getInt("aux_cond_pago_rf"));
									else
									{
										mensaje = "*La condicion de pago relacionada al proveedor esta vacia*";
										rc = 1;
										str = mensaje;
										bw.write(str);
										bw.flush();
										bw.newLine();
										return rc;
									}
								}
								else
								{
									mensaje = "*La condicion de pago relacionada al proveedor no existe*";
									rc = 1;
									str = mensaje;
									bw.write(str);
									bw.flush();
									bw.newLine();
									return rc;
								}
							//*
								
							String sqlOrden_compra   = getSQL(getResource("insert-orden_compra.sql"), inputParams);
							Recordset rsOrden_compra = db.get(sqlOrden_compra); //registra la orden de compra.
							
							str = "*Registrando orden de compra...";
							bw.write(str);
							bw.flush();
							bw.newLine();
							
							if(rsOrden_compra.getRecordCount()>0)
							{
								str = "*Orden de compra registrada correctamente";
								bw.write(str);
								bw.flush();
								bw.newLine();
								
								rsOrden_compra.first();
								
								inputParams.setValue("smn_orden_compra_cabecera_id", rsOrden_compra.getInt("orden_compra_id"));
								
								String sqlOrden_compra_detalle   = getSQL(getResource("insert-orden_compra_detalle.sql"),inputParams);
								Recordset rsOrden_compra_detalle = db.get(sqlOrden_compra_detalle); //registra orden compra detalle.
								
								str = "*Registrando orden_compra_detalle...";
								bw.write(str);
								bw.flush();
								bw.newLine();
								
								if(rsOrden_compra_detalle.getRecordCount()>0)
								{
									str = "*orden_compra_detalle registrada correctamente";
									bw.write(str);
									bw.flush();
									bw.newLine();
									
									rsOrden_compra_detalle.first();
									
									//inyeccion del id del detalle de orden de compra en el validator.
									
									inputParams.setValue("smn_orden_compra_detalle_id", rsOrden_compra_detalle.getInt("smn_orden_compra_detalle_id"));
									
									String sqlConsultarDetalle_oc = getSQL(getResource("select-campos_calculados_ocd.sql"),inputParams);
									Recordset rsConsultarDetalle_oc = db.get(sqlConsultarDetalle_oc);
									
									str = "*Consultando campos calculados de orden_compra_detalle...";
									bw.write(str);
									bw.flush();
									bw.newLine();
									
									if(rsConsultarDetalle_oc.getRecordCount()>0)
									{
										str = "*Calculando montos de orden_compra_detalle...";
										bw.write(str);
										bw.flush();
										bw.newLine();
										
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
											
										str = "*Actualizando montos de orden_compra_detalle...";
										bw.write(str);
										bw.flush();
										bw.newLine();
										
										if(rsActualizarDetalle_oc.getRecordCount()>0)
										{
											str = "*Montos de orden_compra_detalle actualizados correctamente";
											bw.write(str);
											bw.flush();
											bw.newLine();
											
											 registrarImpuesto(inputParams,conn,str,bw); //orden compra impuestos
											 registrarDescuentos_retenciones(inputParams,conn,str,bw); //descuentos y retenciones
											 registrarFecha_entrega(inputParams,conn,"A",str,bw); //orden compra fecha entrega.
											
											 String sqlDetalle_oc   = getSQL(getResource("select-orden_compra_detalle.sql"),inputParams);
											 Recordset rsDetalle_oc = db.get(sqlDetalle_oc); //busca el detalle de la orden de compra.
														
											 str = "*Consultando orden_compra_detalle...";
											 bw.write(str);
											 bw.flush();
											 bw.newLine();
											 
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
												mensaje = "*No se encontro el detalle de orden de compra*";
												rc = 1; //no se encontro el detalle de orden de compra.
												str = mensaje;
												bw.write(str);
												bw.flush();
												bw.newLine();
												break;
											}
										}
										else
										{
											mensaje = "*No se actualizaron los campos calculados del detalle de orden de compra*"; 
											rc = 1; // no se actualizaron los campos calculados de ocd.
											str = mensaje;
											bw.write(str);
											bw.flush();
											bw.newLine();
											break;
										}
									}
									else
									{
										mensaje = "*No se encontraron campos calculados del detalle de orden de compra*"; 
										rc = 1; //no se encontraron campos calculados de ocd(orden de compra detalle).
										str = mensaje;
										bw.write(str);
										bw.flush();
										bw.newLine();;
										break;
									}
								}
								else
								{
									mensaje = "*No se registro el detalle de la orden de compra*";
									rc = 1; //no se registro el detalle de la orden de compra.
									str = mensaje;
									bw.write(str);
									bw.flush();
									bw.newLine();
									break;
								}
							}
							else
							{
								mensaje = "no se registro la orden de compra";
								rc = 23; // no se registro la occ
								System.out.println(mensaje);
								break;
							}
						}
						else
						{
							mensaje = "*No se encontro la requisicion*";
							rc = 1; //no se encontro la requisicion_cabecera.
							str = mensaje;
							bw.write(str);
							bw.flush();
							bw.newLine();
							break;
						}
					}
					
					if(rc == 0)
					{
						String sqlCabecera_oc  = getSQL(getResource("update-orden_compra_cabecera.sql"),inputParams);
						Recordset rsCabecera_oc = db.get(sqlCabecera_oc); //actualiza los campos calculados de la tabla smn_orden_compra_cabecera.
						
						str = "*Actualizando orden_compra_cabecera...";
						bw.write(str);
						bw.flush();
						bw.newLine();
						
						if(rsCabecera_oc.getRecordCount()>0)
						{
							mensaje = "*Se actualizaron los campos calculados de orden de compra exitosamente*";
							rc = 0; //actualizacion de los campos calculados de orden compra cabecera exitoso.
							str = mensaje;
							bw.write(str);
							bw.flush();
							bw.newLine();
						}
						else
						{
							mensaje = "*Ocurrio un error al actualizar los campos calculado de la orden de compra*";
							rc = 1; //ocurrio un error al actualizar los campos calculado de la tabla smn_orden_compra_cabecera.
							str = mensaje;
							bw.write(str);
							bw.flush();
							bw.newLine();
							break;
						}
					}
					else
					{
						mensaje = "*No se actualizaron los campos calculados de la orden de compra*";
						rc = 1; //no se actualizaron los campos calculados de la occ.
						str = mensaje;
						bw.write(str);
						bw.flush();
						bw.newLine();
						break;
					}
					
				}//END WHILE
				
				if(rc == 0)
				{
					String sqlAprobarRequisicion   = getSQL(getResource("update-aprobar_requisicion.sql"),inputParams);
                    db.exec(sqlAprobarRequisicion);
					/* Recordset rsAprobarRequisicion = db.get(sqlAprobarRequisicion); */
					
					str = "*Actualizando requisicion...";
					bw.write(str);
					bw.flush();
					bw.newLine();

                    mensaje = "*Requisicion aprobada exitosamente*";
                    rc = 0;
                    str = mensaje;
                    bw.write(str);
                    bw.flush();
                    bw.newLine();
					
					/* if(rsAprobarRequisicion.getRecordCount()>0)
					{
						mensaje = "*Requisicion aprobada exitosamente*";
						rc = 0;
						str = mensaje;
						bw.write(str);
						bw.flush();
						bw.newLine();
					}
					else
					{
						mensaje = "*La requisicion no cambio su estatus a aprobada*";
						rc = 1; // la requisicion no cambio su estatus a aprobada.
						str = mensaje;
						bw.write(str);
						bw.flush();
						bw.newLine();
					}	 */
				}
			}
			else
			{
				mensaje = "*No se encontro el detalle de la requisicion*";
				rc = 1; // no se encontro el detalle de la requisicion.
				str = mensaje;
				bw.write(str);
				bw.flush();
				bw.newLine();
			}
		}
		catch(Throwable e)
		{
			throw e;
		}
		
		finally
		{
			inputParams.setValue("mensaje", mensaje);
		}
		
		return rc;
	}
	
	public int registrarImpuesto(Recordset inputParams, Connection conn, String str, BufferedWriter bw) throws Throwable 
	{
		int rc = 0;	//variable a retornar.
		String mensaje = ""; //almacen el mensaje.
		
		String jndiName = (String)getContext().getAttribute("dinamica.security.datasource");
		
		if (jndiName==null)
			throw new Throwable("Context attribute [dinamica.security.datasource] is null, check your security filter configuration.");
		
		//establecer la conexion con la base de datos.
		
			this.setConnection(conn);
		
		//**
			
		try
		{
			str = "*Preparando para registrar impuestos...";
			bw.write(str);
			bw.flush();
			bw.newLine();
			
			Db db = getDb(); //objeto de conexion.
			String sqlDetalle_impuesto    = getSQL(getResource("select-req_detalle_impuesto.sql"),inputParams);
			Recordset rsDetalle_impuesto  = db.get(sqlDetalle_impuesto); //consulta el detalle de impuesto.
			
			str = "*Consultando detalles de impuesto...";
			bw.write(str);
			bw.flush();
			bw.newLine();
			
			if(rsDetalle_impuesto.getRecordCount()>0)
			{
				str = "*Total de detalles impuestos encontrados = "+rsDetalle_impuesto.getRecordCount();
				bw.write(str);
				bw.flush();
				bw.newLine();
				
				while(rsDetalle_impuesto.next()!=false)
				{
					str = "*Procesando detalle con id = "+rsDetalle_impuesto.getInt("smn_req_detalle_impuesto_id");
					bw.write(str);
					bw.flush();
					bw.newLine();
					
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
					
					str = "*Registrando orden_compra_impuesto...";
					bw.write(str);
					bw.flush();
					bw.newLine();
					
					if(rsOrdenCompraImp.getRecordCount()>0)
					{
						mensaje = "*Impuesto registrado exitosamente*";
						rc=0; //registro de impuesto exitoso.
						str = mensaje;
						bw.write(str);
						bw.flush();
						bw.newLine();
					}
					else
					{
						mensaje = "*Error al registrar impuesto*";
						rc=1; //error de impuesto.
						str = mensaje;
						bw.write(str);
						bw.flush();
						bw.newLine();
					}
				}//end while
			}
			else
			{
				mensaje = "*No se encontro el detalle impuesto*";
				rc=1; //no se encontro el detalle impuesto
				str = mensaje;
				bw.write(str);
				bw.flush();
				bw.newLine();
			}
		}
		catch(Throwable e)
		{
			throw e;
		}
		finally
		{
			inputParams.setValue("mensaje", mensaje);
		}
		return rc;
	}
	
	public int registrarDescuentos_retenciones(Recordset inputParams, Connection conn, String str, BufferedWriter bw) throws Throwable 
	{
		int rc = 0;	//variable a retornar.
		String mensaje = "";
		
		String jndiName = (String)getContext().getAttribute("dinamica.security.datasource");
		
		if (jndiName==null)
			throw new Throwable("Context attribute [dinamica.security.datasource] is null, check your security filter configuration.");
		
		//establecer la conexion con la base de datos.
		
			this.setConnection(conn);
		
		//**
			
		try
		{
			str = "*Preparando para registrar descuentos y retenciones...";
			bw.write(str);
			bw.flush();
			bw.newLine();
			
			Db db = getDb(); //objeto de conexion.
			
			String sqlDescuentosRetenciones_req   = getSQL(getResource("select-descuento_retenciones_req.sql"),inputParams);
			Recordset rsDescuentosRetenciones_req = db.get(sqlDescuentosRetenciones_req); //consulta los descuentos y retenciones de la requisicion.
			
			str = "*Consultando requisicion_descuentos_retenciones...";
			bw.write(str);
			bw.flush();
			bw.newLine();
			
			if(rsDescuentosRetenciones_req.getRecordCount()>0)
			{
				str = "*Total descuentos y retenciones encontrados = "+rsDescuentosRetenciones_req.getRecordCount();
				bw.write(str);
				bw.flush();
				bw.newLine();
				
				while(rsDescuentosRetenciones_req.next()!=false)
				{
					str = "*Procesando requisicion descuento y retencion con id = "+rsDescuentosRetenciones_req.getInt("smn_req_detalle_dcto_retenc_id");
					bw.write(str);
					bw.flush();
					bw.newLine();
					
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
					
					str = "*Registrando descuentos y retenciones de la orden de compra...";
					bw.write(str);
					bw.flush();
					bw.newLine();
					
					if(rsDescuentosRetenciones_oc.getRecordCount()>0)
					{
						mensaje = "*Registro descuento y retenciones exitoso*";
						rc=0; //registro descuento y retenciones exitoso.
						str = mensaje;
						bw.write(str);
						bw.flush();
						bw.newLine();
					}
					else
					{
						mensaje ="*No se registraron los descuentos y retenciones*";
						rc=1; //no se registraron los descuentos y retenciones.
						str = mensaje;
						bw.write(str);
						bw.flush();
						bw.newLine();
					}
				}//end while
			}
			else
			{
				mensaje ="*No se encontraron los descuentos y retenciones de requisicion*";
				rc=1; //no se encontraron los descuentos y retenciones de requisicion.
				str = mensaje;
				bw.write(str);
				bw.flush();
				bw.newLine();
			}
		}
		catch(Throwable e)
		{
			throw e;
		}
		finally
		{
			inputParams.setValue("mensaje", mensaje);
		}
		
		return rc;
	}
	
	public int registrarFecha_entrega(Recordset inputParams, Connection conn, String version, String str, BufferedWriter bw) throws Throwable 
	{
		int rc = 0;	//variable a retornar.
		String mensaje = "";
		
		String jndiName = (String)getContext().getAttribute("dinamica.security.datasource");
		
		if (jndiName==null)
			throw new Throwable("Context attribute [dinamica.security.datasource] is null, check your security filter configuration.");
		
		//establecer la conexion con la base de datos.
		
			this.setConnection(conn);
		
		//**
			
		try
		{
			str = "*Preparando para registrar fecha_entrega";
			bw.write(str);
			bw.flush();
			bw.newLine();
			
			Db db = getDb(); //objeto de conexion.
			
			String sqlFechaEntrega_req   = getSQL(getResource("select-fecha_entrega_req.sql"),inputParams);
			Recordset rsFechaEntrega_req = db.get(sqlFechaEntrega_req); //consulta la fecha de entrega de la requisicion.
			
			str = "*Consultando fecha de entrega de requisicion...";
			bw.write(str);
			bw.flush();
			bw.newLine();
			
			if(rsFechaEntrega_req.getRecordCount()>0)
			{
				str = "*Total de fecha_entregas encontradas = "+rsFechaEntrega_req.getRecordCount();
				bw.write(str);
				bw.flush();
				bw.newLine();
				
				while(rsFechaEntrega_req.next()!=false)
				{
					str = "*Procesando fecha_entrega con id = "+rsFechaEntrega_req.getInt("smn_rel_requisicion_f_entrega_id");
					bw.write(str);
					bw.flush();
					bw.newLine();
					
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
					
					String sqlFechaEntrega;
					Recordset rsFechaEntrega;
					
					if(version.equals("A"))
					{
						sqlFechaEntrega = getSQL(getResource("insert-fecha_entrega_oc.sql"),inputParams);
						rsFechaEntrega = db.get(sqlFechaEntrega); //registra la fecha de entrega de la orden de compra.
						
						str = "*Registrando fecha_entrega de la orden_compra...";
						bw.write(str);
						bw.flush();
						bw.newLine();
						
						if(rsFechaEntrega.getRecordCount()>0)
						{
							mensaje = "*Registro exitoso de fecha de entrega*";
							rc = 0; // registro exitoso de fecha de entrega.
							str = mensaje;
							bw.write(str);
							bw.flush();
							bw.newLine();
						}
						else
						{
							mensaje = "*No se registro la fecha entrega*";
							rc = 1; //no se registro la fecha entrega.
							str = mensaje;
							bw.write(str);
							bw.flush();
							bw.newLine();
						}
					}
					else
					if(version.equals("D"))
					{	
						sqlFechaEntrega = getSQL(getResource("insert-fecha_entrega_cotizacion.sql"),inputParams);
						rsFechaEntrega = db.get(sqlFechaEntrega); //registra la fecha de entrega de la cotizacion.
						
						str = "*Registrando fecha de entrega de la cotizacion";
						bw.write(str);
						bw.flush();
						bw.newLine();
						
						if(rsFechaEntrega.getRecordCount()>0)
						{
							mensaje = "*Registro exitoso de fecha de entrega*";
							rc = 0; // registro exitoso de fecha de entrega.
							str = mensaje;
							bw.write(str);
							bw.flush();
							bw.newLine();
						}
						else
						{
							mensaje = "*No se registro la fecha entrega*";
							rc = 1; //no se registro la fecha entrega.
							str = mensaje;
							bw.write(str);
							bw.flush();
							bw.newLine();
						}
					}
					
				}//end while
			}
			else
			{
				mensaje = "*No se encontro la fecha de entrega de la requisicion*";
				rc = 1; //no se encontro la fecha de entrega de requisicion.
				str = mensaje;
				bw.write(str);
				bw.flush();
				bw.newLine();
			}
		}
		catch(Throwable e)
		{
			throw e;
		}
		
		finally
		{
			inputParams.setValue("mensaje",mensaje);
		}
		
		return rc;
	}
}
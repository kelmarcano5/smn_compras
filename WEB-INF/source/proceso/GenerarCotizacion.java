package proceso;
import dinamica.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.sql.DataSource;

public class GenerarCotizacion extends GenericTransaction
{
	public int service(Recordset inputParams) throws Throwable
	{
		int rc = 0; //valor a retornar (1=error/0=ejecucion exitosa).
		String mensaje = ""; //mensaje a mostrar en el front
		
		String jndiName = (String)getContext().getAttribute("dinamica.security.datasource");
		
		if (jndiName==null)
			throw new Throwable("Context attribute [dinamica.security.datasource] is null, check your security filter configuration.");
		
		//establecer la conexion con la base de datos.
		
			DataSource ds = Jndi.getDataSource(jndiName); 
			Connection conn = ds.getConnection();
			this.setConnection(conn);
		
		//**
		
		conn.setAutoCommit(false);
		
		String fechaActual= new SimpleDateFormat("yyyy-MM-dd").format(new Date());

		String sistemaOperativo = System.getProperty("os.name");
		String file;
		  
		if(sistemaOperativo.equals("Windows 7") || sistemaOperativo.equals("Windows 8") || sistemaOperativo.equals("Windows 10")) 
			file =  "C:/log/logCotizacion_"+fechaActual+".txt";
		else
			file = "./logCotizacion_"+fechaActual+".txt";
		
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
			
			String sqlRol   = getSQL(getResource("select-rol.sql"),inputParams); 
			Recordset rsRol = db.get(sqlRol); //VALIDA QUE EL ROL SEA COMPRADOR.
			
			str = "Validando rol...";	
			bw.write(str);
			bw.flush();
			bw.newLine();
			
			if(rsRol.getRecordCount()>0)
			{
				
				str = "Rol correcto";	
				bw.write(str);
				bw.flush();
				bw.newLine();
				
				String sqlCotizacion   = getSQL(getResource("select-cotizacion.sql"),inputParams); 
				Recordset rsCotizacion = db.get(sqlCotizacion); //consulta la cotizacion seleccionada.
				
				str = "Consultando cotizacion...";	
				bw.write(str);
				bw.flush();
				bw.newLine();
				
				if(rsCotizacion.getRecordCount()>0)
				{
					while(rsCotizacion.next() != false) 
					{
						//inyeccion de valores al validator
							
							if(rsCotizacion.getString("smn_documento_id")!=null)
							{
								inputParams.setValue("smn_documento_id", rsCotizacion.getInt("smn_documento_id"));
							}
							else
							{
								mensaje = "no existe un documento asociado con la cotizacion";
								rc = 1;
								str = mensaje;	
								bw.write(str);
								bw.flush();
								bw.newLine();
								break;
							}
						
							if(rsCotizacion.getString("smn_rel_cotizacion_proveedor_id")!=null)
							{
								inputParams.setValue("smn_rel_cotizacion_proveedor_id", rsCotizacion.getInt("smn_rel_cotizacion_proveedor_id"));
							}
							else
							{
								mensaje = "no existe un proveedor asociado con la cotizacion";
								rc = 1;
								str = mensaje;	
								bw.write(str);
								bw.flush();
								bw.newLine();
								break;
							}
							
							if(rsCotizacion.getString("smn_proveedor_id")!=null)
							{
								inputParams.setValue("smn_proveedor_id", rsCotizacion.getInt("smn_proveedor_id"));
							}
							else
							{
								mensaje = "no existe un proveedor asociado con la cotizacion";
								rc = 1;
								str = mensaje;	
								bw.write(str);
								bw.flush();
								bw.newLine();
								break;
							}
							
							if(rsCotizacion.getString("smn_item_id")!=null && rsCotizacion.getInt("smn_item_id")!=0)
							{
								inputParams.setValue("smn_item_id", rsCotizacion.getInt("smn_item_id"));
								inputParams.setValue("producto", rsCotizacion.getInt("smn_item_id"));
							}
							else
								inputParams.setValue("smn_item_id", 0);
							
							if(rsCotizacion.getString("smn_servicio_id")!=null && rsCotizacion.getInt("smn_servicio_id")!=0)
							{
								inputParams.setValue("smn_servicio_id", rsCotizacion.getInt("smn_servicio_id"));
								inputParams.setValue("producto", rsCotizacion.getInt("smn_servicio_id"));
							}
							else
								inputParams.setValue("smn_servicio_id", 0);
							
							if(rsCotizacion.getString("smn_activo_fijo_rf")!=null && rsCotizacion.getInt("smn_activo_fijo_rf")!=0)
							{
								inputParams.setValue("smn_activo_fijo_rf", rsCotizacion.getInt("smn_activo_fijo_rf"));
								inputParams.setValue("producto", rsCotizacion.getInt("smn_activo_fijo_rf"));
							}
							else
								inputParams.setValue("smn_activo_fijo_rf", 0);
							
							String sql = getSQL(getResource("select-smn_rel_proveedor_producto.sql"),inputParams);
							Recordset rs_rpp = db.get(sql);
							
							if(rs_rpp.getRecordCount()>0)
							{
								rs_rpp.first();
								
								if(rs_rpp.getString("rpp_precio_ml") != null)
									inputParams.setValue("rpp_precio_ml", rs_rpp.getDouble("rpp_precio_ml"));
								else
									inputParams.setValue("rpp_precio_ml", 0.0);
								
								if(rs_rpp.getString("rpp_precio_ma") != null)
									inputParams.setValue("rpp_precio_ma", rs_rpp.getDouble("rpp_precio_ma"));
								else
									inputParams.setValue("rpp_precio_ma", 0.0);
							}		
							
							if(rsCotizacion.getString("cot_fecha_requerido")!=null)
							{
								inputParams.setValue("cot_fecha_requerido", rsCotizacion.getDate("cot_fecha_requerido"));
							}
							else
							{
								mensaje = "No existe una fecha requerida para la cotizacion";
								rc = 1;
								str = mensaje;	
								bw.write(str);
								bw.flush();
								bw.newLine();
								break;
							}
							
							sql = getSQL(getResource("select-smn_condicion_financiera.sql"),inputParams);
							Recordset rsCondicionFinanciera = db.get(sql);
							
							if(rsCondicionFinanciera.getRecordCount()>0)
							{
								rsCondicionFinanciera.first();
								
								if(rsCondicionFinanciera.getString("smn_condicion_financiera_rf") != null)
									inputParams.setValue("smn_condicion_financiera_rf", rsCondicionFinanciera.getInt("smn_condicion_financiera_rf"));
								else
									inputParams.setValue("smn_condicion_financiera_rf", 0);
							}	
							
							String sqlConsecutivo   = getSQL(getResource("select-consecutivo.sql"),inputParams);
							Recordset rsConsecutivo = db.get(sqlConsecutivo);
						
							if(rsConsecutivo.getRecordCount()>0)
							{
								rsConsecutivo.first();
								inputParams.setValue("dcc_secuencia", rsConsecutivo.getInt("secuencia"));
							}
							else
							{
								mensaje = "ERROR en la secuencia del documento";
								rc = 1;
								str = mensaje;	
								bw.write(str);
								bw.flush();
								bw.newLine();
								break;
							}
							
							if(inputParams.getString("producto") == null || inputParams.getInt("producto") == 0)
							{
								mensaje = "ERROR no se encontro el producto de la cotizacion";
								rc = 1;
								str = mensaje;	
								bw.write(str);
								bw.flush();
								bw.newLine();
								break;
							}
							
							sql = getSQL(getResource("select-smn_requisicion_detalle.sql"),inputParams);
							Recordset rsRequisicion_detalle = db.get(sql);
							
							str = "Consultando requisicion detalle...";	
							bw.write(str);
							bw.flush();
							bw.newLine();
							
							if(rsRequisicion_detalle.getRecordCount()>0)
							{
								rsRequisicion_detalle.first();
								
								if(rsRequisicion_detalle.getString("rss_cantidad") != null)
									inputParams.setValue("rss_cantidad", rsRequisicion_detalle.getDouble("rss_cantidad"));
								else
								{
									rc = 1;
									inputParams.setValue("mensaje","*La cantidad de la cotizacion esta vacia*");
									str = inputParams.getString("mensaje");	
									bw.write(str);
									bw.flush();
									bw.newLine();
									return rc;
								}
							}
							else
							{
								rc = 1;
								inputParams.setValue("mensaje","*no se encontro el detalle de la requisicion*");
								str = inputParams.getString("mensaje");	
								bw.write(str);
								bw.flush();
								bw.newLine();
								return rc;
							}
							
						//*********************************
							
						rc = registrarOferta(conn,inputParams,str,bw); //registra oferta.
							
						if(rc == 1)
						{
							mensaje = "ERROR al registrar oferta";
							rc = 1;
							str = mensaje;	
							bw.write(str);
							bw.flush();
							bw.newLine();
							break;
						}
					}//END WHILE
					
					if(rc == 0)
					{
						String sqlActualizarEstatus   = getSQL(getResource("update-estatus_cotizacion.sql"),inputParams);
						Recordset rsActualizarEstatus = db.get(sqlActualizarEstatus); //update al estatus de la cotizacion
						
						str = "Actualizando estatus de la cotizacion...";	
						bw.write(str);
						bw.flush();
						bw.newLine();
						
						if(rsActualizarEstatus.getRecordCount()>0)
						{
							rc = 0;
							mensaje = "Cotizacion generada correctamente";
							str = mensaje;	
							bw.write(str);
							bw.flush();
							bw.newLine();
						}
						else
						{
							rc = 1;
							mensaje = "ERROR al actualizar el estatus de la cotizacion";
							str = mensaje;	
							bw.write(str);
							bw.flush();
							bw.newLine();
						}
					}
				}
				else
				{
					rc = 1;
					mensaje = "no se encontro la cotizacion seleccionada";
					str = mensaje;	
					bw.write(str);
					bw.flush();
					bw.newLine();
				}
			}
			else
			{
				rc = 1;
				mensaje = "El rol de usuario debe ser comprador";
				str = mensaje;	
				bw.write(str);
				bw.flush();
				bw.newLine();
			}
			
			inputParams.setValue("mensaje", mensaje);
			
			str = "FIN DEL PROCESO";	
			bw.write(str);
			bw.flush();
			bw.newLine();
			bw.newLine();
		}
		catch(Throwable e)
		{
			conn.rollback();
			throw e;
		}
		finally
		{
			if(rc == 0)
			{
				conn.commit();
				str = "Cambios en la base de datos guardados correctamente";	
				bw.write(str);
				bw.flush();
				bw.newLine();
			}
			else
			{
				conn.rollback();
				str = "Los cambios en la base de datos no se guardaron";	
				bw.write(str);
				bw.flush();
				bw.newLine();
			}
			
			str = "FIN DEL PROCESO";	
			bw.write(str);
			bw.flush();
			bw.newLine();
			bw.newLine();
	        bw.close();
			
			if(conn!=null)
				conn.close();
		}
		
		if(rc==1)
			System.out.print("** ERROR **: " + mensaje);
		
		return rc;
	}
	
	public int registrarOferta(Connection conn, Recordset inputParams, String str, BufferedWriter bw) throws Throwable
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
			Db db = getDb(); //objeto de conexion.
			
			String sqlOferta   = getSQL(getResource("insert-oferta.sql"),inputParams);
			Recordset rsOferta = db.get(sqlOferta); //registra oferta
			
			str = "Registrando oferta...";	
			bw.write(str);
			bw.flush();
			bw.newLine();
			
			if(rsOferta.getRecordCount()>0)
			{
				String sqlDocumento         = getSQL(getResource("update-documento.sql"),inputParams);
				Recordset rsDocumentoUpdate = db.get(sqlDocumento); //update a la secuencia del documento
				
				if(rsDocumentoUpdate.getRecordCount()>0)
				{
					str = "*Oferta registrada correctamente*";	
					bw.write(str);
					bw.flush();
					bw.newLine();
					
					rc = 0;
				}
				else
				{
					rc = 1;
					mensaje = "ocurrio un error al actualizar la secuencia del documento";
					str = mensaje;	
					bw.write(str);
					bw.flush();
					bw.newLine();
				}
			}
			else
			{
				rc = 1; 
				mensaje = "ocurrio un error al registrar la oferta";
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
		
		if(rc==1)
			System.out.print("** ERROR **: " + mensaje);
		
		return rc;
	}
}

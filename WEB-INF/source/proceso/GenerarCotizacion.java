package proceso;
import dinamica.*;
import java.sql.*;
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
			
		try
		{
			Db db = getDb(); //objeto de conexion.
			
			String sqlRol   = getSQL(getResource("select-rol.sql"),inputParams); 
			Recordset rsRol = db.get(sqlRol); //VALIDA QUE EL ROL SEA COMPRADOR.
			
			if(rsRol.getRecordCount()>0)
			{
				String sqlCotizacion   = getSQL(getResource("select-cotizacion.sql"),inputParams); 
				Recordset rsCotizacion = db.get(sqlCotizacion); //consulta la cotizacion seleccionada.
				
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
							
							if(rsCotizacion.getString("cot_fecha_requerido")!=null)
							{
								inputParams.setValue("cot_fecha_requerido", rsCotizacion.getDate("cot_fecha_requerido"));
							}
							else
							{
								mensaje = "No existe una fecha requerida para la cotizacion";
								rc = 1;
								break;
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
								break;
							}
							
							if(inputParams.getString("producto") == null || inputParams.getInt("producto") == 0)
							{
								mensaje = "ERROR no se encontro el producto de la cotizacion";
								rc = 1;
								break;
							}
							
							String sql = getSQL(getResource("select-smn_requisicion_detalle.sql"),inputParams);
							Recordset rsRequisicion_detalle = db.get(sql);
							
							if(rsRequisicion_detalle.getRecordCount()>0)
							{
								rsRequisicion_detalle.first();
								
								if(rsRequisicion_detalle.getString("rss_cantidad") != null)
									inputParams.setValue("rss_cantidad", rsRequisicion_detalle.getDouble("rss_cantidad"));
								else
								{
									rc = 1;
									inputParams.setValue("mensaje","*La cantidad de la cotizacion esta vacia*");
									System.out.println(inputParams.getValue("mensaje"));
									return rc;
								}
								
								if(rsRequisicion_detalle.getString("rrs_precio") != null)
									inputParams.setValue("rrs_precio", rsRequisicion_detalle.getDouble("rrs_precio"));
								else
									inputParams.setValue("rrs_precio", 0.0);
								
								if(rsRequisicion_detalle.getString("rrs_monto") != null)
									inputParams.setValue("rrs_monto", rsRequisicion_detalle.getDouble("rrs_monto"));
								else
									inputParams.setValue("rrs_monto", 0.0);
							}
							else
							{
								rc = 1;
								inputParams.setValue("mensaje","*no se encontro el detalle de la requisicion*");
								System.out.println(inputParams.getValue("mensaje"));
								return rc;
							}
							
						//*********************************
							
							rc = registrarOferta(conn,inputParams); //registra oferta.
							
							if(rc == 1)
							{
								mensaje = "ERROR al registrar oferta";
								rc = 1;
								break;
							}
					}//END WHILE
					
					if(rc == 0)
					{
						String sqlActualizarEstatus   = getSQL(getResource("update-estatus_cotizacion.sql"),inputParams);
						Recordset rsActualizarEstatus = db.get(sqlActualizarEstatus); //update al estatus de la cotizacion
						
						if(rsActualizarEstatus.getRecordCount()>0)
						{
							rc = 0;
							mensaje = "Cotizacion generada correctamente";
						}
						else
						{
							rc = 1;
							mensaje = "ERROR al actualizar el estatus de la cotizacion";
						}
					}
				}
				else
				{
					rc = 1;
					mensaje = "no se encontro la cotizacion seleccionada";
				}
			}
			else
			{
				rc = 1;
				mensaje = "El rol de usuario debe ser comprador";
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
		
		if(rc==1)
			System.out.print("** ERROR **: " + mensaje);
		
		return rc;
	}
	
	public int registrarOferta(Connection conn, Recordset inputParams) throws Throwable
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
			
			if(rsOferta.getRecordCount()>0)
			{
				String sqlDocumento         = getSQL(getResource("update-documento.sql"),inputParams);
				Recordset rsDocumentoUpdate = db.get(sqlDocumento); //update a la secuencia del documento
				
				if(rsDocumentoUpdate.getRecordCount()>0)
				{
					rc = 0;
				}
				else
				{
					rc = 1;
					mensaje = "ocurrio un error al actualizar la secuencia del documento";
				}
			}
			else
			{
				rc = 1; 
				mensaje = "ocurrio un error al registrar la oferta";
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

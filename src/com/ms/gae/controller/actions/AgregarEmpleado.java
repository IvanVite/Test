package com.ms.gae.controller.actions;

//clases para acceder al modelo

//calses para que sea servlet
import java.io.IOException;
import java.io.Serializable;

import javax.servlet.ServletException;


//clases para almacenamiento de blobs
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.ms.gae.model.entity.Departamento;
import com.ms.gae.model.entity.Empleado;
import com.ms.gae.model.jdo.JDO;


//clases para el tratamiento de los datos
import java.util.Map;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class AgregarEmpleado extends Action implements Serializable {
	private static final long serialVersionUID = 1979443514163928074L;
	private static final DateFormat formato_fecha = new SimpleDateFormat("dd/MM/yyyy");
	
	
	@SuppressWarnings("unchecked")
	public void run()throws ServletException, IOException{
		String dir="/index.jsp?pagina=ge";
		BlobstoreService blobstoreService= BlobstoreServiceFactory.getBlobstoreService();
    	Map<String, BlobKey> blobs = blobstoreService.getUploadedBlobs(request);
    	
    	BlobKey blobKey = blobs.get("foto");
    	
		Empleado empleado=new Empleado();
		empleado.setNombre(request.getParameter("nombre"));
		empleado.setApellido(request.getParameter("apellido"));
		empleado.setIdentificacion(Integer.parseInt(request.getParameter("identificacion")));
		
//		jdo= JDO.getInstance(Departamento.class);
//		Departamento departamento=(Departamento)jdo.findOneByParameter("id", "Integer", Integer.parseInt(request.getParameter("departamento")));
//		empleado.setDepartamento(departamento.getKey());
		
		empleado.setSueldo(Double.parseDouble(request.getParameter("sueldo")) );
		empleado.setTransporte(Double.parseDouble(request.getParameter("transporte")));
		try {
			empleado.setFdn(formato_fecha.parse(request.getParameter("fdn")));
		} catch (ParseException e) {e.printStackTrace();}
		empleado.setFotoKey(blobKey);
		dir+="&blob-key="+blobKey.getKeyString();
		jdo= JDO.getInstance(Empleado.class);
		jdo.insert(empleado);
		response.sendRedirect(dir);
	}
	
} 
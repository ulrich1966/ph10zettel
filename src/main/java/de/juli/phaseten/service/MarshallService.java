package de.juli.phaseten.service;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.transform.Result;

import org.eclipse.persistence.jaxb.JAXBContextProperties;
import org.eclipse.persistence.jaxb.MarshallerProperties;

import de.juli.phaseten.model.Model;

public class MarshallService<T> {
	
	public String marshall(Class<T> clazz, Model model) throws JAXBException {
		javax.xml.bind.JAXBContext jaxbContext = JAXBContext.newInstance(clazz.getClass());
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		jaxbMarshaller.setProperty(MarshallerProperties.MEDIA_TYPE, "application/json");
		jaxbMarshaller.setProperty(MarshallerProperties.JSON_INCLUDE_ROOT, true);
		StringWriter sw = new StringWriter();
		return sw.toString();
	}

	public String marshall(Class<T> clazz, List<Model> modelList) throws JAXBException {
		
		javax.xml.bind.JAXBContext jaxbContext = JAXBContext.newInstance(clazz.getClass());
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		jaxbMarshaller.setProperty(MarshallerProperties.MEDIA_TYPE, "application/json");
		jaxbMarshaller.setProperty(MarshallerProperties.JSON_INCLUDE_ROOT, true);
		
		
		//jaxbMarshaller.marshal(modelList, );
		StringWriter sw = new StringWriter();
		return sw.toString();
	}

}

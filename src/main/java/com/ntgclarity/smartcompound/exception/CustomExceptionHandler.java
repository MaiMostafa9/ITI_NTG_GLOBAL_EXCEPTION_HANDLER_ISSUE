package com.ntgclarity.smartcompound.exception;

import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;

import java.util.Iterator;

public class CustomExceptionHandler extends ExceptionHandlerWrapper {

	private ExceptionHandler exceptionHandler;
	// public static final String MESSAGE_DETAIL_KEY = "ip.client.jsftoolkit.messageDetail";

	public CustomExceptionHandler(ExceptionHandler exceptionHandler) {
		this.exceptionHandler = exceptionHandler;
	}

	@Override
	public ExceptionHandler getWrapped() {
		return exceptionHandler;
	}

	@Override
	public void handle() throws FacesException {
		for (Iterator<ExceptionQueuedEvent> i = getUnhandledExceptionQueuedEvents()
				.iterator(); i.hasNext();) {
			ExceptionQueuedEvent event = i.next();
			ExceptionQueuedEventContext context = (ExceptionQueuedEventContext) event
					.getSource();

			FacesContext fc = FacesContext.getCurrentInstance();
			Throwable t = context.getException();
			try {
				if (t instanceof AbortProcessingException) {
					//System.out.println("An unexpected exception has occurred by event listener(s)"+t.getMessage()+"local  "+t.getLocalizedMessage());
					//fc.getExternalContext().getSessionMap().put(CustomExceptionHandler.MESSAGE_DETAIL_KEY,t.getLocalizedMessage());
					fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,t.getLocalizedMessage(),""));
			} 
			}finally {
				i.remove();
			}

		}
	}
}
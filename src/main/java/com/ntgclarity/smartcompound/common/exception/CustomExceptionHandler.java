package com.ntgclarity.smartcompound.common.exception;

import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ntgclarity.smartcompound.portal.base.BaseBean;

import java.util.Iterator;

/**
 * 
 * @author Mai
 */
public class CustomExceptionHandler extends ExceptionHandlerWrapper {

	static final Logger logger = LogManager.getLogger(CustomExceptionHandler.class.getName());

	private ExceptionHandler exceptionHandler;
	private BaseBean baseBean;

	public CustomExceptionHandler(ExceptionHandler exceptionHandler) {
		this.exceptionHandler = exceptionHandler;
	}

	@Override
	public ExceptionHandler getWrapped() {
		return exceptionHandler;
	}

	@Override
	public void handle() throws FacesException {
		logger.entry();
		baseBean=new BaseBean();
		for (Iterator<ExceptionQueuedEvent> i = getUnhandledExceptionQueuedEvents().iterator(); i.hasNext();) {
			ExceptionQueuedEvent event = i.next();
			ExceptionQueuedEventContext context = (ExceptionQueuedEventContext) event.getSource();

			//FacesContext fc = FacesContext.getCurrentInstance();
			Throwable t = context.getException();
			try {
				// if (t instanceof AbortProcessingException) {
				// System.out.println("An unexpected exception has occurred by event listener(s)"+t.getMessage()+"local  "+t.getLocalizedMessage());
				baseBean.addInfoMessage(t.getLocalizedMessage());
			//	fc.addMessage(null, new FacesMessage(	FacesMessage.SEVERITY_INFO,"",""));
				// }
			} finally {
				i.remove();
			}

		}
	}

}
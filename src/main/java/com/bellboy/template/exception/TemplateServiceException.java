package com.bellboy.template.exception;

/**
 * TODO - Change the class and package name according to service.
 * <p>
 * A custom exception class.
 */
public class TemplateServiceException extends RuntimeException {

  public TemplateServiceException(String message, Throwable ex) {
    super(message, ex);
  }

  public TemplateServiceException(String message) {
    super(message);
  }

  public TemplateServiceException(Throwable ex) {
    super(ex);
  }

}

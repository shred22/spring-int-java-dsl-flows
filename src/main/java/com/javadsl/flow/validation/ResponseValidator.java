package com.javadsl.flow.validation;

import org.springframework.messaging.Message;

public interface ResponseValidator {
  Boolean validate(Message<?> response);
}

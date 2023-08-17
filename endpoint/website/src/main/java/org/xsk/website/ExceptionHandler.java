package org.xsk.website;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.xsk.domain.common.DomainException;

@Slf4j
@RestControllerAdvice(basePackages = "org.xsk.website")
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler({DomainException.class})
    public ResponseEntity<String> domainException(DomainException e) {
        log.warn("domainException", e);
        return ResponseEntity.status(HttpStatus.OK.value()).body(e.getClass().getSimpleName() + ": " + e.getMessage());
    }
}

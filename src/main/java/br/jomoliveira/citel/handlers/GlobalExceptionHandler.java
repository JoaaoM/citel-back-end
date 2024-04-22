package br.jomoliveira.citel.handlers;


import br.jomoliveira.citel.dtos.ErroDTO;
import br.jomoliveira.citel.exceptions.ImportacaoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler
    public ResponseEntity<ErroDTO> handleGenericError(ImportacaoException e){
        ErroDTO erroDTO = new ErroDTO(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erroDTO);
    }


}

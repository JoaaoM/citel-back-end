package br.jomoliveira.citel.controllers;

import br.jomoliveira.citel.dtos.CandidatosPorEstadoDTO;
import br.jomoliveira.citel.dtos.ImcMedioPorFaixaEtariaDTO;
import br.jomoliveira.citel.dtos.PessoaDTO;
import br.jomoliveira.citel.services.PessoaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/importar")
public class BancoDeSangueController {
    private final PessoaService pessoaService;
    public BancoDeSangueController(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }
    @PostMapping
    @Transactional
    public ResponseEntity<String> importarPessoas(@RequestParam("jsonFile") MultipartFile file) {
        try {
            String json = new String(file.getBytes());
            pessoaService.salvar(_mapearJsonParaPessoaDTO(json));
            return ResponseEntity.status(HttpStatus.CREATED).body("Pessoas importadas com sucesso.");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao importar pessoas.");
        }
    }

    @GetMapping
    public List<CandidatosPorEstadoDTO> candidatosPorEstado () {
        return pessoaService.getAll();
    }

    @GetMapping(path = "imc")
    public List<ImcMedioPorFaixaEtariaDTO> imcMedioPorFaixaEtariaDTOList(){
        return pessoaService.getAlldsada();
    }

    private List<PessoaDTO> _mapearJsonParaPessoaDTO(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return Arrays.asList(mapper.readValue(json, PessoaDTO[].class));
    }

}

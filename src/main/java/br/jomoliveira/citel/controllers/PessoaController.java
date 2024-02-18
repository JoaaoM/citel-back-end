package br.jomoliveira.citel.controllers;

import br.jomoliveira.citel.dtos.*;
import br.jomoliveira.citel.services.ImportacaoService;
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
@RequestMapping("/api/pessoas")
public class PessoaController {
    private final PessoaService pessoaService;
    private final ImportacaoService importacaoService;
    public PessoaController(PessoaService pessoaService, ImportacaoService importacaoService) {
        this.pessoaService = pessoaService;
        this.importacaoService = importacaoService;
    }
    @PostMapping(path = "/importacao")
    @Transactional
    public ResponseEntity<String> importarPessoas(@RequestParam("jsonFile") MultipartFile file) {
        try {
            String json = new String(file.getBytes());
            importacaoService.salvar(file);
            pessoaService.salvar(_mapearJsonParaPessoaDTO(json), file.getName());
            return ResponseEntity.status(HttpStatus.CREATED).body("Pessoas importadas com sucesso.");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao importar pessoas.");
        }
    }
    @GetMapping(path = "/candidatos-por-estado")
    public List<CandidatosPorEstadoDTO> obterCandidatosPorEstado () {return pessoaService.obterCandidatosPorEstado();}
    @GetMapping(path = "/imc-medio-por-faixa-etaria")
    public List<ImcMedioPorFaixaEtariaDTO> obterImcMedioPorFaixaEtaria(){return pessoaService.obterImcMedioPorFaixaEtaria();}
    @GetMapping(path = "/doadores-por-receptor")
    public List<DoadoresPorReceptorDTO> obterDoadoresPorReceptor () {return pessoaService.obterDoadoresPorReceptor();}
    @GetMapping(path = "/media-por-tipo-sanguineo")
    public List<MediaParaCadaTipoSanguineoDTO> obterMediaPorTipoSanguineo () {return pessoaService.obterMediaPorTipoSanguineo();}
    @GetMapping(path = "/obesidade-por-sexo")
    public List<ObesidadePorSexoDTO> obterObesidadePorSexo () {return pessoaService.obterObesidadePorSexo();}
    private List<PessoaDTO> _mapearJsonParaPessoaDTO(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return Arrays.asList(mapper.readValue(json, PessoaDTO[].class));
    }

}

package com.eglise.secretariat.document;

import com.eglise.model.Fidele;
import com.eglise.secretariat.document.dto.LettreRecommandationRequestDto;
import com.eglise.secretariat.fidele.FideleRepository;
import com.eglise.secretariat.shared.exception.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {

    private final FideleRepository fideleRepository;
    private final FidelePdfExporter fidelePdfExporter;
    private final LetterExporter letterExporter;

    public DocumentController(FideleRepository fideleRepository,
                              FidelePdfExporter fidelePdfExporter,
                              LetterExporter letterExporter) {
        this.fideleRepository = fideleRepository;
        this.fidelePdfExporter = fidelePdfExporter;
        this.letterExporter = letterExporter;
    }

    // 1. Exporter la fiche d'inscription au format PDF
    @GetMapping(value = "/fidele/{id}/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> getFidelePdf(@PathVariable Long id) {
        Fidele fidele = fideleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fidèle non trouvé avec l'ID : " + id));

        byte[] pdfBytes = fidelePdfExporter.generateFidelePdf(fidele);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.builder("inline")
                .filename("fiche_fidele_" + id + ".pdf")
                .build());

        return ResponseEntity.ok().headers(headers).body(pdfBytes);
    }

    // 2. Générer la Lettre de Recommandation sortante au format PDF
    @PostMapping(value = "/lettre-recommandation/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> generateLettrePdf(@Valid @RequestBody LettreRecommandationRequestDto request) {
        Fidele fidele = fideleRepository.findById(request.fideleId())
                .orElseThrow(() -> new ResourceNotFoundException("Fidèle non trouvé avec l'ID : " + request.fideleId()));

        byte[] pdfBytes = letterExporter.generateLettreRecommandationPdf(fidele, request);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.builder("inline")
                .filename("lettre_recommandation_" + fidele.getId() + ".pdf")
                .build());

        return ResponseEntity.ok().headers(headers).body(pdfBytes);
    }
}

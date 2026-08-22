package com.eglise.secretariat.document;

import com.eglise.model.Fidele;
import com.eglise.secretariat.document.dto.LettreRecommandationRequestDto;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;

@Component
public class LetterExporter {

    private final TemplateEngine templateEngine;

    public LetterExporter(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public byte[] generateLettreRecommandationPdf(Fidele fidele, LettreRecommandationRequestDto request) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Context context = new Context();
            context.setVariable("fidele", fidele);
            context.setVariable("request", request);
            context.setVariable("today", java.time.LocalDate.now());

            // Rendu HTML via Thymeleaf
            String htmlContent = templateEngine.process("lettre_recommandation", context);

            // Conversion HTML -> PDF via Flying Saucer avec résolution du baseUrl vers les ressources statiques
            ITextRenderer renderer = new ITextRenderer();
            java.net.URL staticUrl = LetterExporter.class.getResource("/static/");
            String baseUrl = staticUrl != null ? staticUrl.toString() : LetterExporter.class.getResource("/").toString();
            renderer.setDocumentFromString(htmlContent, baseUrl);
            renderer.layout();
            renderer.createPDF(out);

            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la génération de la lettre de recommandation PDF", e);
        }
    }
}
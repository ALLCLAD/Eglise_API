package com.eglise.secretariat.document;

import com.eglise.model.Fidele;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;
import java.io.ByteArrayOutputStream;


@Component
public class FidelePdfExporter {

    private final TemplateEngine templateEngine;

    public FidelePdfExporter(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public byte[] generateFidelePdf(Fidele fidele) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Context context = new Context();
            context.setVariable("fidele", fidele);
            context.setVariable("today", java.time.LocalDate.now());

            // Rendu HTML via Thymeleaf
            String htmlContent = templateEngine.process("fiche_inscription", context);

            // Conversion HTML -> PDF via Flying Saucer avec résolution du baseUrl vers les ressources statiques
            ITextRenderer renderer = new ITextRenderer();
            java.net.URL staticUrl = FidelePdfExporter.class.getResource("/static/");
            String baseUrl = staticUrl != null ? staticUrl.toString() : FidelePdfExporter.class.getResource("/").toString();
            renderer.setDocumentFromString(htmlContent, baseUrl);
            renderer.layout();
            renderer.createPDF(out);

            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la génération du PDF de la fiche d'inscription", e);
        }
    }
}
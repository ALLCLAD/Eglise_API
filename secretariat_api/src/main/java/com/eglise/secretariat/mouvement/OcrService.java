package com.eglise.secretariat.mouvement;

import com.eglise.secretariat.mouvement.dto.OcrResultDto;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class OcrService {

    public OcrResultDto processOcr(MultipartFile file) throws IOException, TesseractException {
        File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + System.currentTimeMillis() + "_" + file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convFile)) {
            fos.write(file.getBytes());
        }

        Tesseract tesseract = new Tesseract();
        String datapath = System.getenv("TESSDATA_PREFIX");
        if (datapath == null) {
            datapath = System.getProperty("TESSDATA_PREFIX");
        }
        if (datapath != null) {
            tesseract.setDatapath(datapath);
        }
        tesseract.setLanguage("fra");
        
        String extractedText;
        try {
            extractedText = tesseract.doOCR(convFile);
        } finally {
            convFile.delete();
        }

        return parseOcrText(extractedText);
    }

    public OcrResultDto parseOcrText(String text) {
        if (text == null) {
            return new OcrResultDto(null, null, null, "");
        }

        String pasteur = null;
        String eglise = null;
        LocalDate date = null;

        // 1. Recherche du Pasteur Signataire
        Pattern pasteurPattern = Pattern.compile("(?i)(?:pasteur|signataire|pstr|signé par)\\s*:?\\s*([A-Za-zÀ-ÿ\\-\\s]+)");
        Matcher pasteurMatcher = pasteurPattern.matcher(text);
        if (pasteurMatcher.find()) {
            pasteur = pasteurMatcher.group(1).trim();
            if (pasteur.contains("\n")) {
                pasteur = pasteur.split("\n")[0].trim();
            }
        }

        // 2. Recherche de l'Église d'Origine
        Pattern eglisePattern = Pattern.compile("(?i)(?:eglise|temple|provenance|paroisse|eglise d'origine)\\s*:?\\s*(?:de)?\\s*([A-Za-zÀ-ÿ\\-\\s0-9]+)");
        Matcher egliseMatcher = eglisePattern.matcher(text);
        if (egliseMatcher.find()) {
            eglise = egliseMatcher.group(1).trim();
            if (eglise.contains("\n")) {
                eglise = eglise.split("\n")[0].trim();
            }
        }

        // 3. Recherche de la Date
        Pattern datePattern = Pattern.compile("(\\d{1,2})[\\/\\-\\s](\\d{1,2}|[a-zA-ZÀ-ÿûéè]+)[\\/\\-\\s](\\d{4})");
        Matcher dateMatcher = datePattern.matcher(text);
        if (dateMatcher.find()) {
            String dayStr = dateMatcher.group(1);
            String monthStr = dateMatcher.group(2);
            String yearStr = dateMatcher.group(3);

            try {
                int day = Integer.parseInt(dayStr);
                int year = Integer.parseInt(yearStr);
                int month = 1;

                if (monthStr.matches("\\d+")) {
                    month = Integer.parseInt(monthStr);
                } else {
                    month = convertFrenchMonth(monthStr);
                }
                date = LocalDate.of(year, month, day);
            } catch (Exception e) {
                // En cas d'erreur de parsing
            }
        }

        if (date == null) {
            date = LocalDate.now();
        }

        return new OcrResultDto(pasteur, eglise, date, text);
    }

    private int convertFrenchMonth(String monthStr) {
        String lower = monthStr.toLowerCase();
        if (lower.contains("jan")) return 1;
        if (lower.contains("fév") || lower.contains("fev")) return 2;
        if (lower.contains("mar")) return 3;
        if (lower.contains("avr")) return 4;
        if (lower.contains("mai")) return 5;
        if (lower.contains("jun") || lower.contains("jui")) {
            if (lower.contains("juil")) return 7;
            return 6;
        }
        if (lower.contains("aoû") || lower.contains("aou")) return 8;
        if (lower.contains("sep")) return 9;
        if (lower.contains("oct")) return 10;
        if (lower.contains("nov")) return 11;
        if (lower.contains("déc") || lower.contains("dec")) return 12;
        return 1;
    }
}

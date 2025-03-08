package sparkdigital.co.za.service;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import sparkdigital.co.za.model.DataEntry;
import sparkdigital.co.za.repository.DataEntryRepository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper; 
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument; 

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.io.InputStream;


@Service
public class DataService {

    @Autowired
    private DataEntryRepository repository;

    public void processFile(MultipartFile file) {
        try {
            String fileName = file.getOriginalFilename();
            if (fileName == null) {
                throw new RuntimeException("File name is null");
            }

            if (fileName.endsWith(".json")) {
                // Handle JSON file
                processJsonFile(file);
            } else if (fileName.endsWith(".csv")) {
                // Handle CSV file
                processCsvFile(file);
            } else if (fileName.endsWith(".docx") || fileName.endsWith(".doc")) {
                // Handle Word file
                processWordFile(file);
            } 
            // else if (fileName.endsWith(".pdf")) {
            //     // Handle PDF file
            //     processPdfFile(file);
            // }
             else {
                throw new RuntimeException("Unsupported file format");
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to process file: " + e.getMessage());
        }
    }

    private void processJsonFile(MultipartFile file) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<DataEntry> entries = objectMapper.readValue(file.getInputStream(), new TypeReference<List<DataEntry>>() {});
        repository.saveAll(entries);
    }

    private void processCsvFile(MultipartFile file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());
            List<DataEntry> entries = new ArrayList<>();
    
            // Validate headers
            List<String> headers = csvParser.getHeaderNames();
            if (!headers.contains("Date")) {
                throw new IllegalArgumentException("CSV file is missing the required column: 'Date'");
            }
    
            for (CSVRecord record : csvParser) {
                DataEntry entry = new DataEntry();
    
                // Safely retrieve values from the CSV record
                entry.setDate(getValueOrThrow(record, "Date", "CSV file is missing the required column: 'Date'"));
                entry.setTotalConfirmedCases(Integer.parseInt(getValueOrThrow(record, "Total Confirmed Cases", "CSV file is missing the required column: 'Total Confirmed Cases'")));
                entry.setTotalDeaths(Integer.parseInt(getValueOrThrow(record, "Total Deaths", "CSV file is missing the required column: 'Total Deaths'")));
                entry.setTotalRecovered(Integer.parseInt(getValueOrThrow(record, "Total Recovered", "CSV file is missing the required column: 'Total Recovered'")));
                entry.setActiveCases(Integer.parseInt(getValueOrThrow(record, "Active Cases", "CSV file is missing the required column: 'Active Cases'")));
                entry.setDailyConfirmedCases(Integer.parseInt(getValueOrThrow(record, "Daily Confirmed Cases", "CSV file is missing the required column: 'Daily Confirmed Cases'")));
                entry.setDailyDeaths(Integer.parseInt(getValueOrThrow(record, "Daily deaths", "CSV file is missing the required column: 'Daily deaths'")));
    
                entries.add(entry);
            }
    
            repository.saveAll(entries);
        }
    }
    
    /**
     * Helper method to safely retrieve a value from a CSV record.
     * Throws an exception with a meaningful message if the column is missing.
     */
    private String getValueOrThrow(CSVRecord record, String columnName, String errorMessage) {
        if (!record.isMapped(columnName)) {
            throw new IllegalArgumentException(errorMessage);
        }
        return record.get(columnName);
    }
    private void processWordFile(MultipartFile file) throws IOException {
        try (InputStream inputStream = file.getInputStream();
             XWPFDocument document = new XWPFDocument(inputStream)) {
            XWPFWordExtractor extractor = new XWPFWordExtractor(document);
            String text = extractor.getText();

            // Parse the text and convert it to DataEntry objects
            List<DataEntry> entries = parseTextToDataEntries(text);
            repository.saveAll(entries);
        }
    }

    // private void processPdfFile(MultipartFile file) throws IOException {
    //     try (InputStream inputStream = file.getInputStream();
    //          PDDocument document = PDDocument.load(inputStream)) {
    //         PDFTextStripper pdfStripper = new PDFTextStripper();
    //         String text = pdfStripper.getText(document);

    //         // Parse the text and convert it to DataEntry objects
    //         List<DataEntry> entries = parseTextToDataEntries(text);
    //         repository.saveAll(entries);
    //     }
    // }

    private List<DataEntry> parseTextToDataEntries(String text) {
        // Implement logic to parse text and convert it to DataEntry objects
        // This is a placeholder implementation. You need to customize it based on your file format.
        List<DataEntry> entries = new ArrayList<>();

        // Example: Split text by lines and parse each line
        String[] lines = text.split("\n");
        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts.length >= 7) {
                DataEntry entry = new DataEntry();
                entry.setDate(parts[0]);
                entry.setTotalConfirmedCases(Integer.parseInt(parts[1]));
                entry.setTotalDeaths(Integer.parseInt(parts[2]));
                entry.setTotalRecovered(Integer.parseInt(parts[3]));
                entry.setActiveCases(Integer.parseInt(parts[4]));
                entry.setDailyConfirmedCases(Integer.parseInt(parts[5]));
                entry.setDailyDeaths(Integer.parseInt(parts[6]));
                entries.add(entry);
            }
        }

        return entries;
    }

    public List<DataEntry> getAllData() {
        return repository.findAll();
    }
}


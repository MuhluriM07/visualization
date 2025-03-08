package sparkdigital.co.za.controller;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import sparkdigital.co.za.model.DataEntry;
import sparkdigital.co.za.service.DataService;

import java.util.List;

@RestController
@RequestMapping("/api/data")
public class DataController {

    @Autowired
    private DataService dataService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        dataService.processFile(file);
        return ResponseEntity.ok("File processed successfully");
    }

    @GetMapping("/all")
    public List<DataEntry> getAllData() {
        return dataService.getAllData();
    }
}

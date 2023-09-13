package com.example.demo;

import java.io.File;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class RequestHandler {
    
    public static HashMap<String, HashMap<String, ArrayList<Double>>> storedTrades= new HashMap<>(); 
    
    

    @GetMapping(path = "/data", produces = "application/json")
    public HashMap<String, ArrayList<Double>> getData(@RequestParam String filename, double lr, boolean enableWeekend,
    @RequestParam(required = false) String startDateString, 
            @RequestParam(required = false) String endDateString){
                
                LocalDate startDate =null;
                LocalDate endDate=null;
                
                try {
                     startDate = LocalDate.parse(startDateString, DateTimeFormatter.ISO_LOCAL_DATE);

                } catch(Exception e){
                    

                }
                try {

                     endDate = LocalDate.parse(endDateString, DateTimeFormatter.ISO_LOCAL_DATE);

                } catch (Exception e){
                    

                }
                
                
                if (startDate!=null){
                    Trade.lowerBound = startDate;
                }

                 if (endDate!=null){
                    Trade.upperBound = endDate;
                }
                System.out.println(Trade.lowerBound);
                System.out.println(Trade.upperBound);



                System.out.println(filename+" requested");
                Trade.leverageRatio = lr;
                System.out.println("running calculation for "+filename);
                String p = new File("").getAbsolutePath();
                p+="/"+filename;
                System.out.println(p);
                Trade.loadData(p);
                Trade.calculateCapital(Trade.leverageRatio, enableWeekend);
                Trade.createMap();

                Trade.lowerBound = LocalDate.of(1970, Month.SEPTEMBER, 13);

                Trade.upperBound = LocalDate.of(2300, Month.SEPTEMBER, 13);

                
            
            return Trade.getCapitalValuesMap();

    }


    @GetMapping(path = "/ideal-data", produces = "application/json")
    public HashMap<String, ArrayList<Double>> getIdealData(@RequestParam String filename, double lr){
            System.out.println(filename+" requested");
                Trade.leverageRatio = lr;
                System.out.println("running calculation for "+filename);
                String p = new File("").getAbsolutePath();
                p+="/"+filename;
                System.out.println(p);
                Trade.loadData(p);
                Trade.calculateIdealCapital(Trade.leverageRatio);
                Trade.createMap();

                
            
            return Trade.getCapitalValuesMap();

    }


    @GetMapping(path="/table", produces="application/json")
    public synchronized HashMap<Integer, Trade> getTable(){
        
        return Trade.tradeMap;
    }


    @GetMapping(path="/labels", produces="application/json")
    public ArrayList<DataPoint> getLabels(){

        return Trade.allClosingPoints;
    }

    @GetMapping(path="/drawdown", produces="application/json")
    public double getMaxDrawdonw(){

        return Trade.getMaxDrawdonw();
    }



    @GetMapping(path = "/ema", produces = "application/json")
    public HashMap<Integer, ArrayList<Double>> getEma(@RequestParam int values[]){
            Trade.calculateEma(values);

                
            
        return Trade.getEma_map();

    }

    @PostMapping("/upload-csv")
    public ResponseEntity<String> uploadCSVFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return new ResponseEntity<>("Please select a CSV file to upload.", HttpStatus.BAD_REQUEST);
        } else {
            try {
                // Get the project directory path
                String projectPath = new File("src/main/java/com/example/demo").getAbsolutePath();

                // Save the CSV file to the project directory
                file.transferTo(new File(projectPath + File.separator + file.getOriginalFilename()));
                
                return new ResponseEntity<>("CSV file uploaded and saved successfully: " + file.getOriginalFilename(), HttpStatus.OK);
                
            } catch (IOException e) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    private static String UPLOADED_FOLDER = "src/main/java/com/example/demo/uploaded_csv_files/";

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFiles(@RequestParam("files") MultipartFile[] files) throws IOException {
        System.out.println("called");
        if (files.length == 0) {
            return ResponseEntity.badRequest().body("No files received");
        }

        try {
            for (MultipartFile file : files) {
                if (file.isEmpty()) {
                    return ResponseEntity.badRequest().body("Received an empty file");
                }

                byte[] bytes = file.getBytes();
                System.out.println(file.getOriginalFilename());
                Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
                Files.write(path, bytes);
            }
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Failed to upload files");
        }
        String projectPath = new File("src/main/java/com/example/demo/uploaded_csv_files").getAbsolutePath();

        // Create a File object for the project directory
        File directory = new File(projectPath);

        // List all CSV files in the directory
        List<String> csvFiles = Arrays.stream(directory.listFiles())
                .filter(file -> file.getName().endsWith(".csv") ||file.getName().endsWith(".trace") )
                .map(File::getName)
                .collect(Collectors.toList());
        System.out.println(csvFiles);
        mergeCSVFiles(csvFiles);

        return ResponseEntity.ok("Files uploaded successfully");
    }

    @PostMapping("/upload-and-process")
    public HashMap<String, ArrayList<Double>>  uploadAndProcess(@RequestParam("files") MultipartFile[] files) throws IOException {
        System.out.println("called");
        if (files.length == 0) {
            return null;
        }

        try {
            for (MultipartFile file : files) {
                if (file.isEmpty()) {
                    return null;
                }
                System.out.println("uploaded: "+file.getOriginalFilename());

                byte[] bytes = file.getBytes();
                System.out.println(file.getOriginalFilename());
                Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
                Files.write(path, bytes);
            }
        } catch (IOException e) {
            return null;
        }
        String projectPath = new File("src/main/java/com/example/demo/uploaded_csv_files/").getAbsolutePath();

        // Create a File object for the project directory
        File directory = new File(projectPath);

        // List all CSV files in the directory
        List<String> csvFiles = Arrays.stream(directory.listFiles())
                .filter(file -> file.getName().endsWith(".csv")||file.getName().endsWith(".trace") )
                .map(File::getName)
                .collect(Collectors.toList());
        
        mergeCSVFiles(csvFiles);


        

        return Trade.getCapitalValuesMap();
    }



    @GetMapping("/list-csv")
    public List<String> listCSVFiles() {
        
        // Get the project directory path
        String projectPath = new File("").getAbsolutePath();

        // Create a File object for the project directory
        File directory = new File(projectPath);

        // List all CSV files in the directory
        List<String> csvFiles = Arrays.stream(directory.listFiles())
                .filter(file -> file.getName().endsWith(".csv")|| file.getName().endsWith(".trace"))
                .map(File::getName)
                .collect(Collectors.toList());

        return csvFiles;
    }

    public static int counter=0;
    

    public static void mergeCSVFiles(List<String> csvFiles) throws IOException {
        String path="";
        String outputFileName =counter+ "_merged_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd_mm_yyyy_HH_mm_ss")) + ".csv";
        counter+=1;
        System.out.println("Merged file name: " + outputFileName);
        most_recent_filepath = outputFileName;
        System.out.println(csvFiles);
    
        try {
            Path outputFile = Files.createFile(Paths.get(outputFileName));
            System.out.println("Created file at: " + outputFile.toAbsolutePath().toString());
    
            for (String filePath : csvFiles) {
                filePath= "src/main/java/com/example/demo/uploaded_csv_files/"+filePath;
                System.out.println("Reading file: " + filePath);
    
                List<String> lines = Files.readAllLines(Paths.get(filePath));
                System.out.println(lines);
                /* 
                if (!outputFile.equals(Paths.get(filePath))) {
                    lines.remove(0);
                }
               
                */
                 for (int i=0; i<lines.size(); i++){
                    String arr[] = lines.get(i).split(",");
                    try {if (Double.parseDouble(arr[12])==0){
                        lines.remove(i);
                    }


                    } catch (Exception e){
                        lines.remove(i);
                    }
                    
                }
    
                Files.write(outputFile, lines, StandardOpenOption.APPEND);
                System.out.println("Wrote to file: " + outputFile.toAbsolutePath().toString());
                path = outputFile.toAbsolutePath().toString();
    
                Files.delete(Paths.get(filePath));
                System.out.println("Deleted file: " + filePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        Trade.loadData(path);
        Trade.calculateCapital(Trade.leverageRatio, true);
        Trade.createMap();
        System.out.println(outputFileName);
        storedTrades.put(outputFileName, Trade.getCapitalValuesMap());
        
    }

    public static String most_recent_filepath="";

    public static void mergeAndFilter(List<String> csvFiles) {
        String outputFileName = "merged_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd_mm_yyyy_HH_mm_ss")) + ".csv";
        System.out.println("Merged file name: " + outputFileName);

        most_recent_filepath = "src/main/java/com/example/demo/"+outputFileName;

        List<String[]> allLines = new ArrayList<>();
        try {
            for (String filePath : csvFiles) {
                filePath = "src/main/java/com/example/demo/uploaded_csv_files/" + filePath;
                List<String> lines = Files.readAllLines(Paths.get(filePath));

                for (int i = 0; i < lines.size(); i++) {
                    String arr[] = lines.get(i).split(",");
                    try {
                        if (Double.parseDouble(arr[12]) == 0) {
                            continue;  // Skip lines with zero at the 13th position
                        }
                    } catch (Exception e) {
                        continue;  // Skip lines that can't be parsed or don't have enough columns
                    }
                    allLines.add(arr);
                }

                Files.delete(Paths.get(filePath));
                System.out.println("Deleted file: " + filePath);
            }

            // Sorting by ticker and trigger_date
            allLines.sort((a, b) -> {
                int tickerCompare = a[0].compareTo(b[0]);
                if (tickerCompare == 0) {
                    return a[1].compareTo(b[1]);
                }
                return tickerCompare;
            });

            String prevTicker = "";
            String maxCloseDate = "1970-01-01";
            List<String> filteredLines = new ArrayList<>();

            for (String[] line : allLines) {
                String currentTicker = line[0];
                String triggerDate = line[1];
                String closeDate = line[2];

                if (!currentTicker.equals(prevTicker)) {
                    maxCloseDate = "1970-01-01";
                    prevTicker = currentTicker;
                }

                if (triggerDate.compareTo(maxCloseDate) > 0) {
                    filteredLines.add(String.join(",", line));
                    if (closeDate.compareTo(maxCloseDate) > 0) {
                        maxCloseDate = closeDate;
                    }
                }
            }

            

            Path outputPath = Paths.get("src/main/java/com/example/demo/");
            if (!Files.exists(outputPath)) {
                Files.createDirectories(outputPath);
                                                    }   

            Path outputFile = Files.createFile(Paths.get(most_recent_filepath));
            Files.write(outputFile, filteredLines); // Removed the StandardOpenOption.APPEND
            System.out.println("Wrote to file: " + outputFile.toAbsolutePath().toString());
            Files.write(outputFile, filteredLines, StandardOpenOption.APPEND);
            
            Trade.loadData(outputFile.toAbsolutePath().toString());
            Trade.calculateCapital(Trade.leverageRatio, true);
            Trade.createMap();
            System.out.println(outputFileName);
            storedTrades.put(outputFileName, Trade.getCapitalValuesMap());

        } catch (IOException e) {
            e.printStackTrace();
        }

        
        
    }
   

    @PostMapping("/api/upload")
    public ResponseEntity<?> uploadFile(
            @RequestParam("file") MultipartFile uploadfile) {

        if (uploadfile.isEmpty()) {
            return new ResponseEntity<>("please select a file!", HttpStatus.OK);
        }

        try {
            saveUploadedFiles(uploadfile);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("Successfully uploaded - " +
                uploadfile.getOriginalFilename(), new HttpHeaders(), HttpStatus.OK);

    }

    @GetMapping(path="/api/equity-per-ticker", produces = "application/json")
    public HashMap<String,ArrayList<AdvancedDataPoint>> equityPerTicker(){

        return Trade.equityPerTrade();
    }


    private void saveUploadedFiles(MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);
        }
    }
    
}






    


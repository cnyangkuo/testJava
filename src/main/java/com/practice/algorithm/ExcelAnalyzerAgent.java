package com.practice.algorithm;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.*;

public class ExcelAnalyzerAgent {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java ExcelAnalyzerAgent <path-to-excel-file>");
            return;
        }

        String filePath = args[0];
        try {
            analyzeExcel(filePath);
        } catch (Exception e) {
            System.err.println("Error analyzing Excel file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void analyzeExcel(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new FileNotFoundException("File not found at path: " + filePath);
        }

        Workbook workbook = WorkbookFactory.create(file);
        Sheet sheet = workbook.getSheetAt(0); // Assume we're analyzing the first sheet

        List<Map<String, Object>> analysisResults = new ArrayList<>();

        // Assume the first row contains headers
        Row headerRow = sheet.getRow(0);
        int numberOfColumns = headerRow.getLastCellNum();
        List<String> columnNames = new ArrayList<>();

        for (int i = 0; i < numberOfColumns; i++) {
            Cell cell = headerRow.getCell(i);
            if (cell != null) {
                columnNames.add(cell.getStringCellValue());
            } else {
                columnNames.add("Column_" + (i + 1));
            }
        }

        // Initialize data structures to hold column statistics
        Map<Integer, List<Double>> columnData = new HashMap<>();
        for (int i = 0; i < numberOfColumns; i++) {
            columnData.put(i, new ArrayList<Double>());
        }

        // Process all rows except the header
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue;

            for (int j = 0; j < numberOfColumns; j++) {
                Cell cell = row.getCell(j);
                if (cell == null) continue;

                switch (cell.getCellType()) {
                    case NUMERIC:
                        columnData.get(j).add(cell.getNumericCellValue());
                        break;
                    default:
                        // Skip non-numeric cells
                        break;
                }
            }
        }

        // Calculate statistics for each column
        for (int i = 0; i < numberOfColumns; i++) {
            List<Double> values = columnData.get(i);
            if (values.isEmpty()) continue;

            Map<String, Object> columnAnalysis = new HashMap<>();
            columnAnalysis.put("columnIndex", i);
            columnAnalysis.put("columnName", columnNames.get(i));
            
            double sum = 0;
            double min = Double.MAX_VALUE;
            double max = Double.MIN_VALUE;
            
            for (double value : values) {
                sum += value;
                if (value < min) min = value;
                if (value > max) max = value;
            }
            
            double average = sum / values.size();
            
            columnAnalysis.put("count", values.size());
            columnAnalysis.put("average", average);
            columnAnalysis.put("min", min);
            columnAnalysis.put("max", max);
            
            analysisResults.add(columnAnalysis);
        }

        // Generate HTML report
        generateHtmlReport(analysisResults, file.getName());
        
        workbook.close();
    }

    private static void generateHtmlReport(List<Map<String, Object>> analysisResults, String fileName) throws IOException {
        StringBuilder htmlContent = new StringBuilder();
        htmlContent.append("<html><head><title>Excel Analysis Report</title></head><body>");
        htmlContent.append("<h1>Excel File Analysis Report</h1>");
        htmlContent.append("<p>Analyzed Excel file: ").append(fileName).append("</p>");
        htmlContent.append("<table border='1'>");
        htmlContent.append("<tr><th>Column Name</th><th>Count</th><th>Average</th><th>Min</th><th>Max</th></tr>");
        
        for (Map<String, Object> result : analysisResults) {
            htmlContent.append("<tr>");
            htmlContent.append("<td>").append(result.get("columnName")).append("</td>");
            htmlContent.append("<td align='right'>").append(result.get("count")).append("</td>");
            htmlContent.append("<td align='right'>").append(String.format("%.2f", result.get("average"))).append("</td>");
            htmlContent.append("<td align='right'>").append(String.format("%.2f", result.get("min"))).append("</td>");
            htmlContent.append("<td align='right'>").append(String.format("%.2f", result.get("max"))).append("</td>");
            htmlContent.append("</tr>");
        }
        
        htmlContent.append("</table>");
        htmlContent.append("</body></html>");
        
        // Write HTML content to file
        String outputFileName = "ExcelAnalysisReport.html";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName))) {
            writer.write(htmlContent.toString());
            System.out.println("Analysis report generated successfully: " + outputFileName);
        }
    }
}
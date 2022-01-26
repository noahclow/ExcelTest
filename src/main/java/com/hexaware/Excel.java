package com.hexaware;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class Excel {
    public static void main(String[] args) {
        crearExcel();
        leerExcel();
    }

    public static void crearExcel() {
        //Se crea la libro
        XSSFWorkbook workbook = new XSSFWorkbook();

        //Crear una hoja en blanco
        XSSFSheet sheet = workbook.createSheet("Employee Data"); // La hoja y su nombre

        //Los datos a escribir estan en este mapa
        Map<String, Object[]> registro = new TreeMap<String, Object[]>();
        registro.put("0", new Object[]{"NAME", "LASTNAME", "EMAIL", "PASSWORD", "COMPANY", "ADDRESS", "CITY", "ZIP_CODE", "MOBILE_PHONE"});
        registro.put("1", new Object[]{"SomeName", "SomeLastName", "SomeEmail", "SomePassword.", "SomeCompany", "SomeAddress", "SomeCity", "SomePostCode", "SomeMobilePhone"});
        registro.put("2", new Object[]{"testName", "testLastName", "testlead@test.io", "testPsw1234.", "testCompany", "testStreet", "testCity", 99999, 1234567890});
        registro.put("3", new Object[]{"testName", "testLastName", "testlead@test.io", "testPsw1234.", "testCompany", "testStreet", "testCity", 99999, 1234567890});
        registro.put("4", new Object[]{"testName", "testLastName", "testlead@test.io", "testPsw1234.", "testCompany", "testStreet", "testCity", 99999, 1234567890});

        //Aqui se recorre el mapa para escribir los datos en la hoja
        Set<String> valor = registro.keySet();
        int numeroDeFila = 0; // se indica el numero de la fila desde la que comenzara a escribir en la hoja
        for (String llave : valor) // como se esta trabajando con un array se usa un for-each, para recorrer cada registro en el arreglo
        {
            Row fila = sheet.createRow(numeroDeFila++); // Aqui se escribe la fila y se incrementa el valor en 1
            Object[] objArr = registro.get(llave);
            int numeroDeColumna = 0;        // se puede indicar el numero de columna desde la que comenzara a escribir en la hoja
            for (Object objeto : objArr) {
                Cell cell = fila.createCell(numeroDeColumna++);
                if (objeto instanceof String) {
                    cell.setCellValue((String) objeto);
                } else if (objeto instanceof Integer) {
                    cell.setCellValue((Integer) objeto);
                }
            }
        }
        try {
            //Write the workbook in file system
            FileOutputStream out = new FileOutputStream(new File("Directory.xlsx"));
            workbook.write(out);
            out.close();
            System.out.println("Directory.xlsx written successfully on disk. \n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void leerExcel() {
        try {
            FileInputStream file = new FileInputStream(new File("Directory.xlsx"));
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheetAt(0);

            int numFilas = sheet.getLastRowNum();

            for(int a = 0; a <= numFilas; a++){
                Row fila = sheet.getRow(a);
                int numCols = fila.getLastCellNum();

                for(int b = 0; b < numCols; b++){
                    Cell celda = fila.getCell(b);

                    switch (celda.getCellType().toString()){
                        case "NUMERIC":
                            System.out.print(celda.getNumericCellValue() + " ");
                            break;
                        case "STRING":
                            System.out.print(celda.getStringCellValue() + " ");
                            break;
                        case "FORMULA":
                            System.out.print(celda.getCellFormula() + " ");
                            break;
                    }
                }
                System.out.println(" ");
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

}
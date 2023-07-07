package dev.rybalidoy.billingapi.helper;

import dev.rybalidoy.billingapi.entity.Bill;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class FileHelper {

    /**
     *  Get File and determine if file type is supported
     *  If file is supported run the function
     */
    public boolean checkSupported(MultipartFile file) {
        if(file.getContentType().equals("text/plain") || file.getContentType().equals("text/csv")) {
            return true;
        }
        return false;
    }

    public List<Bill> readFile(MultipartFile file) throws IOException, NullPointerException {
        List<Bill> result = new ArrayList<>();
        BufferedReader br;
        if(checkSupported(file)) {
            try {
                String line;
                InputStream stream = file.getInputStream();
                br = new BufferedReader(new InputStreamReader(stream));

                if(file.getContentType().equals("text/plain")) {
                    result = readTextFile(file,br);
                }
                else {
                    result = readCSVFile(file, br);
                }
            }
            catch(IOException e) {
                System.out.println(e.getMessage());
            }
        }
        else {
            System.out.println("File not supported.");
        }
        return result;
    }

    public List<Bill> readTextFile(MultipartFile file, BufferedReader stream) throws IOException {
        String line;
        List<Bill> result = new ArrayList<>();
        while((line = stream.readLine()) != null) {
            int billingCycle = Integer.parseInt(line.substring(0,2));
            String startDateStr = line.substring(2,10);
            String endDateStr = line.substring(10,18);

            SimpleDateFormat format = new SimpleDateFormat("MMddyyyy");

            Date startDate = null;
            Date endDate = null;

            try {
                startDate = new Date(format.parse(startDateStr).getTime());
                endDate = new Date(format.parse(endDateStr).getTime());
            }
            catch (ParseException e) {
                // Handle Exception
            }
            result.add(new Bill(null,billingCycle,startDate,endDate));
        }
        return result;
    }

    public List<Bill> readCSVFile(MultipartFile file, BufferedReader stream) throws IOException {
        String line;
        List<Bill> result = new ArrayList<>();
        while((line = stream.readLine()) != null) {
            if(line.contains(",")) {
                String [] newString = line.split(",");
                int billingCycle = Integer.parseInt(newString[0]);

                String startDateStr = newString[1];
                String endDateStr = newString[2];
                SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
                Date startDate = null;
                Date endDate = null;

                try {
                    startDate = new Date(format.parse(startDateStr).getTime());
                    endDate = new Date(format.parse(endDateStr).getTime());
                }
                catch(ParseException e) {
                    // Handle Exception
                }
                result.add(new Bill(null,billingCycle,startDate,endDate));
            }
        }
        return result;
    }
}

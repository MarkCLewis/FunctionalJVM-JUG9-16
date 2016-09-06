/*
 * Created on Sep 5, 2016
 */
package jugmeetup.java;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumberedLines {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(new File("numberedLines.txt"))) {
            Pattern p = Pattern.compile("(\\d+)\\.(.+)");
            Map<Integer, String> data = new HashMap<>();
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                Matcher m = p.matcher(line);
                if (m.matches()) {
                    data.put(Integer.parseInt(m.group(1)), m.group(2));
                }
            }
            System.out.println(data);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }
}

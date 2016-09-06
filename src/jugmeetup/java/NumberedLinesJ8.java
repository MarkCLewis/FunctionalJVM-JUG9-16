/*
 * Created on Sep 5, 2016
 */
package jugmeetup.java;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class NumberedLinesJ8 {
    public static void main(String[] args) {
        Pattern p = Pattern.compile("(\\d+)\\.(.+)");
        Map<Integer, String> data = new HashMap<>();
        try(Stream<String> stream = Files.lines(Paths.get("numberedLines.txt"))) {
            data.putAll(stream.map(line -> p.matcher(line)).filter(m -> m.matches()).
                collect(Collectors.toMap(m -> Integer.parseInt(m.group(1)), m -> m.group(2))));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        System.out.println(data);
    }
}

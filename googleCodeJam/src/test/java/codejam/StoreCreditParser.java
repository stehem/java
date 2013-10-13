package codejam;


import com.google.common.base.Function;
import com.google.common.base.Predicate;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static codejam.StoreCredit.withoutTooExpensive;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.transform;
import static com.google.common.collect.Maps.newHashMap;

/**
 * Created with IntelliJ IDEA.
 * User: stephane
 * Date: 10/12/13
 * Time: 12:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class StoreCreditParser {

    private URL fileURL;

    public StoreCreditParser(URL fileURL) {
        this.fileURL = fileURL;
    }

    public static StoreCreditParser parse(URL fileURL) {
        return new StoreCreditParser(fileURL);
    }

    public List<String> toLines() throws IOException {
        File smallPractice = new File(fileURL.getFile());
        List<String> lines = Files.readAllLines(smallPractice.toPath(), Charset.defaultCharset());
        lines.remove(0);
        return lines;
    }

    public Map<Integer, Map> toStructure() {
        Map<Integer, Map> data = newHashMap();
        try {
            Integer idx = 1;
            Integer sliceIdx = 1;
            for (String line : toLines()) {
                if (idx.equals(1)) {
                    Map<String, Object> slice = newHashMap();
                    Integer credit = Integer.parseInt(line);
                    slice.put("credit", credit);
                    data.put(sliceIdx, slice);
                }
                if (idx.equals(2)) {
                    data.get(sliceIdx).put("articles", line);
                }
                if (idx.equals(3)) {
                    Function<String, Integer> toInteger = new Function<String, Integer>() {
                        public Integer apply(String string) {
                            return Integer.parseInt(string);
                        }
                    };
                    Integer credit = (Integer) data.get(sliceIdx).get("credit");
                    ArrayList<String> prices = newArrayList(line.split(" "));
                    data.get(sliceIdx).put("prices", withoutTooExpensive(transform(prices, toInteger), credit));
                }
                idx++;
                if (idx.equals(4)) {
                    idx = 1;
                    sliceIdx++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return data;
    }

}


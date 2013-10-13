package codejam;


import org.testng.annotations.Test;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.List;
import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;


public class StoreCreditTest {


    @Test
    public void storeCreditTest() throws IOException {
        assertTrue(1 == 1);
        URL url = this.getClass().getResource("/A-large-practice.in");
        Map<Integer,Map> lines = StoreCreditParser.parse(url).toStructure();
        Map<Integer, List<Integer>> result = StoreCredit.twoItems(lines).get();
        assertEquals(result.get(1), newArrayList(2,3));
        assertEquals(result.get(2), newArrayList(1,4));
        assertEquals(result.get(3), newArrayList(4,5));
        PrintWriter writer = new PrintWriter("/home/stephane/Dev/codeJam/result-large.txt", "UTF-8");
        for (Map.Entry<Integer, List<Integer>> entry : result.entrySet()) {
           writer.println(String.format("Case #%s: %s %s", entry.getKey(), entry.getValue().get(0), entry.getValue().get(1)));
        }
        writer.close();
    }


}
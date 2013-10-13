package codejam;

import com.google.common.base.Function;

import java.util.List;
import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.transform;
import static com.google.common.collect.Maps.newTreeMap;

/**
 *
 */
public class StoreCredit {

    private Map<Integer, Map> data;

    public StoreCredit(Map<Integer, Map> data) {
        this.data = data;
    }

    public static StoreCredit twoItems(Map<Integer, Map> data) {
        return new StoreCredit(data);
    }


    public static List<Integer> withoutTooExpensive(final List<Integer> prices, final Integer price) {
        Function<Integer, Integer> map = new Function<Integer, Integer>() {
            @Override
            public Integer apply(Integer input) {
                if (input != null && input <= price) return input;
                return null;
            }
        };
        return newArrayList(transform(prices, map));
    }


    public Map<Integer, List<Integer>> get() {
        Map<Integer, List<Integer>> result = newTreeMap();
        for (Map.Entry<Integer, Map> entry : data.entrySet()) {
            result.put(entry.getKey(), calculate((List) entry.getValue().get("prices"), (Integer) entry.getValue().get("credit")));
        }
        return result;
    }

    public List<Integer> calculate(final List<Integer> prices, Integer credit) {
        List<Integer> result = newArrayList();
        List<Integer> optimizedPrices = withoutTooExpensive(prices, credit);
        Integer idx = 0;
        Integer otherIdx = 0;
        for (Integer price : optimizedPrices) {
            otherIdx = 0;
            for (Integer otherPrice : optimizedPrices) {
                if (!otherIdx.equals(idx) &&
                        price != null &&
                        otherPrice != null &&
                        price + otherPrice == credit &&
                        !result.contains(idx+1) &&
                        !result.contains(otherIdx+1)) {
                    result.add(idx + 1);
                    result.add(otherIdx + 1);
                }
                otherIdx++;
            }
            idx++;
        }
        return result;
    }





}

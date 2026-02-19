package cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AccountCache {

    // accountId -> balance
    private static final ConcurrentHashMap<Integer, Double> balanceCache =
            new ConcurrentHashMap<>();

    // accountId -> last updated timestamp
    private static final ConcurrentHashMap<Integer, Long> lastUpdated =
            new ConcurrentHashMap<>();

    // put / update balance
    public static void put(int accId, double balance) {
        balanceCache.put(accId, balance);
        lastUpdated.put(accId, System.currentTimeMillis());
    }

    // get balance
    public static Double get(int accId) {
        return balanceCache.get(accId);
    }

    // check existence
    public static boolean contains(int accId) {
        return balanceCache.containsKey(accId);
    }

    // remove account from cache
    public static void remove(int accId) {
        balanceCache.remove(accId);
        lastUpdated.remove(accId);
    }

    // clear cache
    public static void clear() {
        balanceCache.clear();
        lastUpdated.clear();
    }

    // safe snapshot (read-only copy)
    public static Map<Integer, Double> getSnapshot() {
        return new ConcurrentHashMap<>(balanceCache);
    }

    // last updated time
    public static Long getLastUpdated(int accId) {
        return lastUpdated.get(accId);
    }

    // cache size
    public static int size() {
        return balanceCache.size();
    }
}

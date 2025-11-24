import java.util.*;

public class Apriori {
    private double minSupport; // fx 0.2 = 20%

    // Constructor
    public Apriori(double minSupport) {
        this.minSupport = minSupport;
    }

    // Tæl hvor mange gange hvert stjernetegn forekommer
    public Map<String, Integer> findFrequentItems(List<String> items) {
        Map<String, Integer> freq = new HashMap<>();

        for (String item : items) {
            freq.put(item, freq.getOrDefault(item, 0) + 1);
        }

        return freq;
    }

    // Beregn support (andel) for hvert stjernetegn
    public Map<String, Double> computeSupport(Map<String, Integer> freq, int total) {
        Map<String, Double> support = new HashMap<>();
        if (total <= 0) return support;

        for (Map.Entry<String, Integer> e : freq.entrySet()) {
            support.put(e.getKey(), (double) e.getValue() / total);
        }
        return support;
    }

    // Filtrér dem der ligger over minSupport
    public Map<String, Double> filterByMinSupport(Map<String, Double> supportMap) {
        Map<String, Double> filtered = new HashMap<>();

        for (Map.Entry<String, Double> e : supportMap.entrySet()) {
            if (e.getValue() >= this.minSupport) {
                filtered.put(e.getKey(), e.getValue());
            }
        }
        return filtered;
    }

    // Kombi-metode: find hyppige items OG filtrér på minSupport
    public Map<String, Double> findFrequentItemsWithMinSupport(List<String> items) {
        Map<String, Integer> counts = findFrequentItems(items);
        Map<String, Double> supports = computeSupport(counts, items.size());
        return filterByMinSupport(supports);
    }

    // Beregn support for alle 2-item kombinationer
    public Map<Set<String>, Double> computePairSupport(List<List<String>> transactions) {
        Map<Set<String>, Integer> pairCounts = new HashMap<>();
        int totalTransactions = transactions.size();

        for (List<String> t : transactions) {
            for (int i = 0; i < t.size(); i++) {
                for (int j = i + 1; j < t.size(); j++) {
                    Set<String> pair = new HashSet<>();
                    pair.add(t.get(i));
                    pair.add(t.get(j));
                    pairCounts.put(pair, pairCounts.getOrDefault(pair, 0) + 1);
                }
            }
        }

        Map<Set<String>, Double> pairSupport = new HashMap<>();
        for (Map.Entry<Set<String>, Integer> e : pairCounts.entrySet()) {
            pairSupport.put(e.getKey(), (double) e.getValue() / totalTransactions);
        }

        return pairSupport;
    }

    // Beregn confidence for regler A→B og B→A
    public Map<String, Double> computeConfidence(
            Map<Set<String>, Double> pairSupport,
            Map<String, Double> singleSupport) {

        Map<String, Double> confidence = new HashMap<>();

        for (Map.Entry<Set<String>, Double> e : pairSupport.entrySet()) {
            List<String> items = new ArrayList<>(e.getKey());
            if (items.size() == 2) {
                String A = items.get(0);
                String B = items.get(1);

                double supportAB = e.getValue();
                double supportA = singleSupport.getOrDefault(A, 0.0);
                double s

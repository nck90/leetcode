import java.util.*;

public class AllOne {
    private class Bucket {
        int count;
        Set<String> keys;
        Bucket prev, next;

        Bucket(int count) {
            this.count = count;
            keys = new HashSet<>();
        }
    }

    private Map<String, Bucket> keyBucketMap;
    private Bucket head, tail;

    public AllOne() {
        keyBucketMap = new HashMap<>();

        head = new Bucket(Integer.MIN_VALUE);
        tail = new Bucket(Integer.MAX_VALUE);
        head.next = tail;
        tail.prev = head;
    }

    public void inc(String key) {
        if (keyBucketMap.containsKey(key)) {
            Bucket currBucket = keyBucketMap.get(key);
            Bucket nextBucket;

            if (currBucket.next.count == currBucket.count + 1) {
                nextBucket = currBucket.next;
            } else {
                nextBucket = new Bucket(currBucket.count + 1);
                insertBucketAfter(nextBucket, currBucket);
            }

            nextBucket.keys.add(key);
            keyBucketMap.put(key, nextBucket);

            currBucket.keys.remove(key);
            if (currBucket.keys.isEmpty()) {
                removeBucket(currBucket);
            }
        } else {
            Bucket firstBucket;

            if (head.next.count == 1) {
                firstBucket = head.next;
            } else {
                firstBucket = new Bucket(1);
                insertBucketAfter(firstBucket, head);
            }

            firstBucket.keys.add(key);
            keyBucketMap.put(key, firstBucket);
        }
    }

    public void dec(String key) {
        Bucket currBucket = keyBucketMap.get(key);
        if (currBucket == null) return;

        keyBucketMap.remove(key);
        if (currBucket.count > 1) {
            Bucket prevBucket;

            if (currBucket.prev.count == currBucket.count - 1) {
                prevBucket = currBucket.prev;
            } else {
                prevBucket = new Bucket(currBucket.count - 1);
                insertBucketAfter(prevBucket, currBucket.prev);
            }

            prevBucket.keys.add(key);
            keyBucketMap.put(key, prevBucket);
        }

        currBucket.keys.remove(key);
        if (currBucket.keys.isEmpty()) {
            removeBucket(currBucket);
        }
    }

    public String getMaxKey() {
        return tail.prev == head ? "" : tail.prev.keys.iterator().next();
    }

    public String getMinKey() {
        return head.next == tail ? "" : head.next.keys.iterator().next();
    }

    private void insertBucketAfter(Bucket newBucket, Bucket prevBucket) {
        newBucket.next = prevBucket.next;
        newBucket.prev = prevBucket;
        prevBucket.next.prev = newBucket;
        prevBucket.next = newBucket;
    }

    private void removeBucket(Bucket bucket) {
        bucket.prev.next = bucket.next;
        bucket.next.prev = bucket.prev;
    }
}
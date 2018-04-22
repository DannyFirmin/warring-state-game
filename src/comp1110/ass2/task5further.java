package comp1110.ass2;

public class task5further {
}

//whether same kingdom card further
        d = location[(m - 2) / 3];
                l = location[(n - 2) / 3];
                if ((d % 6) == (l % 6)) {
                if (d > l) {
                for (int i = l - 6; (i % 6) != (d % 6); i = i - 6) {
                if (placement.charAt(n - 2) == placement.charAt(Arrays.binarySearch(location, i) * 3)) {
                further = false;
                break;
                }
                }
                }
                if (d < l) {
        for (int i = l + 6; i < 36; i = i + 6) {
        if (placement.charAt(n - 2) == placement.charAt(Arrays.binarySearch(location, i) * 3)) {
        further = false;
        break;
        }
        }
        }
        }
        if ((d / 6) == (l / 6)) {
        if (d > l) {
        for (int i = l - 1; (i / 6) != (d / 6); i--) {
        if (placement.charAt(n - 2) == placement.charAt(Arrays.binarySearch(location, i) * 3)) {
        further = false;
        break;
        }
        }
        }
        if (d < l) {
        for (int i = l + 1; (i / 6) != (d / 6); i++) {
        if (placement.charAt(n - 2) == placement.charAt(Arrays.binarySearch(location, i) * 3)) {
        further = false;
        break;
        }
        }
        }
        }
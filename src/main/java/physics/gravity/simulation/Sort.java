package physics.gravity.simulation;

/**
 * QuickSort algortithm for Individual[]
 */
public class Sort {

    public static Individual[] quicksort(Individual[] array) {
        quicksort(array, 0, array.length - 1);
        return array;
    }

    public static void quicksort(Individual[] array, int left, int right) {
        if (left >= right)
            return;

        int pivot = array[(left+right)/2].getFitness();
        int index = partition(array, left, right, pivot);
        quicksort(array, left, index - 1);
        quicksort(array, index, right);
    }

    public static int partition(Individual[] array, int left, int right, int pivot) {
        while (left <= right) {
            while (array[left].getFitness() < pivot) {
                left++;
            }
            while (array[right].getFitness()> pivot) {
                right--;
            }
            if (left <= right) {
                swap(array, left, right);
                left++;
                right--;
            }
        }
        return left;
    }

    public static void swap(Individual[] array, int left, int right) {
        Individual temp = array[left];
        array[left] = array[right];
        array[right] = temp;
    }

}

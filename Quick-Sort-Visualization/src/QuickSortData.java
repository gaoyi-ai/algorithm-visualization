
public class QuickSortData {

    private int[] numbers;
    public int l, r;
    /*
    每次都会将一个元素放到一个位置。这个位置前面的元素都小于它，后面的元素都大于它，这意味着什么？
    意味着这个元素就已经在了在排好序之后，他本来应该处在的位置。当排好序一个数组之后，随便抽出一个元素。
    那么这个元素前面的所有的元素一定小于这个元素，后面的所有元素一定大于这个元素。
    所以每一个曾经被当过这个标定点，也就是的这样的元素一旦放在了合适的位置，这个位置就不用动了。
    */
    public boolean[] fixedPivots; // finally all true
    public int curPivot;
    public int curElement;

    public QuickSortData(int N, int randomBound){

        numbers = new int[N];
        fixedPivots = new boolean[N];

        for( int i = 0 ; i < N ; i ++) {
            numbers[i] = (int)(Math.random()*randomBound) + 1;
            fixedPivots[i] = false;
        }

    }

    public int N(){
        return numbers.length;
    }

    public int get(int index){
        if( index < 0 || index >= numbers.length)
            throw new IllegalArgumentException("Invalid index to access Sort Data.");

        return numbers[index];
    }

    public void swap(int i, int j) {

        if( i < 0 || i >= numbers.length || j < 0 || j >= numbers.length)
            throw new IllegalArgumentException("Invalid index to access Sort Data.");

        int t = numbers[i];
        numbers[i] = numbers[j];
        numbers[j] = t;
    }
}
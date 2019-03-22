import java.util.*;
 
public class bogo{
    static int j = 0;
  private static final Random generator = new Random();  
 
  public static void bogoSort(int[] array)  {  
    while (!isSorted(array)) {  
      for (int i = 0; i < array.length; i++){  
        int randomPosition = generator.nextInt(array.length);  
        int temp = array[i];  
        array[i] = array[randomPosition];  
        array[randomPosition] = temp;  
      }  
    }  
  }  
 
  private static boolean isSorted(int[] array)  { 
    j++;
      for(int i: array){
        System.out.print(i+"  ");
      }
      System.out.println();
    for (int i = 1; i < array.length; i++){
      if (array[i] < array[i - 1]) {
        return false;  
      }
    }
    System.out.println("took "+ j+" sorts");
    return true;  
  }  
 
  public static void main(String[] args) {
    int [] array = {15,14,100,12,155}; 
    System.out.println("Before: " + Arrays.toString(array));
    bogoSort(array);
    System.out.println("After:  " + Arrays.toString(array));
  }
}
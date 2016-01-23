package week2;

/**
 * Only works for small numbers.
 * Find a divisor? Not prime!
 *   boolean false
 * Tried all divisors?
 *   boolean true
 * Ensure loop breaks
 *   div increases
 */
public class PrimeTester {
  public boolean isPrime(int num) {
    int div = 2;
    if (num == 2) {
      return true;
    }
    while (true) {
      if (num % div == 0) {
        return false;
      }
      if (div > Math.sqrt(num)) {
        break;
      }
      div = div + 1;
    }
    return true;
  }

  public static void main(String[] args) {
    PrimeTester primeTester = new PrimeTester();
    System.out.println(primeTester.isPrime(23));
  }
}

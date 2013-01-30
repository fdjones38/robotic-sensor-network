
package data;
/**
 *Esta clase se dedica a manipular los datos contenidos en una lista
 * 
 * @author ALDAJO
 */
public class Data {
    
    public static int[] stringToInts(String cadena) {
        char[] toCharArray = cadena.toCharArray();
        int salida[] = new int[toCharArray.length];
        for (int i = 0; i < toCharArray.length; i++) {
            salida[i] = toCharArray[i];
        }
        return salida;
    }
    
    public static int encriptValue(int value){
        return 128 + value;
    }
    
    public static int[] divideIn2Bits(int value){
        int[] toReturn = new int[2];
        toReturn[0] = (value >> 8) & 0xff;
        toReturn[1] = value & 0xff;
        return toReturn;
    }
}

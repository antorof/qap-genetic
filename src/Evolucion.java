import java.util.Collections;
import java.util.Random;

/**
 * Clase que contiene los metodos propios de la evolucion, como
 * cruzar o mutar cromosomas.
 * 
 * @author Antonio Toro
 */
public class Evolucion {
	
	/**
	 * Muta un cromosoma por intercambio de alelos aleatorios.
	 * 
	 * @param cromosoma Cromosoma a mutar
	 * @return Cromosoma mutado
	 */
	public static Cromosoma mutar(Cromosoma cromosoma) {
		int tamanio = cromosoma.size();
		Cromosoma mutado = new Cromosoma(cromosoma);
		Random r = new Random();
		
		int indice1 = r.nextInt(tamanio-1),
		    indice2 = r.nextInt(tamanio-1);

		Collections.swap(cromosoma, indice1, indice2);
		
		return mutado;
	}
	
	public static Cromosoma cruzar(Cromosoma p1, Cromosoma p2) {
		int tamanio = p1.size();
		Cromosoma nuevo = new Cromosoma(tamanio);
		
		//TODO Hacer el cruce y obtener el nuevo cromosoma
		
		return nuevo;
	}
}

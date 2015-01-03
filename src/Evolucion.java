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
		
		int ptoCorte1 = r.nextInt(tamanio),
		    ptoCorte2 = r.nextInt(tamanio);

		Collections.swap(mutado, ptoCorte1, ptoCorte2);
		
		return mutado;
	}
	
	/**
	 * Cruza dos cromosomas.
	 * El hijo hereda del padre los alelos que se encuentren entre dos puntos de corte.
	 * El resto de alelos se rellenan desde el principio con los alelos de la madre 
	 * (teniendo en cuenta el no repetir) empezando por el primer punto de corte.
	 * 
	 * @param padre Cromosoma padre
	 * @param madre Cromosoma madre
	 * @return Cromosoma que resulta de cruzar padre y madre
	 */
	public static Cromosoma cruzar(Cromosoma padre, Cromosoma madre) {
		int tamanio = padre.size();
		Cromosoma nuevo = new Cromosoma(tamanio);
		Random r = new Random();
		
		int ptoCorte1 = r.nextInt(tamanio),
		    ptoCorte2 = r.nextInt(tamanio);
		
		if (ptoCorte1 > ptoCorte2) {
			int aux = ptoCorte1;
			ptoCorte1 = ptoCorte2;
			ptoCorte2 = aux;
		}
		
		for (int i = ptoCorte1; i <= ptoCorte2; i++)
			nuevo.set(i, padre.get(i));
		
		int i = ptoCorte1, 
		    j = 0;
		while ( nuevo.contains(null) ) {
			if (!nuevo.contains(madre.get(i)) && nuevo.get(j)==null) {
				nuevo.set(j, madre.get(i));
				j++;
				i = (i+1)%tamanio;
			}
			else if (nuevo.get(j)!=null) {
				j++;
			}
			else {
				i = (i+1)%tamanio;
			}
		}
		
		return nuevo;
	}
}

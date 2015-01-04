import java.util.ArrayList;
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
	public static void mutar(Cromosoma cromosoma) {
		int tamanio = cromosoma.size();
//		Cromosoma mutado = new Cromosoma(cromosoma);
		Random r = new Random();
		
		int ptoCorte1 = r.nextInt(tamanio),
		    ptoCorte2 = r.nextInt(tamanio);
		
		while (ptoCorte1 == ptoCorte2) {
			ptoCorte2 = r.nextInt(tamanio);
		}

		Collections.swap(cromosoma, ptoCorte1, ptoCorte2);
		
//		return mutado;
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
	public static Cromosoma cruzar(Cromosoma padre, Cromosoma madre, int ptoCorte1, int ptoCorte2) {
		int tamanio = padre.size();
		Cromosoma nuevo = new Cromosoma(tamanio);
		
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
//		Cromosoma solucionHijo1 = new Cromosoma();
//		
//		// Se inicializa el hijo
//		for(int i=0; i<tamanio; i++)
//			solucionHijo1.add(-1);
//		
//		// Se copian los alelos del cromosoma p1 del intervalo en el hijo
//		for(int i=ptoCorte1; i<=ptoCorte2; i++)
//			solucionHijo1.set(i, padre.get(i));
//		
//		int aux = (ptoCorte2+1)%tamanio;
//		int aux2 = (ptoCorte2+1)%tamanio;
//		
//		// Se copian en el hijo los alelos en el orden en el que aparecen
//		// en el cromosoma p2 teniendo en cuenta que no se pueden repetir
//		// valores en el hijo
//		while(aux != ptoCorte1)
//		{
//			if(!solucionHijo1.contains(madre.get(aux2)) )
//			{
//				solucionHijo1.set(aux, madre.get(aux2));
//				aux = (aux +1)%tamanio;
//			}
//			
//			aux2 = (aux2 + 1)%tamanio;
//		}
//		return solucionHijo1;
	}
}

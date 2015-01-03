import java.util.ArrayList;

/**
 * Clase que modela un cromosoma.
 * 
 * @author Antonio Toro
 */
public class Cromosoma extends ArrayList<Integer> {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Construye un cromosoma de un determinado tamanio.
	 * 
	 * @param tamanio Tamanio del cromosoma
	 */
	public Cromosoma(int tamanio) {
		super();
		for (int i = 0; i < tamanio; i++)
			this.add(-1);
	}
}

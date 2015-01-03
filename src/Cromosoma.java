import java.util.ArrayList;

/**
 * Clase que modela un cromosoma.
 * 
 * @author Antonio Toro
 */
public class Cromosoma extends ArrayList<Integer> {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor por defecto.
	 */
	public Cromosoma() { }
	
	/**
	 * Construye un cromosoma de un determinado tamanio.
	 * 
	 * @param tamanio Tamanio del cromosoma
	 */
	public Cromosoma(int tamanio) {
		for (int i = 0; i < tamanio; i++)
			this.add(null);
	}
	
	/**
	 * Constructor de copia. Crea un cromosoma igual.
	 * 
	 * @param cromosoma Cromosoma a copiar
	 */
	public Cromosoma(Cromosoma cromosoma) {
		super(cromosoma);
	}
}

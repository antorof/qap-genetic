import java.util.List;

/**
 * Clase que modela un algoritmo genetico estandar.
 * 
 * @author Antonio Toro
 */
public class AlgoritmoGenetico {
	public static final double PROB_MUTACION = 0.05;
	
	private Casos casos;
	private List<Cromosoma> poblacion;
	
	public AlgoritmoGenetico(Casos casos, List<Cromosoma> poblacion) {
		this.casos = casos;
		this.poblacion = poblacion;
	}

	public Casos getCasos() {
		return casos;
	}

	public List<Cromosoma> getPoblacion() {
		return poblacion;
	}
}

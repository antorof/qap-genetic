import java.util.Collections;
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
	private int tamanioPoblacion;
	
	public AlgoritmoGenetico(Casos casos, List<Cromosoma> poblacion) {
		this.casos = casos;
		this.poblacion = poblacion;
		this.tamanioPoblacion = poblacion.size();
	}

	public Casos getCasos() {
		return casos;
	}

	public List<Cromosoma> getPoblacion() {
		return poblacion;
	}

	public int getTamanioPoblacion() {
		return tamanioPoblacion;
	}
	
	public void ejecutar() {
		int generaciones = 0;
		int mejorFitness = -1;
		Cromosoma mejorCromosoma;
		
		// Calculamos el fitness para cada cromosoma
		for (Cromosoma cromosoma : poblacion) {
			cromosoma.setFitness(QAP.fitness(cromosoma, casos.getFlujos(), casos.getDistancias()));
		}
		// Ordenamos la poblacion respecto a su fitness
		Collections.sort(poblacion);
		
		mejorCromosoma = poblacion.get(0);
		mejorFitness   = mejorCromosoma.getFitness();
		
		boolean parar = false;
		while(!parar) {
			generaciones++;
			
			// Cruzamos los individuos
			for (int i = 0; i < poblacion.size()-1; i++) {
				Cromosoma padre = poblacion.get(i),
				          madre = poblacion.get(i);
				
				Cromosoma hijo1 = Evolucion.cruzar(padre, madre),
				          hijo2 = Evolucion.cruzar(madre, padre);

				hijo1.setFitness(QAP.fitness(hijo1, casos.getFlujos(), casos.getDistancias()));
				hijo2.setFitness(QAP.fitness(hijo1, casos.getFlujos(), casos.getDistancias()));

				poblacion.add(hijo1);
				poblacion.add(hijo2);
			}

			// Mutamos los individuos teniendo en cuenta la probabilidad
			for (Cromosoma cromosoma : poblacion) {
				if (Math.random() < PROB_MUTACION ) {
					cromosoma = Evolucion.mutar(cromosoma);
					cromosoma.setFitness(QAP.fitness(cromosoma, casos.getFlujos(), casos.getDistancias()));
				}
			}
			
			// Ordenamos la poblacion respecto al fitness y eliminamos los que sobren
			Collections.sort(poblacion);
			while(poblacion.size() > tamanioPoblacion)
				poblacion.remove(poblacion.size()-1);

			// Condicion de parada
			if (generaciones == 1000) {
				parar = true;
			}
		}
		
		System.out.println("Numero de generaciones: " + generaciones);
		System.out.println("Fitness: " + poblacion.get(0).getFitness());
		System.out.println("Solucion: " + poblacion.get(0));
	}
}

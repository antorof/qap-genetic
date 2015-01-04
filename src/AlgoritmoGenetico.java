import java.util.Collections;
import java.util.List;

/**
 * Clase que modela un algoritmo genetico estandar.
 * 
 * @author Antonio Toro
 */
public class AlgoritmoGenetico {
	public static final double PROB_MUTACION = 0.1;
	
	protected Casos casos;
	protected List<Cromosoma> poblacion;
	protected int tamanioPoblacion;
	
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
//		System.out.println("AlgoritmoGenetico.ejecutar()");
		int generaciones = 0;
		int generacionesSinMejora = 0;
		
		// Calculamos el fitness para cada cromosoma
		for (Cromosoma cromosoma : poblacion) {
			cromosoma.setFitness(QAP.fitness(cromosoma, casos.getFlujos(), casos.getDistancias()));
		}
		// Ordenamos la poblacion respecto a su fitness
		Collections.sort(poblacion);
		
		boolean parar = false;
		while(!parar) {
//			System.out.println("Generacion "+generaciones);
			generaciones++;

			Cromosoma mejorCromosomaAnterior = poblacion.get(0);
			
//			System.out.println("Cruzar");
			// Cruzamos los individuos
			for (int i = 0; i < tamanioPoblacion-1; i++) {
				Cromosoma padre = poblacion.get(i),
				          madre = poblacion.get(i+1);
				
				Cromosoma hijo1 = Evolucion.cruzar(padre, madre),
				          hijo2 = Evolucion.cruzar(madre, padre);

				hijo1.setFitness(QAP.fitness(hijo1, casos.getFlujos(), casos.getDistancias()));
				hijo2.setFitness(QAP.fitness(hijo1, casos.getFlujos(), casos.getDistancias()));
				
				poblacion.add(hijo1);
				poblacion.add(hijo2);
			}

//			System.out.println("Mutar");
			// Mutamos los individuos teniendo en cuenta la probabilidad
			for (Cromosoma cromosoma : poblacion) {
				if (Math.random() < PROB_MUTACION ) {
					Evolucion.mutar(cromosoma);
					cromosoma.setFitness(QAP.fitness(cromosoma, casos.getFlujos(), casos.getDistancias()));
				}
			}

//			System.out.println("Ordenar");
			// Ordenamos la poblacion respecto al fitness y eliminamos los que sobren
			Collections.sort(poblacion);
			while(poblacion.size() > tamanioPoblacion)
				poblacion.remove(poblacion.size()-1);

			// Condicion de parada
//			if (generaciones == 1000) {
			if (mejorCromosomaAnterior.compareTo(poblacion.get(0)) <= 0) {
				generacionesSinMejora++;
//				parar = true;
			} else {
				generacionesSinMejora = 0;
			}
			
			if (generacionesSinMejora > 10 || generaciones == 1000) {
				parar = true;
			}
		}
		
		System.out.println("Numero de generaciones: " + generaciones);
		System.out.println("Fitness: " + poblacion.get(0).getFitness());
		System.out.println("Solucion: " + poblacion.get(0));
	}
}

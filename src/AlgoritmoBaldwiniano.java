import java.util.Collections;
import java.util.List;

/**
 * Clase que modela un algoritmo genetico de tipo baldwiniano.
 * 
 * @author Antonio Toro
 */
public class AlgoritmoBaldwiniano extends AlgoritmoGenetico {

	public AlgoritmoBaldwiniano(Casos casos, List<Cromosoma> poblacion) {
		super(casos, poblacion);
	}

	public void ejecutar() {
//		System.out.println("AlgoritmoBaldwiniano.ejecutar()");
		int generaciones = 0;
		int generacionesSinMejora = 0;
		
		// Calculamos el fitness para cada cromosoma
		for (Cromosoma cromosoma : poblacion) {
			cromosoma.setFitness(QAP.fitness(cromosoma, casos.getFlujos(), casos.getDistancias()));
			cromosoma.actualizarEstadoAnterior();
		}
		// Ordenamos la poblacion respecto a su fitness
		Collections.sort(poblacion);
		
		boolean parar = false;
		while(!parar) {
			generaciones++;

			Cromosoma mejorCromosomaAnterior = poblacion.get(0);
			
			// Cruzamos los individuos
			for (int i = 0; i < tamanioPoblacion-1; i++) {
				Cromosoma padre = poblacion.get(i),
				          madre = poblacion.get(i+1);
				
				Cromosoma hijo1 = Evolucion.cruzar(padre.getEstadoAnterior(), madre.getEstadoAnterior()),
				          hijo2 = Evolucion.cruzar(madre.getEstadoAnterior(), padre.getEstadoAnterior());

				hijo1.setFitness(QAP.fitness(hijo1, casos.getFlujos(), casos.getDistancias()));
				hijo2.setFitness(QAP.fitness(hijo1, casos.getFlujos(), casos.getDistancias()));

				hijo1.actualizarEstadoAnterior();
				hijo2.actualizarEstadoAnterior();

				poblacion.add(hijo1);
				poblacion.add(hijo2);
			}

			// Mutamos los individuos teniendo en cuenta la probabilidad
			for (Cromosoma cromosoma : poblacion) {
				if (Math.random() < PROB_MUTACION ) {
					Evolucion.mutar(cromosoma);
					cromosoma.setFitness(QAP.fitness(cromosoma, casos.getFlujos(), casos.getDistancias()));
				}
			}

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

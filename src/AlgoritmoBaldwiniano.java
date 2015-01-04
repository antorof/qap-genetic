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

	public void greedy(Cromosoma cromosoma) {
		int mejorFitness = cromosoma.getFitness();
	 
        for ( int i = 0; i < cromosoma.size() - 1; i++ ) {
            for (int j=i+1; j < cromosoma.size(); j++) {
            	// Creamos un nuevo cromosoma a partir del actual
            	Cromosoma c = new Cromosoma(cromosoma);
            	
            	// Intercambiamos los alelos
            	Collections.swap(c, i, j);

        		int nuevoFitness = QAP.fitness(c, casos.getFlujos(), casos.getDistancias());
 
        		// Si el nuevo fitness es mejor sustituimos el cromosoma
                if ( nuevoFitness < mejorFitness ) {
                    cromosoma = c;
                    cromosoma.setFitness(nuevoFitness);
                    mejorFitness = nuevoFitness;
                }
            }
        }
//		int mejor;
//		Cromosoma S = cromosoma;
//		do {
//			mejor = S.getFitness();
//			for (int i = 0; i < cromosoma.size(); i++) {
//				for (int j = i+1; j < cromosoma.size(); j++) {
//					Cromosoma T = new Cromosoma(S);
//					Collections.swap(T, i, j);
//					
//					int nuevoFitness = QAP.fitness(T, casos.getFlujos(), casos.getDistancias());
//					if (nuevoFitness < S.getFitness()) {
//						S = T;
//						S.setFitness(nuevoFitness);
//					}
//				}
//			}
//		} while (S.getFitness() < mejor);
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
			
			// Aplicamos el algoritmo greedy
			for (Cromosoma cromosoma : poblacion) {
				greedy(cromosoma);
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

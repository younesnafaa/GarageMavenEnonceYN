package garages;

import java.io.PrintStream;
import java.util.*;

public class Voiture {

	private final String immatriculation;
	private final List<Stationnement> myStationnements = new LinkedList<>();

	public Voiture(String immatriculation) {
		if (immatriculation == null) {
			throw new NullPointerException("L'immatriculation ne peut pas être null.");
		}
		this.immatriculation = immatriculation;
	}

	public String getImmatriculation() {
		return immatriculation;
	}

	public void entreAuGarage(Garage g) throws IllegalStateException {
		if (estDansUnGarage()) {
			throw new IllegalStateException("La voiture est déjà dans un garage.");
		}

		Stationnement s = new Stationnement(this, g);
		myStationnements.add(s);
	}

	public void sortDuGarage() throws IllegalStateException {
		if (!estDansUnGarage()) {
			throw new IllegalStateException("La voiture n'est pas dans un garage.");
		}

		Stationnement dernierStationnement = myStationnements.get(myStationnements.size() - 1);
		dernierStationnement.terminer();
	}

	public Set<Garage> garagesVisites() {
		Set<Garage> garages = new HashSet<>();
		for (Stationnement s : myStationnements) {
			garages.add(s.getGarageVisite());
		}
		return garages;
	}

	public boolean estDansUnGarage() {
		return !myStationnements.isEmpty() && myStationnements.get(myStationnements.size() - 1).estEnCours();
	}

	public void imprimeStationnements(PrintStream out) {
		Map<Garage, List<Stationnement>> stationnementsParGarage = new LinkedHashMap<>();

		for (Stationnement s : myStationnements) {
			stationnementsParGarage
					.computeIfAbsent(s.getGarageVisite(), k -> new ArrayList<>())
					.add(s);
		}

		for (Map.Entry<Garage, List<Stationnement>> entry : stationnementsParGarage.entrySet()) {
			out.println(entry.getKey() + ":");
			for (Stationnement s : entry.getValue()) {
				out.println("\t" + s);
			}
		}
	}

	@Override
	public String toString() {
		return "Voiture{" +
				"immatriculation='" + immatriculation + '\'' +
				'}';
	}
}

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CarnetAdresses {
    private List<String> addresses;  // Liste des adresses IP

    public CarnetAdresses(String nomFichier) throws IOException {
        addresses = new ArrayList<>();  // Initialisation de la liste vide
        chargerAddresses(nomFichier);        // Chargement des adresses IP
    }

    private void chargerAddresses(String nomFichier) throws IOException {
        // Lecture du fichier avec BufferedReader
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(nomFichier))) {
            String line; // Variable pour stocker chaque ligne lue
            while ((line = bufferedReader.readLine()) != null) {
                addresses.add(line.trim());  // Suppression des espaces inutiles et ajout à la liste
            }
        }
    }

    public List<String> getAddresses() {
        return addresses;
    }
}

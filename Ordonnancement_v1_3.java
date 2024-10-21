/*
 * Nom du fichier : Ordonnancement_v1_2.java
 * Auteur : Groupe n°??
 * Date de création : 17 octobre 2023
 * Description : Résout un problème d'ordonnancement
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;

public class Ordonnancement_v1_3{
  public static final int NEUTRAL = -1;

  /**
   * Renvoie le contenu du ficher sous forme de liste de tableau d'entier.
   * @param file Le fichier à lire
   * @return Une liste de tableau d'entier
   */
  public static ArrayList<int[]> readOrdonnancement(String file)
  {
    try
    {
      // On lit le fichier d'entrer      
      BufferedReader br = new BufferedReader(new FileReader(new File(file))); // Création de l'objet de lecture du fichier
      ArrayList<int[]> intArray = new ArrayList<>();
      String line;

      System.out.println("Mise en mémoire de manière struturer des éléments ('entier') du fichier");
      while((line = br.readLine()) != null && !line.isBlank()) // On le lit ligne par ligne
      {
        String[] stringArgs = line.split(" "); // On convertit la ligne en un tableau de string
        int[] intArgs = new int[stringArgs.length]; // Puis on crée un tableau d'entier de même taille

        for(int i = 0; i < stringArgs.length; i++)
        {
          intArgs[i] = Integer.parseInt(stringArgs[i]); // Converti les valeur du tableau string en entier pour les ajouter dans l'ordre dans notre tableau d'entier
        }
        intArray.add(intArgs); // Et on ajoute notre tableau de taille n dans une liste chainé
      }
      br.close(); // Et on ferme l'objet de lecture
      return(intArray);
    }
    catch(IOException e)
    {
      e.printStackTrace();
    }
    return(new ArrayList<>());
  }

  /**
   * Renvoie une matrice carré de taille n avec une valeur par défaut
   * @param n La taille de la matrice
   * @param defaultValue La valeur par défaut à donné au cellule de la matrice
   * @return Une matrice carré de taille n avec comme valeur defaultValue
   */
  public static int[][] matInt(int n, int defaultValue) // Pour génerer une matrice d'entier de taille n avec defaultValue comme valeur par défaut
  {
    int[][] mat = new int[n][n];
    for(int i = 0; i < mat.length; i++)
    {
      for(int j = 0; j < mat[0].length; j++)
      {
        mat[i][j] = defaultValue; 
      }
    }
    return(mat);
  }

  /**
   * Vérifie que le sommet et bien dans la plage donné par le fichier
   * @param id_sommet L'entier qui représente le sommet
   * @param nbSommet Le nombre de sommet du problème d'ordonnancement
   * @return Si il est bien dans la plage des sommet du problème
   */
  public static boolean isValid(int id_sommet, int nbSommet)
  {          
    if(id_sommet < 1 || id_sommet > nbSommet)
    {
      System.err.println("L'id d'un sommet est invalide (pas dans la plage [1," + nbSommet + "])");
      return(false);
    }
    return(true);
  }

  /**
   * Remplit la matrice avec les données du fichier
   * @param matrice La matrice à remplir
   * @param intArray Les données du fichier
   * @param duration La durée respective de chaque sommet
   */
  public static void filling(int[][] matrice, ArrayList<int[]> intArray, int[] duration)
  {
    // Rappel:  On nous donne les prédéccesseur des sommets, donc, on sait directement si un sommet n'as pas de prédeccesseur. 
    //          Mais, on ne saurait qu'à la fin de la lecture si un sommet n'as pas de successeur (not haveSucessor)
    boolean[] haveSuccessor = new boolean[duration.length];

    for(int i = 0; i < intArray.size(); i++)
    {
      int succ = intArray.get(i)[0];
      if(intArray.get(i).length < 3)
      {
        matrice[0][succ] = 0; // On ajoute les sommets sans prédecesseurs
        System.out.println("a -> " + succ + " = 0");
      }
      else
      {
        for(int j = 2; j < intArray.get(i).length; j++)
        {
          int pred = intArray.get(i)[j];
          matrice[pred][succ] = duration[pred-1]; // On ajoute la durer des prédecesseur du sommet
          System.out.println(pred + " -> " + succ + " = " + duration[pred-1]);
          if(haveSuccessor[pred-1] != true)
          {
            haveSuccessor[pred-1] = true; // On met le sommet prédecesseur a 1 (comme il a donc un successeur)
          }
        }
      }
    }

    for(int i = 0; i < haveSuccessor.length; i++)
    {
      if(haveSuccessor[i] == false) // Si c'est vrai, le sommet n'as pas de successeur
      {
        matrice[i+1][duration.length+1] = duration[i]; // On ajoute la durer à omega des sommets qui n'ont pas de successeur
        System.out.println(i+1 + " -> w = " + duration[i]);
      }
    }
    System.out.println();
  }

  /**
   * Renvoie la matrice remplit avec les données du fichier et une valeur par défaut pour les cellules non modifier
   * @param intArray Les données du fichier
   * @param nbSommet Le nombre de sommet du problème d'ordonnancement
   * @param duration Le tableau des durer respective des sommet
   * @return La matrice remplit avec les données et valeur par defaut
   */
  public static int[][] getMatrice(ArrayList<int[]> intArray, int nbSommet, int[] duration) // Crée et remplit la matrice
  {
    int[][] matrice = matInt(nbSommet+2, NEUTRAL); // On crée la matrice d'ajencement
    filling(matrice, intArray, duration); // On la remplie
    return(matrice); // On la retourne
  }

  /**
   * Renvoie la longueur en termes de chaîne de caractères d'un entier.
   * @param i L'entier dont on veut calculer la longueur.
   * @return La longueur de la chaîne représentant l'entier.
   */
  public static int lengthInt(int i) // Renvoie la longueur en terme de chaine de caractère d'un entier
  {
      int counter = 1;
      if (i == NEUTRAL)
        return(1);
      
      if(i < 0)
        counter++;

      while((i = i/10) != 0)
        counter++;
      
      return(counter);
  }

  /**
   * Renvoie la longueur maximal en termes de chaîne de caractères d'une matrice d'entier.
   * @param mat Le tableau dont on veut trouver la longueur maximal en termes de chaîne de caractères.
   * @return La longueur maximal en termes de chaîne de caractères de la matrice.
   */
  public static int maxLengthIntTab(int[] tab) // Renvoie la longueur max en terme de chaine de caractère d'un tableau d'entier
  {
    int maxLength = lengthInt(tab[0]);

    for(int i : tab)
    {
      int lengthofInt = lengthInt(i);

      if(lengthofInt > maxLength)
        maxLength = lengthofInt;
    }
    return(maxLength);
  }

  /**
   * Affiche n espace dans le terminale.
   * @param n Le nombre d'espace à afficher.
   */
  public static void printSpaces(int n) // Pour affichier les espaces
  {
      System.out.print(new String(new char[n]).replace('\0', ' '));
  }

  /**
   * Affiche la matrice avec une identation correcte.
   * @param mat La matrice à afficher dans le terminale.
   */
  public static void printMatrice(int[][] mat, int[] duration) // Afficher la matrice (avec les bons espacements)
  {
    System.out.println("Matrice des valeurs");
    int maxLength = (maxLengthIntTab(duration) > lengthInt(mat.length))?maxLengthIntTab(duration)+1:lengthInt(mat.length)+1; // On regarde la longeur la plus grande pour adapter les espacements

    // Affichage de la première ligne // On s'aligne au début
    printSpaces(maxLength);

    //Et puis on affiche avec les bon espacement les numéro des sommets
    for(int i = 0; i < mat.length; i++)
    {
      printSpaces(maxLength-lengthInt(i));
      System.out.print(i);
    }
    System.out.println();

    for(int i = 0; i < mat.length; i++) // Pour chaque sommet
    {
      // On affiche sont numéro
      printSpaces(maxLength-lengthInt(i));
      System.out.print(i);
      
      // Puis tout ses successeur
      for(int value : mat[i])
      {
        printSpaces(maxLength-lengthInt(value));
        if (value == NEUTRAL)
          System.out.print("*");
        else
          System.out.print(value);
      }
      System.out.println(); // Et on passe a la ligne suivante
    }
  }

  /**
   * Renvoie la liste des entier qui n'ont pas de rang attribué.
   * @param nbSommet Le nombre de sommet du problème d'ordonnancement.
   * @param rang La liste des sommet avec leur rang.
   * @return La liste des sommet sans rang.
   */
  public static ArrayList<Integer> asNoRang(int nbSommet, ArrayList<int[]> rang) // Regarder qui n'as pas encore de rang
  {
    ArrayList<Integer> asNoRang = new ArrayList<Integer>();
    int[] asRang = new int[nbSommet];
    for(int[] i : rang)
      asRang[i[1]] = 1; // On met comme quoi le sommet a un rang
    
    for(int i = 0; i < nbSommet; i++)
    {
      if(asRang[i] == 0)
        asNoRang.add(i);
    }
    return(asNoRang);
  }

  /**
   * Renvoie la liste des sommets sans prédécesseur.
   * @param mat La matrice de notre problème remplit.
   * @param asNoRang La liste des sommet sans rang.
   * @param sommet Le nombre de sommet de notre problème d'ordonnancement.
   * @return Vrai si il N'A pas de prédecesseur.
   */
  public static boolean asNoPredecessor(int[][] mat, ArrayList<Integer> asNoRang, int sommet) // Renvoie si le sommet à un prédecesseur ou non
  {
    for(int i : asNoRang)
    {
      if(mat[i][sommet] != NEUTRAL)
        return(false);
    }
    return(true);
  }

  /**
   * Calcule le rang des sommets d'une matrice de graphe.
   * @param mat La matrice du problème d'ordonnancement.
   * @return Une liste de tableau d'entier sous la forme [rangDuSommet, idDuSommet].
   */
  public static ArrayList<int[]> rang(int[][] mat) // Calcul le rang des sommet et detecte la présence de circuit
  {
    System.out.println("\nDétection de circuit et calcul du rang\n(Méthode de suppression des point d'entrer)");
    ArrayList<int[]> rang = new ArrayList<int[]>();
    int i = 0;
    while(i < mat.length && rang.size() != mat.length)
    {
      boolean asChanged = false;

      ArrayList<Integer> asNoRang = asNoRang(mat.length, rang); //ArrayList<Integer> asNoRang = new ArrayList<Integer>();
      System.out.print("\nSommets restant: ");
      for(int j : asNoRang)
        System.out.print(j + " ");
      System.out.println();

      System.out.print("Points d'entrée: ");
      for(int j : asNoRang)
      {
        if(asNoPredecessor(mat, asNoRang, j))
        {
          rang.add(new int[]{i, j});
          System.out.print(j + " ");
          asChanged = true;
        }
      }

      if(asChanged == false) // Condition de détection de circuit
      {
        System.out.println("Aucun\n");
        for(int j : asNoRang)
          rang.add(new int[]{NEUTRAL, j});
      }
      else
        System.out.println("\nSuppression des points d'entrée");
      i++;
    }
    return(rang);
  }

  /**
   * Affiche uniquement les rangs de chaque sommet.
   * @param rang La liste des rang des sommet [rangSommet, idRang].
   */
  public static void printRang(ArrayList<int[]> rang) // Affiche juste le rang des sommets
  {
    System.out.println("\nSommet:             Rang:");
    for(int i = 0; i < rang.size(); i++)
    {
      System.out.print(i);
      printSpaces(20-lengthInt(i));
      System.out.print(rang.get(i)[0] +"\n");
    }
  }
  
  /**
   * Renvoie si c'est un graphe d'ordonnancement, affiche au passage les critère respecter ou non.
   * @param asNegativeValue Si il y a une valeur négative dans le problème d'ordonnancement.
   * @param rang La liste des rang des sommets [rangSommet, idSommet].
   * @return Vrai si les conditions sont respectées.
   */
  public static boolean respecteCriteria(boolean asNegativeValue, ArrayList<int[]> rang) // Montre les critères respectés et si il on tous été respectés
  {    
    if(rang.get(rang.size()-1)[0] == NEUTRAL) // Pour afficher si il respecte le critère sur le circuit [rang, idSommet]
      System.out.println("\n - Le graphe comporte un/des circuit(s)");
    else
      System.out.println("\n - Le graphe ne comporte pas de circuit");

    if(asNegativeValue) // Pour afficher si il respecte le critère sur les arcs négatif
      System.out.println(" - Le graphe comporte un/des arc(s) negatif(s)");
    else
      System.out.println(" - Le graphe ne comporte pas d'arc(s) negatif(s)");

    if(asNegativeValue || rang.get(rang.size()-1)[0] == NEUTRAL) // Condition pour définir si tt les critères sont respecter (et donc si on doit sortir)
    {
      System.out.println("\nCe n'est donc pas un graphe d'ordonnancement");
      if(asNegativeValue && rang.get(rang.size()-1)[0] != NEUTRAL) // On affiche les rang ici comme on ne peut pas comptinuer (car circuit)
        printRang(rang);
      return(false);
    }
    System.out.println("\nC'est un graphe d'ordonnancement");
    return(true);
  }

  /**
   * Renvoie la liste des sommets de rang nRang.
   * @param nRang Le n-ième rang voulu.
   * @param rang La liste des rang des sommets [rangSommet, idSommet].
   * @return La liste des sommets appartenant au rang nRang.
   */
  public static ArrayList<Integer> getSommetInRang(int nRang, ArrayList<int[]> rang)
  {
    ArrayList<Integer> idSommet = new ArrayList<Integer>();
    for(int[] i : rang)
    {
      if(i[0] == nRang)
        idSommet.add(i[1]);
      else
      {
        if (i[0] > nRang)
          return idSommet;
      }
    }
    return idSommet;
  }

  /**
   * Calcul les dates et marges de chaque sommet de notre problème d'ordonnacement
   * @param result La matrice des dates et marges vide [int[] auPlutot, int[] auPlusTard, int[] MargeTotal, int[] MargeLibre]
   * @param matrice La matrice de notre problème d'ordonnancement
   * @param rang La liste des rang des sommets [rangSommet, idSommet]
   */
  public static void dateAndMarge(int[][] result, int[][] matrice, ArrayList<int[]> rang) // Calcul les dates et les marges
  {
    System.out.println("\nCalcul des dates et des marges");
    result[0][0] = 0;
    int nbSommet = rang.size();

    for (int i = 1; i < rang.get(rang.size()-1)[0]+1; i++)  // Pour chaque rang
    {
      for(int j : getSommetInRang(i, rang)) // On prend les somment appartenant au rang
      {
        int auPlutôt = -1;
        for(int k = 0; k < nbSommet; k++) // Puis on parcour les prédeccesseur de j
        {
          if(matrice[k][j] != NEUTRAL)
            auPlutôt = (result[0][k] + matrice[k][j] > auPlutôt)?result[0][k] + matrice[k][j]:auPlutôt; // Et on applique la forme de au plutôt
        }
        result[0][j] = auPlutôt;
      }
    }

    result[1][nbSommet-1] = result[0][nbSommet-1];
    for (int i = rang.get(rang.size()-2)[0]; i > 0; i--) // De même pour au plus tard mais dans l'ordre des rang décroissant
    {
      for(int j : getSommetInRang(i, rang))
      {
        int auPlusTard = result[0][nbSommet-1];
        for(int k = 0; k < nbSommet; k++)
        {
          if(matrice[j][k] != NEUTRAL)
            auPlusTard = (result[1][k] - matrice[j][k] < auPlusTard)?result[1][k] - matrice[j][k]:auPlusTard;
        }
        result[1][j] = auPlusTard;
      }
    }

    for(int i = 0; i < nbSommet; i++) // Calcul des marges libres
      result[2][i] = result[1][i] - result[0][i]; // On applique la formule

    for(int i = nbSommet-2; i > 0; i--)
    {
      if(result[2][i] != 0)
      {
        int margeLibre = result[2][i];
        for(int j = 0; j < nbSommet; j++)
        {
          if(matrice[i][j] != NEUTRAL)
            margeLibre = (result[0][j] - (result[0][i] + matrice[i][j]) < margeLibre)?result[0][j] - (result[0][i] + matrice[i][j]):margeLibre; // Casse tête (on regarde l'impacte de nos marge sur les successeur)
        }
        result[3][i] = margeLibre;
      }
    }
  }

  /**
   * Renvoie le rang du sommet x.
   * @param rang La liste des rang.
   * @param x L'id du sommet à chercher.
   * @return Le rang du sommet x.
   */
  public static int getRang(ArrayList<int[]> rang, int x)
  {
    for(int[] r : rang)
    {
      if(r[1] == x)
        return r[0];
    }
    System.exit(1);
    return 0; // Juste pour que l'IDE arrete de m'afficher une erreur
  }

  /**
   * Affiche les dates et marges de notre problème d'ordonnancement.
   * @param result La matrice de des dates et marges de notre problème.
   * @param rang La liste des rang des sommets [rangSommet, idSommet].
   */
  public static void printDateAndMarge(int[][] result, ArrayList<int[]> rang) // Affiche les dates et les marges
  {
    System.out.println("\nSommet:             Rang:               Date au plutôt:     Date au plus tard:  Marge totale:       Marge libre:");
    for(int i = 0; i < result[0].length; i++)
    {
      System.out.print(i);
      printSpaces(20-lengthInt(i));
      System.out.print(getRang(rang, i));
      printSpaces(20-lengthInt(getRang(rang, i)));
      for(int j = 0; j < result.length; j++)
      {
        System.out.print(result[j][i]);
        printSpaces(20-lengthInt(result[j][i]));
      }
      System.out.println();
    }
  }

  /**
   * Liste les successeur sans marge vis à vis d'un sommet (car la marge d'un successeur peut être nul sans que ce soit du au sommet courant).
   * @param matrice La matrice d'ordonnancement.
   * @param result La matrice des dates et marges.
   * @param idSommet L'id du sommet à regarder.
   * @return La liste des sommets successeur sans marge vis à vis de notre sommet.
   */
  public static ArrayList<Integer> noMargeSuccessor(int[][] matrice, int[][] result, int idSommet) // Listing des successeur dans le chemin critique
  {
    ArrayList<Integer> nMargeSucc = new ArrayList<Integer>();
    for(int i = 0; i < matrice.length; i++)
    {
      if(matrice[idSommet][i] != NEUTRAL && result[2][i] == 0 && result[1][idSommet] + matrice[idSommet][i] == result[1][i])
        nMargeSucc.add(i);
    }
    return(nMargeSucc);
  }

  /**
   * Calcul les chemins critique de notre problème d'ordonnancement.
   * @param matrice La matrice d'ordonnancement.
   * @param result La matrice des dates et marges.
   * @param rang La liste des rang [rangSommet, idRang].
   * @return La liste des liste d'entier qui représente les chemins critique du problème.
   */
  public static ArrayList<ArrayList<Integer>> getCriticalPath(int[][] matrice, int[][] result, ArrayList<int[]> rang) // Calcul de(s) chemin(s) critique(s)
  {
    ArrayList<ArrayList<Integer>> currentPath = new ArrayList<ArrayList<Integer>>();

    for (int idSommet : getSommetInRang(0, rang)){ // On crée une liste de chemin partant des sommets de rang 0 (logique)
      ArrayList<Integer> newPath = new ArrayList<Integer>();
      newPath.add(idSommet);
      currentPath.add(newPath);
    }

    boolean asChanged = true;
    while(asChanged)
    {
      asChanged = false;
      int initSize = currentPath.size();
      for(int i = 0; i < initSize; i++)
      {
        int lastSommet = currentPath.get(i).get(currentPath.get(i).size()-1); // On prend le dernier sommet du chemin qu'on regarde
        ArrayList<Integer> nMargeSucc = noMargeSuccessor(matrice, result, lastSommet); // On prend les sommet qui n'ont pas de marge (et qui sont causé par ce point (le fait qu'il n'ont pas de marge))
        if(nMargeSucc.size() != 0)
        {
          asChanged=true;

          if(nMargeSucc.size() > 1){ // Si on a plus de 1 changement, on crée une copie du chemins de base
            ArrayList<Integer> temp = new ArrayList<>(currentPath.get(i)); // Copie
            currentPath.get(i).add(nMargeSucc.get(0)); // On ajoute le premier sommet des successeur critique
            for(int j = 1; j < nMargeSucc.size(); j++)
            {
              ArrayList<Integer> temp_cp = new ArrayList<>(temp); // On copie le chimin de base
              temp_cp.add(nMargeSucc.get(j)); // Et on rajoute le j-ième sommet des successeur critique
              currentPath.add(temp_cp); // Et du coup, on rajoute ce nouveau chemin
            }
          } else {
            currentPath.get(i).add(nMargeSucc.get(0));
          }
        }
      }
    }
    return(currentPath);
  }

  /**
   * Affiche les chemins critiques.
   * @param pathAsset La liste des liste d'entier qui représente les chemins.
   */
  public static void printPath(ArrayList<ArrayList<Integer>> pathAsset) // Affichage des chemins
  {
    System.out.println("\nLe(s) chemin(s) critique(s) est/sont: ");
    for(ArrayList<Integer> path : pathAsset) // Pour chaque chemin
    {
      int length = path.size();
      for(int i = 0; i < length; i++) // On affiche tout les sommet
      {
        System.out.print(path.get(i));
        if(i < length-1)
          System.out.print(" -> ");
      }
      System.out.println(); // Puis on saute une ligne
    }
  }

  /**
   * Algorithme globale du traitement d'un fichier d'ordonnancement.
   * @param file Le fichier représentant le problème d'odennancement.
   */
  public static void processOrdonnancement(String file)
  {
    long startTime = System.nanoTime();

    ArrayList<int[]> intArray = readOrdonnancement(file);
    int nbSommet = intArray.size();

    boolean asNegativeValue = false; //On met une variable aUneValeurNegative pour dececter à la lecture si il y a un arc avec une valeur négative
    int[] duration = new int[nbSommet];
    for(int i = 0; i < nbSommet; i++)
    {
      if(!isValid(intArray.get(i)[0], nbSommet)) // Verifie l'id du sommet
        return;

      duration[intArray.get(i)[0]-1] = intArray.get(i)[1]; // On ajoute les durer des tâche
      if(intArray.get(i)[1] < 0)
        asNegativeValue = true;

      for(int j = 2; j < intArray.get(i).length; j++)  //Verifie l'id des sommets autres
      {
        if(!isValid(intArray.get(i)[j], nbSommet))
          return;
      }
    }
      
    int[][] matrice = getMatrice(intArray, nbSommet, duration); // Calcul de la matrice

    printMatrice(matrice, duration); // Affichage de la matrice
      
    ArrayList<int[]> rang = rang(matrice); // Calcul du rang et détection de circuit [rang, idSommet]
    if(!respecteCriteria(asNegativeValue, rang)) // On vérifie si le graphe respecte les critére et affiche les critère respecte ou non
      return; // On sort de la boucle de traitement si un critère n'est pas respecté

    int[][] result = new int[4][nbSommet+2]; // int[n] : n0 = plutôt, n1 = plus tard, n2 = marge total, n3 = marge libre
    dateAndMarge(result, matrice, rang); // Calcul des dates et marges
    printDateAndMarge(result, rang); // Affichage de (rang, idsommet, dates et marges)

    ArrayList<ArrayList<Integer>> criticalPath = getCriticalPath(matrice, result, rang); // On calcul les chemins critiques
    printPath(criticalPath);

    long endTime = System.nanoTime();
    long timeElapsed = endTime - startTime;
 
    System.out.println("\nExecution time in milliseconds: " + timeElapsed / 1000000);
  }

  public static void main(String[] args) // Mettre en argument le fichier d'odenancement à analyser
  {
    boolean again = true;
    Scanner scanner = new Scanner(System.in);
    while(again)
    {
      System.out.println("Entrer le nom/chemin du fichier à analyser: ");
      processOrdonnancement(scanner.nextLine());

      System.out.println("Voulez vous analyser un autre fichier? (y/n)");
      if(!scanner.next().equals("y"))
        again = false;
      scanner.nextLine(); //Sinon, il prend comme fichier à traiter y
    }
    scanner.close();
    System.exit(0);
  }
}
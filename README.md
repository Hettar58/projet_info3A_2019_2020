# projet_info3A_2019_2020
Projet d'info3A

## Principe
L'objectif est de tracer le plus court chemin entre une source et une arrivée en évitant des obstacles.

L'environnement est représenté par un graphe contenant un certain nombres de points placés aléatoirement. les obstacles sont représentés par l'absence de points.
Les points seront placés dans un tas représenté dans la mémoire comme étant un tableau.

Pour trouver le plus court chemin on utilisera la méthode de Djikstra une première fois. Ensuite, on définit un rayon de visibilité autour des points sélectionnés puis on régénère des points et on recommence.
L'algorithme doit être le + optimisé possible.

# Gestion de la concurrence

## Introduction

Le but de ce projet est de rendre une application spring-boot qui implémente la spécification suivante: https://drive.google.com/file/d/1ar3F6Vv4rbuSnKCKBPikEHhe5a8oMsFG/view?usp=sharing

Il faut implémenter les deux méthodes de gestion de la concurrence sur en fonction des interfaces:

- La mise à jour d&#39;un document doit utiliser un lock optimiste via la norme HTTP
- La pose d&#39;un verrou doit utiliser un lock pessimiste via la base de données

Le choix de la base pour le stockage est libre, vous pouvez utiliser ce que vous voulez : mongodb, mysql, postgres, …

Le fichier swagger décrit les services et les DTO à développer. **Attention le swagger ne décrit pas tous les cas d&#39;erreur remontés et toutes les données nécessaires à gérer tous les cas, à vous de compléter si vous avez des besoins supplémentaires.**

## Règles fonctionnelles

En plus de la spécification de l&#39;API nous avons les règles fonctionnelles suivantes. Il est nécessaire d&#39;être authentifié pour accéder aux différentes API. Il existe deux types d&#39;utilisateurs dans l&#39;application :

- Les rédacteurs
- Les relecteurs

En tant que rédacteur je peux créer un document, le modifier et le verrouiller. Un document est créé à l&#39;état &quot;CREATED&quot;

En tant que relecteur je peux modifier un document, le verrouiller et le passer au statut &quot;VALIDATED&quot;, une fois passer au statut &quot;VALIDATED&quot; il n&#39;est plus possible aux rédacteurs de le modifier.

A la création d&#39;un document le système définit les attributs suivants:

- La date de création
- La date de modification
- Le créateur du document est l&#39;utilisateur loggé
- L&#39;éditeur du document est l&#39;utlisateur loggé

Lors de la modification d&#39;un document l&#39;utilisateur peut mettre à jour les éléments suivants:

- Le titre
- Le texte

Le système met automatiquement à jour les éléments suivants:

- La date de modification
- L&#39;éditeur du document avec l&#39;utilisateur loggué

Si un document est verrouillé, personne à part le propriétaire du verrou ne peut le modifier. Seul le propriétaire d&#39;un verrou peut déverrouiller un document.

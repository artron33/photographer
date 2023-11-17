# Pierre Demo


## Overview
* I used a Google Sample about Compose as base for this project.
* I used the MVVM architecture.
* UI (Compose) -> ViewModel -> Repository -> (Retrofit/Room)
* I've tested the ViewModel (cause it's the only one with logic) with JUnit and Mockito.
* I've no interest to use Dagger/Hilt/Koin for this small project
* No point to use Flow or Flow.collect


## How to use
* 1/ Make a search (by clicking to the top bar)
* 2/ Enter few letter (ex: moon)
* 3/ Click on the dropdown (or Enter with the keybboard) redirect to the SEARCH screen
* 4/ With the SEARCH or DETAIL screen: you can tap the Star to add/remove from your favorite list
* 5/ Use the back button to get back to the SEARCH screen (first screen) or restart the App


## Design & Screenshots

| -                                   | <img src="screenshots/poject.gif"/> |
|-------------------------------------|-------------------------------------|
| <img src="screenshots/state_1.png"> | <img src="screenshots/state_2.png"> |
| <img src="screenshots/state_3.png"> | <img src="screenshots/state_4.png"> |

## Details:

* J'ai commencé par faire du multi-screen, en allant jusqu'au foldable.
* J'ai finalement retiré tout ce code, pour simplifier la review du projet.

* J'ai pas compris si on devait afficher le profile d'un user dans une webView ? (à partir de l'URL, ou juste afficher l'URL?)
* J'ai trouvé que c'était + simple de fetch les favoris 1 fois au démarrage, que de garder un flow avec room, afin d'être notifié des changements d'état.
  * Car je réutilise la meme UI pour les favoris et la recherche.
  * Donc c'est + simple de call l'état que je veux afficher
* J'ai ajouté un delay de 200ms avant de lancer une request reseau (quand on tape), afin d'éviter de faire trop de call unitile.
* J'ai perdu trop de temps pour décider quel UI, pour faire du Dagger/Hilt/Koin
* Les tests servent juste à montrer que je sais en faire, et je connais Mockito. Je sais qu'on fait souvent des tests d'UI avec compose, mais je n'en ai pas encore fait officiellement, alors j'ai pas "tenter" ici.
* J'ai retiré les list_par_defaut (pour avoir la preview avec compose)



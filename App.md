YourCookAssistent
Mit YourCookAssistent können Sie ganz leicht vielzählige Rezepte suchen. Aus diesen können Sie bestimmte Zutaten auswählen und zu Ihrer Einkaufsliste hinzufügen. Außerdem ist es möglich, während dem Kochen einen Timer einzustellen, ohne die App verlassen zu müssen.


Projektidee
Ein persönlicher Koch-Assistent der Kochbegeisterten und Experimentierfreudigen, die sich gerne Zeit sparen, der das Zubereiten vielfältiger Gerichte vereinfacht! In unserer App finden Sie sowohl verschiedene Rezepte mit Bilddatei von den fertigen Gerichten und den benötigten Zutaten, sowie der Beschreibung der einzelnen Schritte der Zubereitung. Die unterschiedlichen Zutaten können in dem Rezept ausgewählt und in die integrierte Einkaufsliste hinzugefügt werden, in der sie nach dem Einkaufen auch wieder gelöscht werden können. Um die vorgegebene Kochzeit gut im Auge behalten zu können, ist es möglich einen Timer in der App zu stellen und im Hintergrund laufen zu lassen. Ein Koch-Assistent, der das Kochen für jeden einfacher macht!


Funktionsumfang
Responsive Layout -	User Interface
Notification	- User Interface
Wiederverwendbare Layoutbausteine: Fragments - User Interface
Adapter	- Software-Engineering
Shared Preferences 	- Persistenz
Intents	- Kommunikation
Tabbing	- User Interface
Widgets	- User Interface
Bottom Navigation -	User Interface
Datenbank	- Persistenz
Abspielen von Sound (Timer)	- Audio
Service für Hintergrundaufgaben -	Software-Engineering
API 	


User Interface
Main Layout mit Bottom-Navigation welches zu verschiedenen Fragments führt: HomeFragment, FavoriteFragment, ShoppingListFragment und TimerFragment.
Home:
In diesem Layout ist es möglich nach verschiedenen Rezepten zu Suchen. Unter der Suchleiste werden zufällige Rezepte vorgeschlagen, die man auch auswählen kann, wodurch das Rezepte Layout geöffnet wird. Mit der erweiterten Suche kann man bestimmte Filter anwenden.

  ![image](https://user-images.githubusercontent.com/86496447/194708619-196190d7-cd98-4fec-985d-09838e22eeb4.png)
  ![image](https://user-images.githubusercontent.com/86496447/194708644-58b015ec-cb85-44c2-8f2c-07cf10ae9545.png)

Favorites:
In diesem Layout werden die verschiedenen Rezepte angezeigt, die man mit einem Herz markiert hat, um sie schnell wiederzufinden und nochmal kochen zu können. Diese werden untereinander aufgelistet mit dazugehörigem Bild und Namen. Durch Klicken kommt man zum Rezept Layout.

  ![image](https://user-images.githubusercontent.com/86496447/194708706-1eea3f2e-9e75-4751-b5ce-2a747a10b733.png)

ShoppingList:
In diesem Layout werden die fehlenden Zutaten in einer Liste angezeigt, die in dem Rezepte Layout ausgewählt wurden, es können aber auch Zutaten manuell eingegeben und zur Einkaufsliste durch einen Button hinzugefügt werden. Hinter den jeweiligen Zutaten ist ein Button, um diese zu entfernen.

  ![image](https://user-images.githubusercontent.com/86496447/194708720-c9e567b9-5497-4422-ac4a-0734c338fb12.png)

Timer:
In diesem Layout ist es möglich einen Timer durch Eingabe der Stunden, Minuten und Sekunden einzustellen. Durch einen Start-Button wird dieser gestartet und der Stopp-Button stoppt diesen. Dieser läuft im Hintergrund und man kann währenddessen in einem anderen Layout sein. Nach Ablauf der Zeit bekommt man eine Benachrichtigung. 

  ![image](https://user-images.githubusercontent.com/86496447/194639373-740172b3-c4ae-4713-a8a0-1d1b660c6b23.png)

Rezept Layout:
In diesem Layout wird das ausgewählte Rezept angezeigt und die benötigten Informationen wie der Name des Rezepts, ein Bild des fertigen Gerichts, Kalorien, Dauer und Menge, die benötigten Zutaten und die einzelnen Arbeitsschritte. Im rechten oberen Quadranten ist ein Herz-Button zu finden, mit dem man das jeweilige Gericht favorisieren kann. Die einzelnen Zutaten kann man durch einen Button zur Einkaufsliste hinzufügen.

  ![image](https://user-images.githubusercontent.com/86496447/194708682-ffe894c0-491e-4149-b22c-3376a10e4b5f.png)


Software Design
Die API beziehen wir von einer Webseite mit dem Link https://rapidapi.com/spoonacular/api/recipe-food-nutrition/, durch ein kostenloses Abo, durch welches 150 Rezepte täglich entnommen werden können. Der Aufruf verläuft über Requests (DataRequest, ImageRequest) wodurch Bilder und Informationen zu Zutaten, Kalorien, Menge, Dauer und Zubereitung aufgerufen werden. Außerdem wird zwischen zufälligen und gesuchten Rezepten unterschieden, dadurch ist eine erweiterte Suche möglich. In der RecipesRequest wird diese übergeben.
In der RecipeSearchActivity stehen die Daten für die erweiterte Suche.
Durch die Datenbank werden die Rezepte abgespeichert und die Zutaten in der Einkaufsliste gesichert mit einer DAO.
In der MainActivity wird die Bottom-Navigation gesteuert und die einzelnen Klassen aufgerufen.
In der Timer-Klasse entsteht der Timer, der im Hintergrund laufen kann. Über Notification werden die Benachrichtigungen erstellt.
In der Recipe-Klasse und Product-Klasse stehen die Informationen der Rezepte, welche in der RecipeActivity übergeben werden.


Zip-Datei der fertigen App: 
[RecipeAppDone2.0.zip](https://github.com/Android-Projekte-VHB/vhb-android-ss2022-team-18/files/9739386/RecipeAppDone2.0.zip)


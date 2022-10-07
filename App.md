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
  ![image](https://user-images.githubusercontent.com/86496447/194639269-69fdf2a1-19e7-4cbe-9455-3e8aae148c36.png)
  ![image](https://user-images.githubusercontent.com/86496447/194639301-2e3dae3e-3aca-4d78-9221-4ded72e0501f.png)

Favorites:
In diesem Layout werden die verschiedenen Rezepte angezeigt, die man mit einem Herz markiert hat, um sie schnell wiederzufinden und nochmal kochen zu können. Diese werden untereinander aufgelistet mit dazugehörigem Bild und Namen. Durch Klicken kommt man zum Rezept Layout.
  ![image](https://user-images.githubusercontent.com/86496447/194639322-d142421e-5768-4682-a1ee-8e610911e214.png)

ShoppingList:
In diesem Layout werden die fehlenden Zutaten in einer Liste angezeigt, die in dem Rezepte Layout ausgewählt wurden, es können aber auch Zutaten manuell eingegeben und zur Einkaufsliste durch einen Button hinzugefügt werden. Hinter den jeweiligen Zutaten ist ein Button, um diese zu entfernen.
  ![image](https://user-images.githubusercontent.com/86496447/194639350-6deacb92-c7e0-4c4c-beb7-1ef755829ac9.png)

Timer:
In diesem Layout ist es möglich einen Timer durch Eingabe der Stunden, Minuten und Sekunden einzustellen. Durch einen Start-Button wird dieser gestartet und der Stopp-Button stoppt diesen. Dieser läuft im Hintergrund und man kann währenddessen in einem anderen Layout sein. Nach Ablauf der Zeit bekommt man eine Benachrichtigung. 
  ![image](https://user-images.githubusercontent.com/86496447/194639373-740172b3-c4ae-4713-a8a0-1d1b660c6b23.png)

Rezept Layout:
In diesem Layout wird das ausgewählte Rezept angezeigt und die benötigten Informationen wie der Name des Rezepts, ein Bild des fertigen Gerichts, Kalorien, Dauer und Menge, die benötigten Zutaten und die einzelnen Arbeitsschritte. Im rechten oberen Quadranten ist ein Herz-Button zu finden, mit dem man das jeweilige Gericht favorisieren kann. Die einzelnen Zutaten kann man durch einen Button zur Einkaufsliste hinzufügen.
  ![image](https://user-images.githubusercontent.com/86496447/194639421-a5e48d11-7784-49d7-b856-dff08317f4fe.png)
  ![image](https://user-images.githubusercontent.com/86496447/194639438-9d851869-eda5-436e-b9e0-7949429afea8.png)


Software Design
Die API beziehen wir von einer Webseite mit dem Link https://rapidapi.com/spoonacular/api/recipe-food-nutrition/, durch ein kostenloses Abo, durch welches 150 Rezepte täglich entnommen werden können. Der Aufruf verläuft über Requests (DataRequest, ImageRequest) wodurch Bilder und Informationen zu Zutaten, Kalorien, Menge, Dauer und Zubereitung aufgerufen werden. Außerdem wird zwischen zufälligen und gesuchten Rezepten unterschieden, dadurch ist eine erweiterte Suche möglich. In der RecipesRequest wird diese übergeben.
In der RecipeSearchActivity stehen die Daten für die erweiterte Suche.
Durch die Datenbank werden die Rezepte abgespeichert und die Zutaten in der Einkaufsliste gesichert mit einer DAO.
In der MainActivity wird die Bottom-Navigation gesteuert und die einzelnen Klassen aufgerufen.
In der Timer-Klasse entsteht der Timer, der im Hintergrund laufen kann. Über Notification werden die Benachrichtigungen erstellt.
In der Recipe-Klasse und Product-Klasse stehen die Informationen der Rezepte, welche in der RecipeActivity übergeben werden.




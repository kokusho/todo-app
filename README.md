# todo-app
Webtech2 Projekt SS20 Todo App mit Maven + Angular

# Spring Boot application
https://start.spring.io/

```
mvn package & java -jar target\todo-app-0.0.1-SNAPSHOT.jar
```
# Datenbank
Die Anwendung arbeite mit einer MySqlDatenbank. Diese muss lokal laufen. Die Konfiguration der Datenbank kann in der `application.properties` Datei vorgenommen werden.

# Angular Frontend
https://medium.com/@majdasab/integrating-an-angular-project-with-spring-boot-e3a043b7307b

# Herangehensweise
1. Spring Boot Projekt mit Spring Initializer aufgesetzt
2. Angular App erstellt und dafür gesorgt dass es von Maven mitgebaut wird beim deploy.
3. Datenbank Verbindung eingerichtet und grundlegendes DTO für ein Todo implementiert.
4. Grundlegenden RestController für Basis-Crud Funktionalität für Todos eingebaut.
5. Apache Shiro Abhängigkeiten hinzugefügt + shiro.ini
6. Dafür gesorgt dass shiro user aus der db liest
7. Einfache login und register Formulare in Angular gebaut
8. Formulare an das Backend dran gekabelt.
   * UserService in Angular erstellt, der die http Kommunikation übernimmt
   * bei den onSubmit-Events der jeweiligen Formulare den userService mit der entsprechenden Methode aufgerufen
9. MessageService in Angular eingebaut als util um Benachrichtigungen im Frontend als Bootstrap Alerts anzuzeigen
10. RoutingController hinzugefügt um bestimmte Routen von Angular handlen zu lassen.

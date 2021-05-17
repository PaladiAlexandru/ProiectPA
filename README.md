# ProiectPA




Descriere

După ce serverul a acceptat două conexiuni, jocul începe. Cei doi clienți trimit mutările către server, iar  serverul le trimite înapoi mutarea oponentului. Jocul se termină când unul din jucatori trimite mutarea „4”, având ca și câștigător jucătorul care a trimis comanda.


Mutări de bază:


2- nu am prima mutare
3- am prima mutare
4- jocul s-a încheiat. 

Game flow:

-Server side:
* Așteaptă la portul 8101 cereri de conexiune.
* După ce se conectează un client, acesta este adăugat într-o listă și se așteaptă încă un client pentru a începe jocul
* După ce s-au conectat doi clienți , serverul creează un nou fir de execuție pe care cei doi clienți comunică cu serverul
* Le trimite fiecăruia 2 - nu ai prima mutare sau 3 - ai prima mutare.
*Așteaptă mutare de la player-ul care are prima mutare, și o trimite la celălalt.
*Dacă mutarea este “4” jocul se termină

-Client side:
1.Afișează interfața cu jocul și cu setările jocului.
2.Se alege tipul de joc, și când apasă pe play, dacă tipul de joc este „Human” se conectează la server, iar dacă este „Computer”  afișează tabla și joacă contra computer-ului.
3.Așteaptă de la server 2 sau 3 și setează „myTurn” corespunzător.

4.Dacă „myTurn”:true , așteaptă mutarea clientului pe tablă și apoi o trimite, setând „myTurn”: false
5.Așteaptă de la server mutarea oponentului. După ce primește mutarea, setează „myTurn” : true

6.Se repetă pasul 4,5 cât timp mutarea nu este „4”
7.Dacă primește „4” de la server, atunci a pierdut.
8.Jucătorul care pune primul pe spike-ul 7, respectiv 19, 2 piese, câștigă.

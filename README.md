Prosta gra w GO
# Instrukcja
  Oto instrukcja do gry dla konsolowego ui (niestety gui tak jak baza mongodb nie są dopracowane)

  Aby zagrać, mależy uruchomić 1 instancję serwera oraz jakiś klientów.
## Logowanie
  Przy uruchamianiu klienta, ten łączy się z serwerem i jeśli się mu uda, prosi o login i hasło. Jeśli login i hasło zgadzają się z jakimiś na serwerze bądź wpisze się zupełnie nowy login, serwer zaakceptuje gracza i przechodzimy do głównego menu.
## Główne menu
  W głównym menu należy wybrać, czy chce się grać czy chce się przeglądnąć historię.
  
  komenda “play” – gramy w nową grę; wielkość planszy podajemy w tej samej linijce po spacji np. Play 19 zbuduje nam planszę 19x19. Dostępne rozmiary to 9, 13 i 19
  komenda „history” – odtwarzamy grę z bazy danych
## Gra
  Wyświetla się nam informacja o naszym kolorze, plansza oraz informacja o kolorze, który ma w danej turze ruch. 
  W przypadku naszego ruch wpisujemy współrzędne pola (liczymy od 0): \<numer wiersza od góry\> \<numer kolumny od lewej\>
  Możemy również pominąc naszą turę (pass): p
  Albo poddac się (surrender): r
## Historia
  Wyświetla się podmenu oraz id dostępnych gier (od 0). Wybieramy grę, którą chcemy wyświetlic podając jej id. Aby przeglądac kolejne ruchy należy wpisac "n", aby powrócic do poprzedniego ruchu - "p". W każym momencie możemy wpisac "m" w celu powroty do menu głównego.
  (W celu korzystania z bazy można stworzyc 2-wymiarową tablicę typu np. short, która będzie tłumaczyła 2-wymiarową tablicę enumów, jednakże nie jest to niezbędne)

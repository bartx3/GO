Prosta gra w GO
# Instrukcja
  Oto instrukcja do gry dla konsolowego ui (niestety gui tak jak baza mongodb nie są dopracowane)

  Aby zagrać, mależy uruchomić 1 instancję serwera oraz jakiś klientów.
## Logowanie
  Przy uruchamianiu klienta, ten łączy się z serwerem i jeśli się mu uda, prosi o login i hasło. Jeśli login i hasło zgadzają się z jakimiś na serwerze bądź wpisze się zupełnie nowy login, serwer zaakceptuje gracza i przechodzimy do głównego menu.
## Główne menu
  W głównym menu należy wybrać, czy chce się grać czy chce się przeglądnąć historię. Należy uruchomić za pomocą komendy play <rozmiar>. Dostępne rozmiary to 9, 13 i 19 (odpowiadające odpowiednim instancjom Pairer)
  Można też przedlądać historię, podając komendę history.
## Gra
  Podawanie ruchów (liczymy od 0): \<numer wiersza od góry\> \<numer kolumny od lewej\>
  Passowanie ruchu: p
  Poddanie gry: r
## Historia
  Wyświetla się podmenu oraz id dostępnych gier. Aby wyjść z historii należy podać m . Aby oglądnąć daną grę należy wpisać jej id. Potem wyświetlają się kto grał, kto wygrał i kolejne rundy. n by przejść do kolejnej, p do poprzedniej i m by wyjść z tej gry do menu wyboru gier.

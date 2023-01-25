# System kontroli wind w budynku

## Wstęp

System umożliwia kontrolę do 16 wind w budynku maksymalnie
10 piętrowym za pomocą wspólnego panelu do ich przywoływania.
Służy do symulacji zachowań wind w budynku.

## Uruchomienie

Program został napisany w języku Java 19.

Program wywołujemy z dwoma parametrami: liczbą pięter w budynku 
1 ≤ **floorsNumber** ≤ 10 oraz liczbą wind w budynku 
1 ≤ **elevatorsNumber** ≤ 16.

## Opis problemu

System posiada wspólny panel do przywoływania wind, dlatego program
musi rozwiązać tak naprawdę dwa problemy. Pierwszym z nich jest wybór windy, 
która ma obsłużyć dane żądanie. Drugi problem to algorytm działania 
pojedynczej windy, tzn. to w jakiej kolejności ma ona obsłużyć przypisane jej
żądania.

## Rozwiązanie

### Wybór windy obsługującej dane żądanie

Gdy przyjdzie nowe żądanie do obsłużenia, tzn. ktoś przywoła windę za pomocą panelu, 
to w pierwszej kolejności musimy wybrać windę, która dostanie to żądanie do obsłużenia.


1. W pierwszej kolejności szukamy windy, która jest bezczynna, tzn. nie ma żadnych zadań 
do wykonania i jeżeli taka istnieje to ją wybieramy. Jeżeli nie znaleźliśmy takiej windy to:
2. Szukamy wszystkich wind, których kierunek aktualnie
obsługiwanego żądania **direction** jest zgodny z kierunkiem rozpatrywanego
żądania.
Następnie wybieramy te windy, dla których znajdujemy się na piętrze, które 
jest pomiędzy piętrem, na którym znajduje się aktualnie winda **currentFloor** 
a piętrem, do którego aktualnie zmierza **currentDestinationFloor**, tzn. piętro, 
z którego wysłano żądanie jest na drodze windy. 
W ostatnim kroku z tak wybranego zbioru wind, wybieramy tą, która jest w 
najmniejszej odległości od piętra, z którego wysłano żądanie. 
3. Jeżeli nie było takich wind, dla których jesteśmy na drodze tej windy, 
to najpierw szukamy wszystkich wind, których kierunek aktualnie obsługiwanego 
żądania jest zgodny z kierunkiem rozpatrywanego żądania.
Następnie z tak wybranego zbioru wind wybieramy tą, która po dotarciu na piętro, 
do którego aktualnie zmierza będzie miała do nas najbliżej. 
4. Jeżeli niebyło takich wind, których kierunek aktualnie obsługiwanego 
żądania jest zgodny z kierunkiem rozpatrywanego żądania, to wybieramy tą windę, 
która jest najmniej zajęta, tzn. tą która ma najmniej przypisanych żądań do przetworzenia.
 
### Kolejność w jakiej dana winda obsługuje przypisane jej żądania

Każda z wind może dostać pewną liczbę żądań do obsłużenia i musi mieć algorytm 
wyboru kolejności tych żądań.

Żądania są przechowywane w strukturze **TreeSet**, w której elementy są zawsze są posortowane 
wg ustalonego porządku, a każdy nowo wstawiony element jest wstawiany w odpowiednie miejsce, 
tak aby zachować ustalony porządek. W tym przypadku żądania są postowane rosnąco wg piętra, 
z którego przyszło żądanie.

Każda winda ma 3 takie zbiory: 
- **currentRequests** - są to żądania, które będą kolejno obsługiwane przez windę
- **pendingUpRequests** oraz **pendingDownRequests** - są to żądania odpowiednio jazdy w górę i w dół, 
które oczekują na wybranie do obsłużenia przez windę.

Każde nowo przychodzące żądanie jest dodawane do odpowiedniej kolejki żądań oczekujących - **pendingUpRequests** 
lub **pendingDownRequests**.

Gdy winda wybiera nowe żądanie do obsłużenia, to sprawdza, czy w kolejce **currentRequests** są jakieś żądania:
- jeżeli tak to: 
  - jeżeli **direction** == UP, to wybiera pierwsze żądanie z kolejki, czyli to o 
  najniższym numerze piętra, tak aby jadąc do góry zabrać po drodze pozostałych, którzy też będą 
  chcieli jechać w górę,
  - a jeżeli **direction** == DOWN, to z końca kolejki,
- jeżeli nie to: 
  - jeżeli **direction** == UP to sprawdza najpierw kolejkę żądań oczekujących **pendingDownRequests** 
  i jeżeli nie jest pusta to bierze do przetworzenia wszystkie żądania z tej kolejki, a jeżeli jest 
  pusta to sprawdza drugą kolejkę,
  - jeżeli **direction** == DOWN to kolejki żądań oczekujących są sprawdzane w odwrotnej kolejności.
  Naprzemiennie wybieramy żądania z przeciwnym kierunkiem jazdy, aby te drugie nie oczekiwały 
  w nieskończoność.






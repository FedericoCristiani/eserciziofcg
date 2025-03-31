# Esercizio: CRUD Utenti

## Overview

L’applicazione è stata sviluppata in **Spring Boot**, utilizzando **Spring Data** per l’interazione con il database, che a sua volta si appoggia su **Hibernate**.

L’obiettivo del progetto è implementare un CRUD di oggetti `User` tramite **API REST**.

Gli endpoint partono da `/api/user` e utilizzano il dizionario HTTP:
- **GET** per ottenere i dati
- **POST** per inserire dati
- **PUT** per aggiornare un utente
- **DELETE** per cancellare un utente

I dati scambiati sono in formato JSON, ad esempio:
```json
{
    "id": 3,
    "firstname": "Federico",
    "lastname": "Federico",
    "email": "federico.cristiani@gmail.coaaaaaam",
    "address": "Via dei Magagna,5"
}
```

---

## Endpoint

| Metodo | Endpoint | Descrizione |
|--------|---------|-------------|
| **GET** | `/api/user` | Ottiene la lista di tutti gli utenti |
| **GET** | `/api/user/{id}` | Ottiene un utente specifico |
| **POST** | `/api/user` | Inserisce un nuovo utente |
| **PUT** | `/api/user/{id}` | Aggiorna un utente specifico |
| **DELETE** | `/api/user/{id}` | Cancella un utente specifico |
| **GET** | `/api/user/search?firstname=&lastname=` | Ricerca utenti per nome e/o cognome |
| **POST** | `/api/user/bulkinsert` | Inserimento massivo utenti tramite csv |

I parametri `firstname` e `lastname` in `/api/user/search` sono opzionali e possono essere usati singolarmente o insieme.

L'endpoint per l’inserimento massivo si aspetta un CSV con le colonne FirstName, LastName, Email, Address e restituisce errore se le colonne non corrispondono


---

## Scelte progettuali

- Separazione tra **entità** (`UserEntity`) e **DTO** per il trasporto dei dati.
- Mapping tra entità e DTO con **MapStruct**.
- Utilizzo di **Lombok** per ridurre il codice boilerplate (getter, setter, etc.).
- **Jakarta Validation** per la validazione dei DTO e gestione degli errori `400 Bad Request`.
- Gli errori di risorse non trovate restituiscono un errore `404 Not Found` direttamente dal controller.
- Un **ApplicationRunner** popola il database con un record di test all’avvio.
- L'inserimento massivo tramite CSV è stato sviluppato con il pacchetto Apache-csv

> **Nota:** Gli endpoint `GET /api/user/` e `GET /api/user/search` restituiscono la lista dei risultati con due modalità diverse: una usando uno **stream** per la mappatura degli oggetti e l’altra con un mapper lista-a-lista (`entity => dto`).

---

## Installazione

Il pacchetto include un file `docker-compose.yml`, avviabile con il comando:

```sh
docker-compose up -d
```

Questo comando avvierà:
- **Un container per l’applicazione**
- **Un container per il database MariaDB**, configurato su `tmpfs` in modo che ad ogni avvio riparta con un database pulito.

> **Nota:** Il `Dockerfile` dell’applicazione, oltre a creare l’immagine Docker, compila e genera il JAR con **Maven**, e include uno script per attendere l’avvio completo del database prima di eseguire l’applicazione.

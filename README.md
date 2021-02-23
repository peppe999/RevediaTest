# Revedia
Progetto combinato di INGSW e Web Computing

Revedia è un sito di recensioni di contenuti musicali, film e libri.
Il database è molto variegato e contiene dati inseriti dinamicamente grazie alle API di Spotify, TMDB e Google Books.
Ulteriori informazioni sul sito saranno allegate qui.

# Volume docker
Il volume contenente il container docker è in questa repository, ma nel caso si dovessero verificare problemi 
è stato incluso anche un backup in formato sql.

In questa eventualità:

1) Creare un nuovo container docker:
docker run --name pg-docker -e POSTGRES_PASSWORD=postgres -d -p 5432:5432 -v <directory_locale>:/var/lib/postgresql/data postgres
In questo caso <directory_locale> deve essere una cartella del proprio disco, ma differente da quella presente tra i file di progetto.

2) Aggiungere un nuovo server chiamato revediaserver, in ascolto sulla porta 5432, password: postgres

3) Creare un nuovo database chiamato revedia

4) Effettuare la procedura di restore sul db appena creato, utilizzando come fonte il file sql incluso nel progetto. Prima di avviare la procedura:
   Selezionare la tab Restore options e selezionare tutte le spunte della sola sezione "Sections". Lasciare le altre opzioni come sono impostate.
   
5) Ignorare un eventuale errore del tipo: exit code 1.

Il database è ora correttamente ripristinato allo stato corrente!

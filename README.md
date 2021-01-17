# Teste Símios - Mercado Livre

API to detect human or simian DNA sequences

## Made with

SpringBoot 2.3.8
MongoDB
Spring Data
Lombok
Junit and Mockito
AWS

### Prerequisites

Postman

```
https://dl.pstmn.io/download/latest/win64
```

## Instructions

Make a POST request to the API with Postman

```
http://testesimios-env.eba-ru4mcj7x.us-east-2.elasticbeanstalk.com/api/simian
```

With the following JSON body: String [] dna = {"CTGAGA", "CTGAGC", "TATTGT", "AGAGGG", "CCCCTA", "TCACTG"};

It will return a 400 status because it's already saved in the database
If you input a different valid dna array it will return 200 and saved it
If it's invalid it will return a 403 status

Make a GET request to the API with Postman *

```
http://testesimios-env.eba-ru4mcj7x.us-east-2.elasticbeanstalk.com/api/stats
```

It will return something like this: {
    "count_mutant_dna": 3,
    "count_human_dna": 0,
    "ratio": 3.000
}

We see the stats of all dna saved in the database

### Endpoints

```
Name: /simian
Method: POST
Content-Type: application/json
Response: application/json
Body:   String [] dna = {"CTGAGA", "CTGAGC", "TATTGT", "AGAGGG", "CCCCTA", "TCACTG"};
```

```
Name: /stats
Method: GET
Content-Type: application/json
Response: application/json
```

# Created by Juan Jose Gomez Martinuzzo
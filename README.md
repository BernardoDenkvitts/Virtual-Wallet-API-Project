# 🪙 Crypto Wallet API

A virtual wallet to manage Bitcoin, Litecoin and Dogecoin,
having real addresses but fictitious transactions,
with no real value. Made for study purposes, but can be useful
for personal projects and learning

## 🚀 Technology Stack

- <b>Spring Boot 3.2.2 🌱</b>
- <b>JUnit 5.10.2 🧪</b>
- <b>MongoDB 🍃</b>
- <b>Docker 🐋</b>
- <b>Apache Kafka</b>
- <b>Golang</b>
- <b>PostgreSQL 🐘</b>
- <b>Testify 🧪</b>
- <b>SMTP ✉️</b>

## 🛠️ Integration and Security

- <b>Spring Cloud OpenFeign:</b> employed to facilitate integration with third-party system.
- <b>JWT Token-Based Authentication:</b> is the foundational authorization mechanism of the application. Valid for one hour.
- <b>Spring Security:</b> ensure comprehensive protection of resources and endpoints.
- <b>SMTP:</b> employed to send transaction emails via gmail smtp server.

## 🗺️ Project Architecture

![API.png](project_architecture.png)

#### <b>Why Kafka ?</b>

Kafka was introduced to handle asynchronous processing,
imagining a real world scenario where transactions can take a time
to be completed, same for the register endpoint, where it uses a third party
API.

## 🚩 Getting Started

 <b>1</b> - Make sure to have installed

- Docker
- Maven

<b>2</b> - Clone the project

```bash
git clone https://github.com/BernardoDenkvitts/Virtual-Wallet-API-Project.git
```

Go to project root directory

```bash
cd /Virtual-Wallet-API-Project
```

<b>3</b> - Check https://www.blockcypher.com/ to generate your personal token. After it, goes to VirtualWalletAPI/src/main/resources/application.yaml and set your token to variable called token, it's necessary to use third-party service

```bash
token: your-token
```

<b>4</b> - Create your gmail smtp configurations and setup the .env files inside /EmailSenderService
<p>Useful links:
<p>https://mailtrap.io/blog/gmail-smtp/</p>
<p>https://youtu.be/JDA3a8tEBlo</p> 

```bash
emailhost=
emailport=
emailuser=
emailpassword=
```

<b>5</b> - Create the Application JAR 

```bash
mvn package install -DskipTests
```

<b>6</b> - Starting containers (Application, MongoDB, Zookeeper, Kafka, PostgreSQL, Golang Service)

```bash
docker-compose build
docker-compose up -d
```

## 📜 API Reference

### Base URL

<b> localhost:8080/v1/ </b>

### Crypto types available:

    * btc
    * doge
    * ltc

### Errors

This API uses the following error codes:

- 400 Bad Request: The request was malformed or missing required parameters
- 401 Unauthorized: User not authorized to access the endpoint
- 402 Payment Required: Insufficient funds
- 404 Not Found: The requested resource was not found.
- 500 Internal Server Error: An unexpected error occurred on the server.

### Authorization endpoints

#### Register

```http
   POST /auth/register
```

| Body   | Description                                                  |
| :----- | :----------------------------------------------------------- |
| `json` | `JSON object containing necessary data to register new user` |

    {
        "name": "",
        "email": "",
        "pswd": ""
    }

#### Response

| Body | Response Status |
| :--- | :-------------- |
| ``   | `201`           |

#### Login

```http
  GET /auth/login
```

| Body   | Description                                           |
| :----- | :---------------------------------------------------- |
| `json` | `JSON object containing necessary data to login user` |

    {
        "email": "",
        "pswd": ""
    }

#### Response

| Body   | Response Status |
| :----- | :-------------- |
| `json` | `200`           |

Returns a JSON object with the following propertie:

- login : authorization token

  {
  "login": ""
  }

### Transaction endpoints

#### Send crypto to different address

```http
  POST /transaction/{userId}/send
```

| Parameter | Type     | Description                                |
| :-------- | :------- | :----------------------------------------- |
| `userId`  | `string` | **Required**. Crypto transaction sender ID |

| Body   | Description                                                           |
| :----- | :-------------------------------------------------------------------- |
| `json` | `JSON object containing the necessary data to perfom the transaction` |

    {
        "inputAddress": "",
        "outputAddress": "",
        "quantity": 0.0
        "cryptoType": ""
    }

#### Response

| Body   | Response Status |
| :----- | :-------------- |
| `json` | `202`           |

Returns a JSON object with the following properties:

    {
        "inputAddress": "",
        "outputAddress": "",
        "quantity": 0.0
        "cryptoType": "",
        "timestamp": "YYYY-MM-DDTHH:MM:SS.MS"
    }

#### Get user transactions by crypto type

```http
  GET /transaction/{userId}/{cryptoType}?page=1&size=20&sort=timestamp,asc
```

| Parameter    | Type      | Description                     |
| :----------- | :-------- | :------------------------------ |
| `userId`     | `string`  | **Required**. User id           |
| `cryptoType` | `string`  | **Required**. Crypto type       |
| `page`       | `integer` | Page number to be returned      |
| `size`       | `integer` | Number of transactions per page |
| `sort`       | `string`  | Field to sort by                |

#### Response

| Body   | Response Status |
| :----- | :-------------- |
| `json` | `200`           |

Returns a JSON object with the following properties:

    {
        "content": [
            {
                "inputAddress": "",
                "outputAddress": "",
                "quantity": 0.0,
                "cryptoType": "",
                "timestamp": "YYYY-MM-DDTHH:MM:SS.MS"
            }
        ],
        "pageable": {
            "pageNumber": 1,
            "pageSize": 20,
            "sort": [
                {
                    "direction": "ASC",
                    "property": "timestamp",
                    "ignoreCase": false,
                    "nullHandling": "NATIVE",
                    "ascending": true,
                    "descending": false
                }
            ],
            "offset": 0,
            "unpaged": false,
            "paged": true
        },
        "totalElements": 1,
        "totalPages": 1,
        "last": true,
        "size": 20,
        "number": 0,
        "sort": [
            {
                "direction": "ASC",
                "property": "timestamp",
                "ignoreCase": false,
                "nullHandling": "NATIVE",
                "ascending": true,
                "descending": false
            }
        ],
        "numberOfElements": 1,
        "first": true,
        "empty": false
    }

### User endpoint

#### Add crypto to address

```http
  POST /user/{userId}/add/{cryptoType}/{quantity}
```

| Parameter    | Type     | Description                   |
| :----------- | :------- | :---------------------------- |
| `userId`     | `string` | **Required**. User id         |
| `cryptoType` | `string` | **Required**. Crypto type     |
| `quantity`   | `double` | **Required**. Quantity to add |

#### Response

| Body   | Response Status |
| :----- | :-------------- |
| `json` | `200`           |

Returns JSON object, a String with the following message:

    {
        "Quantity added"
    }

#### Get user information

```http
  GET /user/{userId}
```

| Parameter | Type     | Description           |
| :-------- | :------- | :-------------------- |
| `userId`  | `string` | **Required**. User id |

#### Response

| Body   | Response Status |
| :----- | :-------------- |
| `json` | `200`           |

Returns a JSON object with the following properties:

    {
        "id": "",
        "name": "",
        "email": "",
        "cryptoAddress": {
            "ltc": {
                "address": "",
                "quantity": 0.0
            },
            "btc": {
                "address": "",
                "quantity": 0.0
            },
            "doge": {
                "address": "",
                "quantity": 0.0
            }
        }
    }

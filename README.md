
# Crypto Wallet API
A virtual wallet to manage Bitcoin, Litecoin and Dogecoin,
having real addresses but fictitious transactions, 
with no real value. Made for study purposes, but can be useful
for personal projects and learning


### Technology Stack
 * <b>Spring Boot 3.2.2</b>
 * <b>JUnit 5.10.2</b>
 * <b>MongoDB</b>
 * <b>Docker</b>
 * <b>Apache Kafka</b>

### Integration and Security
 * <b>Spring Cloud OpenFeign:</b> employed to facilitate integration with third-party system.
 * <b>JWT Token-Based Authentication:</b> is the foundational authorization mechanism of the application. Valid for one hour.
 * <b>Spring Security:</b> ensure comprehensive protection of resources and endpoints.

### API Architecture
![API.png](VirtualWalletAPI%2FApiArchiteture%2FAPI.png)

#### <b>Why Kafka ?</b>
Kafka was introduced to handle asynchronous processing, 
imagining a real world scenario where transactions can take a time 
to be completed, same for the register endpoint, where it uses a third party
API.

#### <b>Extra information about the architecture</b>
The API was developed trying to apply S.O.L.I.D principles and thinking about
Clean Architecture

## Getting Started
Make sure to have installed
* Docker
* Maven 3.x


Clone the project

```bash
git clone https://github.com/BernardoDenkvitts/Virtual-Wallet-API-Project.git
```

Go to the project root directory

```bash
cd /Virtual-Wallet-API-Project/VirtualWalletAPI
```

Check https://www.blockcypher.com/ to generate your personal token. After it, goes to application.yaml and set your token to variable called token, it's necessary to use third-party service

```bash
token: your-token
```

Build the project
```bash
mvn clean install
```

Starting containers (Application and Database)
```bash
docker-compose build
docker-compose up -d
```


## API Reference
### Base URL
localhost:8080/v1/

### Errors
This API uses the following error codes:
* 400 Bad Request: The request was malformed or missing required parameters
* 401 Unauthorized: User not authorized to access the endpoint
* 402 Payment Required: Insufficient funds
* 404 Not Found: The requested resource was not found.
* 500 Internal Server Error: An unexpected error occurred on the server.

### Authorization endpoints
#### Register
```http
   POST /auth/register
```
| Body   | Description                                                  |
|:-------|:-------------------------------------------------------------|
| `json` | `JSON object containing necessary data to register new user` |
    
    {
        "name": "",
        "email": "",
        "pswd": ""
    }

#### Response
| Body | Response Status |
|:-----|:----------------|
| ``   | `201`           |


#### Login
```http
  GET /auth/login
```
| Body   | Description                                           |
|:-------|:------------------------------------------------------|
| `json` | `JSON object containing necessary data to login user` |

    {
        "email": "",
        "pswd": ""
    }

#### Response
| Body   | Response Status |
|:-------|:----------------|
| `json` | `200`           |

Returns a JSON object with the following propertie:
* login : authorization token


    {
        "login": ""
    }

### Transaction endpoints
#### Crypto types:
    * btc
    * doge
    * ltc

#### Send crypto to different address
```http
  POST /transaction/{userId}/send
```

| Parameter | Type     | Description                                |
|:----------| :------- |:-------------------------------------------|
| `userId`  | `string` | **Required**. Crypto transaction sender ID |

| Body   | Description                                                           |
|:-------|:----------------------------------------------------------------------|
| `json` | `JSON object containing the necessary data to perfom the transaction` |

    {
        "inputAddress": "",
        "outputAddress": "",
        "quantity": 0.0
        "cryptoType": ""
    }

#### Response
| Body   | Response Status |
|:-------|:----------------|
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
|:-------------|:----------|:--------------------------------|
| `userId`     | `string`  | **Required**. User id           |
| `cryptoType` | `string`  | **Required**. Crypto type       |
| `page`       | `integer` | Page number to be returned      |
| `size`       | `integer` | Number of transactions per page |
| `sort`       | `string`  | Field to sort by                |

#### Response
| Body   | Response Status |
|:-------|:----------------|
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

| Observation                          |
|:-------------------------------------|
| {quantity} path variable is a Double |

    {}

#### Response
| Body   | Response Status |
|:-------|:----------------|
| `json` | `200`           |
Returns JSON object, a String with the following message:
* Quantity added


    {
        "Quantity added"
    }

#### Get user information
```http
  GET /user/{userId}
```
#### Response
| Body   | Response Status |
|:-------|:----------------|
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

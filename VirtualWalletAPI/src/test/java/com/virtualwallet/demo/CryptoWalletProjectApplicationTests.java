package com.virtualwallet.demo;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.virtualwallet.demo.DTO.Authentication.LoginRequestDTO;
import com.virtualwallet.demo.DTO.Authentication.LoginResponseDTO;
import com.virtualwallet.demo.DTO.Authentication.NewAccountRequestDTO;
import com.virtualwallet.demo.DTO.Transaction.TransactionRequestDTO;
import com.virtualwallet.demo.DTO.Transaction.TransactionResponseDTO;
import com.virtualwallet.demo.DTO.User.UserResponseDTO;
import com.virtualwallet.demo.DTO.externalApi.newWallet.NewWalletDTO;
import com.virtualwallet.demo.Mappers.Authentication.IAuthenticatorMapper;
import com.virtualwallet.demo.Mappers.Transaction.ITransactionMapper;
import com.virtualwallet.demo.Model.CryptoType;
import com.virtualwallet.demo.Model.Transaction;
import com.virtualwallet.demo.Model.User.CryptoAddress;
import com.virtualwallet.demo.Model.User.User;
import com.virtualwallet.demo.Service.Authentication.IAuthenticator;
import com.virtualwallet.demo.Service.CryptoWallet.Client.IWalletClient;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestClassOrder(ClassOrderer.OrderAnnotation.class)
class CryptoWalletProjectApplicationTests
{
	@Autowired
	private TestRestTemplate restTemplate;
	@Autowired
	private static MongoClient mongoClient;
	@Autowired
	private IAuthenticator authenticationService;
	private static MongoDatabase mongoDatabase;
	private static MongoTemplate mongoTemplate;


	@BeforeAll
	public static void setUpDB()
	{
		String connectionString = "mongodb://admin:admin@localhost:27014/virtualwallet";
		mongoClient = MongoClients.create(connectionString);
		mongoDatabase = mongoClient.getDatabase("virtualwallet");
		mongoTemplate = new MongoTemplate(mongoClient, "virtualwallet");
	}


	@AfterAll
	public static void clean()
	{
		mongoDatabase.getCollection("user").drop();
		mongoDatabase.getCollection("transaction").drop();
	}

	@Test
	void contextLoads() {}

	@Nested
	@Order(1)
	@DisplayName("Mapper methods")
	class mapperMethods
	{
		@Autowired
		IAuthenticatorMapper authenticatorMapper;
		@Autowired
		ITransactionMapper transactionMapper;

		@Test
		public void should_create_user_object()
		{
			NewAccountRequestDTO newAccount = new NewAccountRequestDTO("testMapper", "testMapper@email.com", "1357");
			User user = authenticatorMapper.newAccountRequestDtoToUser(newAccount);
			assertThat(user).isNotNull();
			assertThat(user.getTimestamp()).isNotNull();
			assertThat(user.getEmail()).isEqualTo("testMapper@email.com");
			System.out.println(user.getTimestamp());
		}

		@Test
		public void should_create_transaction()
		{
			TransactionRequestDTO transaction = new TransactionRequestDTO();
			transaction.setInputAddress("1576dbdab2589c28dd915e597edeec12a0284b83245b2ba6743c5071aab81d3b7a");
			transaction.setOutputAddress("9542dbdab2589c28dd915e597edeec12a0284b83245b2ba6743c5071aab81d1z9j");
			transaction.setQuantity(100.0);
			transaction.setCryptoType(CryptoType.doge);
			Transaction newTransaction = transactionMapper.transactionRequestDTOToTransaction(transaction);

			assertThat(newTransaction).isNotNull();
			assertThat(newTransaction.getCryptoType()).isEqualTo(CryptoType.doge);
			assertThat(newTransaction.getTimestamp()).isNotNull();
		}

		@Test
		public void should_create_TransactionResponseDTO()
		{
			Transaction transaction = new Transaction();
			transaction.setId(null);
			transaction.setInputAddress("1576dbdab2589c28dd915e597edeec12a0284b83245b2ba6743c5071aab81d3b7a");
			transaction.setOutputAddress("9542dbdab2589c28dd915e597edeec12a0284b83245b2ba6743c5071aab81d1z9j");
			transaction.setQuantity(100.0);
			transaction.setCryptoType(CryptoType.doge);
			transaction.setTimestamp(LocalDateTime.now());

			TransactionResponseDTO transactionResponseDTO = transactionMapper.transactionToTransactionResponseDTO(transaction);
			assertThat(transactionResponseDTO).isNotNull();
			assertThat(transactionResponseDTO.getCryptoType()).isEqualTo(CryptoType.doge);
			assertThat(transactionResponseDTO.getTimestamp()).isNotNull();
		}
	}

	@Nested
	@DisplayName("Wallet Service Methods Tests")
	@Order(2)
	class walletServiceMethods
	{
		@Autowired
		private IWalletClient walletClient;
		private static final String walletName = UUID.randomUUID().toString().substring(0, 20).substring(1, 20);


		@Test
		void should_create_new_bitcoin_wallet()
		{
			String cryptoWallet = walletClient.createCryptoWallet(
				new NewWalletDTO(walletName),
				CryptoType.btc.name()
			).name();
			assertThat(cryptoWallet).isNotNull();
			assertThat(cryptoWallet).isNotBlank();
		}

		@Test
		void should_create_address_to_bitcoin_wallet()
		{
			CryptoAddress cryptoAddress = walletClient.createAddressToCryptoWallet(CryptoType.btc.name(), walletName);
			assertThat(cryptoAddress.getAddress()).isNotBlank();
			assertThat(cryptoAddress.getAddress()).isNotNull();
			assertThat(cryptoAddress.getPrivateKey()).isNotBlank();
			assertThat(cryptoAddress.getPrivateKey()).isNotNull();
		}
	}

	@Nested
	@Order(3)
	@DisplayName("Authentication Controller test")
	@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
	class authenticationController
	{
		@Test
		@Order(1)
		void should_create_new_account()
		{
			NewAccountRequestDTO newAccount = new NewAccountRequestDTO("testName", "testEmail@gmail.com", "1357");
			HttpEntity<NewAccountRequestDTO> entity = new HttpEntity<>(newAccount);
			ResponseEntity<Void> registerResponse = restTemplate
					.exchange(
							"/v1/auth/register",
							HttpMethod.POST,
							entity,
							Void.class
					);
			assertThat(registerResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		}

		@Test
		@Order(2)
		void should_login_user()
		{
			LoginRequestDTO login = new LoginRequestDTO("testEmail@gmail.com", "1357");
			ResponseEntity<LoginResponseDTO> loginResponse = restTemplate.postForEntity("/v1/auth/login", login, LoginResponseDTO.class);
			assertThat(loginResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
		}
	}

	@Nested
	@Order(4)
	@DisplayName("User Controller test")
	class userController
	{
		@Test
		void should_return_user_by_id()
		{
			Query query = new Query();
			query.addCriteria(Criteria.where("email").is("testEmail@gmail.com"));
			User user = mongoTemplate.findOne(query, User.class);

			// Generate a new token to avoid 404 (unauthorized in this context)
			LoginResponseDTO token_for_mock_user = authenticationService.login(new LoginRequestDTO("testEmail@gmail.com", "1357"));

			HttpHeaders headers = new HttpHeaders();
			headers.add("Authorization", "Bearer " + token_for_mock_user.login());
			HttpEntity<String> jwtToken = new HttpEntity<>(headers);
			ResponseEntity<UserResponseDTO> response = restTemplate.exchange(
					"/v1/user/" + user.getId(), HttpMethod.GET, jwtToken, UserResponseDTO.class
			);

			assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
			assertThat(response.getBody().getName()).isNotNull();
		}
	}

	@Nested
	@Order(5)
	@DisplayName("Crypto Transaction Controller test")
	class cryptoTransactionController
	{
		private List<User> create_user_for_transaction_test()
		{
			HashMap<CryptoType, CryptoAddress> cryptoAddresses = new HashMap<>();
			cryptoAddresses.put(CryptoType.btc, new CryptoAddress(
					"1769b7bede9dfc00c45a2c845d9e63a81d66bc4ed1ff436a703b9e2085c0cabc",
					"0240dbdab2589c28dd915e597edeec12a0284b83245b2ba6743c5071aab81d6e9g",
					"CEXAfJr5w1op3ArMS1mH2FKN5KFyyftz7c"
			));
			cryptoAddresses.get(CryptoType.btc).setQuantity(000005.0);
			List<User> userExists = mongoTemplate.find(
					new Query().addCriteria(
							Criteria.where("email").is("mockUserTest@email.com")
					),
					User.class
			);

			User newUser;
			if (userExists.isEmpty())
			{
				newUser = new User(
						null,
						"MockUser",
						"mockUserTest@email.com",
						new BCryptPasswordEncoder().encode("1489")
				);
				newUser.setCryptosAddress(cryptoAddresses);
				mongoTemplate.insert(newUser);
			}
			else
			{
				newUser = userExists.get(0);
			}

			Query query = new Query();
			query.addCriteria(Criteria.where("email").is("testEmail@gmail.com"));
			User oldUser = mongoTemplate.findOne(query, User.class);

            assert oldUser != null;
            return List.of(newUser, oldUser);
		}

		@Test
		public void  should_send_crypto_to_another_address()
		{
			List<User> users_for_methods_test = create_user_for_transaction_test();
			User firstUser = users_for_methods_test.get(0);
			User secundaryUser = users_for_methods_test.get(1);

			// Generate a new token to avoid 404 (unauthorized in this context)
			LoginResponseDTO token_for_mock_user = authenticationService.login(new LoginRequestDTO("mockUserTest@email.com", "1489"));

			TransactionRequestDTO transaction = new TransactionRequestDTO();
			transaction.setInputAddress(firstUser.getCryptosAddress().get(CryptoType.btc).getAddress());
			transaction.setOutputAddress(secundaryUser.getCryptosAddress().get(CryptoType.btc).getAddress());
			transaction.setQuantity(00001.0);
			transaction.setCryptoType(CryptoType.btc);;

			HttpHeaders headers = new HttpHeaders();

			headers.add("Authorization", "Bearer " + token_for_mock_user.login());
			HttpEntity<TransactionRequestDTO> entity = new HttpEntity<>(transaction, headers);
			ResponseEntity<TransactionResponseDTO> response = restTemplate
					.exchange(
					"/v1/transaction/" + firstUser.getId() + "/send",
					HttpMethod.POST,
					entity,
					TransactionResponseDTO.class
			);
			assertThat(response.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
			System.out.println(response.getBody().toString());
			System.out.println(response.getBody().getTimestamp());
		}

		@Test
		void should_return_insufficient_funds_error()
		{
			List<User> usersForTest = create_user_for_transaction_test();
			User firstUser = usersForTest.get(0);
			User secundaryUser = usersForTest.get(1);

			// Generate a new token to avoid 404 (unauthorized in this context)
			LoginResponseDTO token_for_mock_user = authenticationService.login(new LoginRequestDTO("mockUserTest@email.com", "1489"));

			TransactionRequestDTO transactionRequest = new TransactionRequestDTO();
			transactionRequest.setInputAddress(firstUser.getCryptosAddress().get(CryptoType.btc).getAddress());
			transactionRequest.setOutputAddress(secundaryUser.getCryptosAddress().get(CryptoType.btc).getAddress());
			transactionRequest.setQuantity(0000010.0);
			transactionRequest.setCryptoType(CryptoType.btc);

			HttpHeaders headers = new HttpHeaders();
			headers.add("Authorization", "Bearer " + token_for_mock_user.login());
			HttpEntity<TransactionRequestDTO> entity = new HttpEntity<>(transactionRequest, headers);
			ResponseEntity<String> response = restTemplate.exchange(
					"/v1/transaction/" + firstUser.getId() + "/send",
					HttpMethod.POST,
					entity,
					String.class
			);
			assertThat(response.getStatusCode()).isEqualTo(HttpStatus.PAYMENT_REQUIRED);
		}
	}

}


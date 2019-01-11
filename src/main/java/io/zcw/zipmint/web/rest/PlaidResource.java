package io.zcw.zipmint.web.rest;

import com.hazelcast.internal.util.ThreadLocalRandomProvider;
import com.netflix.discovery.converters.Auto;
import com.plaid.client.PlaidClient;
import com.plaid.client.request.AccountsGetRequest;
import com.plaid.client.request.ItemPublicTokenExchangeRequest;
import com.plaid.client.request.TransactionsGetRequest;
import com.plaid.client.response.Account;
import com.plaid.client.response.AccountsGetResponse;
import com.plaid.client.response.ItemPublicTokenExchangeResponse;
import com.plaid.client.response.TransactionsGetResponse;
import com.sun.media.jfxmedia.logging.Logger;
import io.zcw.zipmint.domain.MoneyAccount;
import io.zcw.zipmint.domain.PlaidRequest;
import io.zcw.zipmint.domain.Transaction;
import io.zcw.zipmint.domain.User;
import io.zcw.zipmint.domain.enumeration.AccountType;
import io.zcw.zipmint.domain.enumeration.Category;
import io.zcw.zipmint.domain.enumeration.TransactionType;
import io.zcw.zipmint.repository.MoneyAccountRepository;
import io.zcw.zipmint.repository.TransactionRepository;
import io.zcw.zipmint.repository.UserRepository;
import io.zcw.zipmint.security.AuthoritiesConstants;
import io.zcw.zipmint.security.SecurityUtils;
import io.zcw.zipmint.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import retrofit2.Response;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;

@RestController
@RequestMapping("/api")
public class PlaidResource {

    ConsoleHandler handler = new ConsoleHandler();

    private String clientId = "5c18064b5eca930011ff671c";
    private String secret = "a968a95cef7edfc6b22da6c5e6ddcd";
    private String publicKey = "f815660c096befb9b5e508907c50f5";

    private String accessToken = "";
    private String itemId = "";

    private PlaidClient plaidClient;

    private MoneyAccountRepository moneyAccountRepository;
    private TransactionRepository transactionRepository;
    private UserRepository userRepository;
    private LinkedHashSet<MoneyAccount> moneyAccountSet = new LinkedHashSet<>();

    public PlaidResource() {
        // Use builder to create a client
        handler.setLevel(Level.ALL);
        this.plaidClient = PlaidClient.newBuilder()
            .clientIdAndSecret(clientId, secret)
            .publicKey(publicKey) // optional. only needed to call endpoints that require a public key
            .sandboxBaseUrl() // or equivalent, depending on which environment you're calling into
            .build();
    }

    @Autowired
    public PlaidResource(MoneyAccountRepository moneyAccountRepository, UserRepository userRepository, TransactionRepository transactionRepository) {
        this();
        this.moneyAccountRepository = moneyAccountRepository;
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
    }

    @PostMapping(path = "get_access_token", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> plaidCallback(@RequestBody PlaidRequest plaidRequest) throws IOException {
        String publicToken = plaidRequest.getPublicToken();

        System.out.println(plaidRequest);
        System.out.println("******************* getting public token with code: " + publicToken);

        Response<ItemPublicTokenExchangeResponse> response = plaidClient.service()
            .itemPublicTokenExchange(new ItemPublicTokenExchangeRequest(publicToken)).execute();

        System.out.println("******************* message: " + response.message());
        System.out.println("******************* access token: " + response.body().getAccessToken());

        if (response.isSuccessful()) {
            accessToken = response.body().getAccessToken();
            itemId = response.body().getItemId();
        } else {
            accessToken = "Whoops.";
        }

        Date start = new Date();
        Date end = new Date();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            start = sdf.parse("2000-01-01");
            end = sdf.parse("2019-02-01");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        getAccountsAndTransactions(accessToken, start, end);

        return new ResponseEntity<>(accessToken, HttpStatus.OK);
    }


    private void getAccountsAndTransactions(String accessToken, Date start, Date end) throws IOException {
        Response<AccountsGetResponse> accountResponse = plaidClient.service().accountsGet(new AccountsGetRequest(accessToken)).execute();
        Response<TransactionsGetResponse> transactionResponse = plaidClient.service().transactionsGet(new TransactionsGetRequest(accessToken, start, end)).execute();

        List<Account> accountList = accountResponse.body().getAccounts();
//        List<TransactionsGetResponse.Transaction> transactionList = transactionResponse.body().getTransactions();

        System.out.println(SecurityUtils.getCurrentUserLogin());

//        User user = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin().get()).get();

        for (Account account : accountList) {

            String type = account.getSubtype().toUpperCase();
            if (type.equals("CREDIT CARD")) {
                type = "CREDIT";
            } else if (type.equals("MONEY MARKET")) {
                type = "MONEY_MARKET";
            }

            MoneyAccount moneyAccount = new MoneyAccount();

            moneyAccount.setType(AccountType.valueOf(type));
            moneyAccount.setAccountTotal(account.getBalances().getCurrent().longValue());
            moneyAccount.setSignIn("user_good");
            moneyAccount.setPw("pass_good");
            moneyAccount.setBankName(account.getName());

//            moneyAccount.setUser(user);

            moneyAccountRepository.save(moneyAccount);
            moneyAccountSet.add(moneyAccount);
        }

//        for (TransactionsGetResponse.Transaction plaidTransaction : transactionList) {
//
//            Transaction transaction = new Transaction();
//
//            transaction.setCategory(Category.valueOf(plaidTransaction.getCategory().get(0)));
//            transaction.setAmount(plaidTransaction.getAmount());
//            transaction.setDateTime(LocalDate.parse(plaidTransaction.getDate()));
//            transaction.setDescription(plaidTransaction.getName());
//
//            transactionRepository.save(transaction);
//
//        }

    }


}

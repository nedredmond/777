package io.zcw.zipmint.web.rest;

import com.netflix.discovery.converters.Auto;
import com.plaid.client.PlaidClient;
import com.plaid.client.request.AccountsGetRequest;
import com.plaid.client.request.ItemPublicTokenExchangeRequest;
import com.plaid.client.response.Account;
import com.plaid.client.response.AccountsGetResponse;
import com.plaid.client.response.ItemPublicTokenExchangeResponse;
import com.sun.media.jfxmedia.logging.Logger;
import io.zcw.zipmint.domain.MoneyAccount;
import io.zcw.zipmint.domain.PlaidRequest;
import io.zcw.zipmint.domain.User;
import io.zcw.zipmint.domain.enumeration.AccountType;
import io.zcw.zipmint.repository.MoneyAccountRepository;
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
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
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
    private UserRepository userRepository;
    private UserService userService;
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
    public PlaidResource(MoneyAccountRepository moneyAccountRepository, UserRepository userRepository) {
        this();
        this.moneyAccountRepository = moneyAccountRepository;
        this.userRepository = userRepository;
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

        getAccounts(accessToken);

        return new ResponseEntity<>(accessToken, HttpStatus.OK);
    }


    public void getAccounts(String accessToken) throws IOException {
        Response<AccountsGetResponse> response = plaidClient.service().accountsGet(new AccountsGetRequest(accessToken)).execute();

        List<Account> accountList = response.body().getAccounts();

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

            User user = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin().get()).get();
            moneyAccount.setUser(user);

            moneyAccountRepository.save(moneyAccount);
            moneyAccountSet.add(moneyAccount);
        }


    }

}

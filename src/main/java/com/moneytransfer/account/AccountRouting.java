package com.moneytransfer.account;

import com.entrypoint.Routing;
import io.javalin.Javalin;

import javax.inject.Inject;
import javax.inject.Singleton;

import static io.javalin.apibuilder.ApiBuilder.*;

@Singleton
public class AccountRouting extends Routing<AccountController> {

    private Javalin app;

    @Inject
    public AccountRouting(Javalin app) {
        this.app = app;
    }

    @Override
    public void bindRoutes() {

        app.routes(() -> {
                get("/api/moneybank/accounts", ctx -> getController().getAllAccounts(ctx));
                post("/api/moneybank/accounts", ctx -> getController().createAccount(ctx));
                delete("/api/moneybank/accounts/:account-id", ctx -> getController().deleteAccount(ctx, ctx.pathParam("account-id")));
                get("/api/moneybank/accounts/balance/:account-id", ctx -> getController().getAccountBalance(ctx, ctx.pathParam("account-id")));
                get("/api/moneybank/accounts/transaction/:account-id", ctx -> getController().getAllAccountTransaction(ctx, ctx.pathParam("account-id")));
                post("/api/moneybank/accounts/addBalance", ctx -> getController().addBalance(ctx));
                post("/api/moneybank/accounts/withdraw", ctx -> getController().withDrawBalance(ctx));
                post("/api/moneybank/accounts/transfer", ctx -> getController().transferAmount(ctx));

        });
    }
}

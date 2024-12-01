package me.shubhamjain.manning.open_banking_application.e2e;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.matchesPattern;
import static org.hamcrest.Matchers.nullValue;

import io.restassured.RestAssured;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TransactionComponentTest {

  @LocalServerPort
  private int port;

  @BeforeEach
  public void setUp() {
    RestAssured.port = port;
    RestAssured.baseURI = "http://localhost";
  }

  @Test
  void givenAValidAccountNumber_whenGetAllTransactionsIsCalledFromController_thenReturnAllTransactions()
      throws Exception {
    RestAssured.get("/api/v1/transactions/123").then()
        .statusCode(200)
        .body("$", instanceOf(List.class))
        .body("size()", greaterThan(0))
        .body("",
            everyItem(allOf(
                hasKey("type"),
                hasKey("date"),
                hasKey("accountNumber"),
                hasKey("currency"),
                hasKey("amount"),
                hasKey("merchantName"),
                hasKey("merchantLogo")
            ))
        )
        .body("type", everyItem(anyOf(instanceOf(String.class), nullValue())))
        .body("date", everyItem(anyOf(matchesPattern("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}"), nullValue())))
        .body("accountNumber", everyItem(anyOf(matchesPattern("^[0-9]{10,12}$"), nullValue())))
        .body("currency", everyItem(anyOf(matchesPattern("^[A-Z]{3}$"), nullValue())))
        .body("amount", everyItem(anyOf(instanceOf(Number.class), nullValue())))
        .body("merchantName", everyItem(anyOf(instanceOf(String.class), nullValue())))
        .body("merchantLogo", everyItem(anyOf(instanceOf(String.class), nullValue())));
  }
}

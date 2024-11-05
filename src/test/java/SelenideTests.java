import DOM.*;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

@DisplayName("Тесты сайта saucedemo.com")
public class SelenideTests {

    LoginPage loginPage;
    ProductsPage productsPage;
    CartPage cartPage;
    CheckoutPage checkoutPage;
    CompletePage completePage;
    OverviewPage overviewPage;
    static String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    static String appConfigPath = rootPath + "conf.properties";
    static Properties properties = new Properties();


    @BeforeAll
    public static void setUpGlobal() throws IOException {
        properties.load(new FileInputStream(appConfigPath));
        Configuration.baseUrl = properties.getProperty("URL");
    }

    @BeforeEach
    public void setUp() {
        loginPage = new LoginPage();
        productsPage = new ProductsPage();
        cartPage = new CartPage();
        checkoutPage = new CheckoutPage();
        completePage = new CompletePage();
        overviewPage = new OverviewPage();
    }

    @Test
    @DisplayName("Авторизация валидного юзера")
    @Tag("positive")
    public void validUserLogin() {
        loginPage.login(properties.getProperty("STANDARD_USERNAME"), properties.getProperty("PASSWORD"));
        loginPage.clickLogin();
        productsPage.actuallyProductsPage();
    }

    @Test
    @DisplayName("Авторизация заблокированного юзера")
    @Tag("negative")
    public void blockedUserLogin() {
        loginPage.login(properties.getProperty("LOCKED_USERNAME"), properties.getProperty("PASSWORD"));
        loginPage.clickLogin();
        loginPage.checkError();
    }

    @Test
    @DisplayName("Сквозной сценарий пользователя standart_user")
    @Tag("positive")
    public void e2eStandardUser() {
        loginPage.login(properties.getProperty("STANDARD_USERNAME"), properties.getProperty("PASSWORD"));
        loginPage.clickLogin();
        productsPage.addToCart();
        productsPage.header.clickToCart();
        cartPage.itemsInCartShouldBe(3);
        cartPage.checkout();
        checkoutPage.insertDataForm(properties.getProperty("FIRST_NAME"), properties.getProperty("LAST_NAME"), properties.getProperty("POSTAL_CODE"));
        checkoutPage.clickContinue();
        overviewPage.checkItemsInOverview();
        overviewPage.checkTotalPrice();
        overviewPage.finishOrder();
        completePage.checkComplete();
    }

    @Test
    @DisplayName("Сквозной сценарий пользователя performance_glitch_user")
    @Tag("positive")
    public void e2ePerformanceGlitchUser() {
        loginPage.login(properties.getProperty("PERFORMANCE_GLITCH_USERNAME"), properties.getProperty("PASSWORD"));
        loginPage.clickLogin();
        productsPage.addToCart();
        productsPage.header.clickToCart();
        cartPage.itemsInCartShouldBe(3);
        cartPage.checkout();
        checkoutPage.insertDataForm(properties.getProperty("FIRST_NAME"), properties.getProperty("LAST_NAME"), properties.getProperty("POSTAL_CODE"));
        checkoutPage.clickContinue();
        overviewPage.checkItemsInOverview();
        overviewPage.checkTotalPrice();
        overviewPage.finishOrder();
        completePage.checkComplete();
    }
}

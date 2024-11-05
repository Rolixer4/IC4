package DOM;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CompletePage {

    private final SelenideElement orderTextLocator = $(".complete-header");

    @Step("Проверяем успешное завершение заказа")
    public void checkComplete() {
        assertEquals("Thank you for your order!", orderTextLocator.text());
    }
}
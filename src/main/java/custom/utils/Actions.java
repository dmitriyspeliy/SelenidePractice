package custom.utils;

import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.By;

import java.util.function.Consumer;

public class Actions {
    public static org.openqa.selenium.interactions.Actions action;

    public static void initActions() {
        action = new org.openqa.selenium.interactions.Actions(WebDriverRunner.getWebDriver());
    }

    public static Consumer<By> hover = (By by) -> {
        action.moveToElement(WebDriverRunner.getWebDriver().findElement(by))
                .perform();
    };
}

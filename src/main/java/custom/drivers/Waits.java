package custom.drivers;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import custom.properties.TestData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Waits {
    public static WebDriverWait wait;

    public static void initWait() {
        wait = new WebDriverWait(WebDriverRunner.getWebDriver(), TestData.driverProps.defaultTimeout());
    }


    private static void sleep(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void waitUntilElementNotExist(String xpath) {
        sleep(2);
        int timer = 0;
        for (; timer == TestData.driverProps.defaultTimeout(); ++timer) {
            if (WebDriverRunner.getWebDriver().findElements(By.xpath(xpath)).size() == 0)
                break;
            sleep(1);
        }
        if (timer == TestData.driverProps.defaultTimeout()) {
            throw new TimeoutException("Элемент с селектором " + xpath + " не исчез за " + TestData.driverProps.defaultTimeout() + " секунд");
        }
    }

    public static void waitUntilElementClickable(String xpath) {
        sleep(2);
        Waits.wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
    }

    public static void waitUntilElementPresents(String xpath) {
        Waits.wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
    }

    public static void waitUntilAllElementPresents(String xpath) {
        Waits.wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(xpath)));
    }

    public static void waitUntilAttributeIntegerWillBe(WebElement element, String attribute, String value) {
        Waits.wait.until((ExpectedCondition<Boolean>) driver -> Integer.parseInt(element.getAttribute(attribute))<=
                Integer.parseInt(value));
    }

    public static void waitUntilAttributeWillBe(WebElement element, String attribute, String value) {
        Waits.wait.until((ExpectedCondition<Boolean>) driver -> element.getAttribute(attribute).contains(value));
    }

    public static void waitUntilElementTextContainsByLocator(By locator, String text) {
        new WebDriverWait(WebDriverRunner.getWebDriver(), TestData.driverProps.defaultTimeout())
                .until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
    }

    public static void waitUntilElementTextContains(WebElement element, String text) {
        new WebDriverWait(WebDriverRunner.getWebDriver(), TestData.driverProps.defaultTimeout() / 6)
                .until(ExpectedConditions.textToBePresentInElement(element, text));
    }

    public static void waitUntilElementTextContains(WebElement element, String text, int timeout) {
        new WebDriverWait(WebDriverRunner.getWebDriver(), timeout)
                .until(ExpectedConditions.textToBePresentInElement(element, text));
    }

}

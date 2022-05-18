package pages;

import com.codeborne.selenide.Condition;
import custom.drivers.Waits;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.*;

/**
 * @author Pospelov Dmitriy
 * Класс осуществляет нужным нам поиск и переходит по ссылке после поиска
 */
public class YandexSearch extends BaseClass {

    /**
     * @param name - поиск по слову
     * @param linkName - переход по имени ссылки
     * @param nextPage - класс, который нужно вернуть
     * @param <T> - любой тип
     * @return  - наследник от BasePage
     */
    @Step("Ищем в яндексе {name} и переходим по ссылке {linkName}")
    public <T extends BaseClass> T YandexSearch(String name, String linkName,Class<T> nextPage){
        Waits.initWait();
        $(By.xpath("//input[@class='input__control input__input mini-suggest__input']"))
                .setValue(name)
                .pressEnter();

        $$(By.xpath("//ul[@aria-label='Результаты поиска']//li//a"))
                .findBy(Condition.text(linkName))
                .click();

        switchTo().window(1);

        return nextPage.cast(page(nextPage));
    }


}

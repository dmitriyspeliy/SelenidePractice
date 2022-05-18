package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.ElementNotFound;
import custom.drivers.Waits;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;


import static com.codeborne.selenide.Selenide.*;

/**
 * Класс поиска нужного раздела на сайте, фильтрации элементов по производителю,
 * проверки элементов на странице.
 */

public class YandexMarket extends BaseClass {

    /**
     *
     * @param nameSelection - название секции
     * @param nameSubSelection - название подсекции
     * @return объект класса
     */
    @Step("Ищем раздел {nameSelection} и подраздел {nameSubSelection}")
    public YandexMarket searchSelection(String nameSelection, String nameSubSelection) {
        $(By.xpath("//button[@id='catalogPopupButton']")).click();
        $$(By.xpath("//li[@data-zone-name='category-link']"))
                .findBy(Condition.text(nameSelection));
        $$(By.xpath("//div[@data-apiary-widget-name='@MarketNode/NavigationTree']//li"))
                .findBy(Condition.text(nameSubSelection))
                .click();
        return this;
    }

    /**
     * Проверяет, если этот элемент уже выбран, то снимаем галку и ставит фильтр.
     * @param nameProducer - наименование производителя
     */
    @Step("Фильтруем по производителю и проверяет чекбокс {nameProducer}")
    public YandexMarket filterProducer(String nameProducer) {
        $(By.xpath("//legend[contains(text(),'Производитель')]/..//button")).click();

        $(By.xpath("//input[@name='Поле поиска']"))
                .setValue(nameProducer);

        SelenideElement checkSelect = $(By.xpath("//legend[contains(text(),'Производитель')]/..//li/div//input"));
        if(!checkSelect.isSelected()){
            $(By.xpath("//legend[contains(text(),'Производитель')]/../ul//label/div")).click();
        }

        return this;
    }

    /**
     * @param count - установить количество элементов на странице
     * @return объект YandexMarket
     */
    @Step("Устанавливаем количество элементов")
    public YandexMarket SelectCountOfItems(int count){
        Waits.waitUntilElementClickable("//div[@data-apiary-widget-name='@MarketNode/SearchPager']//button[@type='button']");
        try {
            $(By.xpath("//div[@data-apiary-widget-name='@MarketNode/SearchPager']//button[@type='button']")).click();
        }catch (ElementNotFound e){
            Assertions.fail("Установить количество элементов невозможно, нет кнопки количества элементов" +
                    " c этим именем производителя");
        }
        $(By.xpath("//div[@data-apiary-widget-name='@MarketNode/SearchPager']//button[contains(text(),'"+
                count+"')]")).click();

        Waits.waitUntilElementNotExist("//div[@aria-label='Результаты поиска']/div[last()]");
        return this;
    }


    /**
     * @param produceName - наименование марки
     */
    @Step("Убедится что в выборку попали только cмартфоны от {produceName}")
    public void checkItems(String produceName) {
        SelenideElement element = $(By.xpath("//a[contains(text(),'Вперёд')]"));
        int count = 0;
        Waits.waitUntilElementNotExist("//div[@aria-label='Результаты поиска']/div[last()]");
        while (count<15&&element.exists()){
                ElementsCollection arr = $$(By.xpath("//article//h3"));
                Assertions.assertFalse(arr.stream().noneMatch(x->x.getText().contains(produceName)),
                        "В списке есть товар с другим названием");
                count++;
                element.click();
            Waits.waitUntilElementNotExist("//div[@aria-label='Результаты поиска']/div[last()]");
        }

    }
}

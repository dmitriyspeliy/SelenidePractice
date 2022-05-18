package ru.yandex;

import com.codeborne.selenide.Configuration;
import custom.properties.TestData;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import pages.YandexMarket;
import pages.YandexSearch;


import static com.codeborne.selenide.Selenide.*;

/**
 * Главный тест
 */
public class TestMarket  {

    @Feature("Проверка яндекс маркет")
    @DisplayName("Проверка поиска в яндекс маркет с помощью ПО и Selenide")
    @ParameterizedTest(name = "{displayName} {arguments}")
    @Tag("Задание 2.1 Selenide")
    @CsvSource({"Яндекс Маркет,Яндекс.Маркет — покупки с быстрой доставкой," +
            "Электроника,Смартфоны,Apple,12,iPhone"})
    public void MainTest(String searchName,String linkName,String sectionName,String subSectionName,
                        String producer,int countOfItems,String checkNamePhone){
        open(TestData.driverProps.defaultUrl(),YandexSearch.class)
                .YandexSearch(searchName,
                        linkName,YandexMarket.class)
                .searchSelection(sectionName,subSectionName)
                .filterProducer(producer)
                .SelectCountOfItems(countOfItems)
                .checkItems(checkNamePhone);
        }
    }

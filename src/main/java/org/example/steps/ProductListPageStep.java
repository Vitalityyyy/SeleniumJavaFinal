package org.example.steps;

import io.cucumber.java.ru.И;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.security.Key;

public class ProductListPageStep extends BaseStep{

    private String savedProductName;

    @FindBy(css = ".rendererWrapper")
    private WebElement productList;

    @FindBy(css = ".PaginationViewChanger_countSetter__count__65Dji")
    private WebElement productCountSetter;

    @FindBy(id = "searchInput")
    private WebElement searchInput;

    @FindBy(css = ".ListingPageTitle_count__lIz5h.ListingLayout_count__mj4WV")
    private WebElement productCount;

    @FindBy(xpath = "//*[contains(@class, 'rendererWrapper')]/div/div/div/div/div//a/div")
    private WebElement firstProduct;


    @И("Дождаться выполнения поиска")
    @Step("Дождаться выполнения поиска")
    public void assertSearchFinished() {
        Assertions.assertTrue(productList.isDisplayed(), "Список товаров не загрузился");
    }

    @И("Проверить, что в поисковой выдаче не более 24 товаров")
    @Step("Проверить, что в поисковой выдаче не более 24 товаров")
    public void assertProductCountIsLimitedTo24() {
        Assertions.assertEquals(productCountSetter.getText(), "по 24", "Поисковая выдача не ограничена 24 товарами");
    }

    @И("Сохранить наименование первого товара в списке")
    @Step("Сохранить наименование первого товара в списке")
    public void saveFirstProductName() {
        savedProductName = firstProduct.getText();
    }

    @И("В поисковую строку ввести запомненное значение, выполнить поиск")
    @Step("В поисковую строку ввести запомненное значение, выполнить поиск")
    public void searchBySavedName() {
        fillInputField(searchInput, savedProductName);
        searchInput.sendKeys(Keys.ENTER);
    }

    @И("Проверить, что в поисковой выдаче не более 1 товара")
    @Step("Проверить, что в поисковой выдаче не более 1 товара")
    public void verifyOneProductFound() {
        waitTillInVisible(productCountSetter);
        waitTillVisible(productCount);
        Assertions.assertEquals("1 товар", productCount.getText(), "Найдено более 1 товара");
    }

    @И("Проверить, что наименование товара соответствует сохраненному значению")
    @Step("Проверить, что наименование товара соответствует сохранённому значению")
    public void verifyProductNameMatchesSaved() {
        Assertions.assertEquals(savedProductName, firstProduct.getText(), "Название товара не совпадает с сохранённым");
    }
}

package org.example.steps;

import io.cucumber.java.ru.И;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class FilterBlockStep extends BaseStep{

    @FindBy(css = ".RangeSelector_input__26nqz.range-selector-input")
    private WebElement priceMinField;

    private WebElement makerCheckbox(String makerName) {
        return driverManager.getDriver().findElement(By.xpath("//label[text()='" + makerName + "']"));
    }

    @И("Задать параметр поиска по цене от \"(.+)\" рублей$")
    @Step("^Задать параметр поиска по цене - минимальное значение")
    public void setMinPrice(String price) {
        waitTillVisible(priceMinField);
        setInputValueWithJS(priceMinField, price);
    }

    @И("Выбрать производителя \"(.+)\"$")
    @Step("Выбрать производителя")
    public void setMaker(String maker) {
        click(makerCheckbox(maker));;
    }
}

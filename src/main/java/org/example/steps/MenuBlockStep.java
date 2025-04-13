package org.example.steps;

import io.cucumber.java.ru.И;
import io.qameta.allure.Step;
import org.example.managers.PageManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MenuBlockStep extends BaseStep{

    @FindBy(xpath = "//span[text()='Каталог']")
    private WebElement catalogButton;

    private WebElement menuSection(String sectionName) {
        return driverManager.getDriver().findElement(By.xpath("//*[text()='" + sectionName + "']"));
    }

    @И("Открыть меню Каталог")
    @Step("Открыть меню Каталог")
    public void clickCatalog() {
        click(catalogButton);
    }

    @И("^Выбрать раздел \"(.+)\"$")
    @Step("Выбрать раздел меню")
    public void selectMenuSection(String sectionName) {
        waitTillVisible(menuSection(sectionName));
        hoverToElement(menuSection(sectionName));
    }

    @И("^Выбрать подраздел \"(.+)\"$")
    @Step("Выбрать подраздел меню")
    public void selectSubmenuSection(String sectionName) {
        click(menuSection(sectionName));
    }
}

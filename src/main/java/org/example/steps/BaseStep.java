package org.example.steps;


import org.example.managers.DriverManager;
import org.example.managers.PageManager;
import org.example.managers.TestPropManager;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Базовый класс всех страничек
 */
public class BaseStep {

    /**
     * Менеджер WebDriver
     *
     * @see DriverManager#getDriverManager()
     */
    protected final DriverManager driverManager = DriverManager.getDriverManager();

    /**
     * Менеджер страничек
     *
     * @see PageManager
     */
    protected PageManager pageManager = PageManager.getPageManager();

    /**
     * Объект для имитации реального поведения мыши или клавиатуры
     *
     * @see Actions
     */
    protected Actions action = new Actions(driverManager.getDriver());

    /**
     * Объект для выполнения любого js кода
     *
     * @see JavascriptExecutor
     */
    protected JavascriptExecutor js = (JavascriptExecutor) driverManager.getDriver();

    /**
     * Объект явного ожидания
     * При применении будет ожидать заданного состояния 10 секунд с интервалом в 1 секунду
     *
     * @see WebDriverWait
     */
    protected WebDriverWait wait = new WebDriverWait(driverManager.getDriver(), Duration.ofSeconds(10), Duration.ofSeconds(1));

    /**
     * Менеджер properties
     *
     * @see TestPropManager#getTestPropManager()
     */
    protected final TestPropManager props = TestPropManager.getTestPropManager();

    @FindBy(css = "div.loader-mask.shown")
    private WebElement loadingSign;

    /**
     * Конструктор позволяющий инициализировать все странички и их элементы помеченные аннотацией {@link FindBy}
     * Подробнее можно просмотреть в класс {@link org.openqa.selenium.support.PageFactory}
     *
     * @see FindBy
     * @see PageFactory
     */
    public BaseStep() {
        PageFactory.initElements(driverManager.getDriver(), this);
    }

    /**
     * Функция позволяющая производить scroll до любого элемента с помощью js
     *
     * @param element - веб-элемент странички
     * @see JavascriptExecutor
     */
    protected WebElement scrollToElementJs(WebElement element) {
        js.executeScript("arguments[0].scrollIntoView(true);", element);
        return element;
    }

    /**
     * Функция позволяющая производить scroll до любого элемента с помощью js со смещение
     * Смещение задается количеством пикселей по вертикали и горизонтали, т.е. смещение до точки (x, y)
     *
     * @param element - веб-элемент странички
     * @param x       - параметр координаты по горизонтали
     * @param y       - параметр координаты по вертикали
     * @see JavascriptExecutor
     */
    public WebElement scrollWithOffset(WebElement element, int x, int y) {
        String code = "window.scroll(" + (element.getLocation().x + x) + ","
                + (element.getLocation().y + y) + ");";
        ((JavascriptExecutor) driverManager.getDriver()).executeScript(code, element, x, y);
        return element;
    }

    /**
     * Явное ожидание состояния clickable элемента
     *
     * @param element - веб-элемент который требует проверки clickable
     * @return WebElement - возвращаем тот же веб элемент что был передан в функцию
     * @see WebDriverWait
     * @see org.openqa.selenium.support.ui.FluentWait
     * @see org.openqa.selenium.support.ui.Wait
     * @see ExpectedConditions
     */
    protected WebElement waitTillClickable(WebElement element) {
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    /**
     * Явное ожидание того что элемент станет видемым
     *
     * @param element - веб элемент который мы ожидаем что будет  виден на странице
     */
    protected WebElement waitTillVisible(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    protected boolean waitTillInVisible(WebElement element) {
        try {
            return wait.until(ExpectedConditions.invisibilityOf(element));
        } catch (Exception e) {
            throw new RuntimeException("Element did not become invisible: " + element, e);
        }
    }

    /**
     * Общий метод по заполнения полей ввода
     *
     * @param field - веб-элемент поле ввода
     * @param value - значение вводимое в поле
     */
    protected void fillInputField(WebElement field, String value) {
        scrollToElementJs(field);
        waitTillClickable(field).click();
        field.clear();
        field.sendKeys(value);
    }

    /**
     * Общий метод по заполнению полей с датой
     *
     * @param field - веб-элемент поле с датой
     * @param value - значение вводимое в поле с датой
     */
    protected void fillDateField(WebElement field, String value) {
        scrollToElementJs(field);
        field.sendKeys(value);
    }

    protected void click(WebElement element) {
        waitTillClickable(element);
        element.click();
    }

    /**
     * Наводит курсор на указанный веб-элемент
     * @param element - элемент, на который нужно навести курсор
     */
    public void hoverToElement(WebElement element) {
        new Actions(driverManager.getDriver())
                .moveToElement(element)
                .perform();
    }

    public void setInputValueWithJS(WebElement element, String value) {
        JavascriptExecutor js = (JavascriptExecutor) driverManager.getDriver();
        js.executeScript("arguments[0].value = arguments[1];", element, value);
        js.executeScript("arguments[0].dispatchEvent(new Event('input'));", element);
        js.executeScript("arguments[0].dispatchEvent(new Event('change'));", element);
    }
}


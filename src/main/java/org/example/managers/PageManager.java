package org.example.managers;

import org.example.steps.FilterBlockStep;
import org.example.steps.MenuBlockStep;
import org.example.steps.ProductListPageStep;

/**
 * Класс для управления страничками
 */
public class PageManager {

    /**
     * Менеджер страничек
     */
    private static PageManager pageManager;

    /**
     * Блок фильтров
     */
    private FilterBlockStep filterBlock;

    /**
     * Панель меню
     */
    private MenuBlockStep menuBlock;

    /**
     * Страница списка товаров
     */
    private ProductListPageStep productListPage;

    /**
     * Конструктор специально был объявлен как private (singleton паттерн)
     *
     * @see PageManager#getPageManager()
     */
    private PageManager() {
    }

    /**
     * Ленивая инициализация PageManager
     *
     * @return PageManager
     */
    public static PageManager getPageManager() {
        if (pageManager == null) {
            pageManager = new PageManager();
        }
        return pageManager;
    }

    /**
     * Ленивая инициализация {@link FilterBlockStep}
     *
     * @return FilterBlockStep
     */
    public FilterBlockStep getFilterBlock() {
        if (filterBlock == null) {
            filterBlock = new FilterBlockStep();
        }
        return filterBlock;
    }

    /**
     * Ленивая инициализация {@link MenuBlockStep}
     *
     * @return MenuBlockStep
     */
    public MenuBlockStep getMenuBlock() {
        if (menuBlock == null) {
            menuBlock = new MenuBlockStep();
        }
        return menuBlock;
    }

    /**
     * Ленивая инициализация {@link ProductListPageStep}
     *
     * @return ProductListPageStep
     */
    public ProductListPageStep getProductListPage() {
        if (productListPage == null) {
            productListPage = new ProductListPageStep();
        }
        return productListPage;
    }
}

# language: ru

Функция: Поиск товара

  Сценарий: Поиск видеокарты
  * Открыть меню Каталог
  * Выбрать раздел "Комплектующие для ПК"
  * Выбрать подраздел "Видеокарты"
  * Задать параметр поиска по цене от "20000" рублей
  * Выбрать производителя "Gigabyte"
  * Дождаться выполнения поиска
  * Проверить, что в поисковой выдаче не более 24 товаров
  * Сохранить наименование первого товара в списке
  * В поисковую строку ввести запомненное значение, выполнить поиск
  * Проверить, что в поисковой выдаче не более 1 товара
  * Проверить, что наименование товара соответствует сохраненному значению
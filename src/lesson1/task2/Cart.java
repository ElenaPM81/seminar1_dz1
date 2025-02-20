package lesson1.task2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Корзина
 * @param <T> Еда
 */
public class Cart<T extends Food> {

    //region Поля

    /**
     * Товары в магазине
     */
    private final ArrayList<T> foodstuffs;
    private final UMarket market;
    private final Class<T> clazz;

    //endregion

    //region Конструкторы

    /**
     * Создание нового экземпляра корзины
     * @param market принадлежность к магазину
     */
    public Cart(Class<T> clazz, UMarket market)
    {
        this.clazz = clazz;
        this.market = market;
        foodstuffs = new ArrayList<>();
    }

    //endregion

    /**
     * Балансировка корзины
     */
    public void cardBalancing() {

        AtomicBoolean proteins = new AtomicBoolean(false);
        AtomicBoolean fats = new AtomicBoolean(false);
        AtomicBoolean carbohydrates = new AtomicBoolean(false);

        foodstuffs.stream().filter(food -> !proteins.get() && food.getProteins()).forEach(food -> proteins.set(true));

        foodstuffs.stream().filter(food -> !fats.get() && food.getFats()).forEach(food -> fats.set(true));

        foodstuffs.stream().filter(food -> !carbohydrates.get() && food.getCarbohydrates()).forEach(food -> carbohydrates.set(true));

        if (proteins.get() && fats.get() && carbohydrates.get()) {
            System.out.println("Корзина уже сбалансирована по БЖУ.");
            return;
        }

        for (var thing : market.getThings(clazz)) {
            if (!proteins.get() && thing.getProteins()) {
                proteins.set(true);
                foodstuffs.add(thing);
            } else if (!fats.get() && thing.getFats()) {
                fats.set(true);
                foodstuffs.add(thing);
            } else if (!carbohydrates.get() && thing.getCarbohydrates()) {
                carbohydrates.set(true);
                foodstuffs.add(thing);
            }
            if (proteins.get() && fats.get() && carbohydrates.get())
                break;
        }

        foodstuffs.forEach(thing -> market.getThings(clazz).add(thing));


            System.out.println("Корзина сбалансирована по БЖУ.");
    }



    public Collection<T> getFoodstuffs() {
        return foodstuffs;
    }

    /**
     * Распечатать список продуктов в корзине
     */
    public void printFoodstuffs()
    {
        /*int index = 1;
        for (var food : foodstuffs)
            System.out.printf("[%d] %s (Белки: %s Жиры: %s Углеводы: %s)\n", index++, food.getName(), food.getProteins() ? "Да" : "Нет",
                    food.getFats() ? "Да" : "Нет", food.getCarbohydrates() ? "Да" : "Нет");*/
        AtomicInteger index = new AtomicInteger(1);
        foodstuffs.forEach(food -> System.out.printf("[%d] %s (Белки: %s Жиры: %s Углеводы: %s)\n",
                index.getAndIncrement(), food.getName(),
                food.getProteins() ? "Да" : "Нет",
                food.getFats() ? "Да" : "Нет",
                food.getCarbohydrates() ? "Да" : "Нет"));

    }


}

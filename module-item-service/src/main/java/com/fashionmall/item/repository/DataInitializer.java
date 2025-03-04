package com.fashionmall.item.repository;

import com.fashionmall.item.entity.Category1;
import com.fashionmall.item.entity.Category2;
import com.fashionmall.item.entity.ItemColor;
import com.fashionmall.item.entity.ItemSize;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final ItemColorRepository itemColorRepository;
    private final ItemSizeRepository itemSizeRepository;
    private final Category1Repository category1Repository;
    private final Category2Repository category2Repository;

    @Override
    public void run(String... args) throws Exception {
        addItemColors();
        addItemSizes();
        addCategories();
    }

    private void addItemColors() {
        String[] colors = {"Black", "White", "Gray", "Blue", "Red", "Green", "Yellow", "Pink", "Purple", "Brown"};
        for (String color : colors) {
            if (!itemColorRepository.existsByColor(color)) {
                ItemColor itemColor = ItemColor.builder()
                        .color(color)
                        .build();
                itemColorRepository.save(itemColor);
            }
        }
    }

    private void addItemSizes() {
        String[] sizes = {"XS", "S", "M", "L", "XL", "XXL", "XXXL"};
        for (String size : sizes) {
            if (!itemSizeRepository.existsBySize(size)) {
                ItemSize itemSize = ItemSize.builder()
                        .size(size)
                        .build();
                itemSizeRepository.save(itemSize);
            }
        }
    }

    private void addCategories() {
        // Category1 초기화
        String[] category1Names = {"Top", "Outerwear", "Pants", "Skirt", "Dress"};
        for (String name : category1Names) {
            if (!category1Repository.existsByName(name)) {
                Category1 category1 = Category1.builder()
                        .name(name)
                        .build();
                category1Repository.save(category1);
            }
        }

        // Category2 초기화
        addCategory2("Shirt", 1);
        addCategory2("Sweater", 1);
        addCategory2("Hoodie", 1);
        addCategory2("Cardigan", 2);
        addCategory2("Jacket", 2);
        addCategory2("Trench Coat", 2);
        addCategory2("Shorts", 3);
        addCategory2("Slacks", 3);
        addCategory2("Straight Pants", 3);
        addCategory2("Mini Skirt", 4);
        addCategory2("Midi Skirt", 4);
        addCategory2("Long Skirt", 4);
        addCategory2("Mini Dress", 5);
        addCategory2("Midi Dress", 5);
        addCategory2("Long Dress", 5);
    }

    private void addCategory2(String name, long category1Id) {
        Category1 category1 = category1Repository.findById(category1Id)
                .orElseThrow(() -> new RuntimeException("Category1 not found with ID: " + category1Id));

        if (!category2Repository.existsByNameAndCategory1(name, category1)) {
            Category2 category2 = Category2.builder()
                    .category1(category1)
                    .name(name)
                    .build();
            category2Repository.save(category2);
        }
    }

}


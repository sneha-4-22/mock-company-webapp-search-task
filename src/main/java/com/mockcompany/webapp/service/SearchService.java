package com.mockcompany.webapp.service;

import com.mockcompany.webapp.data.ProductItemRepository;
import com.mockcompany.webapp.model.ProductItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class SearchService {

    private final ProductItemRepository productItemRepository;

    @Autowired
    public SearchService(ProductItemRepository productItemRepository) {
        this.productItemRepository = productItemRepository;
    }

    public Collection<ProductItem> searchProducts(String query) {
        Iterable<ProductItem> allItems = productItemRepository.findAll();
        List<ProductItem> itemList = new ArrayList<>();
        boolean isExactMatch = query.startsWith("\"") && query.endsWith("\"");
        String searchQuery = isExactMatch ? query.substring(1, query.length() - 1) : query.toLowerCase();

        for (ProductItem item : allItems) {
            String name = item.getName().toLowerCase();
            String description = item.getDescription().toLowerCase();

            boolean nameMatches = isExactMatch ? item.getName().equals(searchQuery) : name.contains(searchQuery);
            boolean descriptionMatches = isExactMatch ? item.getDescription().equals(searchQuery) : description.contains(searchQuery);

            if (nameMatches || descriptionMatches) {
                itemList.add(item);
            }
        }
        return itemList;
    }

    public Integer countProductsByQuery(String string) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'countProductsByQuery'");
    }
}

package com.itransition.coursework.category;

import com.itransition.coursework.util.ThymeleafResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import static com.itransition.coursework.util.Constants.*;

/**
 * Abdulqodir Ganiev 6/16/2022 10:12 PM
 */

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Page<Category> getAllCategories(int size, int page) {
        Pageable pageable = PageRequest.of(
                page - 1,
                size
        );
        return categoryRepository.findAll(pageable);
    }

    public ThymeleafResponse saveCategory(Long id, String name) {
        if (id != null)
            return editCategory(id, name);
        else
            return addNewCategory(name);
    }

    private ThymeleafResponse addNewCategory(String name) {
        if (categoryRepository.existsByName(name))
            return new ThymeleafResponse(false, CATEGORY_EXISTS);
        Category category = categoryRepository.save(new Category(name, true));
        log.info("created new category {}", category);
        return new ThymeleafResponse(true, SUCCESS_MESSAGE);
    }

    private ThymeleafResponse editCategory(Long id, String name) {
        try {
            Category category = categoryRepository.findById(id)
                    .orElseThrow(() -> new ResourceAccessException(CATEGORY_NOT_FOUND));
            category.setName(name);
            Category savedCat = categoryRepository.save(category);
            log.info("category edited {}", savedCat);
            return new ThymeleafResponse(true, SUCCESS_CHANGE);
        } catch (Exception e) {
            return new ThymeleafResponse(false, e.getMessage());
        }
    }

    public ThymeleafResponse deleteCategory(Long id) {
        try {
            Category category = categoryRepository.findById(id)
                    .orElseThrow(() -> new ResourceAccessException(CATEGORY_NOT_FOUND));
            categoryRepository.delete(category);
            log.info("category {} deleted", category);
            return new ThymeleafResponse(true, SUCCESS_DELETE);
        } catch (Exception e) {
            return new ThymeleafResponse(false, e.getMessage());
        }
    }

    public ThymeleafResponse disableCategory(Long id) {
        try {
            Category category = categoryRepository.findById(id)
                    .orElseThrow(() -> new ResourceAccessException(CATEGORY_NOT_FOUND));
            category.setIsEnabled(false);
            categoryRepository.save(category);
            log.info("category {} disabled", category);
            return new ThymeleafResponse(true, SUCCESS_CHANGE);
        } catch (Exception e) {
            return new ThymeleafResponse(false, e.getMessage());
        }
    }

    public ThymeleafResponse enableCategory(Long id) {
        try {
            Category category = categoryRepository.findById(id)
                    .orElseThrow(() -> new ResourceAccessException(CATEGORY_NOT_FOUND));
            category.setIsEnabled(true);
            categoryRepository.save(category);
            log.info("category {} enabled", category);
            return new ThymeleafResponse(true, SUCCESS_CHANGE);
        } catch (Exception e) {
            return new ThymeleafResponse(false, e.getMessage());
        }
    }
}

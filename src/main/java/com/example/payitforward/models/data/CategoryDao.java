package com.example.payitforward.models.data;

import com.example.payitforward.models.Category;
import com.example.payitforward.models.Opportunity;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Locale;

@Repository
@Transactional
public interface CategoryDao extends CrudRepository<Category, Integer> {
}
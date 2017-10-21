package com.example.payitforward.models.data;

import com.example.payitforward.models.Opportunity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface OpportunityDao extends CrudRepository<Opportunity, Integer> {
    List<Opportunity> findByNameContains (String name);
}

package com.example.payitforward.models.Data;

import com.example.payitforward.models.Opportunity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface OpportunityDao extends CrudRepository<Opportunity, Integer> {
}
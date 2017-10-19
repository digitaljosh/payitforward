package com.example.payitforward.Models.Data;

import com.example.payitforward.Models.Opportunity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface OpportunityDao extends CrudRepository<Opportunity, Integer> {
}

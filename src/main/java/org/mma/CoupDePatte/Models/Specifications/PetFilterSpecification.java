package org.mma.CoupDePatte.Models.Specifications;

import org.mma.CoupDePatte.Models.Entities.Pet;

import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;


import java.util.ArrayList;
import java.util.List;

public class PetFilterSpecification implements Specification<Pet> {
    // utilise specif SearchCriteria et SearchOperation

    private List <SearchCriteria> lstCriteres;

    public PetFilterSpecification(){
       this.lstCriteres = new ArrayList<>();
    }

    public void add(SearchCriteria critere) {lstCriteres.add(critere);}

    @Override
    public Predicate toPredicate(Root<Pet> root, CriteriaQuery<?> query, CriteriaBuilder builder){
       List<Predicate> lstPredicates= new ArrayList<>();
       for (SearchCriteria critere:lstCriteres){
           if(critere.getOperation().equals(SearchOperation.EQUAL)) {
                lstPredicates.add(
                        builder.equal(root.get(critere.getKey()),critere.getValue())
                );
           }else if (critere.getOperation().equals(SearchOperation.NOT_EQUAL)){
               lstPredicates.add(
                       builder.notEqual(root.get(critere.getKey()),critere.getValue())
               );
           }else if(critere.getOperation().equals(SearchOperation.GREATER_THAN)){
               lstPredicates.add(
                       builder.greaterThan(root.get(critere.getKey()),(Comparable) critere.getValue())
               );
           }else if(critere.getOperation().equals(SearchOperation.LESS_THAN)){
               lstPredicates.add(
                       builder.lessThan(root.get(critere.getKey()),(Comparable) critere.getValue())
               );
           }else if(critere.getOperation().equals(SearchOperation.GREATER_THAN_EQUAL)){
               lstPredicates.add(
                       builder.greaterThanOrEqualTo(root.get(critere.getKey()),(Comparable) critere.getValue())
               );
           }else if(critere.getOperation().equals(SearchOperation.LESS_THAN_EQUAL)){
               lstPredicates.add(
                       builder.lessThanOrEqualTo(root.get(critere.getKey()),(Comparable) critere.getValue())
               );
           }else if(critere.getOperation().equals(SearchOperation.CONTAIN)){
               lstPredicates.add(
                       builder.like(builder.lower(root.get(critere.getKey())),
                               "%"+critere.getValue().toString().toLowerCase()+"%")
               );
           }else if(critere.getOperation().equals(SearchOperation.MATCH_END)){
               lstPredicates.add(
                       builder.like(builder.lower(root.get(critere.getKey())),
                               "%"+critere.getValue().toString().toLowerCase())
               );
           }else if(critere.getOperation().equals(SearchOperation.MATCH_START)){
               lstPredicates.add(
                       builder.like(builder.lower(root.get(critere.getKey())),
                               critere.getValue().toString().toLowerCase()+"%")
               );
           }else if(critere.getOperation().equals(SearchOperation.IN)){
               lstPredicates.add(
                       builder.in(root.get(critere.getKey())).value(critere.getValue())
               );
           }else if(critere.getOperation().equals(SearchOperation.NOT_IN)){
               lstPredicates.add(
                       builder.not(root.get(critere.getKey()).in(critere.getValue()))
               );
           }
       }
       return builder.and(lstPredicates.toArray(new Predicate[0]));
    }
}

package com.shyam.gujarat_police.repositories.impl;

import com.shyam.gujarat_police.entities.Police;
import com.shyam.gujarat_police.repositories.BaseRepository;
import com.shyam.gujarat_police.repositories.PoliceBaseRepository;

import javax.persistence.Query;
import javax.persistence.Tuple;
import java.util.List;

public class PoliceBaseRepositoryImpl extends BaseRepository<Police> implements PoliceBaseRepository {

    @Override
    public boolean isPoliceExists(Police police) {
        String queryString = "select p from Police p where " +
                "p.buckleNumber = :buckleNumber";
        Query query = entityManager.createQuery(queryString, Tuple.class);
        query.setParameter("buckleNumber", police.getBuckleNumber());

        List<Tuple> tupleList = query.getResultList();
        return tupleList.size() > 0;
    }
}

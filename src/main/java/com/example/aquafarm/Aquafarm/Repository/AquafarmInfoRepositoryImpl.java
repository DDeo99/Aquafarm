package com.example.aquafarm.Aquafarm.Repository;

import com.example.aquafarm.Aquafarm.Domain.AquafarmInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Repository
public class AquafarmInfoRepositoryImpl implements AquafarmInfoRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public AquafarmInfoRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<AquafarmInfo> findFirstByOrderByAquafarmId() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<AquafarmInfo> query = cb.createQuery(AquafarmInfo.class);
        Root<AquafarmInfo> root = query.from(AquafarmInfo.class);
        query.select(root).orderBy(cb.asc(root.get("aquafarmId")));
        return entityManager.createQuery(query).setMaxResults(1).getResultList().stream().findFirst();
    }

    @Override
    public List<AquafarmInfo> findByAddress(String address) {
        return this.findByAddress(address);
    }

    @Override
    public List<AquafarmInfo> findByUserId(int userId) {
        return this.findByUserId(userId);
    }

    @Override
    public List<AquafarmInfo> findByAddressContaining(String keyword) {
        return this.findByAddressContaining(keyword);
    }

    @Override
    public List<AquafarmInfo> findByCustomCriteria(String criteria) {
        return entityManager.createQuery("SELECT a FROM AquafarmInfo a WHERE a.address = :criteria", AquafarmInfo.class)
                .setParameter("criteria", criteria)
                .getResultList();
    }

    @Override
    public List<AquafarmInfo> findAll() {
        return entityManager.createQuery("SELECT a FROM AquafarmInfo a", AquafarmInfo.class)
                .getResultList();
    }

    @Override
    public List<AquafarmInfo> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<AquafarmInfo> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<AquafarmInfo> findAllById(Iterable<Integer> integers) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Integer integer) {

    }

    @Override
    public <S extends AquafarmInfo> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<AquafarmInfo> findById(Integer integer) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Integer integer) {
        return false;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends AquafarmInfo> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends AquafarmInfo> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<AquafarmInfo> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Integer> integers) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public AquafarmInfo getOne(Integer integer) {
        return null;
    }

    @Override
    public AquafarmInfo getById(Integer integer) {
        return null;
    }

    @Override
    public AquafarmInfo getReferenceById(Integer integer) {
        return null;
    }

    @Override
    public <S extends AquafarmInfo> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends AquafarmInfo> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends AquafarmInfo> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends AquafarmInfo> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends AquafarmInfo> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends AquafarmInfo> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends AquafarmInfo, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public Optional<AquafarmInfo> findById(int aquafarmId){
        return Optional.ofNullable(entityManager.find(AquafarmInfo.class, aquafarmId));
    }
    //@Override
    //public AquafarmInfo findById(int aquafarmId) {
    //    return entityManager.find(AquafarmInfo.class, aquafarmId);
    //}

    @Override
    public AquafarmInfo save(AquafarmInfo aquafarmInfo) {
        return this.save(aquafarmInfo);
    }

    @Override
    public void delete(AquafarmInfo aquafarmInfo) {
        this.delete(aquafarmInfo);
    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> integers) {

    }

    @Override
    public void deleteAll(Iterable<? extends AquafarmInfo> entities) {

    }

    @Override
    public void deleteAll() {

    }

}

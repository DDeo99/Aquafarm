package com.example.aquafarm.Fish.Repository;

import com.example.aquafarm.Fish.Domain.FishInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class FishInfoRepositoryImpl implements FishInfoRepository{

    private final EntityManager entityManager;

    @Autowired
    public FishInfoRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public int countByFoodRecordBetween(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        Query query = entityManager.createQuery("SELECT COUNT(fi) FROM FishInfo fi WHERE fi.foodRecord BETWEEN :startDateTime AND :endDateTime");
        query.setParameter("startDateTime", startDateTime);
        query.setParameter("endDateTime", endDateTime);
        return ((Number) query.getSingleResult()).intValue();
    }

    @Override
    public List<FishInfo> findByFoodRecordBetween(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        Query query = entityManager.createQuery("SELECT fi FROM FishInfo fi WHERE fi.foodRecord BETWEEN :startDateTime AND :endDateTime");
        query.setParameter("startDateTime", startDateTime);
        query.setParameter("endDateTime", endDateTime);
        return query.getResultList();
    }
    @Override
    public List<FishInfo> findAll() {
        return null;
    }

    @Override
    public List<FishInfo> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<FishInfo> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<FishInfo> findAllById(Iterable<Integer> integers) {
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
    public void delete(FishInfo entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> integers) {

    }

    @Override
    public void deleteAll(Iterable<? extends FishInfo> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends FishInfo> S save(S entity) {
        return null;
    }

    @Override
    public <S extends FishInfo> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<FishInfo> findById(Integer integer) {
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
    public <S extends FishInfo> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends FishInfo> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<FishInfo> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Integer> integers) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public FishInfo getOne(Integer integer) {
        return null;
    }

    @Override
    public FishInfo getById(Integer integer) {
        return null;
    }

    @Override
    public FishInfo getReferenceById(Integer integer) {
        return null;
    }

    @Override
    public <S extends FishInfo> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends FishInfo> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends FishInfo> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends FishInfo> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends FishInfo> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends FishInfo> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends FishInfo, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

}

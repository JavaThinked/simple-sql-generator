package com.javathinked.reflection.repository;

import com.javathinked.reflection.model.client.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimpleCrudQueryBuilderTest {

    private static final String EXPECTED_SELECT_BY_ID_QUERY = "SELECT id,firstname,lastname,telephone,email,gender,address FROM client WHERE id=?";
    private static final String EXPECTED_SELECT_QUERY = "SELECT id,firstname,lastname,telephone,email,gender,address FROM client";
    private static final String EXPECTED_INSERT_QUERY = "INSERT INTO client VALUES id=?,firstname=?,lastname=?,telephone=?,email=?,gender=?,address=?";
    private static final String EXPECTED_UPDATE_QUERY = "UPDATE client SET firstname=?,lastname=?,telephone=?,email=?,gender=?,address=? WHERE id=?";
    private static final String EXPECTED_DELETE_QUERY = "DELETE FROM client WHERE id=?";

    private SimpleCrudQueryBuilder<Client> queryBuilder;

    @BeforeEach
    public void setUp() {
        queryBuilder = new SimpleCrudQueryBuilder<>(Client.class);
    }

    @Test
    void testBuildFindByIdQuery() {
        assertEquals(EXPECTED_SELECT_BY_ID_QUERY, queryBuilder.buildFindByIdQuery());
    }

    @Test
    void testBuildFindAllQuery() {
        assertEquals(EXPECTED_SELECT_QUERY, queryBuilder.buildFindAllQuery());
    }

    @Test
    void testBuildInsertQuery() {
        assertEquals(EXPECTED_INSERT_QUERY, queryBuilder.buildInsertQuery());
    }

    @Test
    void testBuildUpdateQuery() {
        assertEquals(EXPECTED_UPDATE_QUERY, queryBuilder.buildUpdateQuery());
    }

    @Test
    void testBuildDeleteQuery() {
        assertEquals(EXPECTED_DELETE_QUERY, queryBuilder.buildDeleteQuery());
    }
}
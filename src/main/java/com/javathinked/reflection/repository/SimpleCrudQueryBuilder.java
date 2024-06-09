package com.javathinked.reflection.repository;

import com.javathinked.reflection.repository.exception.TypeReflectionException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

public class SimpleCrudQueryBuilder<T> {

    private final Class<T> type;
    private StringBuilder stringBuilder;
    private String table;
    private String id;

    public SimpleCrudQueryBuilder(Class<T> type) {
        this.type = type;
        this.table = extractTableName();
    }

    public String buildFindByIdQuery() {
        var allFields = extractAllFieldsForSelect(type);
        return String.format("SELECT %s FROM %s WHERE %s=?", allFields, table, id);
    }
    public String buildFindAllQuery() {
        var allFields = extractAllFieldsForSelect(type);
        return String.format("SELECT %s FROM %s", allFields, table);
    }

    public String buildInsertQuery() {
        var allInsertFields = removeLastComma(extractAllFieldsForInsertOrUpdate(type));
        return String.format("INSERT INTO %s VALUES %s", table, allInsertFields);
    }

    public String buildUpdateQuery() {
        var allUpdateFields = removeLastComma(extractAllFieldsForInsertOrUpdate(type));
        // Remove the id=?, part
        var newAllUpdateFields = allUpdateFields.substring(5);
        return String.format("UPDATE %s SET %s WHERE id=?", table, newAllUpdateFields);
    }

    public String buildDeleteQuery() {
        return String.format("DELETE FROM %s WHERE id=?", table);
    }

    private String extractAllFieldsForSelect(Class<T> type) {
        stringBuilder = new StringBuilder();
        Field[] fields = extractFields(type);
        Stream.of(fields).forEach(field -> {
            try {
                if (field.getName().startsWith("FIELD")) {
                    if (field.getName().endsWith("ID")) {
                        id = (String) field.get(type.getDeclaredConstructor().newInstance());
                    }
                    stringBuilder.append(field.get(type.getDeclaredConstructor().newInstance())).append(",");
                }
                table = extractTableName();
            } catch (IllegalAccessException | InstantiationException | InvocationTargetException |
                     NoSuchMethodException exception) {
                throw new TypeReflectionException(exception);
            }
        });
        return removeLastComma(stringBuilder.toString());
    }

    private String extractAllFieldsForInsertOrUpdate(Class<T> type) {
        stringBuilder = new StringBuilder();
        Field[] fields = extractFields(type);
        Stream.of(fields).forEach(field -> {
            try {
                if (field.getName().startsWith("FIELD")) {
                    stringBuilder.append(field.get(type.getDeclaredConstructor().newInstance())).append("=?").append(",");
                }
            } catch (IllegalAccessException | InstantiationException | InvocationTargetException |
                     NoSuchMethodException exception) {
                throw new TypeReflectionException(exception);
            }
        });
        return stringBuilder.toString();
    }

    private String extractTableName() {
        Arrays.stream(type.getDeclaredFields()).filter(tableField -> tableField.getName().startsWith("TABLE"))
            .findFirst()
            .ifPresent(value -> {
                try {
                    table = (String) value.get(type.getDeclaredConstructor().newInstance());
                } catch (IllegalAccessException | InstantiationException | InvocationTargetException |
                         NoSuchMethodException exception) {
                    throw new TypeReflectionException(exception);
                }
            });
        return table;
    }

    private Field[] extractFields(Class<T> type) {
        Field[] fields;
        if (Objects.nonNull(type.getSuperclass())) {
            fields = type.getSuperclass().getDeclaredFields();
        } else {
            fields = type.getDeclaredFields();
        }
        return fields;
    }

    private String removeLastComma(String fields) {
        var trimmedFields = fields.trim();
        if (!trimmedFields.endsWith(",")) {
            return fields;
        }
        return trimmedFields.substring(0, trimmedFields.length() - 1);
    }
}

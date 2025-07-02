package com.example.demo.enums.converters;

import com.example.demo.enums.Category;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class CategoryConverter implements AttributeConverter<Category, String> {

    @Override
    public String convertToDatabaseColumn(Category category) {
        if(category == null){
            return null;
        }
        return category.getValue();
    }

    @Override
    public Category convertToEntityAttribute(String value) {
        if(value == null){
            return null;
        }
        return Stream.of(Category.values())
                //operador of consegue transformar qualquer lista de infromacoes em um streaming
                //com o streaming em maos, usamos o filter, que serve pra termos um filtro do que iremos obter
                //a expressao booleana pegamos a categoria do enumerador com o getvalue e usamos o equals para comparar com po valor q esta sendo
                //passado como parametro
                //de todos os valores retornados pelo filtro, estamos interessados em apenas um valor
                //logo, usamos o findFirst() para pegar apenas o primeiro elemento retornado
                //caso o valor nao seja encontrado, usamos o illegalargumentexception
                .filter(c -> c.getValue().equals(value))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
